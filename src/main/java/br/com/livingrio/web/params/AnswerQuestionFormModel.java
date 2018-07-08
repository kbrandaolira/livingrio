package br.com.livingrio.web.params;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.livingrio.model.Criterion;
import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Post;

public class AnswerQuestionFormModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public AnswerQuestionFormModel() {
		evaluationCriterionList = new ArrayList<EvaluationCriterionDTO>();
	}
	

	@Getter @Setter
	private Long questionId;
	
	@Getter @Setter
	private String answerText;

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

	public AnswerQuestionFormModel build(Evaluation evaluation, List<Criterion> criterions) {
		
		if(evaluation != null){
			
			evaluationId = evaluation.getId();
			
			neighborhoodId = evaluation.getNeighborhood().getId();
			neighborhoodName = evaluation.getNeighborhood().getName();
			neighborhoodSpecification = evaluation.getNeighborhood().getSpecification();
			neighborhoodPictureUrl = evaluation.getNeighborhood().getMainPictureUrl();
			neighborhoodUrl = evaluation.getNeighborhood().getFullURL();
			
			if(evaluation.getDate() != null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				this.evaluationDate = sdf.format(evaluation.getDate().getTime());
				
			}
			
			if (evaluation.getNeighborhood().getMainPicture() != null){
				neighborhoodPictureUrl = evaluation.getNeighborhood().getMainPicture().getPath();
			}
			
			
		/*	averageResult = evaluation.getAverageResult();
			*/

			if( evaluation.getComment() != null ){
				comment = evaluation.getComment();
				resumeComment = evaluation.getSubstringComment(0, 255);
			}
			
			/*if( evaluation.overall() != null ){
				this.overall = evaluation.overall();
			}*/
				
			creatorName = evaluation.getPerson().getName();
		
			creatorAge = evaluation.getPerson().myAge();
			
			creatorId = evaluation.getPerson().getId();
					
			
		}
		
		evaluationCriterionList = new ArrayList<EvaluationCriterionDTO>();
		
		if( criterions != null ){
			criterionSize = criterions.size();
		}
		
		for (Criterion criterion : criterions){
			
			EvaluationCriterionDTO dto = new EvaluationCriterionDTO();
			
			if (evaluation != null){
			
				Post evaluationCriterion = evaluation.getEvaluationCriterionByCriterionId(criterion.getId());
				
				if(evaluationCriterion != null){
					
					dto.setComment(evaluationCriterion.getComment());
					dto.setGrade(evaluationCriterion.getGrade());
					dto.setEvaluationCriterionId(evaluationCriterion.getId());
					dto.setIconLocation(evaluationCriterion.getCriterion().getIconLocation());
					
				}
			
			}
			
			dto.setCriterionId(criterion.getId());
			dto.setCriterionName(criterion.getName());
			dto.setCriterionDescription(criterion.getDescription());
			dto.setCriterionIcon(criterion.getIconLocation());
			
			evaluationCriterionList.add(dto);
			
		}
		
		
		return this;
		
		
	}

	

}
