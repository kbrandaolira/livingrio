package br.com.livingrio.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Question;

@Repository
public class QuestionDAO extends GenericDAO<Question, Long>{

	public List<Question> getQuestionsFromUser(Long personId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT question FROM Question question WHERE question.responsible.id = " + personId + " ORDER BY question.creationDate DESC ");
		
		return (List<Question>) super.entityManager.createQuery( query.toString())
				.getResultList();
	}

	public int withoutAnswer( Long personId ) {
		return super.entityManager.createQuery( "SELECT q FROM Question q JOIN q.evaluation e WHERE e.person.id = :personId AND q.answer IS NULL" )
				.setParameter( "personId", personId )
				.getResultList().size();
		
		
	}

	
	
}
