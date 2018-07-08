package br.com.livingrio.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationService implements IService<Map<String,Object>> {

	private MD5EncoderService md5EncoderService;
	
	private AuthenticationManager authenticationManager;

	private HttpServletRequest httpServletRequest;
	
	@Autowired
	 public CustomAuthenticationService(
			 @Qualifier("authenticationManager")  AuthenticationManager authenticationManager,
			HttpServletRequest httpServletRequest,MD5EncoderService md5EncoderService) {
		super();
		this.authenticationManager = authenticationManager;
		this.httpServletRequest = httpServletRequest;
		
		this.md5EncoderService = md5EncoderService;
	}

	
	public ServiceResponse execute(Map<String, Object> params) {
		
		String email = params.get("email").toString();
		
		String password = params.get("password").toString();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password,null);
		
		authRequest.setDetails(new WebAuthenticationDetails(httpServletRequest));
		
		Authentication authResult = authenticationManager.authenticate(authRequest);
		
		SecurityContextHolder.getContext().setAuthentication(authResult);		
		
		return new ServiceResponse(true);
	}
	
	
}
