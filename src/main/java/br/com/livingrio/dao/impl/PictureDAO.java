package br.com.livingrio.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Picture;

@Repository
public class PictureDAO extends GenericDAO<Picture, Long>{

	public List<Picture> findBy(Long neighborhoodId) {
		return (List<Picture>) super.entityManager.createQuery( "FROM Picture p WHERE p.neighborhood.id = :neighborhoodId"  )
				.setParameter("neighborhoodId", neighborhoodId).getResultList();
		
		
	}

	
}
