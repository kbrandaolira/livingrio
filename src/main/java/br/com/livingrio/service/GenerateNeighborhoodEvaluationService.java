package br.com.livingrio.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.NeighborhoodCriterion;

@Service
public class GenerateNeighborhoodEvaluationService implements IService<Evaluation> {


	private SincronizeNeighborhoodCriterionsWithCriterionsService sincronizeNeighborhoodCriterionsWithCriterionsService;
	
	
	@Autowired
	public GenerateNeighborhoodEvaluationService(
			SincronizeNeighborhoodCriterionsWithCriterionsService sincronizeNeighborhoodCriterionsWithCriterionsService
			) {
		super();
		this.sincronizeNeighborhoodCriterionsWithCriterionsService = sincronizeNeighborhoodCriterionsWithCriterionsService;
		
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	public ServiceResponse execute(Evaluation arg) {
		
		Set<NeighborhoodCriterion> neighborhoodCriterions = (Set<NeighborhoodCriterion>) sincronizeNeighborhoodCriterionsWithCriterionsService.execute(arg).getObject();
		
		arg.getNeighborhood().setNeighborhoodsCriterion(neighborhoodCriterions);

		for (NeighborhoodCriterion neighborhoodCriterion : neighborhoodCriterions){
			
			StringBuilder queryCount = new StringBuilder(" SELECT count(*) FROM EvaluationCriterion ec WHERE ec.criterion.id = "
					+ neighborhoodCriterion.getCriterion().getId() 
					+ " AND ec.evaluation.neighborhood.id = "+  neighborhoodCriterion.getNeighborhood().getId()
					+ " ");
			
			queryCount.append("AND EXISTS (SELECT status FROM EvaluationStatus status WHERE status.name = 'VALIDATED' AND status.evaluation.id = ec.evaluation.id)  ");
			
			
			Long count = (Long) entityManager.createQuery(queryCount.toString()).getSingleResult();
			count = count - 1;
			
			Double maxRating = neighborhoodCriterion.getOverall();
			
			
			Double overall = ((maxRating * count) + arg.getEvaluationCriterionByCriterionId(neighborhoodCriterion.getCriterion().getId()).getGrade()) / (count + 1);
			
			
			System.out.println("Count: " + count);
			System.out.println("maxRating: " + maxRating);
			System.out.println("overall: " + overall);
			
			neighborhoodCriterion.setOverall(overall);
			
		}
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
