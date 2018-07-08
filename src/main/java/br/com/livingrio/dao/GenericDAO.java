package br.com.livingrio.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class GenericDAO<T,I extends Serializable> {

	@PersistenceContext
	protected EntityManager entityManager;
	

	protected final Class<T> clazz;

	public GenericDAO() {
		super();

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		this.clazz = clazz;

	}

	public void create(T entity) {
		entityManager.persist(entity);
	}

	public T update(T entity) {
		
		return entityManager.merge(entity);
		
	}

	public void destroy(T entity) {
		
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	public T find(I id) {
		return entityManager.find(clazz, id);
	}

	public List<T> findAll() {
		Query query = entityManager.createQuery("from " + clazz.getName());

		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();

		return resultList;
	}
	
	public List<T> findAllOrderedBy(String fieldOrderBy) {
		Query query = entityManager.createQuery("from " + clazz.getName() + " ORDER BY " + fieldOrderBy);

		@SuppressWarnings("unchecked")
		List<T> resultList = query.getResultList();

		return resultList;
	}

}
