package com.example.aiqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fuwuqi.JSONParser;
import android.R.integer;
import android.app.Application;
import android.util.Log;

public class Data extends Application {  
	  private int zhaopian;
	  private String username="";  
	  private String pid="";  
	  private String password="";   

	  private String url123=""; 
	  JSONParser jParser = new JSONParser();	 
	    ArrayList<HashMap<String, String>> productsList;
	    // url to get all products list
	    private static String url_all_products = "http://qq282844655.hk623-hl.6464.cn/get_all_products.php";
	    private static final String TAG_DESCRIPTION = "description";
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_PRODUCTS = "products";
	    private static final String TAG_URL = "url1";
	    private static final String TAG_URL1 = "url";
	    private static final String TAG_PRICE = "price";
	    private static final String TAG_DESCRIPTION1 = "description1";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_PID = "pid";
	    // products JSONArray
	    JSONArray products = null;
	 private String[] url=new String[100];
	 private String[] description=new String[100];
	 private int length=0;
	 private String[] description1=new String[100];
	 private String[] description2=new String[100];
	 private String[] names=new String[100];
	 private String[] pids=new String[100];
	 private String[] names1=new String[100];
	 private String[] pids1=new String[100];
	 private String[] url1=new String[100];
	 private boolean Act=false;
	 private boolean Act1=false;
	 public void setActivityT1(){
		 Act1=true;
	 } 
	 public void setActivityF1(){
		 Act1=false;
	 }
	 public boolean getActivity1(){	 
		return Act1;
		 
	 }
	 public void setActivityT(){
		 Act=true;
	 }
	 public void setActivityF(){
		 Act=false;
	 }
	 public boolean getActivity(){	 
		return Act;
		 
	 }
	 public String[] Geturl(){	 
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	     // getting JSON string from URL
	     JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
	  // Check your log cat for JSON reponse
	     Log.d("All Products: ", json.toString());
	    try {
	         // Checking for SUCCESS TAG
	         int success = json.getInt(TAG_SUCCESS);
	         if (success == 1) {
	             // products found
	             // Getting Array of Products
	             products = json.getJSONArray(TAG_PRODUCTS);
	             // looping through All Products
	             int j=0;
	             for (int i = 0; i < products.length(); i++) {
	                 JSONObject c = products.getJSONObject(i);
	                 // Storing each json item in variable
	                 String url1 = c.getString(TAG_URL);
	              if(url1!=null)
	            	  if(url1.length()>10)
	            	  {   url[j]=url1;
	            	  description1[j] ="出售"+"\n\n"+"价格:"+c.getString(TAG_PRICE) +"元"+"\n\n"+"描述:"+c.getString(TAG_DESCRIPTION1);	
	            	   description2[j] =c.getString(TAG_DESCRIPTION1);	
	            	   names[j]=c.getString(TAG_NAME) ;  
	            	   pids[j]=c.getString(TAG_PID) ; 
	            	  j++;}
	                 // creating new HashMap
	      // adding each child node to HashMap key => value      
	             }
	         } 
	     } catch (JSONException e) {
	         e.printStackTrace();
	     }
		return url;
	 }
	 
	 
	 public String[] Getdescription(){
		 
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        // getting JSON string from URL
	        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
	        // Check your log cat for JSON reponse
	        Log.d("All Products: ", json.toString());

	       try {
	            // Checking for SUCCESS TAG
	            int success = json.getInt(TAG_SUCCESS);

	            if (success == 1) {
	                // products found
	                // Getting Array of Products
	                products = json.getJSONArray(TAG_PRODUCTS);
int j=0;
	                // looping through All Products
	                for (int i = 0; i < products.length(); i++) {
	                    JSONObject c = products.getJSONObject(i);
	                    // Storing each json item in variable
	                    if(c.getString(TAG_DESCRIPTION).length()>10){
	                    	
	              description[j] = c.getString(TAG_DESCRIPTION);	   
	              pids1[j]=c.getString(TAG_PID);	
	              names1[j]=c.getString(TAG_NAME);	
	              url1[j]=c.getString(TAG_URL1);	
	              j++;
	                    }
	                }
	            } 
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }

		return description;
		 
	 }
	 public String[] getpids1(){
		 return pids1;
	 }
	 public String[] getname1(){
		 return names1;
	 }
	 public String[] geturls1(){
		 return url1;
	 }
public String[] Getdescription1(){
		 
		 /*List<NameValuePair> params = new ArrayList<NameValuePair>();
	        // getting JSON string from URL
	        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
	        // Check your log cat for JSON reponse
	        Log.d("All Products: ", json.toString());

	       try {
	            // Checking for SUCCESS TAG
	            int success = json.getInt(TAG_SUCCESS);

	            if (success == 1) {
	                // products found
	                // Getting Array of Products
	                products = json.getJSONArray(TAG_PRODUCTS);
int j=0;
	                // looping through All Products
	                for (int i = 0; i < products.length(); i++) {
	                    JSONObject c = products.getJSONObject(i);
	                    // Storing each json item in variable
	                    if(c.getString(TAG_DESCRIPTION1)!=null&&c.getString(TAG_DESCRIPTION1)!="null")
	                    if(c.getString(TAG_URL).length()>5){
	              description1[j] ="出售"+"\n\n"+"价格:"+c.getString(TAG_PRICE) +"元"+"\n\n"+"描述:"+c.getString(TAG_DESCRIPTION1);	   
	              j++;}                    
	                }
	            } 
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
*/
		return description1;
		 
	 }
public String[] Getdescription2(){
	 
	/* List<NameValuePair> params = new ArrayList<NameValuePair>();
       // getting JSON string from URL
       JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
       // Check your log cat for JSON reponse
       Log.d("All Products: ", json.toString());

      try {
           // Checking for SUCCESS TAG
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // products found
               // Getting Array of Products
               products = json.getJSONArray(TAG_PRODUCTS);
int j=0;
               // looping through All Products
               for (int i = 0; i < products.length(); i++) {
                   JSONObject c = products.getJSONObject(i);
                   // Storing each json item in variable
                   if(c.getString(TAG_DESCRIPTION1)!=null&&c.getString(TAG_DESCRIPTION1)!="null")
                	   if(c.getString(TAG_URL).length()>5){
             description2[j] =c.getString(TAG_DESCRIPTION1);	   
             j++;}                    
               }
           } 
       } catch (JSONException e) {
           e.printStackTrace();
       }
*/
	return description2;
	 
}
public String[] Getnames(){
	 
	/* List<NameValuePair> params = new ArrayList<NameValuePair>();
       // getting JSON string from URL
       JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
       // Check your log cat for JSON reponse
       Log.d("All Products: ", json.toString());

      try {
           // Checking for SUCCESS TAG
           int success = json.getInt(TAG_SUCCESS);

           if (success == 1) {
               // products found
               // Getting Array of Products
               products = json.getJSONArray(TAG_PRODUCTS);
int j=0;
               // looping through All Products
               for (int i = 0; i < products.length(); i++) {
                   JSONObject c = products.getJSONObject(i);
                   // Storing each json item in variable
                   if(c.getString(TAG_DESCRIPTION1)!=null&&c.getString(TAG_DESCRIPTION1)!="null")
                	   if(c.getString(TAG_URL).length()>5){
          names[j]=c.getString(TAG_NAME) ;  
             j++;}                    
               }
           } 
       } catch (JSONException e) {
           e.printStackTrace();
       }
*/
	return names;
	 
}
public String[] Getpids(){
	 
	/* List<NameValuePair> params = new ArrayList<NameValuePair>();
      // getting JSON string from URL
      JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
      // Check your log cat for JSON reponse
      Log.d("All Products: ", json.toString());

     try {
          // Checking for SUCCESS TAG
          int success = json.getInt(TAG_SUCCESS);

          if (success == 1) {
              // products found
              // Getting Array of Products
              products = json.getJSONArray(TAG_PRODUCTS);
int j=0;
              // looping through All Products
              for (int i = 0; i < products.length(); i++) {
                  JSONObject c = products.getJSONObject(i);
                  // Storing each json item in variable
                  if(c.getString(TAG_DESCRIPTION1)!=null&&c.getString(TAG_DESCRIPTION1)!="null")
                	   if(c.getString(TAG_URL).length()>5){
         pids[j]=c.getString(TAG_PID) ;  
            j++;}                    
              }
          } 
      } catch (JSONException e) {
          e.printStackTrace();
      }
*/
	return pids;
	 
}
	 
