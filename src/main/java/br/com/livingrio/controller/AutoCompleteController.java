package br.com.livingrio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.livingrio.service.AutoCompleteService;
import br.com.livingrio.web.params.AutoCompleteModel;

@Controller
public class AutoCompleteController {
	
	private static final int MAX_RESULT = 10;
	
	private AutoCompleteService service;
	
	@Autowired
	public AutoCompleteController(AutoCompleteService service){
		this.service = service;
	}
	
	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocomplete(@RequestParam String name) {
		return service.execute(name, MAX_RESULT);
 
	}
		

}
