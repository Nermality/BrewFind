package coldcoffee.brewfind.api.Controllers;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.bson.types.ObjectId;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import coldcoffee.brewfind.api.Objects.BrewFindQuery;
import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Services.UserService;

// Controller to handle user...stuff
// Authentication, token handling, deleting, updating, etc. 

@Component
@Path("/user")
public class UserController {

	// UserService used for all database interactions
	@Autowired
	private UserService userService;
	
	private Gson gson = new Gson();
	
	// Defs for salt/hash functionality
	private final static int PW_ITERS = 500;
	private final static int SALT_SIZE = 24;
	private final static int HASH_SIZE = 24;
	private final static String ALGORITHM = "PBKDF2WithHmacSHA1";

	/**
	 * Authentication endpoint
	 * @param uname - header parameter containing username
	 * @param pass 	- header parameter containing password
	 * @return - if successful, a BrewFindToken; otherwise errors. 
	 */
	@Path("/auth")
	@Produces("application/json")
	@GET
	public BrewFindResponse auth(
			@NotNull @HeaderParam("uname") 	String uname,
			@NotNull @HeaderParam("pass")	String pass) {
				
		// Sanitize data
		String newu = sanitize(uname);
		
		// Test if user is in db
		User u = userService.findUser(newu);
		if(u == null) {
			return new BrewFindResponse(2, "User " + newu + " not found in DB");
		}
		
		// Else, authenticate
		try {
			if(!validatePass(pass, u.getU_pass())) {
				return new BrewFindResponse(3, "Invalid login information");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return new BrewFindResponse(6, "Sever-side authentication failure");
		}
		
		// Check the existing token...?
		
		// Create token
		BrewFindToken token = new BrewFindToken(u.getU_access(), Base64.encodeAsString(newu.getBytes()));
		
		// Save token
		if(userService.saveNewToken(u, token) == null) {
			// some issue happened
			System.out.println("Didn't like saving that token...");
		}
		
		// Return token
		return new BrewFindResponse(0, "OK", token);
	}
	
	/**
	 * Returns user-safe information on the user
	 * @param body - requires a legitimate user token to do anything
	 * @return - the user's information
	 */
	@Produces("application/json")
	@GET
	public BrewFindResponse getUser(String body) {
		
		BrewFindToken token = gson.fromJson(body, BrewFindToken.class);
		
		// Check to see if user in in db
		String uname = Base64.decodeAsString(token.token);
		User u = userService.findUser(uname);
		
		// If not, error
		if(u == null) {
			return new BrewFindResponse(2, "User " + uname + " was not found in database");
		}
		
		// Else, return user information
		User toRet = sanitizeUser(u);
		
		return new BrewFindResponse(0, "OK", toRet);
	}
	
	/**
	 * Updates the user's information
	 * The only fields required (other than those to change) are username and access
	 * Username, id, and token cannot be changed
	 * @param body - the user object containing updated information
	 * @return user-safe information on the new user created.
	 */
	@Produces("application/json")
	@POST
	public BrewFindResponse updateUser(String body) {
		
		// TODO: Create logic that verifies a brewery user as legitimate or not!!
		
		// Convert to query
		BrewFindQuery query = gson.fromJson(body, BrewFindQuery.class);
		
		// No token - fail
		if(query.getToken() == null) {
			return new BrewFindResponse(4, "No token found, authorization failed");
		}
		
		// Check token
		// If token check passes, user exists. 
		if(!userService.checkToken(query.getToken())) {
			return new BrewFindResponse(5, "Invalid token, authorization failed");
		}
		
		String uname = Base64.decodeAsString(query.getToken().token);
		
		// Check for updates
		User newU = (User) query.getQList().get(0);
		User oldU = userService.findUser(uname);
		User toIns = safeUpdate(oldU, newU);
		
		// Null out for GC
		newU = null;
		oldU = null;
		
		// Sanitize data before insertion...?
		
		// Update information in database
		User dbU = userService.saveUser(toIns);
		
		// User safe. No passwords, no tokens, no ids. 
		dbU = sanitizeUser(dbU);
		
		// Return new user object
		return new BrewFindResponse(0, "OK", dbU);
	}
	
	/**
	 * Removes a user from our system. 
	 * @param body - BrewFindToken object
	 * @return - success is just an {status:0, message:"OK"}
	 */
	@Produces("application/json")
	@DELETE
	public BrewFindResponse deleteUser(String body) {
		
		BrewFindToken tok = gson.fromJson(body, BrewFindToken.class);
		String uname = Base64.decodeAsString(tok.token);
		
		// Check if user is in db
		User u = userService.findUser(uname);
		if(u == null) {
			return new BrewFindResponse(2, "User " + uname + " doesn't exist in our system");
		}
		
		// Else, remove information from user db
		userService.deleteUser(u);
		
		// TODO: Implement un-setting a brewery's user if the user is associated with a brewery
		return new BrewFindResponse(0, "OK");
	}
	
	/**
	 * Creates a new user and incorporates it into our system.
	 * This is where the salt creation and hashing takes place.
	 * @param body - The new user object to be created
	 * @return - on success: user-safe information on the user just created. 
	 */
	@Produces("application/json")
	@PUT
	public BrewFindResponse addUser(String body) {

		User user = gson.fromJson(body, User.class);
		
		// Make sure the user isn't in the database
		if(userService.findUser(user.u_name) != null) {
			return new BrewFindResponse(1, "That username already exists in our system");
		}
				
		// Sanitize data
		// Mash up password	
		try {
			String newPass = createHash(user.u_pass);
			user.setU_pass(newPass);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println(e.getMessage());
			return new BrewFindResponse(6, "Server-side authentication issue");
		}
	
		if(user.u_access == null) {
			user.setU_access(1);
		}
		
		// Insert in db
		User newU = sanitizeUser(userService.saveUser(user));
		  
		// Return created object
		return new BrewFindResponse(0, "OK", newU);
	}
	
	/**
	 * Copies information from an 'update' user object to their original object
	 * This is to maintain ids, tokens, salt/hash, etc. 
	 * @param oldU - the user's original user object
	 * @param newU - user object containing updates
	 * @return - fully updated, comprehensive user object
	 */
	public User safeUpdate(User oldU, User newU) {
		
		if(newU.getU_firstName() != null) {
			oldU.setU_firstName(newU.getU_firstName());
		}
		if(newU.getU_lastName() != null) {
			oldU.setU_lastName(newU.getU_lastName());
		}
		if(newU.getU_pass() != null) {
			try {
				String newPass = createHash(newU.getU_pass());
				oldU.setU_pass(newPass);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return oldU;
	}
	
	/**
	 * An attempt at sanitizing data for MongoDB...
	 * @param s - query string to sanitize
	 * @return - string with all malice removed
	 */
	public String sanitize(String s) {
		
		s = s.trim();
		if(s.startsWith("$")) {
			s = s.substring(1);
		}
		
		return s;
	}
	
	/**
	 * Removes all sensitive data from a user object
	 * Used when user objects are being returned to the user
	 * @param u - user object to sanitize
	 * @return - sanitized object
	 */
	public User sanitizeUser(User u) {
		
		u.setU_pass(null);
		if(u.u_id != null) {
			u.u_id = null;
		}
		u.setU_curToken(null);
		
		return u;
	}
	
	/**
	 * Function to create a 'u_pass' field for the db
	 * @param pass - user's original password
	 * @return - hashed pw with salt and #iterations in string
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String createHash(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		char[] pwArr = pass.toCharArray();
		
		SecureRandom rando = new SecureRandom();
		byte[] salt = new byte[SALT_SIZE];
		rando.nextBytes(salt);
		
		byte[] hash = secretStuff(pwArr, salt, PW_ITERS, HASH_SIZE);

		return PW_ITERS + ":" + toHex(salt) + ":" + toHex(hash);
	}
	
	/**
	 * Verifies password attempt with correct hash
	 * @param attString - password attempt
	 * @param correct - correct hash
	 * @return - true if authorized, false otherwise
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static boolean validatePass(String attString, String correct) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		char[] attempt = attString.toCharArray();
		
		String[] params = correct.split(":");
		int iters = Integer.parseInt(params[0]);
		byte[] salt = fromHex(params[1]);
		byte[] hash = fromHex(params[2]);
		
		byte[] testHash = secretStuff(attempt, salt, iters, HASH_SIZE);
		
		return slowEquals(hash, testHash);
	}
	
	/**
	 * Actually generates the hash for the db
	 * @param pass - char[] of initial password
	 * @param salt - byte[] of newly created salt
	 * @param iterations - number of iterations
	 * @param bytes - size of the hash
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] secretStuff(char[] pass, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		PBEKeySpec spec = new PBEKeySpec(pass, salt, iterations, bytes);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		return skf.generateSecret(spec).getEncoded();	
	}
	
	/**
	 * Comparison of byte arrays
	 * @param a
	 * @param b
	 * @return
	 */
	 private static boolean slowEquals(byte[] a, byte[] b) {
	        int diff = a.length ^ b.length;
	        for(int i = 0; i < a.length && i < b.length; i++)
	            diff |= a[i] ^ b[i];
	        return diff == 0;
	 }
	 
	 /**
	  * Converts from a hex string to a byte[]
	  * @param hex
	  * @return
	  */
	 private static byte[] fromHex(String hex)
	 {
	    byte[] binary = new byte[hex.length() / 2];
	    for(int i = 0; i < binary.length; i++)
	    {
	        binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
	    }
	    return binary;
	 }

	 /**
	  * Converts from a byte[] to a hex string
	  * @param array
	  * @return
	  */
	 private static String toHex(byte[] array)
	 {
	     BigInteger bi = new BigInteger(1, array);
	     String hex = bi.toString(16);
	     int paddingLength = (array.length * 2) - hex.length();
	     if(paddingLength > 0)
	         return String.format("%0" + paddingLength + "d", 0) + hex;
	     else
	         return hex;
	 }
	 
	
	
}
