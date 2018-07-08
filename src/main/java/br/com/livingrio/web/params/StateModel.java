package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class StateModel {

	@Getter @Setter private Long id;
	
	@Getter @Setter private String name;
	
	@Getter @Setter private String acronym;
	
	public StateModel(Long id, String name, String acronym){
		this.id = id;
		this.name = name;
		this.acronym = acronym;
		
	}
	
	public StateModel(){}
	
}
