import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.function.type4.Parser;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdfparser.PDFParser;

import Reader.ReadContentsPage;
import Reader.ReadFontOfText;


public class ReadData {

	
	public static void main(String[] args) {
		PDDocument pd;
		try {
			 File input = new File("2476_butun siirleri_nazim delta.pdf");
			 StringBuilder sb = new StringBuilder();
			 pd = PDDocument.load(input);
			 ReadContentsPage stripper = new ReadContentsPage();
			 //ReadFontOfText stripper = new ReadFontOfText();
			 stripper.setStartPage(5); 
	         stripper.setEndPage(20); 
	         sb.append(stripper.getText(pd));
	         System.out.println(sb.toString());
	         
	        /* PDPage page = new PDPage(); 
	         PDDocument doc = new PDDocument();
	         
	         page = (PDPage) pd.getDocumentCatalog().getAllPages().get(1);
	         doc.addPage(page);
	         page = (PDPage) pd.getDocumentCatalog().getAllPages().get(5);
	         doc.addPage(page);
	         page = (PDPage) pd.getDocumentCatalog().getAllPages().get(26);
	         doc.addPage(page);
	         page = (PDPage) pd.getDocumentCatalog().getAllPages().get(245);
	         doc.addPage(page);
	         
	         
	         doc.save(new File("1-5-26-246.pdf"));
	         
	         doc.close();*/
			 
			 if (pd != null) {
			     pd.close();
			 }
		} catch (Exception e){
	         e.printStackTrace();
		}


	}
	

}
