package br.com.livingrio.web.validation;

import java.util.ArrayList;
import java.util.List;

import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;

public class EvaluationFormValidation implements Validation<EvaluationFormModel>{

	public List<ValidationMessage> validate(EvaluationFormModel object) {
		
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		
		for (EvaluationCriterionDTO ec : object.getEvaluationCriterionList()){
			
			if(ec.getGrade() == null || ec.getGrade() < 1 || ec.getGrade() > 5){
				messages.add(new ValidationMessage("A avaliação para " + ec.getCriterionName() + " é obrigatória.",MessageType.ERROR));
			}
			
		}
		
		return messages;
		
	}

}
