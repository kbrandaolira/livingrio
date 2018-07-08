package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class GetPasswordFormModel {
		
	@Getter @Setter
	private String email;
	
	@Getter @Setter
	private String password;

}
