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
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Picture;
import br.com.livingrio.service.CreateNeighborhoodModelService;
import br.com.livingrio.service.aws.s3.GetFileFromS3Service;

@Controller
public class NeighborhoodController {
	
	private CreateNeighborhoodModelService createNeighborhoodService;
	
	private NeighborhoodDAO neighborhoodDAO;

	@Autowired
	public NeighborhoodController( CreateNeighborhoodModelService service, NeighborhoodDAO neighborhoodDAO ){
		super();
		this.createNeighborhoodService = service;
		this.neighborhoodDAO = neighborhoodDAO;

	}
	
	@RequestMapping(value="/{state}/{city}/{region}/{neighborhood}", method = RequestMethod.GET)
	public ModelAndView	prepare( @PathVariable String state, @PathVariable String city, @PathVariable String region, @PathVariable String neighborhood ){
		ModelAndView mv = new ModelAndView("neighborhood");
		mv.addObject("model", createNeighborhoodService.execute(null, state, city, region, neighborhood));
		
		return mv;
		
	}
	
	/**
	 * @param id do Neighborhood
	 * Tem como objetivo trazer o inputStream da main picture do neighborhood
	*/
	@RequestMapping( value = "/neighborhood/{id}/main/{size}", method = RequestMethod.GET )
	public ResponseEntity<byte[]> getMainFrom( @PathVariable Long id, @PathVariable String size, HttpServletResponse response ){
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		Neighborhood neighborhood = this.neighborhoodDAO.find( id );
		
		Picture picture = neighborhood.getMainPicture();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		
		if (picture != null){
			params.put("bucketName", picture.getBucket());
			params.put("dir", ApplicationInfo.AWS_S3_NEIGHBORHOOD_FILE_BASE_DIR + neighborhood.getId() + "/");
			params.put("fileName", size + "_" + picture.getFileName());
			
			
		}else{
			params.put("bucketName", "livingrio-filesystem");
			params.put("dir", "commons/");
			params.put("fileName", "no-photo-neighborhood.jpg");
			
		}
		
		return new ResponseEntity<byte[]>((byte[]) new GetFileFromS3Service().execute(params).getObject(), headers, HttpStatus.CREATED);
		
	}
	
	
}
