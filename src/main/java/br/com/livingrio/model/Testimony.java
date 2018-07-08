package br.com.livingrio.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Testimony {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	@Getter private Long id;
	
	@Column( nullable = false )
	@Getter @Setter private String testimony;
	
	@Temporal( TemporalType.DATE )
	@Column( nullable = false )
	@Getter @Setter private Calendar date;
	
	@ManyToOne
	@Getter @Setter private Person person;
	
}
