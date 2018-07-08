package br.com.livingrio.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.City;
import br.com.livingrio.model.Region;

@Repository
public class RegionDAO extends GenericDAO<Region, Serializable> {

	public List<Region> search(City city) {
		return super.entityManager.createQuery("FROM Region r WHERE r.city = :city ORDER BY r.name")
				.setParameter("city", city)
				.getResultList();
		
	}
	
}
