package br.com.livingrio.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livingrio.dao.impl.PersonDAO;
import br.com.livingrio.dao.impl.PictureDAO;
import br.com.livingrio.model.Person;
import br.com.livingrio.model.Picture;
import br.com.livingrio.service.aws.s3.UploadFileToS3Service;



@Service
public class SavePersonProfileImageService implements IService<Map<String,Object>>{

	private PersonDAO personDAO;
	
	private PictureDAO pictureDAO;
	
	private UploadFileToS3Service uploadFileToS3Service;
	
	@Autowired
	public SavePersonProfileImageService(PersonDAO personDAO,PictureDAO pictureDAO) {
		super();
		this.personDAO = personDAO;
		this.pictureDAO = pictureDAO;
		uploadFileToS3Service = new UploadFileToS3Service();
	}

	@Transactional
	public ServiceResponse execute(Map<String,Object> params) {
	
		// Fazendo Upload
		
		params = (Map<String, Object>) uploadFileToS3Service.execute(params).getObject();
		
		// Cadastrando a Imagem
		
		Picture picture = new Picture();
		picture.setBucket((String) params.get("bucketName"));
		picture.setFileName((String) params.get("fileName"));
		pictureDAO.create(picture);
		
		// Setando na pessoa
		
		Person person = personDAO.find(((Person) params.get("person")).getId());
		person.setPicture(picture);
		personDAO.update(person);
		
		
		return new ServiceResponse(true);
	}

}
