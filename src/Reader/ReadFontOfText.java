package Reader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;



public class ReadFontOfText extends PDFTextStripper {//PDFText2HTML

	public ReadFontOfText() throws IOException {
		super("UTF-8");
		// TODO Auto-generated constructor stub
	}
	
	public void writeString(String text, List<TextPosition> line) throws IOException{
		StringBuilder builder = new StringBuilder();
		String prevBaseFont = "";
		Boolean isAllUpper = true;
        for (TextPosition position : line)
        {
            String baseFont = position.getFont().getBaseFont();
            
            if(Character.isLetter(position.getCharacter().charAt(0))){
            	if(!Character.isUpperCase(position.getCharacter().charAt(0))){ //burada karakter de önemli, * vs ise atla
            		isAllUpper = false;
            	}
            }
            
            if (baseFont != null && !baseFont.equals(prevBaseFont))
            {
                builder.append('[').append(baseFont).append(']');//.append('{').append(aveHeight).append('}');
                prevBaseFont = baseFont;
            }
            
          /* builder.append( "String[" + position.getXDirAdj() + ","
            + position.getYDirAdj() + " fs=" + position.getFontSize() + " xscale="
            + position.getXScale() + " height=" + position.getHeightDir() + " space="
            + position.getWidthOfSpace() + " width="
            + position.getWidthDirAdj() + "]" + position.getCharacter()+"\t");*/
            
            for(int l = 0; l< position.getCharacter().length(); l++){
            	//builder.append(position.getCharacter().charAt(l));
            	
            	//builder.append(":").append((int)position.getCharacter().charAt(l)).append("/");
            }
            
            builder.append(convertToCorrectForm(position));
            
            
            
            
        }
        if(isAllUpper){
        	builder.append("[Upper]");
        }
    	writeString(builder.toString());

        
	}
	
	private String convertToCorrectForm(TextPosition text){
		String positionText =text.getCharacter();
		if(((int)text.getCharacter().charAt(0)) == 164){
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
		return positionText;//+" pos:"+(int)text.getCharacter().charAt(0)+" ";//kitap sayfasý yanýndaki • iiçin ascii 8226
	}
    
    
}
