package br.com.livingrio.web.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.web.params.CreatePersonFormModel;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;

public class UpdatePersonFormValidation implements Validation<CreatePersonFormModel>{

	private PersonDAO personDAO;
	
	public UpdatePersonFormValidation(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public List<ValidationMessage> validate(CreatePersonFormModel object) {
		
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		

		if(object.getEmail() == null || object.getEmail().equals("")){
			
			messages.add(new ValidationMessage("O campo e-mail � obrigat�rio.", MessageType.ERROR));
		
			
			
		}else{
			
			if(!object.getEmail().contains("@")){
				
				messages.add(new ValidationMessage("E-mail inv�lido.", MessageType.ERROR));
				
			}else{
				//if(personDAO.findByEmail(object.getEmail()) != null){
					//messages.add(new ValidationMessage("O e-mail informado j� est� cadastrado em nossa base de dados.", MessageType.ERROR));
					// TODO Verificar se e-mail foi alterado e ent�o verificar se existe na base de dados
				//}
				
			}
			
		}
		
		

		if(object.getName() == null || object.getName().equals("")){
			
			messages.add(new ValidationMessage("O campo nome � obrigat�rio.", MessageType.ERROR));
			
		}

		
		
		if(object.getBirthDate() == null || object.getBirthDate().equals("")){
			
			messages.add(new ValidationMessage("O campo data de nascimento � obrigat�rio.", MessageType.ERROR));
			
		}else{
			
			
			String[] data = object.getBirthDate().split("/");
			
			if(data.length != 3){
				messages.add(new ValidationMessage("Data de nascimento inv�lida.", MessageType.ERROR));
			}else{
				
				
				try {
				
				int dia = Integer.parseInt(data[0]);
				int mes = Integer.parseInt(data[1]);
				int ano = Integer.parseInt(data[2]);
				
					if (dia > 31 || dia < 0 || mes < 0 || mes > 12 || ano < 1900 || ano > Calendar.getInstance().get(Calendar.YEAR) ){
						messages.add(new ValidationMessage("Data de nascimento inv�lida.", MessageType.ERROR));
					}
				
				}catch(Exception e){
					
					messages.add(new ValidationMessage("Data de nascimento inv�lida.", MessageType.ERROR));
					
				}
				
				
			}
			
		}
		
		if(object.getGender() == null || object.getGender().equals("")){
			
			messages.add(new ValidationMessage("O campo g�nero � obrigat�rio.", MessageType.ERROR));
			
		}
		

		return messages;
		
	}

}
