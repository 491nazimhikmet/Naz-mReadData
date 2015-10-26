package Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import Model.DBDemo;

public class ReadContentsPage extends PDFTextStripper{

	//private boolean isPreviousLineIndex = false; //eðer line • içeriyorsa true olacak
	private List<String> previousLineFont;
	private List<String> currentLineFont;
	private int previousType = 5; // 0: book's book, 1: book, 2:poetry, 3: transWork
	
	private int lastInsertedBookId = 0;
	
	
	public ReadContentsPage() throws IOException {
		super("UTF-8");
		
	}
	
	@Override
	public void writeString(String text, List<TextPosition> line) throws IOException{
		DBDemo db = new DBDemo();
		currentLineFont = new ArrayList<String>();
		getFonts(line);
		
		String lineString = convertListToString(line);
		
		if(!lineString.contains("BÜTÜN ÞÝÝRLERÝ") && !lineString.contains("Ýçindekiler") && !lineString.contains("ÝÇÝNDEKÝLER") && lineString.trim().length()>0){
			if(lineString.contains("•")){
				System.out.println("new Poetry: "+lineString);
				previousType = 2;
			}else{
				if(isUppercase(lineString)){
					if(isBold(line)){
						if(previousType == 0){
							System.out.println("continuing Book's book: "+lineString);
						}else{
							System.out.println("Book's book: "+lineString);
						}
						previousType = 0;
					}else{
						if(previousType == 1){
							System.out.println("continuing new Book : "+lineString);
							db.updateBookName(lineString.trim(), lastInsertedBookId);
						}else{
							System.out.println("new Book : "+lineString);
							
							lastInsertedBookId = db.insertBook(lineString.trim(), "", "");
							System.out.println("last ýnserted: "+lastInsertedBookId);
						}
						previousType = 1;
					}
				}else{
					if(isBold(line)){
						if(previousType == 0){
							System.out.println("continuing Book's book: "+lineString);
						}else{
							System.out.println("Book's book: "+lineString);
						}
						previousType = 0;
					}else if(isFontSameAsPrevLine()){
						if(previousType == 3){
							System.out.println("continuing is a work job like " + lineString);//continouing poetry name with more then one line:
							previousType = 3;
						}else if(previousType == 2){
							System.out.println("continuing poetry " + lineString);//continouing poetry name with more then one line:
							previousType = 2;
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
		
	}
	
	private String convertToCorrectForm(TextPosition text){
		String positionText =text.getCharacter();
		 if(text.getCharacter().length()>1){
			if(text.getCharacter().charAt(0) == 'f' && text.getCharacter().charAt(1) == 'l' ){
				positionText = "þ";
			}else if(text.getCharacter().charAt(0) == 'f' && text.getCharacter().charAt(1) == 'i' ){
				positionText = "Þ";
			}else{
				positionText = text.getCharacter().charAt(0)+""; //e- gibi durumlar için
			}
		}else if(((int)text.getCharacter().charAt(0)) == 164){
			positionText = "ð";
		}else if(((int)text.getCharacter().charAt(0)) == 8250){
			positionText = "ý";
		}else if(((int)text.getCharacter().charAt(0)) == 8249){
			positionText = "I";
		}else if(text.getCharacter().length()>1){
			if(text.getCharacter().charAt(0) == 'f' && text.getCharacter().charAt(1) == 'l' ){
				positionText = "þ";
			}
			if(text.getCharacter().charAt(0) == 'f' && text.getCharacter().charAt(1) == 'i' ){
				positionText = "Þ";
			}
		}
		if((int)positionText.charAt(0) == 173){
			positionText = "-";
		}
		return positionText ;//+ " pos: "+(int)text.getCharacter().charAt(0)+" "; //kitap sayfasý yanýndaki • iiçin ascii 8226
	}
	
	private String convertListToString(List<TextPosition> line){
		StringBuilder builder = new StringBuilder();
        for (TextPosition position : line)
        {
        	int a = position.getCharacter().length();
            builder.append(convertToCorrectForm(position));
            
        }
     
        return builder.toString();
	}

	private boolean isUppercase(String str){
		for(int i = 0; i< str.length(); i++){
			if(Character.isLetter(str.charAt(i))&&!Character.isUpperCase(str.charAt(i))){	// if any of characters is lower case then string is lower case
				return false;
			}
		}
		return true;
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
	
	private boolean isBold(List<TextPosition> line){
		boolean isBold = false;
		for (TextPosition position : line)
        {
            String baseFont = position.getFont().getBaseFont();
            
            if(baseFont.toLowerCase().contains("Bold".toLowerCase())){
            	isBold = true;
            }
            
        }
		return isBold;
	}

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
