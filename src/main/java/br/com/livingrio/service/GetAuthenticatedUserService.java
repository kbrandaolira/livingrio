package br.com.livingrio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.PersonDAO;

@Service
public class GetAuthenticatedUserService implements IService{

	private PersonDAO personDAO;

	@Autowired
	public GetAuthenticatedUserService(PersonDAO personDAO) {
		super();
		this.personDAO = personDAO;
	}

	public ServiceResponse execute(Object notNecessary) {
		return new ServiceResponse(true, personDAO.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
		
	}

}
