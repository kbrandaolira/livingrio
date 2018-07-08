package br.com.livingrio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Person;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.ProfileFormModel;

@Service
public class CreateProfileFormModelService {

	private EvaluationDAO evaluationDAO;
	
	private PersonDAO personDAO;
	
	private CreatePostModelService createEvaluationFormModelService;
	
	@Autowired
	public CreateProfileFormModelService( EvaluationDAO evaluationDAO, PersonDAO personDAO, CreatePostModelService createEvaluationFormModelService ){
		this.evaluationDAO = evaluationDAO;
		this.personDAO = personDAO;
		this.createEvaluationFormModelService = createEvaluationFormModelService;
		
	}
	
	public ProfileFormModel execute( Long idPerson, Person user ){
		ProfileFormModel model = null;
		
		if( idPerson != null ){
			model = new ProfileFormModel();
			Person person = this.personDAO.find( idPerson );
			
			model.setId( person.getId() );
			model.setName( person.getName() );
			model.setEmail( person.getEmail() );
			model.setAge( person.myAge() );
			
			if ( person.getNeighborhood() != null ){
				model.setNeighborhoodName( person.getNeighborhood().getName() );
				model.setNeighborhoodId( person.getNeighborhood().getId() );
				
			}
			
/*			List<EvaluationFormModel> evaluationsModels = new ArrayList<EvaluationFormModel>();
			
			for ( Evaluation aux : this.evaluationDAO.getEvaluationsApprovedFromPerson( person ) ){
				EvaluationFormModel evaluationFormModel = new EvaluationFormModel();
				evaluationFormModel = this.createEvaluationFormModelService.execute( aux, user );
				
				evaluationsModels.add(evaluationFormModel);
					
			}
			
			model.setEvaluations( evaluationsModels );
			
*/			
		}
		
		return model;
					
	}
	
		
}
