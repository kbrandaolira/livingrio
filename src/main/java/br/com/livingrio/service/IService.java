package br.com.livingrio.service;

/**
 * 
 * This interface defines how the services will be.
 *
 * @param The parameter that service will receive.
 * 
 */
public interface IService<K> {

	public ServiceResponse execute (K arg);
		
}
