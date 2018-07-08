package br.com.livingrio.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Question {
	
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
	
	@ManyToOne
	@Getter @Setter
	private Evaluation evaluation;
	
	@OneToOne(cascade=CascadeType.ALL)
	@Getter @Setter
	private Answer answer;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Calendar creationDate;

	public Person getCreatorEvaluation(){
		return this.getEvaluation().getPerson();
	}
	
	public String getCreationDateFormatted(){
		if( this.creationDate != null ){
			SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
			return sdf.format( this.creationDate.getTime() );
		}
		
		return null;
	}
	
}
