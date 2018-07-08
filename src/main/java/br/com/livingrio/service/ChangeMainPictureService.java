package br.com.livingrio.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.model.Picture;

@Service
public class ChangeMainPictureService {

	private PictureDAO pictureDAO;

	private NeighborhoodDAO neighborhoodDAO;
	
	@Autowired
	public ChangeMainPictureService( PictureDAO pictureDAO, NeighborhoodDAO neighborhoodDAO ){
		this.pictureDAO = pictureDAO;
		this.neighborhoodDAO = neighborhoodDAO;
		
	}
	
	
	/**
	 * @param picture to be deleted
	 * @return Long 0L if there's no other to set as a main or ?L (id of new main picture)
	 */
	@Transactional
	public void execute(Picture newMain){
		Picture oldMain = newMain.getNeighborhood().getMainPicture();
		oldMain.setMain(false);
		this.pictureDAO.update(oldMain);
		
		newMain.setMain(true);
		this.pictureDAO.update(newMain);
		
	}
	
}
