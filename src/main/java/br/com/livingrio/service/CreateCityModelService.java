package br.com.livingrio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CityDAO;
import br.com.livingrio.dao.impl.StateDAO;
import br.com.livingrio.model.City;
import br.com.livingrio.web.params.CityModel;

@Service
public class CreateCityModelService {

	private CityDAO cityDAO;
	
	private StateDAO stateDAO;
	
	@Autowired
	public CreateCityModelService(CityDAO cityDAO, StateDAO stateDAO){
		this.cityDAO = cityDAO;
		this.stateDAO = stateDAO;
		
	}
	
	public List<CityModel> execute(Long idState){
		List<CityModel> cityModels = new ArrayList<CityModel>();
		
		cityModels.add( new CityModel(0L, "Todas") );
		
		for(City city : cityDAO.search(stateDAO.find(idState))){
			cityModels.add(new CityModel(city.getId(), city.getName()));
		}
		
		return cityModels;
		
	}
	
}
