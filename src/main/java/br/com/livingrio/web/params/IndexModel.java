package br.com.livingrio.web.params;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.livingrio.model.Evaluation;

public class IndexModel {

	@Getter @Setter public List<NeighborhoodModel> neighborhoodModels;
	
	@Getter @Setter public List<Evaluation> evaluations;
	
	public void addNeighborhoodModel( NeighborhoodModel model ){
		if( this.neighborhoodModels == null ){
			this.neighborhoodModels = new ArrayList<NeighborhoodModel>();
			
		}
		
		this.neighborhoodModels.add(model);
		
	}
	
}
