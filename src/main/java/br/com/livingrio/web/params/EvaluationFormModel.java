package br.com.livingrio.web.params;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

public class EvaluationFormModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	public EvaluationFormModel() {
		evaluationCriterionList = new ArrayList<EvaluationCriterionDTO>();
	}
	
	@Getter @Setter
	private Long evaluationId;
	
	@Getter @Setter
	private Long neighborhoodId;
	
	@Getter @Setter
	private String neighborhoodName;
	
	@Getter @Setter
	private String evaluationDate;
	
	@Getter @Setter
	private String neighborhoodPictureUrl;
	
	@Getter @Setter
	private String neighborhoodUrl;
	
	@Getter @Setter
	private Long creatorId;
	
	@Getter @Setter
	private String creatorName;
	
	@Getter @Setter
	private Integer creatorAge;
	
	@Getter @Setter
	private String comment;
	
	@Getter @Setter
	private String resumeComment;
	
	@Getter @Setter
	private Double overall;
	
	@Getter @Setter
	private String averageResult;
	
	@Getter @Setter
	private String neighborhoodSpecification;
	
	@Getter @Setter
	private List<EvaluationCriterionDTO> evaluationCriterionList;
	
	@Getter @Setter
	private int criterionSize;
	
	@Getter @Setter
	private Boolean liked;
	
	@Getter @Setter
	private int likesSize;
	
	@Getter @Setter
	private int dislikesSize;
	
	@Getter @Setter
	private Long criterionId;
	
	@Getter @Setter
	private String criterionName;
	
	@Getter @Setter
	private String criterionIconPath;


}
