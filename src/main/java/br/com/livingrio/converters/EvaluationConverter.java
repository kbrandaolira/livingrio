package br.com.livingrio.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Post;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;

@Component
public class EvaluationConverter implements IConverter<Evaluation, EvaluationFormModel> {

	private EvaluationDAO evaluationDAO;
	
	private NeighborhoodDAO neighborhoodDAO;
	
	@Autowired
	public EvaluationConverter(EvaluationDAO evaluationDAO,NeighborhoodDAO neighborhoodDAO) {
		super();
		this.evaluationDAO = evaluationDAO;
		this.neighborhoodDAO = neighborhoodDAO;
	}

	public EvaluationFormModel toDTO(Evaluation arg) {
		
		EvaluationFormModel model = new EvaluationFormModel();
		
		model.setEvaluationId(arg.getId());
		model.setNeighborhoodId(arg.getNeighborhood().getId());
		
		List<EvaluationCriterionDTO> evaluationsCriterionDTO = new ArrayList<EvaluationCriterionDTO>();
		
		
		for(Post ec : arg.getEvaluationCriterion()){
			
			EvaluationCriterionDTO ecDTO = new EvaluationCriterionDTO();
			
			ecDTO.setComment(ec.getComment());
			ecDTO.setCriterionId(ec.getCriterion().getId());
			ecDTO.setEvaluationCriterionId(ec.getId());
			ecDTO.setGrade(ec.getGrade());
			
			evaluationsCriterionDTO.add(ecDTO);
			
		}
		
		model.setEvaluationCriterionList(evaluationsCriterionDTO);
		
		return model;
	}

	public Evaluation toReal(EvaluationFormModel arg) {
		
		Evaluation evaluation = new Evaluation();
		
		if (arg.getEvaluationId() != null){
			evaluation = evaluationDAO.find(arg.getEvaluationId());
		}

		evaluation.setNeighborhood(neighborhoodDAO.find(arg.getNeighborhoodId()));
		
		for (EvaluationCriterionDTO dto : arg.getEvaluationCriterionList()){
			
			Post evaluationCriteria;		
			
			if (dto.getEvaluationCriterionId() != null){
				evaluationCriteria = evaluation.getEvaluationCriterionByEvaluationCriterionId(dto.getEvaluationCriterionId());
				
			}else{
				evaluationCriteria = new Post();
			}
			
			evaluationCriteria.setEvaluation(evaluation);
			evaluationCriteria.setCriterion(new Criterion(dto.getCriterionId()));
			evaluationCriteria.setComment(dto.getComment());
			evaluationCriteria.setGrade(dto.getGrade());
			
			if (dto.getEvaluationCriterionId() == null){
				evaluation.addEvaluationCriteria(evaluationCriteria);
			}
		}
		
		
		
		return evaluation;
	}

}
