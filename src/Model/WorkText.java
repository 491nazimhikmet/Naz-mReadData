package Model;

public class WorkText extends Work{
	private Integer workTextId;
	private String text;
	private Integer numLines;
	private Integer workId;
	
	/**
	 * constructor
	 */
	public WorkText(){
		
	}
	
	/**
	 * Compare two WorkText
	 * @param other WorkText
	 * @return equality
	 */
	public boolean isEqual(WorkText other){
		if(this.getWorkTextId() == other.getWorkTextId()){
			return true;
		}
		return false;
	}
	
	public Integer getWorkTextId() {
		return workTextId;
	}
	public void setWorkTextId(Integer workTextId) {
		this.workTextId = workTextId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getNumLines() {
		return numLines;
	}
	public void setNumLines(Integer numLines) {
		this.numLines = numLines;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	
	
}
