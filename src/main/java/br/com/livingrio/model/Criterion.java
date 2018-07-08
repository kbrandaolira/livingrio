package br.com.livingrio.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Criterion {
	
	public static final Long COMMERCE = 1L;

	public static final Long ENTERTAINMENT = 2L;

	public static final Long MOBILITY = 3L;

	public static final Long SECURITY = 4L;
	
	@Id 
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	@Getter private Long id;
	
	@Column( nullable = false )
	@Getter @Setter private String name;
	
	@Column( nullable = false )
	@Getter @Setter private String description;
	
	@Temporal( TemporalType.DATE )
	@Column( nullable = false )
	@Getter @Setter private Calendar startDate;
	
	@Temporal( TemporalType.DATE )
	@Getter @Setter private Calendar endDate;
	
	@Getter @Setter private String iconLocation;
	
	@JsonIgnore
	@OneToMany( mappedBy = "criterion", targetEntity = Post.class )
	@Getter private List<Post> evaluationCriterias;

	@JsonIgnore
	@OneToMany( mappedBy = "criterion", targetEntity = NeighborhoodCriterion.class )
	@Getter private List<NeighborhoodCriterion> neighborhoodsCriterion;
	
	public Criterion() {
		// TODO Auto-generated constructor stub
	}


	public Criterion(Long id) {
		super();
		this.id = id;
	}
	
	
}
