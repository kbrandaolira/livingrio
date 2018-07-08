package br.com.livingrio.web.params.profile;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.livingrio.web.params.EvaluationCriterionDTO;

public class EvaluationModel {
	
	@Getter @Setter
	private Long id;
	
	@Getter @Setter
	private String neighborhoodName;
	
	@Getter @Setter
	private String neighborhoodPictureUrl;
	
	@Getter @Setter
	private List<EvaluationCriterionDTO> evaluationCriterion;

}
