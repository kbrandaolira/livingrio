package br.com.livingrio.model;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class NeighborhoodCriterion {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter private Long id;
	
	@ManyToOne
	@Getter @Setter private Neighborhood neighborhood;
	
	@ManyToOne
	@Getter @Setter private Criterion criterion;
	
	@Column(precision=1, scale=2)
	@Getter private Double overall;
	
	public void setOverall(Double overall) {
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		String formattedValue = df.format(overall);
		
		this.overall = Double.valueOf(formattedValue.replace(",", "."));
	}
	
}
