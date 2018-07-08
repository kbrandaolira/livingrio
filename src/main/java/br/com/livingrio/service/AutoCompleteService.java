package br.com.livingrio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.utils.StringUtils;
import br.com.livingrio.web.params.AutoCompleteModel;

@Service
public class AutoCompleteService {
	
	private NeighborhoodDAO neighborhoodDAO;
	
	@Autowired 
	public AutoCompleteService(NeighborhoodDAO neighborhoodDAO){
		this.neighborhoodDAO = neighborhoodDAO;
	}
	
	public List<AutoCompleteModel> execute(String name, int maxResult){
		return this.makeModel(this.neighborhoodDAO.search(StringUtils.removeAccents(name), 10));
		
	}
	
	private List<AutoCompleteModel> makeModel(List<Neighborhood> neighborhoods){
		List<AutoCompleteModel> models = new ArrayList<AutoCompleteModel>();
		
		for(Neighborhood neighborhood : neighborhoods){
			models.add( new AutoCompleteModel(neighborhood.getId(), neighborhood.getName(),neighborhood.getSpecification(),
					neighborhood.getMainPicture() != null? neighborhood.getMainPicture().getPath() : "http://athomescharlotte.com/forsale/images/nophoto.gif", neighborhood.getFullURL() ));
		}
		
		return models;
		
	}
	
}
