
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class implementation{
	
	//STORING TO JSON FILE
	static void storeurl(String url) {
		
		 JSONParser jsonParser = new JSONParser();

	        try {
	        	
	        	int min=100, max=999,c=0;
	        	//RANDOM UID GENERATION
				int uid = (int) (Math.random()*(max-min+1)+min);
	        	
				//READ JSON FILE
	            Object obj = jsonParser.parse(new FileReader("C:/Users/Aishwarya/Desktop/output.json"));
	            JSONArray jsonArray = (JSONArray)obj;

	            //NEW JSON DATA THAT NEED TO BE APPENDED TO EXISTING JSON FILE
	            JSONObject newEntry = new JSONObject();
	            newEntry.put("uid", uid);
	            newEntry.put("count", c);
	            newEntry.put("url", url);


	            JSONObject newObj = new JSONObject(); 
				newObj.put(url, newEntry);
	            
	            jsonArray.add(newObj);
	         

	          //WRITING TO JSON FILE
	            FileWriter file = new FileWriter("C:/Users/Aishwarya/Desktop/output.json");
	            file.write(jsonArray.toJSONString());
	            file.flush();
	            file.close();

	        } catch (ParseException | IOException e) {
	            e.printStackTrace();
	        }
	
	}
	
	//COUNT METHOD TO GET LATEST USAGE COUNT
	static void count(String url){
		
		JSONParser jsonParser = new JSONParser();
		
		try {
			//READ JSON FILE
			Object obj = jsonParser.parse(new FileReader("C:/Users/Aishwarya/Desktop/output.json"));
			JSONArray jsonArray = (JSONArray)obj;
			
				
            JSONArray outputArray = new JSONArray();
            
			for (int i = 0; i < jsonArray.size(); i++) {
			        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			        Set k = (jsonObj.keySet());
			        Iterator<String> i1 = k.iterator();
			        
			        while (i1.hasNext()) {
			            String str = i1.next();
			            JSONObject jsonObj2 = (JSONObject) jsonObj.get(str);
			            
			            //URL COMPARISION
			            if(url.equals(str)) {
				            long count = (long) jsonObj2.get("count");
				            System.out.println(count);
			            }
			            
			        }
			  }
        }catch (ParseException | IOException e) {
        	e.printStackTrace();
        }
		
	}
	
	//GET THE UID AND INCREMENT COUNT BY TAKING URL AS REFERENCE METHOD
	static long get(String url) {
		JSONParser jsonParser = new JSONParser();
		long uids=0;
		try {
			//READ JSON FILE
			Object obj = jsonParser.parse(new FileReader("C:/Users/Aishwarya/Desktop/output.json"));
			JSONArray jsonArray = (JSONArray)obj;
            JSONArray outputArray = new JSONArray();
            
			  for (int i = 0; i < jsonArray.size(); i++) {
			        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			        Set k = (jsonObj.keySet());
			        Iterator<String> i1 = k.iterator();
			        
			        while (i1.hasNext()) {
			            String str = i1.next();
			         
			            JSONObject countObj = (JSONObject) jsonObj.get(str);
			           // System.out.println(countObj);
			            //URL COMAPRISION
			            if(url.equals(str)) {
			            	long uid = (long) countObj.get("uid"); 
				            long count = (long) countObj.get("count");
				            countObj.put("count",count+1);
                            jsonObj.put(str,countObj);
                            uids =uid;
			            }
			        }
                outputArray.add(jsonObj);
             
			  }
			  
			  //WRITING TO JSON FILE
               FileWriter file = new FileWriter("C:/Users/Aishwarya/Desktop/output.json");
			   file.write(outputArray.toJSONString());
			   file.flush();
			   file.close();
        }catch (ParseException | IOException e) {
        	e.printStackTrace();
        }
        return uids;	       
	}
	
	//LIST OF KEY VALUE PAIRS IN JSON FILE
	static void list() {
		
		JSONParser jsonParser = new JSONParser();
		
		try {
			//READ JSON FILE
			Object obj = jsonParser.parse(new FileReader("C:/Users/Aishwarya/Desktop/output.json"));
			JSONArray jsonArray = (JSONArray)obj;
            JSONArray outputArray = new JSONArray();
            
            	
			for (int i = 0; i < jsonArray.size(); i++) {
			        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
			        Set k = (jsonObj.keySet());
			        Iterator<String> i1 = k.iterator();
			        
			        while (i1.hasNext()) {
			            String str = i1.next();
			            JSONObject listObj = (JSONObject) jsonObj.get(str);
			            System.out.println("{\"url\":" + listObj.get("url") + "," + "\"uid\":" + listObj.get("uid")+"}");			     
			            
			        }
			  }
        }catch (ParseException | IOException e) {
        	e.printStackTrace();
        }
		
	}

}

public class demoProject extends implementation {
		static Boolean exit = false;
		static Scanner sc = new Scanner(System.in);
		
		static void option(String option, String url) throws Exception{
	        
	        //CHOICE SELECTION
			
		        switch(option){
		            case "storeurl": storeurl(url);
		                 			break;
		                 			
		            case "get": long getUID = get(url);
		            			System.out.println(getUID);
		                		break;
		                		
		            case "count":count(url);
		            			break;
		            			
		            case "list": list();
		                        break;
		                        
		            case "exit":
		            			exit=true;
		            			return;

		           
		        }

		}

		
	    public static void main( String[] args ) throws Exception 
	    {
	    	
	    	//  PROGRAM STARTS FROM HERE
	    	while(exit == false) {
				String op = sc.nextLine();
				String[ ] inputArray = op.split(" ");
		        
		        if(inputArray.length > 1){
		           option(inputArray[0].toLowerCase(), inputArray[1]);
		        }
		           
		       else
		           option(inputArray[0].toLowerCase(), "");
		        
		}
	  }
	    
}

