package br.com.livingrio.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Neighborhood;

@Repository
public class NeighborhoodDAO extends GenericDAO<Neighborhood, Long>{

	@SuppressWarnings("unchecked")
	public List<Neighborhood> search(String name, int maxResult){
		return (List<Neighborhood>) super.entityManager.createQuery("SELECT n FROM Neighborhood n WHERE n.cleanName LIKE CONCAT('%', :name, '%')")
				.setParameter("name", name)
				.setMaxResults(maxResult)
				.getResultList();
		
	}

	public Neighborhood getByURL(String stateURL, String cityURL, String regionURL, String neighborhoodURL) {
		return (Neighborhood) super.entityManager.createQuery("SELECT n FROM Neighborhood n INNER JOIN  n.city c INNER JOIN c.state s INNER JOIN n.region r WHERE " +
				"n.url = :neighborhood AND c.url = :city AND s.url = :state AND r.url = :region")
			.setParameter("state", stateURL)
			.setParameter("city", cityURL)
			.setParameter("region", regionURL)
			.setParameter("neighborhood", neighborhoodURL)
			.getSingleResult();
	}
	
	public Neighborhood getByURL(String stateURL, String cityURL, String neighborhoodURL) {
		return (Neighborhood) super.entityManager.createQuery("SELECT n FROM Neighborhood n INNER JOIN  n.city c INNER JOIN c.state s WHERE " +
				"n.url = :neighborhood AND c.url = :city AND s.url = :state")
			.setParameter("state", stateURL)
			.setParameter("city", cityURL)
			.setParameter("neighborhood", neighborhoodURL)
			.getSingleResult();
	}
	
	public List<Neighborhood> getBy( Long stateId, Long cityId, Long regionId, String neighborhoodName ){
		List<Neighborhood> neighborhoods = super.entityManager.createQuery("SELECT n FROM Neighborhood n INNER JOIN  n.city c INNER JOIN c.state s INNER JOIN n.region r WHERE " +
				"n.name = :neighborhoodName AND c.id = :cityId AND s.id = :stateId AND r.id = :regionId")
			.setParameter("stateId", stateId)
			.setParameter("cityId", cityId)
			.setParameter("regionId", regionId)
			.setParameter("neighborhoodName", neighborhoodName)
			.getResultList();
		
		if( neighborhoods != null && neighborhoods.size() > 0 ){
			return neighborhoods;
		}
		
		return null;
		
	}
	
}
