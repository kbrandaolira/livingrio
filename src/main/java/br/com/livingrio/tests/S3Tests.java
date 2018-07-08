package br.com.livingrio.tests;



/*

 * Copyright 2010-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.livingrio.service.aws.s3.UploadFileToS3Service;

/**
 * This sample demonstrates how to make basic requests to Amazon S3 using the
 * AWS SDK for Java.
 * <p>
 * <b>Prerequisites:</b> You must have a valid Amazon Web Services developer
 * account, and be signed up to use Amazon S3. For more information on Amazon
 * S3, see http://aws.amazon.com/s3.
 * <p>
 * <b>Important:</b> Be sure to fill in your AWS access credentials in the
 * AwsCredentials.properties file before you try to run this sample.
 * http://aws.amazon.com/security-credentials
 */
public class S3Tests {

	public static void main(String[] args) throws IOException {
		
		
/*		InputStream is = null;
		
		String fileName = null;
		
		
		AmazonS3 s3 = new AmazonS3Client();
		
		//s3.set

		String bucketName = "elasticbeanstalk-us-west-2-862447630067";

		s3.putObject(new PutObjectRequest(bucketName, "neighborhood/2/teste.jpg", file));*/

		UploadFileToS3Service service = new UploadFileToS3Service();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("fileName", "teste.jpeg");
		params.put("dir", "testelouco/1/");
		params.put("inputStream", new FileInputStream("C:\\files\\neighborhoods\\1\\1433435292185.jpeg"));
		
		service.execute(params);
	
System.out.println("FIm");
		
	}
	


}
