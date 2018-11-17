package com.haima.regeneration;

import java.util.Iterator;
import java.util.Map;

public class TestENV {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 Map<String, String> map = System.getenv();
	        for(Iterator<String> itr = map.keySet().iterator();itr.hasNext();){
	            String key = itr.next();
	            System.out.println(key + "=" + map.get(key));
	        }   
	        
	        System.out.println("path is "+System.getenv("DestDir"));
	}

}
