package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class ContactModel {
	
	@Getter @Setter private String name;
	
	@Getter @Setter private String email;
	
	@Getter @Setter private String message;
	
	@Override
	public String toString() {
		return name + " - " + email + " - " + message;
		
	}
}
