package br.com.livingrio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.StateDAO;
import br.com.livingrio.model.State;
import br.com.livingrio.web.params.StateModel;

@Service
public class CreateStateModelService {

	private StateDAO stateDAO;
	
	@Autowired
	public CreateStateModelService(StateDAO stateDAO){
		this.stateDAO = stateDAO;
	}
	
	public List<StateModel> execute(){
		List<StateModel> stateModels = new ArrayList<StateModel>();
		
		stateModels.add(new StateModel(0L, "Todos", "Todos"));
		
		for( State state : this.stateDAO.findAllOrderedBy("name") ){
			stateModels.add(new StateModel(state.getId(), state.getName(), state.getAcronym()));
			
		}
		
		return stateModels;
		
	}

}