	 /*public int getlength(){
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	     // getting JSON string from URL
	     JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
	  // Check your log cat for JSON reponse
	     Log.d("All Products: ", json.toString());
	    try {
	         // Checking for SUCCESS TAG
	         int success = json.getInt(TAG_SUCCESS);
	         if (success == 1) {
	             // products found
	             // Getting Array of Products
	             products = json.getJSONArray(TAG_PRODUCTS);
	             // looping through All Products
	             length=products .length();
	         } 
	     } catch (JSONException e) {
	         e.printStackTrace();
	     }
		return length;
		 
	 }*/
	  
	  public String getUser(){  
	    return username;  
	  }  
	  public void setuser(String s){  
	  username = s;  
	  }  
	  public String getUrl123(){  
		    return url123;  
		  }  
		  public void seturl123(String s){  
		  url123 = s;  
		  }  
	  public String getpid(){  
		    return pid;  
		  }  
		  public void setpid(String s){  
		 pid = s;  
		  }  
		  public String getpassword(){  
			    return password;  
			  }  
			  public void setpassword(String s){  
			  password = s;  
			  }  
			  public int getzhaopian(){  
				    return zhaopian;  
				  }  
				  public void setzhanpian(int s){  
				  zhaopian = s;  
				  }  
	}  
	  