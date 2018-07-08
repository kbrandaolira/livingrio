package br.com.livingrio.web.validation;

import java.util.List;

public interface Validation<T> {

	public List<ValidationMessage> validate(T object);
	
}
