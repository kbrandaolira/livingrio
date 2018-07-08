package br.com.livingrio.service;

import lombok.Getter;


/**
 * 
 * This class defines how the services will return your responses.
 * 
 */
public class ServiceResponse {
	
	@Getter
	private String message;
	
	@Getter
	private boolean ok;
	
	@Getter
	private Object object;

	public ServiceResponse(String message, boolean ok, Object object) {
		super();
		this.message = message;
		this.ok = ok;
		this.object = object;
	}

	public ServiceResponse(String message, boolean ok) {
		super();
		this.message = message;
		this.ok = ok;
	}

	public ServiceResponse(boolean ok) {
		super();
		this.ok = ok;
	}

	public ServiceResponse(boolean ok, Object object) {
		super();
		this.ok = ok;
		this.object = object;
	}
	
}
