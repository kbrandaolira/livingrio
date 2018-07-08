package br.com.livingrio.service.aws.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.service.IService;
import br.com.livingrio.service.ServiceResponse;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class GetFileFromS3Service implements IService<Map<String,Object>>{

	public ServiceResponse execute(Map<String, Object> params) {

		String key = params.get("dir").toString() + params.get("fileName").toString();
		
		String bucketName = (String) params.get("bucketName");
		
		
		AWSCredentials credentials = new BasicAWSCredentials(ApplicationInfo.AWS_ACCESS_KEY_ID, ApplicationInfo.AWS_ACCESS_KEY_SECRET);
		
	    ClientConfiguration clientConfig = new ClientConfiguration();
	    clientConfig.setProtocol(Protocol.HTTP);
		
		AmazonS3 s3Client = new AmazonS3Client(credentials,clientConfig);     
		
		S3Object object = s3Client.getObject(
		                  new GetObjectRequest(bucketName, key));
		
		InputStream objectData = object.getObjectContent();

		byte[] bytes = null;
		
		
		try {
			bytes = IOUtils.toByteArray(objectData);
			objectData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ServiceResponse(true, bytes);
		
	}
	
}
