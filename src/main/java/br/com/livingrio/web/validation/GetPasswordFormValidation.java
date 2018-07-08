package br.com.livingrio.web.validation;

import java.util.ArrayList;
import java.util.List;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.MD5EncoderService;
import br.com.livingrio.web.params.CreatePersonFormModel;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.GetPasswordFormModel;
import br.com.livingrio.web.params.LoginFormModel;

public class GetPasswordFormValidation implements Validation<GetPasswordFormModel>{

	private PersonDAO personDAO;
	
	public GetPasswordFormValidation(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public List<ValidationMessage> validate(GetPasswordFormModel object) {
		
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		

		if(object.getEmail() == null || object.getEmail().equals("")){
			
			messages.add(new ValidationMessage("O campo e-mail � obrigat�rio.", MessageType.ERROR));
		
			
			
		}else{
			
			if(!object.getEmail().contains("@")){
				
				messages.add(new ValidationMessage("E-mail inv�lido.", MessageType.ERROR));
				
			}
			
		}
		
		if (messages.size() == 0){
			
			Person person = personDAO.findByEmail(object.getEmail());
			
			if(person == null){
				
				messages.add(new ValidationMessage("E-mail n�o cadastrado em nosso sistema.", MessageType.ERROR));
				
			}
			
		}
		

		return messages;
		
	}

}
