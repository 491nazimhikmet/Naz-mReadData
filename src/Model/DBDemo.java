package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBDemo {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/db_nazim_hikmet?useUnicode=true&characterEncoding=utf8";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	
	public DBDemo(){
		
	}
	
	
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

	
	public int insertBook(String bkName, String bkYear, String bkLocation){
		this.startConnection();
		Statement stmt = null;
		Integer newRecordId = 0;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `book`(`Name`, `Year`, `Location`)"	
				+ "Values ('"+bkName+"','"+bkYear+"','"+bkLocation+"')";
		
		try {
			newRecordId = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()){
	        	newRecordId=rs.getInt(1);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.closeConnection();
		return newRecordId;
	}
	
	public void updateBookName(String bkName, int bookId){
		this.startConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "UPDATE BOOK SET Name = CONCAT(Name, ' "+bkName+"') WHERE BookId = "+bookId+"";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.startConnection();
	}
	
	
	
	public void insertWord(String text, double wordStart, double wordFinish, Integer workLineId, boolean isBold, boolean isItalic, String font ){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `word`(`wordId`, `text`, `workStart`, `workFinish`, `workLineId`, `isBold`, `isItalic`, `font`)"+
		" VALUES ('',"+text+","+wordStart+","+wordFinish+","+workLineId+","+isBold+","+isItalic+","+font+")";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertWordCharacter(String characterText, double characterStart, double characterFinish, Integer wordId, boolean isBold, boolean isItalic, String font ){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `wordcharacter`(`characterId`, `characterText`, `characterStart`, `characterFinish`, `wordId`, `isBold`, `isItalic`, `font`)"+
		" VALUES ('',"+characterText+","+characterStart+","+characterFinish+","+wordId+","+isBold+","+isItalic+","+font+")";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertWork(String Name, String Year, int BookId, String LocationOfComp, Integer length, String Title, String font ){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `work`(`WorkId`, `Name`, `Year`, `BookId`, `LocationOfComp`, `length`, `Title`)"+
		" VALUES ('',"+Name+","+Year+","+BookId+","+LocationOfComp+","+length+","+Title+")";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertWorkline(String line, double lineStart, double lineFinish, Integer workTextId){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `workline`(`lineId`, `line`, `lineStart`, `lineFinish`, `workTextId`)"+
		" VALUES ('',"+line+","+lineStart+","+lineFinish+","+workTextId+")";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertWorkText(String Text, Integer numLines, Integer WorkId){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `worktext`(`WorkTextId`, `Text`, `numLines`, `WorkId`)"+
		" VALUES ('',"+Text+","+numLines+","+WorkId+")";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
