package br.com.livingrio.model;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Post {
	
	@Id 
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Getter 
	@Setter
	private Long id;
	
	@ManyToOne
	@Getter @Setter private Criterion criterion;
	
	@ManyToOne
	@Getter @Setter private Evaluation evaluation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Getter @Setter
	private Calendar createdDate;
	
	@ManyToOne
	@Getter @Setter private Person person;
	
	@ManyToOne
	@Getter @Setter private Neighborhood neighborhood;
	
	@OneToMany( mappedBy="post", targetEntity = Liked.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	@Getter  private Set<Liked> likes;
	
	public int likesSize(){
		if( this.likes != null ){
			return this.likes.size();
		}
		
		return 0;
		
	}
	
	@Column( nullable = true )
	@Getter @Setter private Double grade;

	@Column( nullable = false, length = 10000 )
	@Getter @Setter private String comment;

	public Post(Long id, Criterion criterion,Evaluation evaluation, Double grade, String comment) {
		super();
		this.id = id;
		this.criterion = criterion;
		this.evaluation = evaluation;
		this.grade = grade;
		this.comment = comment;
	}
	
	public Post() {}
	
	@Transient
	public String getShortComment(int begin, int end){
		if( this.comment != null && !StringUtils.isEmpty( this.comment ) ){
			if(this.comment.length() <= end){
				return '"'+ this.comment + '"';
			}else{
				return '"' + this.comment.substring(begin, end) + "..." + '"';
			}
			
			
		}
		
		return "";
	}
	
	
}
