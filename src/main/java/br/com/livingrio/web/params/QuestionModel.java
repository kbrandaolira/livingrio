package br.com.livingrio.web.params;

import lombok.Getter;
import lombok.Setter;

public class QuestionModel {
	
	@Getter @Setter
	private Long evaluationId;
	
	@Getter @Setter
	private Long questionId;
	
	@Getter @Setter
	private String question;
	
	@Getter @Setter
	private String questionResponsibleName;
	
	@Getter @Setter
	private Long questionResponsibleId;
	
	@Getter @Setter
	private String questionCreationDate;
	
	@Getter @Setter
	private String answerCreationDate;
	
	@Getter @Setter
	private String answer;
	
	@Getter @Setter
	private String answerResponsibleName;
	
	@Getter @Setter
	private Long answerResponsibleId;

}
