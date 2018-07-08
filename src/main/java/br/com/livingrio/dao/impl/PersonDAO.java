package br.com.livingrio.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Person;

@Repository
public class PersonDAO extends GenericDAO<Person, Long>{

	public Person findByEmail(String username) {
	
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT person FROM Person person WHERE person.email = '" + username + "'");
		
		try{
			return (Person) super.entityManager.createQuery( query.toString() ).getSingleResult();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	
	public Person findByWithLoadingEvaluations(Long id){
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT person FROM Person person JOIN FETCH person.evaluations e JOIN FETCH e.evaluationCriterion ec WHERE person.id = " + id + "");
		
		try{
			
			return (Person) super.entityManager.createQuery( query.toString()).getSingleResult();
			
		}catch(NoResultException exception){
			
			return null;
		}
		
	}

	/**
	 * @param limit is the number of people with evaluation and picture that will return in query
	*/
	public List<Person> getPeopleWithEvaluationAndPicture(int limit) {
		return (List<Person>) super.entityManager.createQuery( "SELECT p FROM Person p, Evaluation ev "
				+ "JOIN p.picture pic WHERE ev.person.id = p.id GROUP BY p.id ORDER BY ev.date DESC" )
				.setMaxResults(limit).getResultList();
		
	}

}
