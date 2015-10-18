package Model;

public class Work{
	private Integer workId;
	private String name;
	private String year;
	private String bookId;
	private String locationOfComp;
	private String length;
	private String title;
	
	/**
	 * constructor
	 */
	public Work(){
		
	}
	
	/**
	 * Compare two work
	 * @param other work
	 * @return equality
	 */
	public boolean isEqual(Work other){
		if(this.getWorkId() == other.getWorkId()){
			return true;
		}
		return false;
	}
	
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
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
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getLocationOfComp() {
		return locationOfComp;
	}
	public void setLocationOfComp(String locationOfComp) {
		this.locationOfComp = locationOfComp;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	

}
