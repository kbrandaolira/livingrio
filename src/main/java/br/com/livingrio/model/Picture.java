package br.com.livingrio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Picture {
	
	@Id @GeneratedValue
	@Getter private Long id;

	@Getter @Setter @Deprecated private String original;
	
	@Getter @Setter @Deprecated private String thumbnail;
	
	@Getter @Setter @Deprecated private String path;
	
	@Getter @Setter private Boolean main;
	
	@Getter @Setter private String fileName;
	
	@Getter @Setter private String bucket;
	
	@ManyToOne
	@Getter @Setter private Neighborhood neighborhood;
	
	
}
