package br.com.livingrio.web.params;


import lombok.Getter;
import lombok.Setter;

public class EvaluationCriterionDTO {
	
	@Getter @Setter
	private Long criterionId;
	
	@Getter @Setter
	private String criterionName;
	
	@Getter @Setter
	private String criterionDescription;
	
	@Getter @Setter
	private String criterionIcon;
	
	@Getter @Setter
	private Long evaluationCriterionId;
	
	@Getter @Setter
	private String comment;
	
	@Getter @Setter
	private Double grade = 0.0;

	@Getter @Setter
	private String iconLocation;
	

}
