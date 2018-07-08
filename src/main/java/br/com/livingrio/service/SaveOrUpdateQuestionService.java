package br.com.livingrio.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.EvaluationCriterionDAO;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.dao.impl.QuestionDAO;
import br.com.livingrio.model.Answer;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Question;
import br.com.livingrio.web.params.QuestionModel;

@Service
public class SaveOrUpdateQuestionService implements IService<QuestionModel>{
	
	private EvaluationDAO evaluationDAO;
	
	private EvaluationCriterionDAO evaluationCriterionDAO;
	
	private QuestionDAO questionDAO;
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private PersonDAO personDAO;
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	
	private NewQuestionMailService newQuestionMailService;
	
	private QuestionAnsweredMailService questionAnsweredMailService;
	
	@Autowired
	public SaveOrUpdateQuestionService(EvaluationDAO evaluationDAO, 
			EvaluationCriterionDAO evaluationCriterionDAO, 
			QuestionDAO questionDAO,
			NeighborhoodDAO neighborhoodDAO,
			PersonDAO personDAO,
			GetAuthenticatedUserService getAuthenticatedUserService,
			NewQuestionMailService notifyEvaluationResponsibleHasNewQuestionService,
			QuestionAnsweredMailService notifyQuestionResponsibleHasNewAnswerService) {
		super();
		this.evaluationDAO = evaluationDAO;
		this.evaluationCriterionDAO = evaluationCriterionDAO;
		this.questionDAO = questionDAO;
		this.neighborhoodDAO = neighborhoodDAO;
		this.personDAO= personDAO;
		this.getAuthenticatedUserService = getAuthenticatedUserService;
		this.newQuestionMailService = notifyEvaluationResponsibleHasNewQuestionService;
		this.questionAnsweredMailService = notifyQuestionResponsibleHasNewAnswerService;
	}

	@Transactional
	public ServiceResponse execute(QuestionModel model) {

		Question question;
		
		if (model.getQuestionId() != null){
			
			question = questionDAO.find(model.getQuestionId());
			
			if(model.getAnswer() != null && !model.getAnswer().equals("")){
				
				Answer answer = new Answer();
				answer.setCreationDate(Calendar.getInstance());
				answer.setQuestion(question);
				answer.setText(model.getAnswer());
				answer.setResponsible(personDAO.find(model.getAnswerResponsibleId()));
				
				question.setAnswer(answer);
				
				question = questionDAO.update(question);
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("question",question);
				params.put("to",question.getResponsible().getEmail());
				
				questionAnsweredMailService.execute(params);
				
			}
			
			
		}else{
			
			question = new Question();
			
			question.setCreationDate(Calendar.getInstance());
			
			Evaluation evaluation = evaluationDAO.find(model.getEvaluationId());
			
			question.setEvaluation(evaluation);
			
			question.setText(model.getQuestion());
			
			//TODO REFATORAR, ISTO TEM QUE VIM POR PARAM E NAO BUSCAR AQUI!
			question.setResponsible((Person) getAuthenticatedUserService.execute(null).getObject());
			
			question = questionDAO.update(question);
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("question",question);
			params.put("to",question.getEvaluation().getPerson().getEmail());
			
			newQuestionMailService.execute(params);
			
			
		}
			
		
		
		return  new ServiceResponse(true, question);
		
	}

}
