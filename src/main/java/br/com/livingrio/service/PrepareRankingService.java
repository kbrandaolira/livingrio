package br.com.livingrio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.dao.impl.NeighborhoodCriterionDAO;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.web.params.CriterionModel;
import br.com.livingrio.web.params.NeighborhoodModel;

@Service
public class PrepareRankingService {

	private NeighborhoodCriterionDAO neighborhoodCriterionDAO;
	
	private CriterionDAO criterionDAO;
	
	@Autowired
	public PrepareRankingService(NeighborhoodCriterionDAO neighborhoodCriterionDAO, CriterionDAO criterionDAO){
		this.neighborhoodCriterionDAO = neighborhoodCriterionDAO;
		this.criterionDAO = criterionDAO;
		
	}
	
	public List<NeighborhoodModel> execute(Long idState, Long idCity, Long idRegion){
		List<NeighborhoodModel> neighborhoodModels = this.neighborhoodCriterionDAO.top(20, idState, idCity, idRegion);
		
		for( NeighborhoodModel neighModel : neighborhoodModels ){
			for( Criterion criterion : (List<Criterion>) this.criterionDAO.available() ){
				CriterionModel critModel = this.neighborhoodCriterionDAO.getCriterionModelBy( criterion.getId(), neighModel.getId() );
				neighModel.addCriterionModel( critModel );
				
			}
			
		}
		
		return neighborhoodModels;
	}
	
}
