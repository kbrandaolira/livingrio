package br.com.livingrio.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Answer {
	
	@Id
	@GeneratedValue
	@Getter
	private Long id;
	
	@Getter @Setter
	@Column( nullable = false, length = 10000 )
	private String text;
	
	@ManyToOne
	@Getter @Setter
	private Person responsible;
	
	@OneToOne
	@Getter @Setter
	private Question question;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Calendar creationDate;

}
