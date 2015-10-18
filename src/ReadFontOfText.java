import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import org.apache.pdfbox.util.PDFText2HTML;

public class ReadFontOfText extends PDFTextStripper  {//PDFText2HTML

	public ReadFontOfText() throws IOException {
		super("UTF-8");
		// TODO Auto-generated constructor stub
	}
	
	public void writeString(String text, List<TextPosition> textPositions) throws IOException{
		escape(text);
		StringBuilder builder = new StringBuilder();
		String prevBaseFont = "";
		Boolean isAllUpper = true;
        for (TextPosition position : textPositions)
        {
            String baseFont = position.getFont().getBaseFont();
            
            if(Character.isLetter(position.getCharacter().charAt(0))){
            	if(!Character.isUpperCase(position.getCharacter().charAt(0))){
            		isAllUpper = false;
            	}
            }
            
            if (baseFont != null && !baseFont.equals(prevBaseFont))
            {
                builder.append('[').append(baseFont).append(']');//.append('{').append(aveHeight).append('}');
                prevBaseFont = baseFont;
            }
            
            
            builder.append(position.getCharacter());
            
        }
        if(isAllUpper){
        	builder.append("[Upper]");
        }

        writeString(builder.toString());
	}
	
	   /**
     * Escape some HTML characters.
     *
     * @param chars String to be escaped
     * @return returns escaped String.
     */
    private static String escape(String chars)
    {
        StringBuilder builder = new StringBuilder(chars.length());
        for (int i = 0; i < chars.length(); i++)
        {
            appendEscaped(builder, chars.charAt(i));
        }
        return builder.toString();
    }

    private static void appendEscaped(StringBuilder builder, char character)
    {
        // write non-ASCII as named entities
        if ((character < 32) || (character > 126))
        {
            int charAsInt = character;
            builder.append("&#").append(charAsInt).append(";");
        }
        else
        {
            switch (character)
            {
            case 34:
                builder.append("&quot;");
                break;
            case 38:
                builder.append("&amp;");
                break;
            case 60:
                builder.append("&lt;");
                break;
            case 62:
                builder.append("&gt;");
                break;
            default:
                builder.append(String.valueOf(character));
            }
        }
    }
    
    
}
