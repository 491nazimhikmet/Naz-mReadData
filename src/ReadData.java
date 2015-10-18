import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;


public class ReadData {

	public static void main(String[] args) {
		PDDocument pd;
		try {
			 //  PDF file from the phone numbers are extracted
			 File input = new File("2476_butun siirleri_nazim delta.pdf");
			
			 // StringBuilder to store the extracted text
			 StringBuilder sb = new StringBuilder();
			 pd = PDDocument.load(input);
			 //PDFTextStripper stripper = new PDFTextStripper();
			 ReadFontOfText stripper = new ReadFontOfText();
			 stripper.setStartPage(5); //Start extracting from page 3
	         stripper.setEndPage(20); //Extract till page 5
	         sb.append(stripper.getText(pd));
	         System.out.println(sb.toString());
	         
	         
	         /*System.out.println(stripper.getText(pd));//sb.toString());
	         
			 /*PDFTextStripper stripper = new PDFTextStripper();
			
			 // Add text to the StringBuilder from the PDF
			 sb.append(stripper.getText(pd));*/
	        
	         
	         
	         
			/* List<PDPage> pages = new ArrayList<PDPage>();
			 pages = pd.getDocumentCatalog().getAllPages();
			 Map<String,PDFont> pageFonts = new HashMap<String,PDFont>();
			 System.out.println("pages size : "+ pages.size());
			 for(int i = 0 ; i<pages.size(); i++){
				 PDPage pg = pages.get(i);
				 System.out.println("pageim : " + i );
				 //pageFonts=pg.getResources().getFonts();,
				 pageFonts.putAll(pg.getResources().getFonts());
				 Set set = pg.getResources().getFonts().entrySet();
				 Iterator im = set.iterator();
				 while(im.hasNext()) {
			         Map.Entry me = (Map.Entry)im.next();
			         System.out.print(me.getKey() + ": ");
			         PDFont fnt = (PDFont)me.getValue();
			         System.out.println(fnt.getBaseFont());//fnt.getAverageFontWidth());
			      }
			 }
			 
			 System.out.println("size is "+ pageFonts.size());*/
			 /*for(int i=0; i<pageFonts.size(); i++){
				 System.out.print(pageFonts.values().toArray()[i] + "\t");
				 System.out.print("");
			 }*/
			 
			 if (pd != null) {
			     pd.close();
			 }
		} catch (Exception e){
	         e.printStackTrace();
		}


	}

}
