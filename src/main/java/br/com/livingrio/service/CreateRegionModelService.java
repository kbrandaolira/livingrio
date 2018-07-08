package br.com.livingrio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CityDAO;
import br.com.livingrio.dao.impl.RegionDAO;
import br.com.livingrio.model.Region;
import br.com.livingrio.web.params.RegionModel;

@Service
public class CreateRegionModelService {

	private RegionDAO regionDAO;
	
	private CityDAO cityDAO;

	@Autowired
	public CreateRegionModelService(RegionDAO regionDAO, CityDAO cityDAO){
		this.regionDAO = regionDAO;
		this.cityDAO = cityDAO;
		
	}
	
	public List<RegionModel> execute(Long idCity){
		List<RegionModel> regionModels = new ArrayList<RegionModel>();
		
		regionModels.add(new RegionModel(0L, "Todas"));
		
		for( Region region : this.regionDAO.search( cityDAO.find(idCity)) ){
			regionModels.add(new RegionModel(region.getId(), region.getName()));
			
		}
		
		return regionModels;
		
	}
	
}
