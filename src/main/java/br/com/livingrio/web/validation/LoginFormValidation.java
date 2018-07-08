package br.com.livingrio.web.validation;

import java.util.ArrayList;
import java.util.List;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.MD5EncoderService;
import br.com.livingrio.web.params.CreatePersonFormModel;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.LoginFormModel;

public class LoginFormValidation implements Validation<LoginFormModel>{

	private PersonDAO personDAO;
	
	public LoginFormValidation(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public List<ValidationMessage> validate(LoginFormModel object) {
		
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		

		if(object.getEmail() == null || object.getEmail().equals("")){
			
			messages.add(new ValidationMessage("O campo e-mail é obrigatório.", MessageType.ERROR));
		
			
			
		}else{
			
			if(!object.getEmail().contains("@")){
				
				messages.add(new ValidationMessage("E-mail inválido.", MessageType.ERROR));
				
			}
			
		}


		if(object.getPassword() == null || object.getPassword().equals("")){
			
			messages.add(new ValidationMessage("O campo senha é obrigatório.", MessageType.ERROR));
			
		}
		
		
		if (messages.size() == 0){
			
			Person person = personDAO.findByEmail(object.getEmail());
			
			if(person == null || person != null && !person.getPassword().equals(new MD5EncoderService().execute(object.getPassword()).getObject().toString())){
				
				messages.add(new ValidationMessage("Dados de acesso inválidos.", MessageType.ERROR));
				
			}
			
		}
		

		return messages;
		
	}

}
