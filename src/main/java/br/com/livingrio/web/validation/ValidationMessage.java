package br.com.livingrio.web.validation;

import lombok.Getter;
import lombok.Setter;

public class ValidationMessage {
	
	
	
	public ValidationMessage(String message, MessageType type) {
		super();
		this.message = message;
		this.type = type;
	}

	@Getter @Setter
	private String message;
	
	@Getter @Setter
	private MessageType type;

}
