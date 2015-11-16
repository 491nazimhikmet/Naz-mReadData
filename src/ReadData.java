import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import Model.DBDemo;
import Reader.ReadContentsPage;
import Reader.ReadFontOfText;


public class ReadData {

	
	public static void main(String[] args) {
		PDDocument pd;
		try {
			 File input = new File("2476_butun siirleri_nazim delta.pdf");
			 pd = PDDocument.load(input);
			 //readTheIndexPage(pd);
			 readAllWorks(pd);
			 
			 if (pd != null) {
			     pd.close();
			 }
		} catch (Exception e){
	         e.printStackTrace();
		}


	}
	
	
	public static void readAllWorks(PDDocument pd) throws IOException, SQLException{
		
		DBDemo db = new DBDemo();
		
		ResultSet worksWithBooks = db.getAllWorks(6180,6337);
		
		while(worksWithBooks.next()){
			int workId = worksWithBooks.getInt("WorkId");
			int startPage = worksWithBooks.getInt("pageNum");
			int endPage = worksWithBooks.getInt("finishPage");
			String workName = worksWithBooks.getString("Name");
			ReadFontOfText stripper = new ReadFontOfText(workId,workName,db);
			
			if(startPage < endPage){
				stripper.setStartPage(startPage);
				stripper.setEndPage(endPage-1);
			}else if(startPage == endPage){
				stripper.setStartPage(startPage);
				stripper.setEndPage(startPage);
			}
			
			stripper.getText(pd);
			
		}
		
		db.closeConnection();
		
	}
	
	
	/**
	 * for reading index page. It takes book names and related poetry names
	 * @param pd PdDocument
	 * @throws IOException e
	 */
	public static void readTheIndexPage(PDDocument pd) throws IOException{
		StringBuilder sb = new StringBuilder();
		ReadContentsPage stripper = new ReadContentsPage();
		stripper.setStartPage(5); 
        stripper.setEndPage(20); 
        sb.append(stripper.getText(pd));
	}
	

}
