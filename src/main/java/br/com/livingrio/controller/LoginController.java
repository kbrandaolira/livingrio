package br.com.livingrio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.config.auth.facebook.CustomFacebookConnectionFactory;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.CreateOrUpdatePersonService;
import br.com.livingrio.service.CustomAuthenticationService;
import br.com.livingrio.service.GetPasswordService;
import br.com.livingrio.service.LikedService;
import br.com.livingrio.service.MD5EncoderService;
import br.com.livingrio.service.ServiceResponse;
import br.com.livingrio.service.NewUserMailService;
import br.com.livingrio.web.params.CreatePersonFormModel;
import br.com.livingrio.web.params.GetPasswordFormModel;
import br.com.livingrio.web.params.LoginFormModel;
import br.com.livingrio.web.validation.CreatePersonFormValidation;
import br.com.livingrio.web.validation.GetPasswordFormValidation;
import br.com.livingrio.web.validation.LoginFormValidation;
import br.com.livingrio.web.validation.MessageType;
import br.com.livingrio.web.validation.ValidationMessage;

@Controller
public class LoginController {

	private PersonDAO personDAO;
	
	private NeighborhoodDAO neighborhoodDAO;

	private CreateOrUpdatePersonService createPersonService;

	private CustomAuthenticationService customAuthenticateService;
	
	private MD5EncoderService md5EncoderService;
	
	private ReCaptchaImpl reCaptcha;
	
	private GetPasswordService getPasswordService;
	
	private NewUserMailService newUserMailService;
	
	@Autowired
	public LoginController(PersonDAO personDAO, CreateOrUpdatePersonService createPersonService,
			CustomAuthenticationService facebookAuthenticationService, MD5EncoderService md5EncoderService,
			ReCaptchaImpl reCaptcha, GetPasswordService getPasswordService, NeighborhoodDAO neighborhoodDAO, 
			LikedService likedService, EvaluationDAO evaluationDAO, NewUserMailService newUserMailService ) {
		super();
		this.personDAO = personDAO;
		this.createPersonService = createPersonService;
		this.customAuthenticateService = facebookAuthenticationService;
		this.md5EncoderService = md5EncoderService;
		this.reCaptcha = reCaptcha;
		this.getPasswordService = getPasswordService;
		this.neighborhoodDAO = neighborhoodDAO;
		this.newUserMailService = newUserMailService;
	}

	@RequestMapping("/login")
	public ModelAndView login() {

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("createAccountForm", new CreatePersonFormModel());
		mv.addObject("loginForm", new LoginFormModel());
		mv.addObject("getPasswordForm",new GetPasswordFormModel());
		
		return mv;

	}
	
	@RequestMapping("/register")
	public ModelAndView register() {

		ModelAndView mv = new ModelAndView("register");
		mv.addObject("createAccountForm", new CreatePersonFormModel());
		mv.addObject("loginForm", new LoginFormModel());
		mv.addObject("getPasswordForm",new GetPasswordFormModel());
		
		return mv;

	}
	
