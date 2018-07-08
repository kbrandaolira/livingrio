package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class CityModel {

	@Getter @Setter public Long id;
	
	@Getter @Setter public String name;

	public CityModel(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CityModel(){}
	
}
