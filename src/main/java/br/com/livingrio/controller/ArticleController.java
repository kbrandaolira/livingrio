package br.com.livingrio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.dao.impl.EvaluationCriterionDAO;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.EvaluationStatusName;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Post;
import br.com.livingrio.service.CreatePostModelService;
import br.com.livingrio.utils.CalendarUtils;
import br.com.livingrio.utils.LongUtils;
import br.com.livingrio.web.params.EvaluationByType;
import br.com.livingrio.web.params.EvaluationFormModel;
import br.com.livingrio.web.params.FilterPeriod;

@Controller
public class ArticleController {

	private EvaluationDAO evaluationDAO;
	
	private EvaluationCriterionDAO evaluationCriterionDAO;
	
	private CreatePostModelService createEvaluationFormModelService;
	
	@Autowired
	public ArticleController( EvaluationDAO evaluationDAO, CriterionDAO criterionDAO,EvaluationCriterionDAO evaluationCriterionDAO, CreatePostModelService createEvaluationFormModelService ){
		this.evaluationDAO = evaluationDAO;
		this.createEvaluationFormModelService = createEvaluationFormModelService;
		this.evaluationCriterionDAO= evaluationCriterionDAO;

	}
	
	/**
	 * @param
	 * id do bairro ou pessoa
	 * type string que ajuda a identificar o retorno, se será, avaliações da pessoa ou do bairro
	 * filter tipo do filtro, podendo ser, ALL, YEAR e MONTH
	 * evaluationsIds os ids da avaliações que já estão presentes na tela
	 * 
	 * @return List<EvaluationFormModel>
	*/
	@RequestMapping(value="/article/search", method = RequestMethod.GET)
	public @ResponseBody List<EvaluationFormModel> search(
			HttpServletRequest request, 
			@RequestParam Long identifier, 
			@RequestParam EvaluationByType type, 
			@RequestParam FilterPeriod filter, 
			@RequestParam String evaluationsIds){
		
		//List<Evaluation> evaluations = this.evaluationDAO.search( EvaluationStatusName.VALIDATED, identifier, type, "e.date", CalendarUtils.getDateAgoBy(filter), 9, LongUtils.makeStringToListLongDelimetedBy(evaluationsIds, ",") );
				
		Person user = (Person) request.getSession().getAttribute( "userAuthenticated" );
		
		List<Post> posts = evaluationCriterionDAO.search( identifier, type, "e.createdDate", CalendarUtils.getDateAgoBy(filter), 9, LongUtils.makeStringToListLongDelimetedBy(evaluationsIds, ",") );
		
		List<EvaluationFormModel> evaluationModels = new ArrayList<EvaluationFormModel>();
		
		if( posts != null && posts.size() > 0 ){
			
			for(Post evaluation : posts ){
				evaluationModels .add( this.createEvaluationFormModelService.execute( evaluation, user ) );
			}
			
		}
		
		
		
		return evaluationModels;
	}
	
	@RequestMapping(value = "/article/evaluationById", method = RequestMethod.GET)
	public @ResponseBody EvaluationFormModel evaluationById(HttpServletRequest request, @RequestParam Long id) {
		Evaluation evaluation = this.evaluationDAO.find(id);
		
		return createEvaluationFormModelService.execute( null, null );
 
	}
	
}
