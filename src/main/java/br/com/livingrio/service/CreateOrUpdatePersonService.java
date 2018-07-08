package br.com.livingrio.service;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.NeighborhoodDAO;
import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.model.Gender;
import br.com.livingrio.model.Person;

@Service
public class CreateOrUpdatePersonService implements IService<Map<String,Object>>{

	private PersonDAO personDAO;
	
	private NeighborhoodDAO neighborhoodDAO;
	
	private GeneratePasswordService generatePasswordService;
	
	private MD5EncoderService md5EnconderService;
	
	private SavePersonProfileImageService saveImageService;
	
	@Autowired
	public CreateOrUpdatePersonService(PersonDAO personDAO,NeighborhoodDAO neighborhoodDAO,GeneratePasswordService generatePasswordService,MD5EncoderService md5EnconderService,SavePersonProfileImageService saveImageService) {
		super();
		this.personDAO = personDAO;
		this.generatePasswordService = generatePasswordService;
		this.md5EnconderService = md5EnconderService;
		this.neighborhoodDAO = neighborhoodDAO;
		this.saveImageService = saveImageService;
	}

	/**
	 * 
	 *  Parâmetros:
	 *  - email
	 *  - name
	 *  - password
	 *  - birthDate
	 *  - profileImageBytes
	 * 
	 */
	@Transactional
	public ServiceResponse execute(Map<String, Object> params) {
				
		//TODO Verificar novamente se pessoa existe no banco de dados.
		
		Person person;
		
		if (params.get("id") != null){
			person = personDAO.find(Long.parseLong(params.get("id").toString()));
		}else{
			 person = new Person();
			 person.setPassword(params.get("password") == null ? generatePasswordService.execute(null).getObject().toString() : params.get("password").toString());
			 person.setPassword(md5EnconderService.execute(person.getPassword()).getObject().toString());
		}
		
	
		person.setEmail(params.get("email").toString());
		person.setName(params.get("name").toString());
		
		person.setGender(Gender.valueOf(params.get("gender").toString().toUpperCase()));
				
		if (params.get("birthDate") != null){
			
			String[] birthDateArray = params.get("birthDate").toString().split("/");
			
			Calendar birthDate = Calendar.getInstance();
			
			birthDate.set(Calendar.DATE, Integer.parseInt(birthDateArray[0]));
			birthDate.set(Calendar.MONTH, Integer.parseInt(birthDateArray[1] ) - 1);
			birthDate.set(Calendar.YEAR, Integer.parseInt(birthDateArray[2]));
			
			person.setBirthDate(birthDate);
			
		}
		
		if (params.get("neighborhoodId") != null && Long.parseLong(params.get("neighborhoodId").toString()) > 0L){
			
			person.setNeighborhood(neighborhoodDAO.find(Long.parseLong(params.get("neighborhoodId").toString())));
			
		}

		if (person.getId() != null){
			personDAO.update(person);
		}else{
			personDAO.create(person);
		}
		
		
		
		byte[] imageFacebookInputStream = (byte[]) params.get("imageFacebookInputStream");
		
		if (imageFacebookInputStream != null){
		
			params = new HashMap<String, Object>();
			
			params.put("person", person);
			params.put("dir", person.getFilesBaseDir());
			params.put("fileName","profile.jpg");
			params.put("inputStream", new ByteArrayInputStream(imageFacebookInputStream));
			
			saveImageService.execute(params);
		
		}
		
		
		return new ServiceResponse(true,person);
		
	}

}
