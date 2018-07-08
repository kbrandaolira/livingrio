package br.com.livingrio.web.params;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import br.com.livingrio.model.Evaluation;
import br.com.livingrio.model.Picture;

public class NeighborhoodModel {

	@Getter @Setter private Long id;
	
	@Getter @Setter private  Double overall;
	
	@Getter @Setter private String url;
	
	@Getter @Setter private String name;
	
	@Getter @Setter private String state;
	
	@Getter @Setter private Long stateId;

	@Getter @Setter private String city;
	
	@Getter @Setter private Long cityId;
	
	@Getter @Setter private String region;
	
	@Getter @Setter private Long regionId;
	
	@Getter @Setter private String description;
	
	@Getter @Setter private Picture mainPicture;
	
	@Getter @Setter private CommonsMultipartFile [] multipartFiles;
	
	@Getter @Setter private List<Picture> otherPictures;
	
	@Getter @Setter private Set<Picture> pictures;
	
	@Getter @Setter private List<Evaluation> evaluations;
	
	@Setter double average;
	
	@Getter @Setter private List<CriterionModel> criterionModels;
	
	public NeighborhoodModel(){}

	public NeighborhoodModel(Long id, String stateURL, String cityURL, String regionURL, String neighborhoodURL, String name, String state, String city, String region, double average){
		this.id = id;
		this.name = name;
		this.state = state;
		this.city = city;
		this.region = region;
		this.average = average;
		
		if(regionURL == null || regionURL.equals("")){
			regionURL = "~";
		}
		
		this.url = stateURL + "/" + cityURL + "/" + regionURL + "/" + neighborhoodURL;
	}
	
	public double getAverage() {
		DecimalFormat df = new DecimalFormat("0.##");
		return new Double(df.format(this.average).replace(",", ".")).doubleValue();
		
	}
	
	public void addPicture(Picture picture){
		if(this.pictures == null){
			this.pictures = new HashSet<Picture>();
		}
		
		this.pictures.add(picture);
		
	}
	
	public void addEvaluation(Evaluation evaluation){
		if(this.evaluations == null){
			this.evaluations = new ArrayList<Evaluation>();
		}
		
		this.evaluations.add(evaluation);
		
	}
	
	public void addCriterionModel(CriterionModel criterionModel){
		if(this.criterionModels == null){
			this.criterionModels = new ArrayList<CriterionModel>();
		}
		
		this.criterionModels.add(criterionModel);
		
	}
	
	public void removeAllEvaluations(){
		if( this.evaluations != null ){
			this.evaluations.clear();
		}
		
	}
	
	public String getSubstringDescription(int begin, int end){
		if(this.description != null){
			if(this.description.length() <= end){
				return this.description;
			}else{
				return this.description.substring(begin, end) + "...";
			}
			
			
		}
		
		return "";
	}

	public String getSpecification(){
		
		if(region == null || region.equals("")){
			return this.state + " - " + this.city;
		}else{
			return this.state + " - " + this.city + " - " + this.region;
		}
		
	
		
	}
	
}
