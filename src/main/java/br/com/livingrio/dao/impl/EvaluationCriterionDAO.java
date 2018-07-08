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
import br.com.livingrio.model.Post;
import br.com.livingrio.web.params.EvaluationByType;

@Repository
public class EvaluationCriterionDAO  extends GenericDAO<Post, Long>{

	private NeighborhoodDAO neighborhoodDAO;
	
	private PersonDAO personDAO;
	
	@Autowired
	public EvaluationCriterionDAO(NeighborhoodDAO neighborhoodDAO,
			PersonDAO personDAO) {
		super();
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
	public List<Post> search(Long id, EvaluationByType type, String fieldOrderBy, 
			Calendar startDate, int maxResult, List<Long> evaluationsIds ) {
		
		StringBuilder queryBuilder = new StringBuilder();
		int cont = 0;

		queryBuilder.append( "SELECT e from Post e " );
		
		if( startDate != null || (id != null && type != null) || evaluationsIds != null ){
			queryBuilder.append(" WHERE ");
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

			queryBuilder.append( " :startDate <= e.createdDate " );
			cont++;
			
		}
		
		if( fieldOrderBy != null ){
			queryBuilder.append( " ORDER BY "+ fieldOrderBy +" DESC" );

		}
		
		Query query = super.entityManager.createQuery(queryBuilder.toString());

		
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
		
		return query.getResultList(	);
	}
	
	
}
