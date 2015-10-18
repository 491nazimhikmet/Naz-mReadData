package Model;

public class Book {
	private Integer bookId;
	private String name;
	private String year;
	private String location;
	
	/**
	 *  Constructor
	 */
	public Book(){
		
	}
	
	/**
	 * Compare two book
	 * @param other book
	 * @return equality
	 */
	public boolean isEqual(Book other){
		if(this.getBookId() == other.getBookId()){
			return true;
		}
		return false;
	}
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
