package br.com.livingrio.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Liked;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Post;

@Repository
public class LikedDAO extends GenericDAO<Liked, Serializable> {

	@SuppressWarnings("unchecked")
	public List<Liked> search( Post post, Person user, Boolean liked ){
		StringBuilder hql = new StringBuilder();
		hql.append("FROM Liked l ");
		
		if( post != null || user != null || liked != null ){
			hql.append(" WHERE ");
			
		}
		
		if( post != null ){
			hql.append(" l.post = :post ");
			
		}
		
		if( user != null ){
			if( post != null ){
				hql.append(" AND ");
				
			}
			
			hql.append(" l.person = :user" );
			
		}
		
		if( liked != null ){
			if( post != null || user != null ){
				hql.append(" AND ");
				
			}
			
			hql.append(" l.liked = :liked ");
			
		}
		
		Query query = super.entityManager.createQuery( hql.toString() );
		
		if( post != null ){
			query.setParameter("post", post);
		}
		
		if( user != null ){
			query.setParameter("user", user);
		}

		if( liked != null ){
			query.setParameter("liked", liked);
		}
		
		return (List<Liked>) query.getResultList();
		
	}
	
}
