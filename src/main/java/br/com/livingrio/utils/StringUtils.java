package br.com.livingrio.utils;

public class StringUtils {

	public static String removeAccents(String name) {
		if(name != null){
			return name.toLowerCase().replace("�", "a").replace("�","a").replace("�", "a").replace("�", "e").replace("�", "i").replace("�", "o").replace("�", "o").replace("�", "o").replace("�", "c");
		}
		return null;
	}
	
}
