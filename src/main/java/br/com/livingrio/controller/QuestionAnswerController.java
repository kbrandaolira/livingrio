package br.com.livingrio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.dao.impl.QuestionDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Question;
import br.com.livingrio.service.CreateProfileFormModelService;
import br.com.livingrio.service.GetAuthenticatedUserService;
import br.com.livingrio.service.SaveOrUpdateEvaluationService;
import br.com.livingrio.service.SaveOrUpdateQuestionService;
import br.com.livingrio.web.params.AnswerQuestionFormModel;
import br.com.livingrio.web.params.ProfileFormModel;
import br.com.livingrio.web.params.QuestionModel;

@Controller()
public class QuestionAnswerController {
	
	private PersonDAO personDAO;
	
	private SaveOrUpdateQuestionService saveOrUpdateQuestionService;
	
	private EvaluationDAO evaluationDAO;
	
	private CreateProfileFormModelService createProfileFormModelService;
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	
	private QuestionDAO questionDAO;
	
	@Autowired
	public QuestionAnswerController(PersonDAO personDAO, EvaluationDAO evaluationDAO,
			SaveOrUpdateEvaluationService saveOrUpdateEvaluationService,
			GetAuthenticatedUserService getAuthenticatedUserService,
			SaveOrUpdateQuestionService saveOrUpdateQuestionService,
			CreateProfileFormModelService createProfileFormModelService,QuestionDAO questionDAO) {
		super();
		this.personDAO = personDAO;
		this.evaluationDAO = evaluationDAO;
		this.getAuthenticatedUserService = getAuthenticatedUserService;
		this.saveOrUpdateQuestionService= saveOrUpdateQuestionService;
		this.createProfileFormModelService = createProfileFormModelService;
		this.questionDAO = questionDAO;
	}

	
	
	@RequestMapping(value="/profile/{id}/questions/answer", method = RequestMethod.GET)
	public ModelAndView	form( @PathVariable Long id ){
		Person authenticatedPerson = (Person) getAuthenticatedUserService.execute(null).getObject();
		
		ModelAndView mv;
		
		if (authenticatedPerson == null || authenticatedPerson.getId() != id){
			
			mv = new ModelAndView("accessDenied");
			
		}else{
			
			mv = new ModelAndView("answerQuestions");
			
			Person person = personDAO.find(id);

			List<Question> questions = evaluationDAO.getQuestionsToUserWithoutAnswer(person);
			
			mv.addObject("questions", questions);
			
			ProfileFormModel profileFormModel = this.createProfileFormModelService.execute( id, authenticatedPerson );
			mv.addObject("model", profileFormModel);
		}
		
		return mv;
		
	}
	
	@RequestMapping(value="/profile/{id}/questions/answer", method = RequestMethod.POST)
	public @ResponseBody AnswerQuestionFormModel formAjaxSubmit(@RequestBody AnswerQuestionFormModel form,@PathVariable Long id, HttpServletRequest request){
	
		QuestionModel questionModel = new QuestionModel();
		
		questionModel.setQuestionId(form.getQuestionId());
		questionModel.setAnswer(form.getAnswerText());
		questionModel.setAnswerResponsibleId(((Person)getAuthenticatedUserService.execute(null).getObject()).getId());
		
		saveOrUpdateQuestionService.execute(questionModel);
		
		return form;
		
	}
	
	@RequestMapping(value="/profile/{id}/questions", method = RequestMethod.GET)
	public ModelAndView getUserQuestions(@PathVariable Long id, HttpServletRequest request){
	
		//TODO Refatorar este método, colocar em outro controller

		ModelAndView mv = new ModelAndView("myQuestions");
		
		List<Question> questions = questionDAO.getQuestionsFromUser(id);
		
		List<QuestionModel> questionsModel = new ArrayList<QuestionModel>();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm");
		
		for (Question question : questions){
			
			QuestionModel model = new QuestionModel();
			
			model.setQuestion(question.getText());
			
			model.setQuestionCreationDate(simpleDateFormat.format(question.getCreationDate().getTime()));
			
			if(question.getResponsible() != null){
			
				model.setQuestionResponsibleName(question.getResponsible().getName());
				model.setQuestionResponsibleId(question.getResponsible().getId());
				
			}
			
			if(question.getAnswer() != null){
				
				model.setAnswer(question.getAnswer().getText());
				
				model.setAnswerCreationDate(simpleDateFormat.format(question.getAnswer().getCreationDate().getTime()));
				
				if(question.getAnswer().getResponsible() != null){
					
					model.setAnswerResponsibleId(question.getAnswer().getResponsible().getId());
					model.setAnswerResponsibleName(question.getAnswer().getResponsible().getName());
					
				}
				
			}
			
			
			questionsModel.add(model);
			
		}
		
		mv.addObject("questions", questionsModel);
		
		Person authenticatedPerson = (Person) getAuthenticatedUserService.execute(null).getObject();
		ProfileFormModel profileFormModel = this.createProfileFormModelService.execute( id, authenticatedPerson );
		mv.addObject("model", profileFormModel);
		
		return mv;
		
	}
	

	
}
