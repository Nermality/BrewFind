package coldcoffee.brewfind.api.Database;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DatabaseFactory {

	private static DB db;
	private static MongoClient mongo = null;
	
	private static String server = "localhost";
	private static String dbname = "brewFind";
	
	public static DB init() {
		try {
			mongo = new MongoClient(server, 27017);
		} catch (UnknownHostException e) {
			System.out.println("Error!");
		}
		
		db = mongo.getDB(dbname);
		return db;
	}
	
	public static DBCollection getConn(String conn) {
		return db.getCollection(conn);
	}
}
