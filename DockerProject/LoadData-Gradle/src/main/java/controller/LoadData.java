package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import DAO.Books;
import beans.BookDetails;

import com.google.gson.Gson;

public class LoadData {

	public static void main(String[] args) {
        String path = "src/main/java/resources/Book";

        try {
            
            List<BookDetails> booksList = Books.getBooksList(path);
            int id = 1;
            for(BookDetails book : booksList){
            	HttpClient httpClient = HttpClientBuilder.create().build();
	            
            	Gson gson = new Gson();
	            String json = gson.toJson(book);
	            //System.out.println(json);
	            
	            String url = "http://localhost:9200/book/programing/"+id+"";
	            HttpPost postRequest = new HttpPost(url);
	            StringEntity input = new StringEntity(json);
	            input.setContentType("application/json");
	            postRequest.setEntity(input);
	            //System.out.println(input);
	            
	            HttpResponse response = httpClient.execute(postRequest);

	            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
	                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
	            }
	            
	            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
	            String postResponse;
	            System.out.println("\n --Server Response for loading data-- \n");
	            while ((postResponse = br.readLine()) != null) {
	                System.out.println(postResponse);
	            }
	            
	            id++;
            }
            
            HttpClient httpClient = HttpClientBuilder.create().build();
            
            String url = "http://localhost:9200/book/programing/_search";
            HttpGet getRequest = new HttpGet(url);
            
            HttpResponse response = httpClient.execute(getRequest);
            
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String getResponse;
            System.out.println("\n --Server Response for getting data-- \n");
            while ((getResponse = br.readLine()) != null) {
                System.out.println(getResponse);
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
