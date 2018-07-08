package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class AutoCompleteModel {

	@Getter @Setter private Long id;
	
	@Getter @Setter private String state;
	
	@Getter @Setter private String city;
	
	@Getter @Setter private String region;
	
	@Getter @Setter private String neighborhood;

	@Getter @Setter private String url;
	
	@Getter @Setter private String specification;
	
	@Getter @Setter private String imagePath;

	public AutoCompleteModel(Long id, String neighborhood, String specification, String imagePath, String url) {
		super();
		this.id = id;
		this.neighborhood = neighborhood;
		this.specification = specification;
		this.imagePath = imagePath;
		this.url = url;
	}
	
	
	
	
	
}
