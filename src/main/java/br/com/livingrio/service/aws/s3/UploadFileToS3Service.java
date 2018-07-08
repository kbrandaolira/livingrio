package br.com.livingrio.service.aws.s3;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.service.IService;
import br.com.livingrio.service.ServiceResponse;
import br.com.livingrio.service.images.CompressImageService;

@Service
public class UploadFileToS3Service implements IService<Map<String,Object>>{

	/**
	 * 
	 * Necessário passar os seguintes paramentros no mapa:
	 *  fileName
	 *  dir
	 *  inputStream
	 *  
	 *  
	 * 
	 */
	public ServiceResponse execute(Map<String, Object> params) {
	
		String fileName = (String) params.get("fileName");
		
		String dir = (String) params.get("dir");
		
		InputStream inputStream  = (InputStream) params.get("inputStream");	
		
		AWSCredentials credentials = new BasicAWSCredentials(ApplicationInfo.AWS_ACCESS_KEY_ID, ApplicationInfo.AWS_ACCESS_KEY_SECRET);
		
	    ClientConfiguration clientConfig = new ClientConfiguration();
	    clientConfig.setProtocol(Protocol.HTTP);
		
		AmazonS3 s3 = new AmazonS3Client(credentials,clientConfig);     
		
		String bucketName = ApplicationInfo.AWS_S3_BUCKETS_LIST.get(0);
		
		
		// Comprimindo as Imagens
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Fake code simulating the copy
		// You can generally do better with nio if you need...
		// And please, unlike me, do something about the Exceptions :D
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buffer)) > -1 ) {
			    baos.write(buffer, 0, len);
			}
			baos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		CompressImageService compressImageService = new CompressImageService();
		
			// Criando Small Size
		
		Map<String,Object> compressParams = new HashMap<String, Object>();
	
		compressParams.put("inputStream", new ByteArrayInputStream(baos.toByteArray()));
		compressParams.put("width", ApplicationInfo.IMAGE_SMALL_WIDTH);
		compressParams.put("height", ApplicationInfo.IMAGE_SMALL_HEIGHT);
		
		InputStream smallInputStream = (InputStream) compressImageService.execute(compressParams).getObject();
		
		s3.putObject(new PutObjectRequest(bucketName, dir + "s_" + fileName, smallInputStream,null));
		
			// Criando Medium Size
		
		compressParams = new HashMap<String, Object>();
		
		compressParams.put("inputStream", new ByteArrayInputStream(baos.toByteArray()));
		compressParams.put("width", ApplicationInfo.IMAGE_MEDIUM_WIDTH);
		compressParams.put("height", ApplicationInfo.IMAGE_MEDIUM_HEIGHT);
		
		InputStream mediumInputStream = (InputStream) compressImageService.execute(compressParams).getObject();
		
		s3.putObject(new PutObjectRequest(bucketName, dir + "m_" + fileName, mediumInputStream,null));
		
		params.put("bucketName", bucketName);	
		
		return new ServiceResponse(true,params);
		
	}

}
