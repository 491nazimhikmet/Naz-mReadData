import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

public class ReadFontOfText extends PDFTextStripper  {

	public ReadFontOfText() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void writeString(String text, List<TextPosition> textPositions) throws IOException{
		
		StringBuilder builder = new StringBuilder();
		String prevBaseFont = "";
		
        for (TextPosition position : textPositions)
        {
            String baseFont = position.getFont().getBaseFont();
            if (baseFont != null && !baseFont.equals(prevBaseFont))
            {
                builder.append('[').append(baseFont).append(']');
                prevBaseFont = baseFont;
            }
            builder.append(position.getCharacter());
        }

        writeString(builder.toString());
	}
}
