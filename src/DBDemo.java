import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This class demonstrates how to connect to MySQL and run some basic commands.
 * 
 * In order to use this, you have to download the Connector/J driver and add its
 * .jar file to your build path. You can find it here:
 * 
 * http://dev.mysql.com/downloads/connector/j/
 * 
 * You will see the following exception if it's not in your class path:
 * 
 * java.sql.SQLException: No suitable driver found for
 * jdbc:mysql://localhost:3306/
 * 
 * To add it to your class path: 1. Right click on your project 2. Go to Build
 * Path -> Add External Archives... 3. Select the file
 * mysql-connector-java-5.1.24-bin.jar NOTE: If you have a different version of
 * the .jar file, the name may be a little different.
 * 
 * The user name and password are both "root", which should be correct if you
 * followed the advice in the MySQL tutorial. If you want to use different
 * credentials, you can change them below.
 * 
 * You will get the following exception if the credentials are wrong:
 * 
 * java.sql.SQLException: Access denied for user 'userName'@'localhost' (using
 * password: YES)
 * 
 * You will instead get the following exception if MySQL isn't installed, isn't
 * running, or if your serverName or portNumber are wrong:
 * 
 * java.net.ConnectException: Connection refused
 */
public class DBDemo {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/dbnazýmhikmetsiirleri";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	
	
	public void startConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection(){
		if (conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	
	public void insertBook(String bkName, String bkYear, String bkLocation){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO book"
				+ "Values ('','"+bkName+"','"+bkYear+"','"+bkLocation+"')";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
