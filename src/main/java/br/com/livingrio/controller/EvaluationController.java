package br.com.livingrio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.converters.EvaluationConverter;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.dao.impl.QuestionDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Grade;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Question;
import br.com.livingrio.service.CreatePostModelService;
import br.com.livingrio.service.GetAuthenticatedUserService;
import br.com.livingrio.service.SaveOrUpdateEvaluationService;
import br.com.livingrio.service.SaveOrUpdateQuestionService;
import br.com.livingrio.service.ServiceResponse;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.QuestionModel;
import br.com.livingrio.web.params.ResponsibleQuestionsInfoDTO;
import br.com.livingrio.web.validation.EvaluationFormValidation;
import br.com.livingrio.web.validation.ValidationMessage;

@Controller()
public class EvaluationController {
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private SaveOrUpdateEvaluationService saveOrUpdateEvaluationService;
	
	private SaveOrUpdateQuestionService saveOrUpdateQuestionService;
	
	private EvaluationDAO evaluationDAO;
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	
	private CreatePostModelService createEvaluationFormModelService;
	
	@Autowired
	public EvaluationController(PersonDAO personDAO, NeighborhoodDAO neighborhoodDAO, EvaluationDAO evaluationDAO,
			SaveOrUpdateEvaluationService saveOrUpdateEvaluationService, EvaluationConverter evaluationConverter, 
			GetAuthenticatedUserService getAuthenticatedUserService, SaveOrUpdateQuestionService saveOrUpdateQuestionService, QuestionDAO questionDAO,
			CreatePostModelService createEvaluationFormModelService) {
		super();
		this.neighborhoodDAO = neighborhoodDAO;
		this.evaluationDAO = evaluationDAO;
		this.saveOrUpdateEvaluationService = saveOrUpdateEvaluationService;
		this.getAuthenticatedUserService = getAuthenticatedUserService;
		this.saveOrUpdateQuestionService= saveOrUpdateQuestionService;
		this.createEvaluationFormModelService = createEvaluationFormModelService;
	}

	@RequestMapping(value="/neighborhood/{neighborhoodId}/evaluate", method = RequestMethod.GET)
	public ModelAndView	form(@PathVariable Long neighborhoodId){
	
		ModelAndView mv = new ModelAndView("evaluation");
		mv.addObject("neighborhood", neighborhoodDAO.find(neighborhoodId));
		mv.addObject("gradeList", Grade.values());
		
		EvaluationFormModel model = createEvaluationFormModelService.execute( null, null );
		
		model.setNeighborhoodId(neighborhoodId);
		
		mv.addObject("form",model );
	
		return mv;
		
	}
	
	@RequestMapping(value="/neighborhood/{neighborhoodId}/evaluate/{evaluationId}", method = RequestMethod.GET)
	public ModelAndView	form(@PathVariable Long neighborhoodId, @PathVariable Long evaluationId){
		
		ModelAndView mv = new ModelAndView("evaluation");
		
		mv.addObject("neighborhood", neighborhoodDAO.find(neighborhoodId));
		mv.addObject("gradeList", Grade.values());

		Evaluation evaluation = evaluationDAO.find(evaluationId);
		
		//TODO  Refatorar, passar post
		
		EvaluationFormModel model = createEvaluationFormModelService.execute( null, null );
		
		mv.addObject("form",model);
	
		return mv;
		
	}
	
	@RequestMapping(value="/neighborhood/{neighborhoodId}/evaluate/{evaluationId}", method = RequestMethod.POST)
	public ModelAndView	form(@PathVariable Long neighborhoodId, @PathVariable Long evaluationId,  @ModelAttribute("form") EvaluationFormModel form){
		
		return form(neighborhoodId,form);
		
	}
	
	@RequestMapping(value="/neighborhood/{neighborhoodId}/evaluate", method = RequestMethod.POST)
	public ModelAndView form(@PathVariable Long neighborhoodId, @ModelAttribute("form") EvaluationFormModel form){
		
		ModelAndView mv;
		
		
		List<ValidationMessage> messages = new EvaluationFormValidation().validate(form);
		
	    if (messages.size() > 0) {
	    	
	    	mv = new ModelAndView("evaluation");
 
	    	mv.addObject("messages",messages);
 
	    	mv.addObject("neighborhood", neighborhoodDAO.find(neighborhoodId));
	    	 
        }else{
        	
        	Evaluation evaluation = (Evaluation) saveOrUpdateEvaluationService.execute(form).getObject();
        	
        	mv = new ModelAndView( "redirect:/neighborhood/"+ neighborhoodId +"/evaluate/"+ evaluation.getId() +"/success" );
        	 
        	 
        	
        }

		return mv;
		
	}
	
