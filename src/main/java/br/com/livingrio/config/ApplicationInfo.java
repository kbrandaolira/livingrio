package br.com.livingrio.config;

import java.util.ArrayList;
import java.util.List;

public class ApplicationInfo {
	
	public static final String FACEBOOK_AUTH_ID = "387609931420513";
	
	public static final String FACEBOOK_AUTH_SECRET = "d78dda060155fa6dd3d185b7ab4be992";

	
	
	// LOCALHOST
	
	//public static final String DOMAIN_NAME= "livingrio.ddns.net";
			
	//public static final String PORT_NUMBER = "8090";
	
	//public static final String CONTEXT_PATH = "br.com.livingrio/";
	
	//public static final String APPLICATION_URL = "HTTP://" + DOMAIN_NAME + PORT_NUMBER + "/" +  CONTEXT_PATH;
	
	
	// PRODUÇÃO
	
	public static final String DOMAIN_NAME= "www.livingrio.com.br";
	
	public static final String PORT_NUMBER = "";
	
	public static final String CONTEXT_PATH = "";
	
	public static final String APPLICATION_URL = "HTTP://" + DOMAIN_NAME + PORT_NUMBER + "/" +  CONTEXT_PATH;

	
	
	// AMAZON CORE CONFIGS
	
	public static final String AWS_ACCESS_KEY_ID =  "AKIAIRWZEKKXHPLZXLIA";
			
	public static final String AWS_ACCESS_KEY_SECRET = "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";
	
	
	// AMAZON S3 CONFIGS
	
	
	public static final List<String> AWS_S3_BUCKETS_LIST;
	
	public static final String		 AWS_S3_PEOPLE_FILE_BASE_DIR = "people/";
	
	public static final String		 AWS_S3_NEIGHBORHOOD_FILE_BASE_DIR = "neighborhood/";
	
	static {
		AWS_S3_BUCKETS_LIST = new ArrayList<String>();
		AWS_S3_BUCKETS_LIST.add("livingrio-filesystem");
		AWS_S3_BUCKETS_LIST.add("elasticbeanstalk-us-west-2-862447630067");
	}
	
	
	// IMAGES CONFIG
	
	
	public static final int IMAGE_SMALL_WIDTH = 130;
	
	public static final int IMAGE_SMALL_HEIGHT = 130;
	
	public static final int IMAGE_MEDIUM_WIDTH = 320;
	
	public static final int IMAGE_MEDIUM_HEIGHT = 320;
	
	public static final String IMAGE_DEFAULT_WOMAN_NO_PHOTO 		= "commons/no-photo-woman.jpg";
	public static final String IMAGE_DEFAULT_MAN_NO_PHOTO 			= "commons/no-photo-man.jpg";
	public static final String IMAGE_DEFAULT_NEIGHBORHOOD_NO_PHOTO 	= "commons/no-photo-neighborhood.jpg";
	
	
	
}
