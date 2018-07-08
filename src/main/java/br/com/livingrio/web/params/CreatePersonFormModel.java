package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class CreatePersonFormModel {
	
	@Getter @Setter
	private Long id;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String email;
	
	@Getter @Setter
	private String password;
	
	@Getter @Setter
	private String gender;
	
	@Getter @Setter
	private String birthDate;
	
	@Getter @Setter
	private String passwordConfirm;

	@Getter @Setter
	private String neighborhoodName;
	

	@Getter @Setter
	private String neighborhoodSpecification;
	
	@Getter @Setter
	private Long neighborhoodId;

}
