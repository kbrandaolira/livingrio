package br.com.livingrio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Liked {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Getter private Long id;
	
	@Getter @Setter private Boolean liked;
	
	@ManyToOne
	@Getter @Setter private Post post;
	
	@ManyToOne
	@Getter @Setter private Person person;
	
	
}
