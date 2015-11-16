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
		this.startConnection();
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

	
	public int insertBook(String bkName, String bkYear, String bkLocation,int bookType){ ///bookType = 1 : þiir kitabý. bookType = 2: þiirlerin sonradan birleþtirildiði kitaplar
		Statement stmt = null;
		Integer newRecordId = 0;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `book`(`Name`, `Year`, `Location`,`type`)"	
				+ "Values ('"+bkName+"','"+bkYear+"','"+bkLocation+"',"+bookType+")";
		
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
		
		return newRecordId;
	}
	
	public void updateBookName(String bkName, int bookId){
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
	}
	
	public Integer insertWord(String text, double wordStart, double wordFinish, Integer workLineId, boolean isBold, boolean isItalic, String font ){	
		Statement stmt = null;
		Integer newRecordId = 0;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `word`( `text`, `wordStart`, `wordFinish`, `workLineId`, `isBold`, `isItalic`, `font`)"+
				" VALUES ('"+text+"',"+wordStart+","+wordFinish+","+workLineId+","+isBold+","+isItalic+",'"+font+"')";
		
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
		return newRecordId;
	}
	
	public void insertWordCharacter(String characterText, double characterStart, double characterFinish, Integer wordId, boolean isBold, boolean isItalic, String font ){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `wordcharacter`( `characterText`, `characterStart`, `characterFinish`, `wordId`, `isBold`, `isItalic`, `font`)"+
		" VALUES ('"+characterText+"',"+characterStart+","+characterFinish+","+wordId+","+isBold+","+isItalic+",'"+font+"')";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int insertWork(String Name, String Year, int BookId, String LocationOfComp, Integer pageNum, String Title, String font ){
		Statement stmt = null;
		Integer newRecordId = 0;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `work`( `Name`, `Year`, `BookId`, `LocationOfComp`, `pageNum`, `Title`)"+
		" VALUES ('"+Name+"','"+Year+"',"+BookId+",'"+LocationOfComp+"',"+pageNum+",'"+Title+"')";
		
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
		return newRecordId;
	}
	
	public void updateWork(String Name, Integer workId){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "UPDATE work SET Name = CONCAT(Name, ' "+Name+"') WHERE WorkId = "+workId+"";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String nextWorkName(Integer workId){
		Statement stmt = null;
		ResultSet rs = null;
		String workName = "";
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "select * from work where WorkId = "+(workId+1)+"";
		
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			workName = rs.getString("Name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workName;
	}

	
	public Integer insertWorkline(String line, double lineStart, double lineFinish, Integer workId){
		Statement stmt = null;
		Integer newRecordId = 0;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "INSERT INTO `workline`( `line`, `lineStart`, `lineFinish`, `workId`)"+
		" VALUES ('"+line+"',"+lineStart+","+lineFinish+","+workId+")";
		
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
		return newRecordId;
	}
	
	public ResultSet  getAllWorks(int start, int end){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "select a.*,b.pageNum as finishPage, b.Name as BookName from work a, work b, book k where b.WorkId = a.workId +1 and a.BookId = k.BookId and a.workId <="
							+end+" and a.workId >= "+start+" ";
		
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
