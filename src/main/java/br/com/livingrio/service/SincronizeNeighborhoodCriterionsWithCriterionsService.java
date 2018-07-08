package br.com.livingrio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.NeighborhoodCriterion;

@Service
public class SincronizeNeighborhoodCriterionsWithCriterionsService implements IService<Evaluation> {

	private CriterionDAO criterionDAO;
	
	
	@Autowired
	public SincronizeNeighborhoodCriterionsWithCriterionsService(
			CriterionDAO criterionDAO) {
		super();
		this.criterionDAO = criterionDAO;
	}



	@Transactional
	public ServiceResponse execute(Evaluation evaluation) {

		
		Neighborhood neighborhood = evaluation.getNeighborhood();
		
		
		Set<NeighborhoodCriterion> neighborhoodCriterions = neighborhood.getNeighborhoodsCriterion();
		
		
		if(neighborhoodCriterions == null || neighborhoodCriterions.size() == 0){
			
			
			List<Criterion> criterions = criterionDAO.available();
			
			for (Criterion c : criterions){
				
				NeighborhoodCriterion nc = new NeighborhoodCriterion();
				nc.setCriterion(c);
				nc.setNeighborhood(neighborhood);
				nc.setOverall(0.0);
				
				
				neighborhoodCriterions.add(nc);
				
			}
			
			
			
			
		}
		
		
		return new ServiceResponse(true,neighborhoodCriterions);
	}

}
