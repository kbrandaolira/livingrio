package br.com.livingrio.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.exception.InvalidDataException;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Picture;
import br.com.livingrio.service.ChangeMainPictureService;
import br.com.livingrio.service.CreateNeighborhoodModelService;
import br.com.livingrio.service.DeletePictureService;
import br.com.livingrio.service.ManageNeighborhoodService;
import br.com.livingrio.service.ServiceResponse;
import br.com.livingrio.web.params.NeighborhoodModel;

@Controller
public class ManageNeighborhoodController{

	private ManageNeighborhoodService manageNeighborhoodService;
	
	private CreateNeighborhoodModelService createNeighborhoodModelService;
	
	private PictureDAO pictureDAO;
	
	private DeletePictureService deletePictureService;
	
	private ChangeMainPictureService changeMainPictureService;
	
	@Autowired
	public ManageNeighborhoodController( ManageNeighborhoodService manageNeighborhoodService, CreateNeighborhoodModelService createNeighborhoodModelService, DeletePictureService deletePictureService, PictureDAO pictureDAO, ChangeMainPictureService changeMainPictureService ) {
		this.manageNeighborhoodService = manageNeighborhoodService;
		this.createNeighborhoodModelService = createNeighborhoodModelService;
		this.deletePictureService = deletePictureService;
		this.pictureDAO = pictureDAO;
		this.changeMainPictureService = changeMainPictureService;
	}
	
	@RequestMapping( value="/manageNeighborhood/init/{id}", method=RequestMethod.GET )
	public ModelAndView init(HttpServletRequest request, @PathVariable Long id, Model model){
		ModelAndView mv = new ModelAndView("manageNeighborhood");
		
		Person user = (Person) request.getSession().getAttribute("userAuthenticated");
		if( user == null || user.isUser() ){
			mv.setViewName("403");
		}
		
		if( id == 0L ){
			model.addAttribute("model", new NeighborhoodModel());
		} else {
			model.addAttribute( "model", createNeighborhoodModelService.execute(id, null, null, null, null) );
		}
		
		return mv;
		
	}
	
	@RequestMapping( value="/manageNeighborhood/submit", method=RequestMethod.POST )
	public ModelAndView submit( @ModelAttribute(value="model") NeighborhoodModel model ){
		ModelAndView mv = new ModelAndView("manageNeighborhood");
		
		try{
			this.manageNeighborhoodService.execute(model);
			mv.addObject( "response", new ServiceResponse("Bairro salvo com sucesso!", true) );
			mv.getModel().remove("model");
			mv.addObject( "model", new NeighborhoodModel() );
			
			
		} catch(InvalidDataException invalidException){
			if( model.getId() != null ){
				List<Picture> pictures = this.pictureDAO.findBy( model.getId() );
				if( pictures != null && pictures.size() > 0 ){
					model.setPictures( new HashSet<Picture>(pictures) );
					
				}
				
			}
			
			mv.addObject( "response", new ServiceResponse(invalidException.getMessage(), false) );
			
		}
		
		return mv;
	}
	
	
	/**
	 * 
	 * @param id of picture to be deleted
	 * @return String with 0L (no more main to be setted) OR ?L (new main picture id) OR error (exception :p)
	 * 	-1 Error
	 */
	@RequestMapping( value = "/manageNeighborhood/deletePicture/{id}", method = RequestMethod.GET )
	public @ResponseBody String deletePicture( @PathVariable Long id ){
		try{
			return this.deletePictureService.execute( this.pictureDAO.find(id) ).toString();
			
		}catch( Exception e ){
			e.printStackTrace();
			return "error";
			
		}
		
	}
	
	@RequestMapping( value = "/manageNeighborhood/changeMainPicture/{id}", method = RequestMethod.GET )
	public @ResponseBody String changeMainPicture( @PathVariable Long id ){
		System.out.println(id);
		try{
			this.changeMainPictureService.execute( this.pictureDAO.find(id) );
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
			
		}
		
	}
	
}
