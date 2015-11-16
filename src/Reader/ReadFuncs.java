package Reader;

import java.util.List;

import org.apache.pdfbox.util.TextPosition;

public class ReadFuncs {
	
	public ReadFuncs(){
		
	}
	
	public String convertToCorrectForm(TextPosition text){
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
		}else if(((int)text.getCharacter().charAt(0)) == 165){
			positionText = "Ð";
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
	
	public String convertListToString(List<TextPosition> line){
		StringBuilder builder = new StringBuilder();
        for (TextPosition position : line)
        {
        	int a = position.getCharacter().length();
            builder.append(convertToCorrectForm(position));
            
        }
     
        return builder.toString();
	}

	public boolean isUppercase(String str){
		for(int i = 0; i< str.length(); i++){
			if(Character.isLetter(str.charAt(i))&&!Character.isUpperCase(str.charAt(i))){	// if any of characters is lower case then string is lower case
				return false;
			}
		}
		return true;
	}

	
	public boolean isBold(List<TextPosition> line){
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
	
	public boolean isItalic(List<TextPosition> line){
		boolean isBold = false;
		for (TextPosition position : line)
        {
            String baseFont = position.getFont().getBaseFont();
            
            if(baseFont.toLowerCase().contains("italic".toLowerCase())){
            	isBold = true;
            }
            
        }
		return isBold;
	}
	
	public float getIdentation(List<TextPosition> line,String lineString){
		if(lineString.contains("•")){
			for (int i= 0; i<line.size(); i++)
	        {
	        	if(line.get(i).getCharacter().indexOf("•") == 0){
	        		TextPosition pos = line.get(i+1);
	    			return pos.getXDirAdj();
	        	}	            
	        }
			
		}else{
			TextPosition pos = line.get(0);
			return pos.getXDirAdj();
		}
		return 0;
	}
	
}
