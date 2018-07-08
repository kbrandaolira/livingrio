package br.com.livingrio.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import br.com.livingrio.config.ApplicationInfo;
import br.com.livingrio.utils.StringUtils;

@Entity
public class Neighborhood {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter private Long id;
	
	@Column( nullable = false )
	@Getter private String name;
	
	/**
	 * 
	 * Nome sem acentos, utilizado para buscas em autocompletes o campos texto, onde não sabemos como o usuário irá digitar o nome do bairro.
	 * Com o cleanName, podemos fazer comparações ignorando acentos.
	 */
	@Column( nullable = false )
	@Getter @Setter private String cleanName;
	
	@Column( nullable = false )
	@Getter @Setter private String url;
	
	@Column( nullable = false, length = 4000 )
	@Getter @Setter private String description;
	
	@ManyToOne
	@Getter @Setter private Region region;
	
	@ManyToOne
	@Getter @Setter private City city;
	
	@OneToMany( mappedBy = "neighborhood", targetEntity = Evaluation.class, fetch=FetchType.LAZY )
	@Getter private List<Evaluation> evaluations;
	
	@OneToMany( targetEntity = Picture.class, fetch = FetchType.EAGER )
	@Getter private Set<Picture> pictures;
	
	@OneToMany( mappedBy = "neighborhood", targetEntity = NeighborhoodCriterion.class, fetch = FetchType.EAGER ,cascade=CascadeType.ALL)
	@Getter @Setter private Set<NeighborhoodCriterion> neighborhoodsCriterion;
	
	@Override
	public String toString() {
		return this.getCity().getState().getAcronym() + " - " + this.getCity().getName() + " - " + this.getRegion().getName() + " - " + this.getName();
	}

	public Neighborhood(Long id) {
		super();
		this.id = id;
	}
	
	public Neighborhood() {}
	
	public String getSpecification(){
		
		String spec = this.city.getState().getAcronym() + " - " + this.getCityName();
		
		if (region != null){
			spec += " - " + this.region.getName();
		}
		
		return  spec;
		
	}
	
	public void addPicture( Picture picture ){
		if( pictures == null ){
			this.pictures = new HashSet<Picture>();
		}
		
		this.pictures.add(picture);
		
	}
	
	/**
	 * @return List<Evaluation> Retorna apenas Avaliações com o Status Aprovado 
	*/
	public List<Evaluation> getApprovedEvaluation(){
		List<Evaluation> approved = new ArrayList<Evaluation>();
		if(this.getEvaluations() != null){
			for(Evaluation evaluation : this.getEvaluations()){
				for(EvaluationStatus status : evaluation.getEvaluationStatus()){
					if(status.getName().equals(EvaluationStatusName.VALIDATED)){
						approved.add(evaluation);
						
					}
					
				}
			}
			
		}
		
		return approved;
		
	}
	
	/**
	* @return Double A média geral do Bairro
	*/
	public Double overall(){
		Double overall = 0.0;
		int count = 0;
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		for(NeighborhoodCriterion neigh_crit : this.getNeighborhoodsCriterion()){
			overall = overall + neigh_crit.getOverall();
			count++;
		}
				
		if( overall != 0.0 && count != 0){
			return new Double(df.format(overall / count).replace(",", ".")).doubleValue();
			
		}else{
			return overall;
			
		}
		
	}
	
	/**
	* @return Double A média geral do Bairro por Critério
	*/
	public Double overall(Long criterion_id){
		DecimalFormat df = new DecimalFormat("0.##");
		
		for(NeighborhoodCriterion neigh_crit : this.getNeighborhoodsCriterion()){
			if(neigh_crit.getCriterion().getId().equals(criterion_id)){
				return new Double(df.format(neigh_crit.getOverall()).replace(",", ".")).doubleValue();
			}
		}
		
		return null;
	}

	public Picture getMainPicture() {
		for( Picture picture : this.getPictures() ){
			if( picture.getMain() ){
				return picture;
			}
		}
		
		return null;
	}
	
	/**
	 * @return Lista de todas as pictures do bairro, exceto a principal.
	 */
	public List<Picture> getOtherPictures(){
		if(this.getPictures() != null){
			List<Picture> pictures = new ArrayList<Picture>();
			
			for( Picture picture : this.getPictures() ){
				if(!picture.getMain()){
					pictures.add(picture);
				}
					
			}
			
			return pictures;
		}
		
		return null;
	}
	
	public String getAcronymStateName(){
		return this.getCity().getState().getAcronym();
	}
	
	public String getCityName(){
		return this.getCity().getName();
	}
	
	public String getRegionName(){
		
		if(getRegion() == null){
			return "";
		}
		
		return this.getRegion().getName();
	}
	
	public boolean hasEvaluation(){
		if( this.neighborhoodsCriterion != null && this.neighborhoodsCriterion.size() > 0 ){
			return true;
		}
		
		return false;
	}
	
	public String getFullURL(){
		
		String fullUrl = this.getCity().getState().getUrl() + "/" + this.getCity().getUrl() + "/";
				
		if (getRegion() != null){
			
			fullUrl += this.getRegion().getUrl();			
		}else{
			
			fullUrl += "~";
			
		}
				
		fullUrl += "/" + this.getUrl();
		
		return fullUrl;
		
		
	}

	public String getMainPictureUrl() {
		
		if (this.getMainPicture() == null){
			return "";
		}
		
		return this.getMainPicture().getPath();
		
	}
	
	public String getFilesBaseDir(){
		return ApplicationInfo.AWS_S3_NEIGHBORHOOD_FILE_BASE_DIR + id + "/";
		
	}
	

	public boolean hasMainPicture() {
		if( this.pictures != null ){
			for( Picture picture : this.getPictures() ){
				if( picture.getMain() ){
					return true;
				}
					
			}
			
		}
		
		return false;
	}

	public void setName(String name) {
		
		this.name = name;
		
		cleanName = StringUtils.removeAccents(name);
		
		
	}
	
	
	

}
