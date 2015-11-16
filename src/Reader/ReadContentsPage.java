package Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import Model.DBDemo;


/**
 * psedocode
 * if line does not contain "BÜTÜN ÞÝÝRLERÝ" or "ÝÇÝNDEKÝLER"
	if line contains •
		then its a new poetry
	else
		if all characters are upper
			if font is bold
				if previous is Book's book
					then continuing book's book
				else
					books' book
			else 
				if previous is book
					then continuing book
				else
					if same identiation with previous
						then countueing poetry 
					else
						its a new book
		else
			if font is bold
				if previous is Book's book
					then continuing book's book
				else
					books' book
			else if font is same as previous
			 	if previous is poetry
					then this is continuing poetry name with more then one line
				else if preivous is a work job
					then a continuing work job
			else
				this is a work job like "Benerci Kendini Niçin Öldürdü?"
 * @author OnurM
 *
 */

public class ReadContentsPage extends PDFTextStripper{

	//private boolean isPreviousLineIndex = false; //eðer line • içeriyorsa true olacak
	private List<String> previousLineFont;
	private List<String> currentLineFont;
	private int previousType = 5; // 0: book's book, 1: book, 2:poetry, 3: transWork
	
	private int lastInsertedBookId = 0;
	private int lastInsertedPoetryId = 0;
	
	private float prevIndentationLevel= 0;
	private float currentIndentationLevel=0;
	
	
	private ReadFuncs rf;
	
	public ReadContentsPage() throws IOException {
		super("UTF-8");
		rf = new ReadFuncs();
		
	}
	
	@Override
	public void writeString(String text, List<TextPosition> line) throws IOException{
		DBDemo db = new DBDemo();
		currentLineFont = new ArrayList<String>();
		getFonts(line);
		
		
		String lineString = rf.convertListToString(line);
		currentIndentationLevel = rf.getIdentation(line,lineString);
		
		if(!lineString.contains("BÜTÜN ÞÝÝRLERÝ") && !lineString.contains("Ýçindekiler") && !lineString.contains("ÝÇÝNDEKÝLER") && lineString.trim().length()>0){
			if(lineString.contains("•")){
				System.out.println("new Poetry: "+lineString);
				previousType = 2;
				
				lastInsertedPoetryId = db.insertWork(lineString.substring(lineString.indexOf("•")+1, lineString.length()).trim(),"", lastInsertedBookId, "", Integer.parseInt(lineString.substring(0,lineString.indexOf("•")).trim()), "", "" );
				
			}else{
				if(rf.isUppercase(lineString)){
					if(rf.isBold(line)){
						if(previousType == 0){
							System.out.println("continuing Book's book: "+lineString);
							db.updateBookName(lineString.trim(), lastInsertedBookId);
						}else{
							System.out.println("Book's book: "+lineString);
							lastInsertedBookId = db.insertBook(lineString.trim(), "", "",2);
						}
						previousType = 0;
					}else{
						if(previousType == 1){
							System.out.println("continuing new Book : "+lineString);
							db.updateBookName(lineString.trim(), lastInsertedBookId);
						}else{
							/** bu duruma mecbur kaldým **/
							if(lineString.trim().equals("(1)") || lineString.trim().equals("(2)")){//(currentIndentationLevel-prevIndentationLevel)<0.001 && previousType == 2){
								System.out.println("continuing poetry " + lineString);//continouing poetry name with more then one line:
								previousType = 2;
								
								db.updateWork(lineString.trim(), lastInsertedPoetryId);
							}else{
								System.out.println("new Book : "+lineString);
								
								lastInsertedBookId = db.insertBook(lineString.trim(), "", "",1);
							}
							
							
						}
						previousType = 1;
					}
				}else{
					if(rf.isBold(line)){
						if(previousType == 0){
							System.out.println("continuing Book's book: "+lineString);
							db.updateBookName(lineString.trim(), lastInsertedBookId);
						}else{
							System.out.println("Book's book: "+lineString);
							lastInsertedBookId = db.insertBook(lineString.trim(), "", "",2);
						}
						previousType = 0;
					}else if(isFontSameAsPrevLine()){
						if(previousType == 3){
							System.out.println("continuing is a work job like " + lineString);//continouing poetry name with more then one line:
							previousType = 3;
						}else if(previousType == 2){
							System.out.println("continuing poetry " + lineString);//continouing poetry name with more then one line:
							previousType = 2;
							
							db.updateWork(lineString.trim(), lastInsertedPoetryId);
						}
					}else{
						System.out.println(" is a work job like " + lineString);
						previousType = 3;
					}
				}
			}
		}
		
		db.closeConnection();
		previousLineFont = currentLineFont; // bir sonraki line a geçmeden önce þu anki line font u erski line fontun eþit olur
		prevIndentationLevel = currentIndentationLevel;
	}
	
	

	private void getFonts(List<TextPosition> line){
		String prevBaseFont = "";
		for (TextPosition position : line)
        {
            String baseFont = position.getFont().getBaseFont();
            
            if (baseFont != null && !baseFont.equals(prevBaseFont))
            {
            	currentLineFont.add(baseFont);
                prevBaseFont = baseFont;
            }
        }
	}
	

	/***
	 * to compare whether previous line font is same with current ones'
	 * @return
	 */
	private boolean isFontSameAsPrevLine(){
		for(int i = 0; i<previousLineFont.size(); i++){
			for(int l = 0 ; l<currentLineFont.size(); l++){
				if(previousLineFont.get(i).toLowerCase().equals(currentLineFont.get(l).toLowerCase())){
					return true;
				}
			}
		}
		return false;
	}
}
