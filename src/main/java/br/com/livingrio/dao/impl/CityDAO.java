package br.com.livingrio.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.City;
import br.com.livingrio.model.State;

@Repository
public class CityDAO extends GenericDAO<City, Serializable>{

	public List<City> search(State state) {
		return super.entityManager.createQuery("FROM City c WHERE c.state = :state ORDER BY c.name")
				.setParameter("state", state)
				.getResultList();
		
	}

}
