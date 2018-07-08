package br.com.livingrio.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Gender;
import br.com.livingrio.model.Person;
import br.com.livingrio.service.CreateOrUpdatePersonService;
import br.com.livingrio.service.CreateProfileFormModelService;
import br.com.livingrio.service.GetAuthenticatedUserService;
import br.com.livingrio.service.SavePersonProfileImageService;
import br.com.livingrio.service.ServiceResponse;
import br.com.livingrio.service.aws.s3.GetFileFromS3Service;
import br.com.livingrio.web.params.CreatePersonFormModel;
import br.com.livingrio.web.params.ProfileFormModel;
import br.com.livingrio.web.validation.MessageType;
import br.com.livingrio.web.validation.UpdatePersonFormValidation;
import br.com.livingrio.web.validation.ValidationMessage;

@Controller
public class ProfileController {

	private PersonDAO personDAO;

	private CreateProfileFormModelService createProfileFormModelService;

	private SavePersonProfileImageService saveImageService;
	
	private GetAuthenticatedUserService getAuthenticatedUserService;
	
	private CreateOrUpdatePersonService createPersonService;
	
	private GetFileFromS3Service getFileFromS3Service;
	
	@Autowired
	public ProfileController(PersonDAO personDAO, CreateProfileFormModelService createProfileFormModelService, 
			SavePersonProfileImageService saveImageService,GetAuthenticatedUserService getAuthenticatedUserService,CreateOrUpdatePersonService createPersonService) {
		super();
		this.personDAO = personDAO;
		this.saveImageService = saveImageService;
		this.getAuthenticatedUserService = getAuthenticatedUserService;
		this.createProfileFormModelService = createProfileFormModelService;
		this.createPersonService = createPersonService;
		getFileFromS3Service = new GetFileFromS3Service();
		
	}

	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	@Transactional
	public ModelAndView profile(HttpServletRequest request, @PathVariable Long id) {
		ModelAndView mv = new ModelAndView("profile");
		
		Person user = (Person) request.getSession().getAttribute("userAuthenticated");
		
		ProfileFormModel profileFormModel = this.createProfileFormModelService.execute( id, user );

		mv.addObject("model", profileFormModel);

		return mv;

	}
	
	@RequestMapping(value="/profile/{id}/editProcess", method = RequestMethod.POST)
	public ModelAndView form(
			@ModelAttribute("createAccountForm") CreatePersonFormModel accountForm, 
			HttpServletRequest request, 
			HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView("editProfile");
		
		List<ValidationMessage> messages = new UpdatePersonFormValidation(personDAO).validate(accountForm);
		
		if(messages.size() == 0){			
			
			Map<String,Object> params = new HashMap<String, Object>();
			
			params.put("id", accountForm.getId());
			params.put("name", accountForm.getName());
			params.put("email", accountForm.getEmail());
			params.put("birthDate", accountForm.getBirthDate());
			params.put("neighborhoodId", accountForm.getNeighborhoodId());
			params.put("gender", accountForm.getGender());
			
			ServiceResponse serviceResponse = createPersonService.execute(params);
			
			mv = editProfile(request, accountForm.getId());
			
			if (serviceResponse.isOk()){
				
				messages.add(new ValidationMessage("Perfil alterado com sucesso!", MessageType.INFO));
				
				
			}else{
				
				messages.add(new ValidationMessage("Ocorreu um erro ao tentar alterar sua conta, tente novamente mais tarde.", MessageType.ERROR));
				
			}
			
			
		}
		
		mv.addObject("messages",messages);
		
		return mv;
		
	}
	
	@RequestMapping(value = "/profile/{id}/edit", method = RequestMethod.GET)
	@Transactional
	public ModelAndView editProfile(HttpServletRequest request, @PathVariable Long id) {
		ModelAndView mv = new ModelAndView("editProfile");
		
		Person user = (Person) request.getSession().getAttribute("userAuthenticated");
		
		if (!id.equals(user.getId())){
			
			return new ModelAndView("redirect:/profile/" + id);
			
		}

		ProfileFormModel profileFormModel = this.createProfileFormModelService.execute( id, user );
		
		Person person = personDAO.find(id);
		
		CreatePersonFormModel model = new CreatePersonFormModel();
		model.setId(person.getId());
		model.setName(person.getName());
		model.setEmail(person.getEmail());
		model.setGender(person.getGender().name().toUpperCase());
		
		if (person.getBirthDate() != null)
			model.setBirthDate( new SimpleDateFormat("dd/MM/yyyy").format(person.getBirthDate().getTime()));
		
		if (person.getNeighborhood() != null){
			model.setNeighborhoodId(person.getNeighborhood().getId());
			model.setNeighborhoodName(person.getNeighborhood().getName());
			model.setNeighborhoodSpecification(person.getNeighborhood().getSpecification());
		}
		
		
		mv.addObject("createAccountForm", model);
		mv.addObject("model", profileFormModel);

		return mv;

	}
	

	@RequestMapping(value = "/profile/{id}/photo/{size}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> profilePhoto(@PathVariable Long id,@PathVariable String size) {

		Person person = personDAO.find(id);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		
		if (person.getPicture() != null){
			params.put("bucketName", person.getPicture().getBucket());			
			params.put("dir", ApplicationInfo.AWS_S3_PEOPLE_FILE_BASE_DIR + person.getId() + "/");
			params.put("fileName", size + "_" + person.getPicture().getFileName());
			
			
			
		}else{
			
			params.put("bucketName", "livingrio-filesystem");
				
				params.put("dir", "commons/");
			
				

			if (person.getGender().equals(Gender.MALE)){
				params.put("fileName", "no-photo-man.jpg");
			}else{
				params.put("fileName", "no-photo-woman.jpg");
			}
			
		

			
		}
		
		return new ResponseEntity<byte[]>((byte[]) getFileFromS3Service.execute(params).getObject(), headers, HttpStatus.CREATED);
		

	}
	
	
	@RequestMapping(value = "/profile/photo/upload", method = RequestMethod.POST)
	@Transactional
	   public @ResponseBody String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {                 
	 
	     //0. notice, we have used MultipartHttpServletRequest
	 
	     //1. get the files from the request object
	     Iterator<String> itr =  request.getFileNames();
	 
	     MultipartFile mpf = request.getFile(itr.next());
	     System.out.println(mpf.getOriginalFilename() +" uploaded!");
	 
	    
	 
	    Map<String,Object> params = new HashMap<String, Object>();
			
			params.put("person", getAuthenticatedUserService.execute(null).getObject());
			params.put("dir", ((Person)getAuthenticatedUserService.execute(null).getObject()).getFilesBaseDir());
			params.put("fileName", mpf.getOriginalFilename());
			params.put("inputStream", mpf.getInputStream());
			
			saveImageService.execute(params);
	 
	     return "<img src='http://localhost:8080/spring-mvc-file-upload/rest/cont/get/"+Calendar.getInstance().getTimeInMillis()+"' />";
	 
	  }
	
}
