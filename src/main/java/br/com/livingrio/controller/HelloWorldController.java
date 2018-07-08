package br.com.livingrio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.dao.impl.PersonDAO;

@Controller
public class HelloWorldController {

	String message = "Welcome to Spring MVC Framework!";
	
	private PersonDAO personDAO;
	
	@Autowired
	public HelloWorldController(PersonDAO personDAO) {
		super();
		this.personDAO = personDAO;
	}

	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name){
	
		
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		mv.addObject("personDAO", personDAO.find(1L).getName());
	
		return mv;
		
	}
	
	
	@RequestMapping("/admin")
	public ModelAndView showMessageADmin(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name){
	
		
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", "ADMINNNNNNNN");
		mv.addObject("personDAO", personDAO.find(1L).getName());
	
		return mv;
		
	}
	
}
