package br.com.livingrio.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.livingrio.dao.GenericDAO;
import br.com.livingrio.model.Criterion;

@Repository
public class CriterionDAO extends GenericDAO<Criterion, Long>{

	public List<Criterion> available(){
		return super.entityManager
				.createQuery("FROM Criterion c WHERE c.startDate <= :today AND (:today <= c.endDate OR c.endDate is null) ORDER BY c.name")
				.setParameter("today", Calendar.getInstance())
				.getResultList();
		
	}
	
}
