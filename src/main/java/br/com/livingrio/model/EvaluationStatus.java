package br.com.livingrio.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class EvaluationStatus {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Long id;
	
	@Column( nullable = false )
	@Enumerated(EnumType.STRING)
	@Getter @Setter private EvaluationStatusName name;
	
	@Column( nullable = false )
	@Getter @Setter private Calendar date;
	
	@ManyToOne
	@Getter @Setter private Evaluation evaluation;
	

}
