package br.com.livingrio.web.params;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ProfileFormModel {
	
	@Getter @Setter private Long id;
	
	@Getter @Setter private String name;
	
	@Getter @Setter private Integer age;
	
	@Getter @Setter private String email;
	
	@Getter @Setter private Long pictureId;
	
	@Getter @Setter private String photoUrl;
	
	@Getter @Setter private String neighborhoodName;
	
	@Getter @Setter private Long neighborhoodId;
	
	@Getter @Setter private List<EvaluationFormModel> evaluations;

}
