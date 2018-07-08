package br.com.livingrio.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.CreateIndexFormModelService;

@Controller
public class IndexController {

	private CreateIndexFormModelService createIndexFormModelService;
	
	private EvaluationDAO evaluationDAO;
	
	@Autowired
	public IndexController(CreateIndexFormModelService createIndexFormModelService, EvaluationDAO evaluationDAO){
		this.createIndexFormModelService = createIndexFormModelService;
		this.evaluationDAO = evaluationDAO;
	}
	
	
	
	
	@RequestMapping("/privacyPolicy")
	public ModelAndView privacyPolicy(HttpServletRequest httpServletRequest){
		
		return new ModelAndView("privacyPolicy");
		
	}
	
	@RequestMapping("/")
	public ModelAndView prepare(HttpServletRequest httpServletRequest){
		
		ModelAndView mv = new ModelAndView("index");
		
		HttpSession session = httpServletRequest.getSession();
		
		if (session.getAttribute("userAuthenticated") != null){
			
			Person authUser = (Person) session.getAttribute("userAuthenticated");
			
			if (authUser.getNeighborhood() != null){
				List<Evaluation> evaluation = evaluationDAO.getPersonEvaluationsFromHisNeighborhood(authUser);
				
				if(evaluation != null && evaluation.size() > 0){
					mv.addObject("didAuthUserAvaliatedHisNeighborhood", true);
				}else{
					mv.addObject("didAuthUserAvaliatedHisNeighborhood", false);
				}
				
			}else{
				mv.addObject("didAuthUserAvaliatedHisNeighborhood", false);
			}

		}else{
			mv.addObject("didAuthUserAvaliatedHisNeighborhood", false);
		}
		
		
		mv.addObject( "model", this.createIndexFormModelService.execute() );
		
		return mv;
		
	}
	
}
