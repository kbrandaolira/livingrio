package br.com.livingrio.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.EvaluationCriterionDAO;
import br.com.livingrio.dao.impl.EvaluationDAO;
import br.com.livingrio.dao.impl.LikedDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Liked;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Post;

@Service
public class LikedService {

	private LikedDAO likedDAO;
	
	private EvaluationCriterionDAO evaluationCriterionDAO;
	
	@Autowired
	public LikedService(LikedDAO likedDAO, EvaluationCriterionDAO evaluationCriterionDAO){
		this.likedDAO = likedDAO;
		this.evaluationCriterionDAO = evaluationCriterionDAO;
		
	}
	
	@Transactional
	public void execute(Person person, Long postId, Boolean isLike) throws Exception {
		Post post = this.evaluationCriterionDAO.find(postId);
		
		List<Liked> likes = this.likedDAO.search(post, person, null);

		Liked oldLike = null;
		
		//Caso o usuário já possua um Like ou Dislike para uma avaliação, devemos atualizá-la.
		if( likes != null && likes.size() > 0 ){
			oldLike = likes.get(0);
			oldLike.setLiked(isLike);
			this.likedDAO.update(oldLike);
			
		//Caso não possua, devemos criar
		} else {
			this.likedDAO.create( this.prepare(post, isLike, person) );
		
		}
		
	}
	
	private Liked prepare( Post evaluation, Boolean isLike, Person person ){
		Liked liked = new Liked();
		
		liked.setPost( evaluation );
		liked.setLiked(isLike);
		liked.setPerson(person);
		
		return liked;
	}
	
	
}
