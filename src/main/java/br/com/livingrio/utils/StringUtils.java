package br.com.livingrio.utils;

public class StringUtils {

	public static String removeAccents(String name) {
		if(name != null){
			return name.toLowerCase().replace("á", "a").replace("â","a").replace("ã", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("õ", "o").replace("ô", "o").replace("ç", "c");
		}
		return null;
	}
	
}
