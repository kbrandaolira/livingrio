package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class CriterionModel {

	@Getter @Setter private Long id;
	
	@Getter @Setter private String name;
	
	@Getter @Setter private Double overall;
	
	@Getter @Setter private String iconLocation;

	public CriterionModel(Long id, String name, Double overall, String iconLocation) {
		super();
		this.id = id;
		this.name = name;
		this.overall = overall;
		this.iconLocation = iconLocation;
	}



	public CriterionModel(){}
	
	
}
