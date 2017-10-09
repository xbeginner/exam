package com.xx.test.Utils;

import java.util.Map;

public  class JsonUtils {
	
	public static String getJsonString(Map<String,String> map){
		   
		    StringBuffer json = new StringBuffer();
		    json.append("{");
		    for(String key:map.keySet()){
		    	  
		    	    json.append("\""+key+"\":");
		    	    json.append("\""+map.get(key)+"\",");
		    	   
		    }
		    if(map.size()>=1){
		    	json.deleteCharAt(json.length()-1);
		    }
		    json.append("}");

		    return json.toString();
	}
	
	public static String getAddableJsonString(Map<String,String> map){
		   
	    StringBuffer json = new StringBuffer();
	    for(String key:map.keySet()){
	    	  
	    	    json.append("\""+key+"\":");
	    	    json.append("\""+map.get(key)+"\",");
	    	   
	    }
	    if(map.size()>=1){
	    	json.deleteCharAt(json.length()-1);
	    }

	    return json.toString();
}
	
	
	
	public static String getJsonStringByName(String name,Map<String,String> map){
	    StringBuffer json = new StringBuffer();
	   json.append("\""+name+"\":");
	   json.append(getJsonString(map));
	    return json.toString();
}
	
	

}
