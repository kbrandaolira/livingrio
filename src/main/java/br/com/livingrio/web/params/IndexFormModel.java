package br.com.livingrio.web.params;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class IndexFormModel {

	@Getter private List<ProfileFormModel> profilesModels;
	
	@Getter private List<EvaluationFormModel> evaluationsModels;
	
	public void addProfileModel(ProfileFormModel profileModel){
		if( this.profilesModels == null ){
			this.profilesModels = new ArrayList<ProfileFormModel>();
		}
		
		this.profilesModels.add( profileModel );
		
	}
	
	public void addEvaluationModel(EvaluationFormModel evaluationModel){
		if( this.evaluationsModels == null ){
			this.evaluationsModels = new ArrayList<EvaluationFormModel>();
		}
		
		this.evaluationsModels.add( evaluationModel );
		
	}
	
}
