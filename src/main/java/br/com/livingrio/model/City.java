package br.com.livingrio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class City {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	@Column( nullable = false )
	@Getter @Setter private String name;
	
	@Column( nullable = false )
	@Getter @Setter private String url;
	
	@OneToMany( mappedBy = "city", targetEntity = Neighborhood.class )
	@Getter private List<Neighborhood> neighborhoods;
	
	@OneToMany( mappedBy = "city", targetEntity = Region.class )
	@Getter private List<Region> regions;
	
	@ManyToOne
	@Getter @Setter private State state;
	
	
}
