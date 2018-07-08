package br.com.livingrio.service;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Picture;

@Service
public class DeletePictureService {

	private PictureDAO pictureDAO;

	private NeighborhoodDAO neighborhoodDAO;
	
	@Autowired
	public DeletePictureService( PictureDAO pictureDAO, NeighborhoodDAO neighborhoodDAO ){
		this.pictureDAO = pictureDAO;
		this.neighborhoodDAO = neighborhoodDAO;
		
	}
	
	
	/**
	 * @param picture to be deleted
	 * @return Long 0L if there's no other to set as a main or ?L (id of new main picture)
	 */
	@Transactional
	public Long execute(Picture picture){
	
		Neighborhood neighborhood = picture.getNeighborhood();
		neighborhood.getPictures().remove(picture);
		this.neighborhoodDAO.update(neighborhood);
		this.pictureDAO.destroy(picture);
		
		if( picture.getMain() ){
			for( Picture pic : picture.getNeighborhood().getPictures() ){
				pic.setMain(true);
				this.pictureDAO.update(pic);
				return pic.getId();
				
			}
		}
			
		return 0L;
		
	}
	
}
