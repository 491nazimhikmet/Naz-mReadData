package Reader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import Model.DBDemo;



public class ReadFontOfText extends PDFTextStripper{//PDFText2HTML
	
	private Integer currentWorkId;
	
	private ReadFuncs rf;
	
	private String nextPoetryName;
	private String currentPoetryName;
	private boolean contToRead = false;
	private Integer lastInsertedWorkLineId;
	private Integer lastInsertedWordId;
	DBDemo db;
	
	public ReadFontOfText(Integer WorkId,String workName,DBDemo db) throws IOException {
		super("UTF-8");
		currentWorkId = WorkId;
		currentPoetryName = workName;
		this.db = db;
		initialize();
		rf = new ReadFuncs();
		lastInsertedWorkLineId = 0;
		lastInsertedWordId = 0;
	}
	
	private void initialize(){
		
		nextPoetryName = db.nextWorkName(currentWorkId).trim();
	}
	
	public void writeString(String text, List<TextPosition> line) throws IOException{
				
		String lineString = rf.convertListToString(line);
		System.out.println(lineString);
		if(normalizeString(lineString).toLowerCase().contains(normalizeString(nextPoetryName).toLowerCase())){
			contToRead = false;
		}
		String a = normalizeString(lineString);
		String b = normalizeString(currentPoetryName);
		if(normalizeString(lineString).toLowerCase().contains(normalizeString(currentPoetryName).toLowerCase())){
			contToRead = true;
		}
		
		if(contToRead == true){
		
			float[] borders = getLineStartEnd(line);
			
			lastInsertedWorkLineId = db.insertWorkline(lineString.trim(), borders[0], borders[1], currentWorkId);
			getTheWords(line);
		}
		
		
	}
	
	
	private float[] getLineStartEnd(List<TextPosition> line){
		TextPosition first = line.get(0);
		TextPosition last = line.get(line.size()-1);
		
		float[] borderArr  = new float[2];
		borderArr[0] = first.getXDirAdj();
		borderArr[1] = last.getXDirAdj();
		
		return borderArr;
	}
	
	
	private void getTheWords(List<TextPosition> line){
		float wordStart = 0;
		float wordEnd = 0;
		boolean isNextStart = false;
		StringBuilder builder = new StringBuilder();
		int startIndex=0;
		
		for(int i = 0 ; i< line.size(); i++){
			TextPosition pos = line.get(i);
			if(i == 0){
				wordStart = pos.getXDirAdj();
				builder.delete(0, builder.length());
				startIndex = i;
			}
			
			if(isNextStart == true){
				isNextStart = false;
				wordStart = pos.getXDirAdj();
				builder.delete(0, builder.length());
				startIndex = i;
			}
			
			
			if(rf.convertToCorrectForm(pos).trim().equals("") || i == (line.size()-1)){
				if(i == (line.size()-1)){
					builder.append(rf.convertToCorrectForm(pos));
					i++;
				}
				isNextStart = true;
				wordEnd = pos.getXDirAdj();	
				List<TextPosition> word = line.subList(startIndex, i);
				lastInsertedWordId = db.insertWord(builder.toString(), wordStart, wordEnd, lastInsertedWorkLineId, rf.isBold(word), rf.isItalic(word), getFont(word));
				getTheCharacters(word);
			}
			
			builder.append(rf.convertToCorrectForm(pos));	
			
		}
	}
	
	private void getTheCharacters(List<TextPosition> word){
		
		for(int i = 0 ; i< word.size(); i++){
			TextPosition cha = word.get(i);
			
			List<TextPosition> sublist = word.subList(i, i);
			db.insertWordCharacter(rf.convertToCorrectForm(cha), cha.getXDirAdj(), cha.getXDirAdj(), lastInsertedWordId, rf.isBold(sublist), rf.isItalic(sublist), getFont(sublist));
		}
	}
    
	private String getFont(List<TextPosition> word){
		String baseFont = "";
		for (TextPosition position : word)
        {
            baseFont = position.getFont().getBaseFont();
            
        }
		return baseFont;
	}
	
	private String normalizeString(String str){
		for(int i = 0; i< str.length(); i++){
			int asciiVal =	(int)str.charAt(i);
			if(!((asciiVal>=65 && asciiVal <=90) || (asciiVal>=97 && asciiVal <=122))){
				str = str.replaceAll(("["+str.charAt(i)+"]"), "");
				i--;
			}
		}
		str = str.replaceAll("Ð", "");
		str = str.replaceAll("ð", "");
		str = str.replaceAll("Ü", "");
		str = str.replaceAll("ü", "");
		str = str.replaceAll("Ý", "");
		str = str.replaceAll("i", "");
		str = str.replaceAll("I", "");
		str = str.replaceAll("ý", "");
		str = str.replaceAll("ö", "");
		str = str.replaceAll("Ö", "");
		str = str.replaceAll("Ç", "");
		str = str.replaceAll("ç", "");
		str = str.replaceAll("Þ", "");
		str = str.replaceAll("þ", "");
		return str;
	}

}
