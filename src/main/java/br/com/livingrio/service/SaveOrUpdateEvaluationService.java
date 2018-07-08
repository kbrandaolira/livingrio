package br.com.livingrio.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.dao.impl.EvaluationCriterionDAO;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Post;
import br.com.livingrio.model.Person;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;

@Service
public class SaveOrUpdateEvaluationService implements IService<EvaluationFormModel>{
	
	private EvaluationDAO evaluationDAO;
	
	private EvaluationCriterionDAO evaluationCriterionDAO;
	
	private CriterionDAO criterionDAO;
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	
	private NewEvaluationMailService newEvaluationMailService;
	
	@Autowired
	public SaveOrUpdateEvaluationService(EvaluationDAO evaluationDAO, 
			EvaluationCriterionDAO evaluationCriterionDAO, 
			CriterionDAO criterionDAO,
			NeighborhoodDAO neighborhoodDAO,
			GetAuthenticatedUserService getAuthenticatedUserService,
			NewEvaluationMailService newEvaluationMailService) {
		super();
		this.evaluationDAO = evaluationDAO;
		this.evaluationCriterionDAO = evaluationCriterionDAO;
		this.criterionDAO = criterionDAO;
		this.neighborhoodDAO = neighborhoodDAO;
		this.getAuthenticatedUserService = getAuthenticatedUserService;
		this.newEvaluationMailService = newEvaluationMailService;
	}

	@Transactional
	public ServiceResponse execute(EvaluationFormModel evaluationFormModel) {

		Evaluation evaluation;
		
		if (evaluationFormModel.getEvaluationId() == null){
			evaluation = new Evaluation();
			evaluation.setDate(Calendar.getInstance());	
			evaluation.setNeighborhood(neighborhoodDAO.find(evaluationFormModel.getNeighborhoodId()));
			evaluation.changeStatusToWaitingForValidation();
			evaluation.setPerson((Person) getAuthenticatedUserService.execute(null).getObject());
		}else{
			evaluation = evaluationDAO.find(evaluationFormModel.getEvaluationId());
		}
	
		evaluation.setComment(evaluationFormModel.getComment());
		
		for (EvaluationCriterionDTO ecDTO : evaluationFormModel.getEvaluationCriterionList()){
			
			Post ec;
			
			if (ecDTO.getEvaluationCriterionId() == null){
				
				ec = new Post();
				
				ec.setCriterion(criterionDAO.find(ecDTO.getCriterionId()));
				
				ec.setEvaluation(evaluation);
				
				evaluation.addEvaluationCriteria(ec);
				
			}else{
				ec = evaluationCriterionDAO.find(ecDTO.getEvaluationCriterionId());
			}
			
			ec.setComment(ecDTO.getComment());
			ec.setGrade(ecDTO.getGrade());
			
		}
		
		evaluation = evaluationDAO.update(evaluation);
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("to","livingrio@livingrio.com.br");
		
		newEvaluationMailService.execute(params);
		
		return  new ServiceResponse(true, evaluation);
		
	}

}
