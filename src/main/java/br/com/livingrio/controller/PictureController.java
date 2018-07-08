package br.com.livingrio.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Picture;
import br.com.livingrio.service.aws.s3.GetFileFromS3Service;

@Controller
public class PictureController {

	private PictureDAO pictureDAO;
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private GetFileFromS3Service getFileFromS3Service;
	
	@Autowired
	public PictureController( PictureDAO pictureDAO, NeighborhoodDAO neighborhoodDAO ){
		this.pictureDAO = pictureDAO;
		this.neighborhoodDAO = neighborhoodDAO;
		this.getFileFromS3Service = new GetFileFromS3Service();	
	}
	
	/**
	 * @param id da Picture
	 * Tem como objetivo trazer o inputStream da picture pelo ID
	 *  
	 * SOMENTE PARA BAIRRO!! REFATORAR COLOCAR EM NEIGHBORHOODCONTROLLER FAZER IGUAL DE ProfileController
	 * 
	*/
	@Deprecated
	@RequestMapping( value = "/picture/find/{id}", method = RequestMethod.GET )
	public ResponseEntity<byte[]> find( @PathVariable Long id, HttpServletResponse response ){
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		Picture picture = this.pictureDAO.find( id );
		
		Neighborhood neighborhood = picture.getNeighborhood();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("dir", ApplicationInfo.AWS_S3_NEIGHBORHOOD_FILE_BASE_DIR + neighborhood.getId() + "/");
		params.put("fileName", picture.getFileName());
		params.put("bucketName", picture.getBucket());
		
		
		return new ResponseEntity<byte[]>((byte[]) getFileFromS3Service.execute(params).getObject(), headers, HttpStatus.CREATED);
		
	}
	
	/**
	 * @param id do Neighborhood
	 * Tem como objetivo trazer o inputStream da main picture do neighborhood
	*/
	@RequestMapping( value = "/picture/getMainFrom/{id}", method = RequestMethod.GET )
	public ResponseEntity<byte[]> getMainFrom( @PathVariable Long id, HttpServletResponse response ){
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		Neighborhood neighborhood = this.neighborhoodDAO.find( id );
		
		Picture picture = neighborhood.getMainPicture();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("dir", ApplicationInfo.AWS_S3_NEIGHBORHOOD_FILE_BASE_DIR + neighborhood.getId() + "/");
		params.put("fileName", picture.getFileName());
		params.put("bucketName", picture.getBucket());
		
		return new ResponseEntity<byte[]>((byte[]) getFileFromS3Service.execute(params).getObject(), headers, HttpStatus.CREATED);
		
	}
	
}
