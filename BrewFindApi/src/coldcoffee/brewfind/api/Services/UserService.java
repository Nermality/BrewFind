package coldcoffee.brewfind.api.Services;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< 36a84c6f7bfd4768f2492694571c60a5460683c5
=======
import coldcoffee.brewfind.api.Objects.BrewFindQuery;
>>>>>>> 6e03dfba72568b674217e36dfce4102e2703ceda
import coldcoffee.brewfind.api.Objects.BrewFindResponse;
import coldcoffee.brewfind.api.Objects.BrewFindToken;
import coldcoffee.brewfind.api.Objects.User;
import coldcoffee.brewfind.api.Repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	
	// Defs for salt/hash functionality
	private final static int PW_ITERS = 500;
	private final static int SALT_SIZE = 24;
	private final static int HASH_SIZE = 24;
	private final static String ALGORITHM = "PBKDF2WithHmacSHA1";
	
	// BASE REPOSITORY FUNCTIONS
	// Find, save, delete
	
	/**
	 * Retrieves a user from the database
	 * @param uname - username of the user to retrieve
	 * @return - user matching the username
	 */
	public User findUser(String uname) {
		return userRepository.searchByUname(uname);
	}
	
	/**
	 * Saves a new token to a user object
	 * @param user - User getting the token
	 * @param tok - Token to associate with the user
	 * @return - The user object saved to DB
	 */
	public User saveNewToken(User user, BrewFindToken tok) {
		
		user.setU_curToken(tok);
		return userRepository.save(user);
	
	}
	
	/**
	 * Saves a user to the database
	 * @param u - User to save
	 * @return - User saved to DB
	 */
	public User saveUser(User u) {
		userRepository.save(u);
		return findUser(u.u_name);
	}
	
	/**
	 * Removes a user from the database
	 * Currently doesn't function...stupid MongoRepository
	 * @param u - User to delete
	 */
	public void deleteUser(User u) {
		userRepository.delete(u);
	}
	
	// CONTROLLER FUNCTIONS
	
	public BrewFindResponse getUserFromToken(BrewFindToken token) {
		
		// Check to see if user in in db
		String uname = Base64.decodeAsString(token.token);
		User u = findUser(uname);
				
		// If not, error
		if(u == null) {
			return new BrewFindResponse(2, "User " + uname + " was not found in database");
		}
				
		// Else, return user information
		User toRet = sanitizeUser(u);
				
		return new BrewFindResponse(0, "OK", toRet);
	}
	
	public BrewFindResponse updateUserFromQuery(BrewFindQuery query) {
		
		// Check token
		// If token check passes, user exists. 
		if(!checkToken(query.getToken())) {
			return new BrewFindResponse(5, "Invalid token, authorization failed");
		}
				
		String uname = Base64.decodeAsString(query.getToken().token);
				
		if(query.getQList().isEmpty()) {
			return new BrewFindResponse(9, "No content found");
		}
				
		// Check for updates
		User newU = (User) query.getQList().get(0);
		User oldU = findUser(uname);
		User toIns = safeUpdate(oldU, newU);
				
		// Null out for GC
		newU = null;
		oldU = null;
				
		// Sanitize data before insertion...?
				
		// Update information in database
		User dbU = saveUser(toIns);
				
		// User safe. No passwords, no tokens, no ids. 
		dbU = sanitizeUser(dbU);
				
		// Return new user object
		return new BrewFindResponse(0, "OK", dbU);
	}
	
	public BrewFindResponse deleteUserFromToken(BrewFindToken tok) {
		
		// Check token
		// If token check passes, user exists. 
		if(!checkToken(tok)) {
			return new BrewFindResponse(5, "Invalid token, authorization failed");
		}
				
		String uname = Base64.decodeAsString(tok.token);
				
		// Check if user is in db
		User u = findUser(uname);
		if(u == null) {
			return new BrewFindResponse(2, "User " + uname + " doesn't exist in our system");
		}
				
		// Else, remove information from user db
		deleteUser(u);
				
		// TODO: Implement un-setting a brewery's user if the user is associated with a brewery
		return new BrewFindResponse(0, "OK");
	}
	
	public BrewFindResponse createUser(User user) {
		
		if(user.u_access == null) {
			user.setU_access(1);
		} /*
			else if(user.u_access > 1) {
			if(query.token == null) {
				return new BrewFindResponse(4, "No token found, authorization failed");
			}
			if(!userService.checkToken(query.token)) {
				return new BrewFindResponse(5, "Invalid token, authorization failed");
			}
			if(user.getU_access() == 2) {
				// TODO: Brewer validation
			} else if(user.getU_access() > 2) {
				// TODO: Admin validation
			}
		}
		*/
		
		// Make sure the user isn't in the database
		if(findUser(user.u_name) != null) {
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
		
		// Insert in db
		User newU = sanitizeUser(saveUser(user));
		  
		// Return created object
		return new BrewFindResponse(0, "OK", newU);
	}
	
	public BrewFindResponse authUser(String uname, String pass, User u) {
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
		BrewFindToken token = new BrewFindToken(u.getU_access(), Base64.encodeAsString(uname.getBytes()));
				
		// Save token
		if(saveNewToken(u, token) == null) {
			return new BrewFindResponse(12, "Refreshing token failed");
		}
				
		// Return token
		return new BrewFindResponse(0, "OK", token);
	}
	
	// HELPER FUNCTIONS
	
	/**
	 * Verifies that a token is legitimate
	 * @param tok - Token to check
	 * @return true if valid, else false. 
	 */
	public boolean checkToken(BrewFindToken tok) {
		
		// The 'token' field on BrewFindToken contains the username base64 encoded
		String uname = Base64.decodeAsString(tok.token);
		
		// Make sure the user exists
		User u = findUser(uname);
		if(u == null) {
			return false;
		}
		
		// Make sure the user has a token associated with it
		BrewFindToken uTok = u.getU_curToken();	
		if(uTok == null) {
			return false;
		}
		
		// Check that the token matches
		if((tok.access == uTok.access) && (tok.stamp == uTok.stamp) && (tok.token.equals(uTok.token))) {
			return true;
		}
		
		return false;
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
	
	// HASHING, SALTING, AND VERIFICATION FUNCTIONS
	
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
<<<<<<< 36a84c6f7bfd4768f2492694571c60a5460683c5
	/**
	 * Updates user token with new timestamp
	 * @param tok token with new timestamp
	 * @return 
	 */
	public BrewFindResponse updateToken(BrewFindToken tok)
	{
		// The 'token' field on BrewFindToken contains the username base64 encoded
		String uname = Base64.decodeAsString(tok.token);
				
		// Make sure the user exists
		User u = findUser(uname);
		if(u == null) {
		return new BrewFindResponse(2,"User not found, token update failed");
		}
		u.setU_curToken(tok);
		userRepository.save(u);
		return new BrewFindResponse(0, "ok");
	}
=======
	
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
	
>>>>>>> 6e03dfba72568b674217e36dfce4102e2703ceda
	
}
