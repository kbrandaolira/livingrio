package br.com.livingrio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.livingrio.dao.impl.NeighborhoodCriterionDAO;
import br.com.livingrio.service.CreateCityModelService;
import br.com.livingrio.service.CreateRegionModelService;
import br.com.livingrio.service.CreateStateModelService;
import br.com.livingrio.web.params.CityModel;
import br.com.livingrio.web.params.NeighborhoodModel;
import br.com.livingrio.web.params.RegionModel;
import br.com.livingrio.web.params.StateModel;

@Controller
public class DynamicPlaceController {

	private CreateStateModelService createStateModelService;

	private CreateCityModelService createCityModelService;
	
	private CreateRegionModelService createRegionModelService;
	
	private NeighborhoodCriterionDAO neighborhoodCriterionDAO;
	
	@Autowired
	public DynamicPlaceController( CreateStateModelService createStateModelService, CreateCityModelService createCityModelService, 
			CreateRegionModelService createRegionModelService, NeighborhoodCriterionDAO neighborhoodCriterionDAO ) {
		this.createStateModelService = createStateModelService;
		this.createCityModelService = createCityModelService;
		this.createRegionModelService = createRegionModelService;
		this.neighborhoodCriterionDAO = neighborhoodCriterionDAO;
		
	}
	
	@RequestMapping(value = "/dynamic/place/states", method = RequestMethod.GET)
	public @ResponseBody List<StateModel> getAllStates(){
		return createStateModelService.execute();
		
	}
	
	@RequestMapping(value = "/dynamic/place/cities/by/{idState}", method = RequestMethod.GET)
	public @ResponseBody List<CityModel> getCitiesBy(@PathVariable Long idState){
		return createCityModelService.execute(idState);
		
	}
	
	@RequestMapping(value = "/dynamic/place/regions/by/{idCity}", method = RequestMethod.GET)
	public @ResponseBody List<RegionModel> getRegionsBy(@PathVariable Long idCity){
		return createRegionModelService.execute(idCity);
		
	}
	
	@RequestMapping(value = "/dynamic/place/filter", method = RequestMethod.GET)
	public @ResponseBody List<NeighborhoodModel> filter( @RequestParam Long idState, @RequestParam Long idCity, @RequestParam Long idRegion ){
		return neighborhoodCriterionDAO.top(8, idState, idCity, idRegion);
		
	}
	
}
