package br.com.livingrio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Person;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.IndexFormModel;
import br.com.livingrio.web.params.ProfileFormModel;

@Service
public class CreateIndexFormModelService {

	private EvaluationDAO evaluationDAO;
	
	private PersonDAO personDAO;
	
	@Autowired
	public CreateIndexFormModelService(EvaluationDAO evaluationDAO, PersonDAO personDAO){
		super();
		this.evaluationDAO = evaluationDAO;
		this.personDAO = personDAO;
		
	}
	
	public IndexFormModel execute(){
		IndexFormModel model = new IndexFormModel();
		
		//Adicionando avaliações para os últimos três comentários na Index
		for(Evaluation evaluation : this.evaluationDAO.getLastWithGeneralComment(3) ){
			 
			 EvaluationFormModel evaluationModel = new EvaluationFormModel();
			 
			 evaluationModel.setEvaluationId( evaluation.getId() );
			 evaluationModel.setCreatorId( evaluation.getPerson().getId() );
			 evaluationModel.setCreatorName( evaluation.getPerson().getName() );
			 evaluationModel.setNeighborhoodName( evaluation.getNeighborhood().getName() );
			 evaluationModel.setNeighborhoodSpecification( evaluation.getNeighborhood().getSpecification() );
			 evaluationModel.setComment( evaluation.getSubstringComment(0, 255) );
			 evaluationModel.setOverall( evaluation.getNeighborhood().overall() );
			 
			 model.addEvaluationModel( evaluationModel );
			 
		 }
		
		//Adicionando pessoas para as quatro imagens que aparecerão na Index
		for(Person person : this.personDAO.getPeopleWithEvaluationAndPicture(12)){
			ProfileFormModel profileModel = new ProfileFormModel();
			profileModel.setId( person.getId() );
			profileModel.setName( person.getName() );
			profileModel.setEmail( person.getEmail() );
			profileModel.setPictureId( person.getPicture().getId() );
		
			model.addProfileModel( profileModel );
		}
		
		return model;
		
	}
	
}
