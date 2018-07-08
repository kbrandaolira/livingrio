package br.com.livingrio.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.CriterionDAO;
import br.com.livingrio.dao.impl.LikedDAO;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Post;
import br.com.livingrio.model.Liked;
import br.com.livingrio.model.Person;
import br.com.livingrio.web.params.EvaluationCriterionDTO;
import br.com.livingrio.web.params.EvaluationFormModel;

@Service
public class CreatePostModelService {

	private CriterionDAO criterionDAO;

	private LikedDAO likedDAO;
	
	@Autowired
	public CreatePostModelService( CriterionDAO criterionDAO, LikedDAO likedDAO ){
		this.criterionDAO = criterionDAO;
		this.likedDAO = likedDAO;
		
	}
	
	public EvaluationFormModel execute( Post post, Person user ){
		EvaluationFormModel model = new EvaluationFormModel();
		
		if(post != null){
			model.setEvaluationId( post.getId() );
			
			model.setNeighborhoodId( post.getNeighborhood().getId() );
			model.setNeighborhoodName( post.getNeighborhood().getName() );
			model.setNeighborhoodSpecification( post.getNeighborhood().getSpecification() );
			model.setNeighborhoodPictureUrl( post.getNeighborhood().getMainPictureUrl() );
			model.setNeighborhoodUrl( post.getNeighborhood().getFullURL() );

			// TODO REVER OS LINKS
			
			if( user != null ){
				List<Liked> allLikes = this.likedDAO.search( post, user, null );
				model.setLiked( allLikes != null && allLikes.size() > 0 ? allLikes.get(0).getLiked() : null );
			}
			
			List<Liked> likes = this.likedDAO.search( post, null, true );
			List<Liked> dislikes = this.likedDAO.search( post, null, false );
			
			model.setLikesSize( likes != null ? likes.size() : 0 );
			model.setDislikesSize( dislikes != null ? dislikes.size() : 0 );
			
			if(post.getCreatedDate() != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				model.setEvaluationDate( sdf.format(post.getCreatedDate().getTime()) );
				
			}
			
			if (post.getNeighborhood().getMainPicture() != null){
				model.setNeighborhoodPictureUrl( post.getNeighborhood().getMainPicture().getPath() );
				
			}
			
		/*	model.setAverageResult( evaluation.getAverageResult() );*/
			
			if( post.getComment() != null ){
				model.setComment( post.getComment() );
				model.setResumeComment( post.getShortComment(0, 255) );
			}
			
			/*if( evaluation.overall() != null ){
				model.setOverall( evaluation.overall() );
			}*/

			model.setCreatorName( post.getPerson().getName() );
			model.setCreatorAge( post.getPerson().myAge() );
			model.setCreatorId( post.getPerson().getId() );
	
			model.setCriterionId(post.getCriterion().getId());
			model.setCriterionName(post.getCriterion().getName());
			model.setCriterionIconPath(post.getCriterion().getIconLocation());
			
			
		}
		
		
		
		return model;
		
	}
	
}