	@RequestMapping(value="/processRegister", method = RequestMethod.POST)
	public ModelAndView processRegister(
			@ModelAttribute("createAccountForm") CreatePersonFormModel createAccountForm, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		
		ModelAndView mv = new ModelAndView("register");
		
		List<ValidationMessage> messages = new CreatePersonFormValidation(personDAO).validate(createAccountForm);
		
		if(messages.size() == 0){			
			
			Map<String,Object> params = new HashMap<String, Object>();
			
			params.put("name", WordUtils.capitalizeFully( createAccountForm.getName() ) );
			params.put("email", createAccountForm.getEmail());
			params.put("password", createAccountForm.getPassword());
			params.put("birthDate", createAccountForm.getBirthDate());
			params.put("neighborhoodId", createAccountForm.getNeighborhoodId());
			params.put("gender", createAccountForm.getGender());
			
			ServiceResponse serviceResponse = createPersonService.execute(params);
			
			if (serviceResponse.isOk()){
				
				Person person = (Person) serviceResponse.getObject();
				
				//Enviar E-mail
				Map<String,Object> emailParams = new HashMap<String, Object>();
				emailParams.put("to", person.getEmail());
				emailParams.put("person", person);
				
				this.newUserMailService.execute(emailParams);
				
				Map<String, Object> authenticationParams = new HashMap<String, Object>();
				
				authenticationParams.put("email", person.getEmail());
				authenticationParams.put("password", person.getPassword());

				customAuthenticateService.execute(authenticationParams);
				
				SavedRequest savedRequest = 
					    new HttpSessionRequestCache().getRequest(request, response);
				
				if (savedRequest == null){
					return new ModelAndView("redirect:/");
				}
				
				return new ModelAndView("redirect:" + savedRequest.getRedirectUrl());
				
				
			}else{
				
				messages.add(new ValidationMessage("Ocorreu um erro ao tentar criar sua conta, tente novamente mais tarde.", MessageType.ERROR));
				
			}
			
			
		}
		
		if (messages.size() > 0){
			
			if(createAccountForm.getNeighborhoodId() != null){
				Neighborhood neighborhood = neighborhoodDAO.find(createAccountForm.getNeighborhoodId());
				
				createAccountForm.setNeighborhoodName(neighborhood.getName());
				createAccountForm.setNeighborhoodSpecification(neighborhood.getSpecification());
				
			}
			
		}
		
		mv.addObject("loginForm",new LoginFormModel());
		mv.addObject("getPasswordForm",new GetPasswordFormModel());
		mv.addObject("createAccountValidationMessages",messages);
		
		return mv;
		
	}
	
	/**
	 * Método responsável pelo Login de um usuário já cadastrado VIA LIVINGRIO
	*/
	@RequestMapping(value="/processLogin", method = RequestMethod.POST)
	public ModelAndView form(
			@ModelAttribute("loginForm") LoginFormModel loginForm, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		
		ModelAndView mv = new ModelAndView("login");
		
		List<ValidationMessage> messages = new LoginFormValidation(personDAO).validate(loginForm);
		
		
	
		
		
		if(messages.size() == 0){			
			
				Map<String,Object> authenticationParams = new HashMap<String, Object>();
							
				authenticationParams.put("email", loginForm.getEmail());
				authenticationParams.put("password", md5EncoderService.execute(loginForm.getPassword()).getObject().toString());

				customAuthenticateService.execute(authenticationParams);
				
				SavedRequest savedRequest = 
					    new HttpSessionRequestCache().getRequest(request, response);
				
				if (savedRequest == null){
					return new ModelAndView("redirect:/");
				}
				
				return new ModelAndView("redirect:" + savedRequest.getRedirectUrl());
			
		}
		
		mv.addObject("loginFormValidationMessages",messages);
		
		mv.addObject("createAccountForm",new CreatePersonFormModel());
		
		mv.addObject("getPasswordForm",new GetPasswordFormModel());
		
		return mv;
		
	}
	
	
	@RequestMapping(value="/getPassword", method = RequestMethod.POST)
	public ModelAndView form(
			@ModelAttribute("getPasswordForm") GetPasswordFormModel getPasswordForm, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView("login");
		
		List<ValidationMessage> errorMessages = new GetPasswordFormValidation(personDAO).validate(getPasswordForm);
		List<ValidationMessage> successMessages = new ArrayList<ValidationMessage>();
		
		
		if (errorMessages.size() == 0){
			
			ServiceResponse serviceResponse = getPasswordService.execute(getPasswordForm.getEmail());
			
			if(serviceResponse.isOk()){
				
				successMessages.add(new ValidationMessage("Uma mensagem com seus dados de acesso foi enviada para seu e-mail.", MessageType.INFO));
				
			}else{
				
				errorMessages.add(new ValidationMessage(serviceResponse.getMessage(), MessageType.ERROR));
				
			}
			
		}
		
		
		mv.addObject("getPasswordFormErrorMessages",errorMessages);
		
		mv.addObject("getPasswordFormSuccessMessages",successMessages);
		
		mv.addObject("createAccountForm",new CreatePersonFormModel());
		
		mv.addObject("loginForm",new LoginFormModel());
		
		return mv;
		
	}
	
	
	
