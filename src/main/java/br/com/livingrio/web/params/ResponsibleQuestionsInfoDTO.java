package br.com.livingrio.web.params;

import br.com.livingrio.config.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;

public class ResponsibleQuestionsInfoDTO {

	@Getter @Setter
	private String answerQuestionsURL;
	
	@Getter @Setter
	private String name;

	@Getter @Setter
	private int questionsWithoutAnswers;
	
	public void setResponsibleId(Long id){
		
		answerQuestionsURL = ApplicationInfo.APPLICATION_URL + "profile/" +  id + "/questions/answer";
		
	}

}
