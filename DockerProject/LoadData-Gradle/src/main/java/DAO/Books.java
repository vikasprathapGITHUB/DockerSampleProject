package DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import beans.BookDetails;

public class Books {
	
	private static LineNumberReader lineNumber;
	private static BufferedReader in;

	public static List<BookDetails> getBooksList(String file) throws IOException{
		lineNumber = new LineNumberReader(new FileReader(new File(file)));
		lineNumber.skip(Long.MAX_VALUE);
		int length = lineNumber.getLineNumber();
		lineNumber.close();
		in = new BufferedReader(new FileReader(file));
		List<BookDetails> books= new ArrayList<BookDetails>();
		
		for(int i=0;i<length+1/3;i+=3){
			
			BookDetails bookDetails = new BookDetails();
			bookDetails.setName(in.readLine());
			bookDetails.setPublish(in.readLine());
			bookDetails.setYear(in.readLine());
			
			books.add(bookDetails);
		}
		return books;
	}

}