package br.com.livingrio.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Person;

@Service
public class GetPasswordService implements IService<String>{

	private PersonDAO personDAO;
	
	private GeneratePasswordService generatePasswordService;
	
	private RecoverPasswordMailService recoverPasswordMailService;
	
	private MD5EncoderService md5EncoderService;
	
	
	
	@Autowired public GetPasswordService(PersonDAO personDAO,
			GeneratePasswordService generatePasswordService,
			RecoverPasswordMailService recoverPasswordMailService,
			MD5EncoderService md5EncoderService) {
		super();
		this.personDAO = personDAO;
		this.generatePasswordService = generatePasswordService;
		this.recoverPasswordMailService = recoverPasswordMailService;
		this.md5EncoderService = md5EncoderService;
	}



	@Transactional
	public ServiceResponse execute(String email) {
		
		Person person = personDAO.findByEmail(email);
		
		String password = generatePasswordService.execute(null).getObject().toString();
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("to", person.getEmail());
		params.put("name", person.getName());
		params.put("email", person.getEmail());
		params.put("password", password);
		
		person.setPassword(md5EncoderService.execute(password).getObject().toString());
		
		recoverPasswordMailService.execute(params);
		
		return new ServiceResponse(true);
	}

}
