package br.com.livingrio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.ChangeEvaluationStatusService;
import br.com.livingrio.service.CreatePostModelService;
import br.com.livingrio.web.params.EvaluationFormModel;

@Controller
public class ValidateEvaluationController {

	String message = "Welcome to Spring MVC Framework!";
	
	private EvaluationDAO evaluationDAO;
	
	private ChangeEvaluationStatusService changeEvaluationStatusService;
	
	private CreatePostModelService createEvaluationFormModelService;
	
	
	@Autowired
	public ValidateEvaluationController(EvaluationDAO evaluationDAO, ChangeEvaluationStatusService changeEvaluationStatusService,
			CreatePostModelService createEvaluationFormModelService ) {
		super();
		this.evaluationDAO = evaluationDAO;
		this.changeEvaluationStatusService = changeEvaluationStatusService;
		this.createEvaluationFormModelService = createEvaluationFormModelService;
	}

	@RequestMapping("/neighborhood/evaluations/validation")
	public ModelAndView listEvaluationsWaitingForValidation(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("validation");
		
		Person authenticatedPerson = (Person) request.getSession().getAttribute("userAuthenticated");
		if( authenticatedPerson == null || authenticatedPerson.isUser() ){
			mv.setViewName("403");
		}
		
		List<Evaluation> evaluations = evaluationDAO.getAllWaitingForValidation();
		
		List<EvaluationFormModel> evaluationsFormModel = new ArrayList<EvaluationFormModel>();
		
		for (Evaluation evaluation : evaluations){
			
			// TODO REFATORAR, PASSAR POST EM VEZ DE EVALUATION
			
			EvaluationFormModel model = createEvaluationFormModelService.execute( null, null );
			
			evaluationsFormModel.add(model);
			
		}

		mv.addObject("evaluations", evaluationsFormModel);
		
		return mv;
		
	}
	
	
	@RequestMapping(value="/neighborhood/evaluations/changeStatus", method = RequestMethod.POST)
	public ModelAndView	changeStatus(HttpServletRequest request, Long evaluationId, String newStatus){
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("evaluationId", evaluationId);
		params.put("newStatus", newStatus);
		
		changeEvaluationStatusService.execute(params);
		
		return listEvaluationsWaitingForValidation(request);
		
	}
	
	
}
