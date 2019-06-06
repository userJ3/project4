
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Testing {
	
	  private static final String url = "jdbc:mysql://localhost:3306/basa1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	  private static final String user = "root";
	  private static final String password = "12345";
	  
	  private static Connection con;
	  private static Statement stmt;
	  private static int resultSet;
	  private static ResultSet resultSet2;
	  
  public static void main (String[] args) throws MalformedURLException, IOException {
    for (int s = 1; s<100; s++) {
	    try {
	    	
		    String url2 = "https://www.moyo.ua/comp-and-periphery/noutebook_pc/comp?page="+s;
	        Document doc;
				doc = Jsoup.parse(new URL(url2), 3000);
		        Elements resultProduct = doc.select("div[class=product-tile_title ddd]");
		        Elements resultCost = doc.select("div[class=product-tile_price-current]");
		        String[] product = new String[resultProduct.size()];
		        for (int iw = 0; iw < resultProduct.size(); iw++) {
		            product[iw] = resultProduct.get(iw).text(); 
		        }
		    
		        String[] cost = new String[resultCost.size()];
		        for (int i = 0; i < resultCost.size(); i++) {
		            cost[i] = resultCost.get(i).text(); 
			} 
	    
	          con = DriverManager.getConnection(url, user, password);
	          stmt = con.createStatement();
	    
	    for (int i1 = 0; i1<23; i1++) {
	    	  String t1 = product[i1];
	    	  String t2 = cost[i1];
	    	  String t3 = t2.replaceAll(" ","").replaceAll("грн", "").replaceAll("÷ена:", "");
	    	  String b1 = String.valueOf(i1);
	    	  float min = 3.14f;
	    	  float max = 10.14f;
	    	  Random random = new Random();
	    	  float diff = max - min;
	    	  float r1 = random.nextFloat();
	    	  System.out.println(t3);
	          String toSend = "INSERT INTO basa1.product (CODE, NAME, PRICE) \n" +
	          "VALUES (" + b1 + ", '"+ t1 + "', '" + r1 + "');";
	          resultSet = stmt.executeUpdate(toSend);
	          System.out.println(i1);
	    }
	      
	      String request = "select CODE, NAME, PRICE from product";
	      resultSet2 = stmt.executeQuery(request); 
	      
	    while (resultSet2.next()) {
	      
	          int id = resultSet2.getInt(1);
	          String product1 = resultSet2.getString(2);
	          String cost1 = resultSet2.getString(3);
	          System.out.printf("CODE: %d, NAME: %s, PRICE: %s %n", id, product1, cost1); 
	      
	    }
	      
	    } catch(SQLException e) {
	      
	      System.out.println("SqlManager" + e);
	      
	    } 
    }
	 }
	  
        }
  