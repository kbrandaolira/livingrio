package br.com.livingrio.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.EvaluationStatusName;
import br.com.livingrio.model.Neighborhood;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Question;
import br.com.livingrio.web.params.EvaluationByType;

@Repository
public class EvaluationDAO extends GenericDAO<Evaluation, Long> {
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private PersonDAO personDAO;
	
	@Autowired
	public EvaluationDAO( NeighborhoodDAO neighborhoodDAO, PersonDAO personDAO ){
		this.neighborhoodDAO = neighborhoodDAO;
		this.personDAO = personDAO;
		
	}
	
	/**
	 * @param
	 * status da Avaliação (Validated, Denied or Waiting_For_Validation)
	 * id ID do Type
	 * type Pode ser Person ou Neighborhood
	 * fieldOrderBy Será ordenado por esse campo
	 * startDate data inicial para poder filtrar por período (startDate até hoje)
	 * maxResult máximo de resultados
	 * evaluationsIds esses ids irão entrar numa cláusula NOT IN
	 * 
	*/
	public List<Evaluation> search( EvaluationStatusName status, Long id, EvaluationByType type, String fieldOrderBy, 
			Calendar startDate, int maxResult, List<Long> evaluationsIds ) {
		
		StringBuilder queryBuilder = new StringBuilder();
		int cont = 0;

		queryBuilder.append( "SELECT e from Evaluation e INNER JOIN e.evaluationStatus es " );
		
		if( status != null || startDate != null || (id != null && type != null) || evaluationsIds != null ){
			queryBuilder.append(" WHERE ");
		}
		
		
		if( status != null ){
			queryBuilder.append( " es.name = :status " );
			cont++;
		}
		
		Neighborhood neighborhood = null;
		Person person = null;
		
		if( id != null && type != null ){
			if( cont > 0 ){
				queryBuilder.append(" AND " );
			}
			
			if( type.equals( EvaluationByType.NEIGHBORHOOD ) ){
				neighborhood = neighborhoodDAO.find(id);
				queryBuilder.append( " e.neighborhood = :neighborhood " );
			}else if( type.equals(EvaluationByType.PERSON ) ){
				person = personDAO.find(id);
				queryBuilder.append( " e.person = :person " );
			}
			
			cont++;
			
		}
		
		if( evaluationsIds != null && evaluationsIds.size() > 0 ){
			if( cont > 0 ){
				queryBuilder.append(" AND ");
			}
			queryBuilder.append( "e.id NOT IN (:evaluationsIds)" );
			
			cont++;
		}
		
		if( startDate != null ){
			if( cont > 0 ){
				queryBuilder.append(" AND ");
			}

			queryBuilder.append( " :startDate <= e.date " );
			cont++;
			
		}
		
		if( fieldOrderBy != null ){
			queryBuilder.append( " ORDER BY "+ fieldOrderBy +" DESC" );

		}
		
		Query query = super.entityManager.createQuery(queryBuilder.toString());
		
		if( status != null ){
			query.setParameter("status", status);
		}
		
		if( neighborhood != null ){
			query.setParameter("neighborhood", neighborhood);
		}
		
		if( person != null ){
			query.setParameter("person", person);
		}
		
		if( evaluationsIds != null && evaluationsIds.size() > 0 ){
			query.setParameter("evaluationsIds", evaluationsIds);
		}
		
		if( startDate != null ){
			query.setParameter("startDate", startDate);
		}
		
		query.setMaxResults(maxResult);
		
		return query.getResultList();
	}

	/** Últimas x @param avaliações validadas com comentário geral
	*/
	public List<Evaluation> getLastWithGeneralComment( int x ) {
		
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append( "SELECT e from Evaluation e INNER JOIN e.evaluationStatus es " );
		
		queryBuilder.append(" WHERE ");
		
		queryBuilder.append( " es.name = 'VALIDATED' " );
		
		queryBuilder.append(" AND e.comment IS NOT NULL AND e.comment <> ''");
	
		queryBuilder.append( " ORDER BY e.date DESC" );
		
		Query query = super.entityManager.createQuery(queryBuilder.toString());
		
		query.setMaxResults(x);
		
		return query.getResultList();
		
	}
	
	public List<Evaluation> getAllWaitingForValidation() {
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT evaluation FROM Evaluation evaluation WHERE");
		
		query.append(" NOT EXISTS (SELECT status FROM EvaluationStatus status WHERE status.name = 'VALIDATED' AND status.evaluation.id = evaluation.id) AND ");
		query.append(" NOT EXISTS (SELECT status FROM EvaluationStatus status WHERE status.name = 'DENIED' AND status.evaluation.id = evaluation.id) ");
		
		return (List<Evaluation>) super.entityManager.createQuery( query.toString())
				.getResultList();

	}
	
	public List<Evaluation> getEvaluationsFromPerson(Person person) {
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT evaluation FROM Evaluation evaluation  WHERE evaluation.person.id = " + person.getId());
		
		
		return (List<Evaluation>) super.entityManager.createQuery( query.toString())
				.getResultList();

	}

	public List<Evaluation> getEvaluationsApprovedFromPerson(Person person) {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT evaluation FROM Evaluation evaluation  WHERE evaluation.person.id = " + person.getId());
		
		query.append(" AND  EXISTS (SELECT status FROM EvaluationStatus status WHERE status.name = 'VALIDATED' AND status.evaluation.id = evaluation.id)  ");
		
		return (List<Evaluation>) super.entityManager.createQuery( query.toString())
				.getResultList();
	}
	
	
	public List<Question> getQuestionsToUserWithoutAnswer(Person person){
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT question FROM Question question WHERE question.answer is null AND question.evaluation.person.id = " + person.getId() + " ORDER BY question.creationDate DESC");
		
		return (List<Question>) super.entityManager.createQuery( query.toString())
				.getResultList();

	}

	public List<Question> getQuestionsFromEvaluationOrderByCreationDate(
			Evaluation evaluations) {


		StringBuilder query = new StringBuilder();
		
		query.append("SELECT question FROM Question question WHERE question.evaluation.id = " + evaluations.getId() + " ORDER BY question.creationDate DESC");
		
		return (List<Question>) super.entityManager.createQuery( query.toString())
				.getResultList();
		
	}

	public List<Evaluation> getPersonEvaluationsFromHisNeighborhood(Person person) {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT evaluation FROM Evaluation evaluation  WHERE evaluation.person.id = " + person.getId() + " AND evaluation.neighborhood.id = " + person.getNeighborhood().getId());
		
		
		return (List<Evaluation>) super.entityManager.createQuery( query.toString())
				.getResultList();
	}
	

}
