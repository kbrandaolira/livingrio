package br.com.livingrio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.service.ContactMailService;
import br.com.livingrio.web.params.ContactModel;

@Controller
public class ContactController {

	private ContactMailService sendContactEmailService;
	
	@Autowired
	public ContactController( ContactMailService sendContactEmailService ){
		this.sendContactEmailService = sendContactEmailService;
	}
	
	@RequestMapping( value="/contact", method=RequestMethod.GET)
	public String init(Model model){
		model.addAttribute("contact", new ContactModel());
		return "contact";
		
	}
	
	@RequestMapping( value="/contact", method=RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute(value="contact") ContactModel contact){
		ModelAndView mv = new ModelAndView("contact");
		System.out.println(contact);

		try{
			this.sendContactEmailService.execute(this.convertContactModelToMap(contact));
			mv.addObject("success", "E-mail enviado com sucesso. Obrigado por entrar em contato conosco.");
		}catch( Exception e ){
			e.printStackTrace();
			mv.addObject("error", "Erro ao enviar o e-mail. Desculpe o transtorno.");
		}
		
		return mv;
		
	}

	private Map<String, Object> convertContactModelToMap(ContactModel contact) {
		Map params = new HashMap<String, String>();
		
		params.put("to", "livingrio@livingrio.com.br");
		
		if(contact != null){
			StringBuilder stringBlder = new StringBuilder();
			
			stringBlder.append("<p><b>"+ contact.getName() +"<b> (" + contact.getEmail() +") </p>");
			
			stringBlder.append("<br>");
			
			stringBlder.append("<p>"+ contact.getMessage() +"</p>");
			
			params.put("body", stringBlder.toString() );
			
			params.put("replyTo", contact.getEmail() );
			
		}
		
		
		
		return params;
	}
	
}
