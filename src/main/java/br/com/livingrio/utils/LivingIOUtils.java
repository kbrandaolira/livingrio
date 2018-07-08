package br.com.livingrio.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.MediaType;

public class LivingIOUtils {
	
	/**
	 * @param contentType, example, image/jpeg
	 * @return String with the type, example, jpeg
	*/
	public static String getType( String contentType ){
		if( contentType != null && contentType.contains("/") ){
			String [] type = contentType.split("/");
			return "." + type[type.length-1];
		}
		
		return null;
		
	}

	public static byte[] getBytesFromFile(String filePath) {

		File file = new File(filePath);

		FileInputStream fin = null;

		byte fileContent[] = null;
		
		try {

			fin = new FileInputStream(file);

			fileContent = new byte[(int) file.length()];

			// Reads up to certain bytes of data from this input stream into an
			// array of bytes.

			fin.read(fileContent);

			// create string from byte array

			String s = new String(fileContent);

		}

		catch (FileNotFoundException e) {

			System.out.println("File not found" + e);

		}

		catch (IOException ioe) {

			System.out.println("Exception while reading file " + ioe);

		}

		finally {

			// close the streams using close method

			try {

				if (fin != null) {

					fin.close();
				}

			}

			catch (IOException ioe) {

				System.out.println("Error while closing stream: " + ioe);

			}

		}
		
		return fileContent;

	}

	public static MediaType toMediaType(String path) {
		String type = getType(path);
		
		if(type.equals(".jpeg") || type.equals(".jpg") ){
			return MediaType.IMAGE_JPEG;
			
		}else if(type.equals(".png")){
			return MediaType.IMAGE_PNG;
			
		}else{
			return null;
			
		}
	}

}
