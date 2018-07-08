package br.com.livingrio.utils;

import java.util.ArrayList;
import java.util.List;

public class LongUtils {

	public static List<Long> makeStringToListLongDelimetedBy( String str, String delimeter ){
		List<Long> longs = null;
		
		if( str != null && str != "" ){
			longs = new ArrayList<Long>();
			String [] arr = str.split(delimeter);
			
			for(int i = 0; i<arr.length; i++){
				longs.add(Long.parseLong(arr[i]));
			}

		}
		
		return longs;
		
	}
	
}
