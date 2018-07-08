package br.com.livingrio.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.web.params.CriterionModel;
import br.com.livingrio.web.params.NeighborhoodModel;

@Service
public class CreateNeighborhoodModelService {

	private NeighborhoodDAO neighborhoodDAO;

	private CriterionDAO criterionDAO;

	@Autowired
	public CreateNeighborhoodModelService(NeighborhoodDAO neighborhoodDAO, CriterionDAO criterionDAO) {
		this.neighborhoodDAO = neighborhoodDAO;
		this.criterionDAO = criterionDAO;

	}

	/**
	 * Pode-se criar o NeighborhoodModel através do ID ou das URLS
	*/
	public NeighborhoodModel execute( Long id, String stateURL, String cityURL, String regionURL, String neighborhoodURL ) {
		Neighborhood neighborhood = null;
		
		
		
		if( id != null ){
			neighborhood = neighborhoodDAO.find( id );
		} else {
			
			if(regionURL != null && regionURL.equals("~")){
				neighborhood = (Neighborhood) neighborhoodDAO.getByURL(stateURL, cityURL, neighborhoodURL);
			}else{
				neighborhood = (Neighborhood) neighborhoodDAO.getByURL(stateURL, cityURL, regionURL, neighborhoodURL);
			}	
			
		}
		
		if( neighborhood != null ){
		
			NeighborhoodModel model = new NeighborhoodModel();
					
			model.setId(neighborhood.getId());
	
			model.setUrl(neighborhood.getFullURL());
			
			model.setName(neighborhood.getName());
			
			model.setState(neighborhood.getAcronymStateName());
			
			model.setStateId(neighborhood.getCity().getState().getId());
			
			model.setCity(neighborhood.getCityName());
			
			model.setCityId(neighborhood.getCity().getId());
			
			model.setRegion(neighborhood.getRegionName());
			
			if (neighborhood.getRegion() != null){
				model.setRegionId(neighborhood.getRegion().getId());		
			}
			
			
			
			model.setDescription(neighborhood.getDescription());
			
			model.setMainPicture(neighborhood.getMainPicture());
			
			model.setOtherPictures(neighborhood.getOtherPictures());
			
			model.setPictures(neighborhood.getPictures());
			DecimalFormat df = new DecimalFormat("0.##");
			model.setOverall(new Double(df.format(neighborhood.overall()).replace(",", ".")).doubleValue()  );
			
			//Overall do Bairro
			List<CriterionModel> criterionModels = new ArrayList<CriterionModel>();
			
		
			
			if( neighborhood.hasEvaluation() ){
			
					
				
				for(Criterion crit : criterionDAO.available()){
				// TODO Rever isto!!!	
/*					criterionModels.add(new CriterionModel(crit.getId(),crit.getName() , new Double(df.format(neighborhood.overall(crit.getId())).toString().replace(",", ".")).doubleValue(),crit.getIconLocation() ) );*/
				}
		
				model.setCriterionModels(criterionModels);
				//Fim do Overall do Bairro
				
	
			}
			
			return model;
			
		}
		
		return null;
		
	}
	
}