	@RequestMapping(value = "/facebookAuthentication", method = RequestMethod.GET)
	public ModelAndView facebookAuthentication(HttpServletRequest request, HttpServletResponse response) {

		try {

			FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
					ApplicationInfo.FACEBOOK_AUTH_ID,
					ApplicationInfo.FACEBOOK_AUTH_SECRET);
			
			OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
			
			OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
			oAuth2Parameters.setScope("user_about_me,user_birthday,user_status,email");
			oAuth2Parameters.add("display", "popup");

			
			
			String serverPath = ApplicationInfo.APPLICATION_URL + "processFacebookAuthentication";
			oAuth2Parameters.setRedirectUri(serverPath);
			
			System.out.println("-- TESTE0 ---");
			System.out.println("URL: " + serverPath);

			String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
			System.out.println("authorizeUrl: " + authorizeUrl);
			return new ModelAndView("redirect:" + authorizeUrl);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ModelAndView("redirect:" + null);
		}
	}

	@RequestMapping(value = "/processFacebookAuthentication", method = RequestMethod.GET)
	public ModelAndView processFacebookAuthentication(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		if (code != null && !code.isEmpty()) {

			FacebookConnectionFactory connFactory = new CustomFacebookConnectionFactory();

			OAuth2Operations oauth2Operations = connFactory.getOAuthOperations();

			AccessGrant accessGrant = null;
			
			try{
				
				accessGrant = oauth2Operations.exchangeForAccess(code, ApplicationInfo.APPLICATION_URL + "processFacebookAuthentication", null);
			
			
			}catch(HttpClientErrorException e){
				System.out.println("JSON Error: " + e.getStatusCode() + " - " + e.getStatusText());
				System.out.println(e.getResponseBodyAsString());
				
			}
			
			Connection<Facebook> connection = connFactory.createConnection(accessGrant);
			
			Facebook facebook = connection.getApi();			

			if (facebook.isAuthorized()) {

				FacebookProfile fp = facebook.userOperations().getUserProfile();

				if (fp.getEmail() != null && !fp.getEmail().isEmpty()) {

					Person person = personDAO.findByEmail(fp.getEmail());

					if (person == null) {

						Map<String, Object> personInfo = new HashMap<String, Object>();

						personInfo.put("name", fp.getName());

						personInfo.put("email", fp.getEmail());
						
						System.out.println("THE GENDER: " + fp.getGender());
						
						personInfo.put("gender", fp.getGender() == null ? "Male" : fp.getGender());
						
						
						
						personInfo.put("birthDate", fp.getBirthday());
										
						personInfo.put("imageFacebookInputStream", facebook.userOperations().getUserProfileImage(ImageType.LARGE));
						
						person = (Person) createPersonService.execute(personInfo).getObject();
						
						
						

					}
					
					Map<String, Object> authenticationParams = new HashMap<String, Object>();
					
					authenticationParams.put("email", person.getEmail());
					authenticationParams.put("password", person.getPassword());

					customAuthenticateService.execute(authenticationParams);

				}else{
					
					// Email pode não ter sido verificado, pendente de verificação, 
					// mandar para tela de cadastro com alguns dados já preenchido e mensagem de nao foi possível linkar ao facebook
					
				}
				
			}
		}
		
		
		
		SavedRequest savedRequest = 
			    new HttpSessionRequestCache().getRequest(request, response);
		
		if (savedRequest == null){
			return new ModelAndView("redirect:/");
		}
		
		return new ModelAndView("redirect:" + savedRequest.getRedirectUrl());
		

	}
	
}