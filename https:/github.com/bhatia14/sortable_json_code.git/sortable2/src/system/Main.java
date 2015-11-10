package system;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

	public static void main(String[] args) throws FileNotFoundException{
		File  file = new File("./files/listings.txt");
		File  fin = new File("./files/products.txt");
		try {
			ArrayList<Listings> listListing=new ArrayList<>();
			listListing=readFile(file);
			ArrayList<Products> listProduct=new ArrayList<>();
			listProduct=readProduct(fin);
			Hashtable<String, ArrayList<Listings>> hashtable=new Hashtable<>();
			
			for(Products products:listProduct){
				ArrayList<Listings> matchListingsList=new ArrayList<>();
				for(Listings listings:listListing){
					
					if(products.getManufacturer().contains(listings.getManufacturer())){
						//System.out.println(products.getManufacturer());
						matchListingsList.add(listings);
					}
					
					
				}
				hashtable.put(products.getProduct_name(), matchListingsList);
			}
			JSONObject obj = new JSONObject(hashtable);
			
			System.out.println(obj.get("Samsung_TL240"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static ArrayList<Products> readProduct(File fin) throws IOException{
		// Construct BufferedReader from FileReader
				BufferedReader br = new BufferedReader(new FileReader(fin));
				ArrayList<Products> list=new ArrayList<>(); 
				String line = null;
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					JSONParser parser = new JSONParser();
					try {
						Products products=new Products(); 
						
						Object value = parser.parse(line);
						JSONObject obj2=(JSONObject) value;
						products.setProduct_name(obj2.get("product_name").toString());
						products.setModel((obj2.get("model").toString()!=null)?obj2.get("model").toString():"");
						//products.setFamily((obj2.get("family").toString()!=null)?obj2.get("family").toString():"");
						products.setAnnounced_date(obj2.get("announced-date").toString());
						products.setManufacturer(obj2.get("manufacturer").toString());
						list.add(products);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//continue;
					}
					//System.out.println(list);
					
				}
				
				br.close();
				return list;
	}
	
	private static ArrayList<Listings> readFile(File fin) throws IOException {
		// Construct BufferedReader from FileReader
		BufferedReader br = new BufferedReader(new FileReader(fin));
		ArrayList<Listings> list=new ArrayList<>(); 
		String line = null;
		while ((line = br.readLine()) != null) {
			//System.out.println(line);
			JSONParser parser = new JSONParser();
			try {
				Listings listings=new Listings(); 
				
				Object value = parser.parse(line);
				JSONObject obj2=(JSONObject) value;
				listings.setTitle(obj2.get("title").toString());
				listings.setPrice(obj2.get("price").toString());
				listings.setCurrency(obj2.get("currency").toString());
				listings.setManufacturer(obj2.get("manufacturer").toString());
				list.add(listings);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(list);
			
		}
		
		br.close();
		return list;
	}


}
