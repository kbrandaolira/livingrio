package br.com.livingrio.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.LikedService;

@Controller
public class LikedController {

	private LikedService likedService;
	
	private EvaluationDAO evaluationDAO;
	
	@Autowired
	public LikedController( LikedService likedService, EvaluationDAO evaluationDAO ){
		this.likedService = likedService;
		this.evaluationDAO = evaluationDAO;
		
	}
	
	/**
	 * Esse método será responsável pelas requisições de usuários logados
	*/
	@RequestMapping(value="/liked", method = RequestMethod.GET)
	public @ResponseBody String liked(HttpServletRequest request, @RequestParam Long evaluationId, @RequestParam Boolean liked){

		Person user = (Person)request.getSession().getAttribute("userAuthenticated");
		
		try{
			this.likedService.execute( user, evaluationId, liked );
			return "ok";
			
		} catch( Exception e ){
			e.printStackTrace();
			return "error";
			
		}
		
	}
	
	/**
	 * Esse método será responsável pelas requisições de usuários deslogados
	*/
	@RequestMapping(value="/notLogged/liked/{evaluationId}/{liked}", method = RequestMethod.GET)
	public ModelAndView notLoggedLiked( HttpServletRequest request, @PathVariable Long evaluationId, @PathVariable Boolean liked ) {
		Person user = (Person) request.getSession().getAttribute("userAuthenticated");
		
		try{
			this.likedService.execute( user, evaluationId, liked );
			
		} catch( Exception e ){
			e.printStackTrace();
			
		}

		return new ModelAndView( new RedirectView( request.getContextPath() + "/" + this.evaluationDAO.find( evaluationId ).getNeighborhood().getFullURL() ));
		
		
	}
	
	
}