	@RequestMapping(value="/neighborhood/{neighborhoodId}/evaluate/{evaluationId}/success", method = RequestMethod.GET)
	public ModelAndView success(@PathVariable Long neighborhoodId, @PathVariable Long evaluationId){
		
		Evaluation evaluation = evaluationDAO.find(evaluationId);
		//TODO Refatorar, talvez nem exista mais
		EvaluationFormModel model = createEvaluationFormModelService.execute( null, null );
		
		ModelAndView mv = new ModelAndView("evaluationSuccess");
		
		mv.addObject("form",model);
		
		return mv;
		
	}

	
	@RequestMapping(value="/evaluation/questions", method = RequestMethod.GET)
	public @ResponseBody List<QuestionModel> questions(@RequestParam Long id){
		
		Evaluation evaluations = this.evaluationDAO.find(id);
		
		List<Question> questions = this.evaluationDAO.getQuestionsFromEvaluationOrderByCreationDate(evaluations);
	
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
		
		
		return questionsModel;
	}
	
	
	@RequestMapping(value="/evaluation/questions/verifyPostQuestionPermissions", method = RequestMethod.GET)
	public @ResponseBody ServiceResponse verifyPostQuestionPermissions(@RequestParam Long id){
					
		Evaluation evaluation = evaluationDAO.find(id);
		
		Person authenticatedUser = (Person) getAuthenticatedUserService.execute(null).getObject();
		
		if(authenticatedUser == null){
			
			// Necessário logar para enviar uma pergunta
			
			return new ServiceResponse("NEEDLOGIN", true);
			
		}else if(evaluation.getPerson().getId().equals(authenticatedUser.getId())){
			
			// Caso em que o usuário logado é o dono da avaliação, neste caso, o formulário de envio de perguntas não é exibido.
			// O que é exibido são algumas mensagens para auxíliar o avaliador sobre as perguntas.
		
			if(evaluation.getQuestions() != null && evaluation .getQuestions().size() > 0){
				
				if(evaluation.hasQuestionsWithoutAnswer()){
					
					// Existe perguntas para serem respondidas, será exibido um link para página de responder perguntas.
					
					ResponsibleQuestionsInfoDTO dto = new ResponsibleQuestionsInfoDTO();
					
					dto.setResponsibleId(evaluation.getPerson().getId());
					dto.setName(evaluation.getPerson().getName());
					dto.setQuestionsWithoutAnswers(evaluationDAO.getQuestionsToUserWithoutAnswer(evaluation.getPerson()).size());
					
					
					return new ServiceResponse("ANSWERQUESTIONS", true,dto);
				}else{
				
					// Não possui nenhuma pergunta para ser respondida, todas as perguntas foram respondidas.
					// Exibe uma mensagem de apoio e agradecimento pela contribuição com a 
					
					return new ServiceResponse("THANKYOU", true);
				
				}
				
			}else{
				
				// Não existem perguntas para serem respondidas, provavelmente nada será exibido na tela.
				
				return new ServiceResponse("DONTHAVEQUESTIONS", true);
				
			}
			
			
			
		}else if(evaluation.getQuestions() != null && evaluation.getQuestions().size() > 0 && evaluation.hasQuestionsWithoutAnswerFromUser(authenticatedUser)){
			
			// Caso tenha alguma mensagem do usuário logado sem resposta, não será possível o envio de uma nova mensagem.
			// Isto acontece para evitar qualquer tipo de FLOOD.
							
			return new ServiceResponse("FORMDISABLE", true);

			
		}else{
			
			// Habilita formulário de envio de perguntas.
			
			return new ServiceResponse("FORM", true);
			
		}
		
	}
	
	
	@RequestMapping(value="/evaluations/questions/new", method = RequestMethod.POST)
    public @ResponseBody List<QuestionModel>  submitQuestion(@RequestBody QuestionModel model, HttpServletRequest request) {	

		saveOrUpdateQuestionService.execute(model);
		
		return questions(model.getEvaluationId());
		
	}	
	
	
	
}
