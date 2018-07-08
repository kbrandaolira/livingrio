package br.com.livingrio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class State {
	
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Getter private Long id;
	
	@Column( nullable = false )
	@Getter @Setter private String name;
	
	@Column( nullable = false )
	@Getter @Setter private String url;
	
	@Column( nullable = false, length=3 )
	@Getter @Setter private String acronym;
	
	@OneToMany( mappedBy = "state", targetEntity = City.class )
	@Getter private List<City> cities;

}
