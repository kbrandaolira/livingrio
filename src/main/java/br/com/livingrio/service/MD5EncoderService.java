package br.com.livingrio.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MD5EncoderService implements IService<String>{

	public ServiceResponse execute(String arg) {

		PasswordEncoder encoder = new Md5PasswordEncoder();		
		
		return new ServiceResponse(true, encoder.encodePassword(arg,null));
	}

}
