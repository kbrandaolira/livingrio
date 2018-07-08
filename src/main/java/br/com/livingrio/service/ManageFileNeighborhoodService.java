package br.com.livingrio.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Picture;
import br.com.livingrio.service.aws.s3.UploadFileToS3Service;
import br.com.livingrio.utils.LivingIOUtils;

@Service
public class ManageFileNeighborhoodService {

	private PictureDAO pictureDAO; 
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private UploadFileToS3Service uploadFileToS3Service;
	
	@Autowired
	public ManageFileNeighborhoodService( PictureDAO pictureDAO, NeighborhoodDAO neighborhooDAO ){
		this.pictureDAO = pictureDAO;
		this.neighborhoodDAO = neighborhooDAO;
		this.uploadFileToS3Service = new UploadFileToS3Service();
		
	}
	
	@Transactional
	public void execute( Neighborhood neighborhood, CommonsMultipartFile [] multipartFiles ){
		if( neighborhood != null && multipartFiles != null ){
			
			Map<String,Object> params = new HashMap<String, Object>();
			
			params.put("dir", neighborhood.getFilesBaseDir());
			
			
			for( int i = 0; i < multipartFiles.length; i++ ){
			
				Picture picture = new Picture();
				
				
				if( i == 0 && !neighborhood.hasMainPicture() ){
					picture.setMain(true);
					
				}else{
					picture.setMain(false);
					
				}
				
				params.put("fileName", Calendar.getInstance().getTimeInMillis() + "-" +  multipartFiles[i].getOriginalFilename());
				
				try {
					params.put("inputStream", multipartFiles[i].getInputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				params = (Map<String, Object>) uploadFileToS3Service.execute(params).getObject();
				
				picture.setBucket((String) params.get("bucketName"));
				
				picture.setFileName((String) params.get("fileName"));
				
				picture.setNeighborhood(neighborhood);
				
				this.pictureDAO.create(picture);
				
				neighborhood.addPicture(picture);
								
				
			}
			
			this.neighborhoodDAO.update(neighborhood);
			
			
			
		}
		
	}
	
}
