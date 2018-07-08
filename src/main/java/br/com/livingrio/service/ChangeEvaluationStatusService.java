package br.com.livingrio.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.EvaluationStatusName;

@Service
public class ChangeEvaluationStatusService implements IService<Map<String,Object>> {

	private EvaluationDAO evaluationDAO;
	
	private GenerateNeighborhoodEvaluationService generateNeighborhoodEvaluationService;
	
	private EvaluationValidatedMailService evaluationValidatedMailService;
	
	
	@Autowired
	public ChangeEvaluationStatusService(EvaluationDAO evaluationDAO,
			GenerateNeighborhoodEvaluationService generateNeighborhoodEvaluationService,
			EvaluationValidatedMailService evaluationValidatedMailService) {
		super();
		this.evaluationDAO = evaluationDAO;
		this.generateNeighborhoodEvaluationService = generateNeighborhoodEvaluationService;
		this.evaluationValidatedMailService = evaluationValidatedMailService;
	}



	@Transactional
	public ServiceResponse execute(Map<String, Object> map) {
		
		Long evaluationId = (Long) map.get("evaluationId");
		String newStatus = (String) map.get("newStatus");
		
		Evaluation evaluation = evaluationDAO.find(evaluationId);

		if (newStatus.equals(EvaluationStatusName.VALIDATED.toString())){
			evaluation.changeStatusToValidated();
			
			generateNeighborhoodEvaluationService.execute(evaluation);
			
			Map<String,Object> params = new HashMap<String, Object>();
			
			params.put("to", evaluation.getPerson().getEmail() );
			params.put("name", evaluation.getPerson().getName() );
			params.put("neighborhood", evaluation.getNeighborhood().getName() );
			
			evaluationValidatedMailService.execute(params);
			
			
		}else if (newStatus.equals(EvaluationStatusName.DENIED.toString())){
			evaluation.changeStatusToDenied();
		}
		
		return new ServiceResponse(true);
		
	}


}
