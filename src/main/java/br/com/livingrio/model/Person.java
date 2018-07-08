package br.com.livingrio.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import br.com.livingrio.config.ApplicationInfo;

@Entity
public class Person {
	
	@Id @GeneratedValue
	@Getter private Long id; 
	
	@Getter @Setter
	@Column( nullable = false )
	private String name;

	@Temporal( TemporalType.DATE )
	@Getter @Setter private Calendar birthDate;
	
	@Getter @Setter private String email;

	@Column( nullable = false )
	@Getter @Setter private String password;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private Gender gender;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private Role role;
	
	@ManyToOne
	@Getter @Setter
	private Neighborhood neighborhood;
	
	@OneToMany( mappedBy = "person", targetEntity = Testimony.class )
	@Getter private List<Testimony> testimonials;
	
	/*@OneToMany( mappedBy = "person", targetEntity = Evaluation.class )
	@Getter private Set<Evaluation> evaluations = new HashSet<Evaluation>();*/
	
	@OneToOne(cascade=CascadeType.ALL)
	@Getter @Setter
	private Picture picture;
	
	@OneToMany( mappedBy="person", targetEntity = Liked.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	@Getter  private List<Liked> likes;
	
	public void addTestimony(Testimony testimony){
		if(this.testimonials == null){
			this.testimonials = new ArrayList<Testimony>();
		}
		
		this.testimonials.add(testimony);
		
	}
	
	public void removeTestimony(Testimony testimony){
		if(this.testimonials != null){
			this.testimonials.remove(testimony);
		}
		
	}
	
	public Integer myAge(){
		if(this.birthDate != null){
			Calendar today = Calendar.getInstance();
			Integer age = today.get(Calendar.YEAR) - this.birthDate.get(Calendar.YEAR);
			
			if(today.before(this.birthDate)){
				age --;
			}
			
			return age;
			
		} else {
			return null;
		}
		
	}
	
	public String getFilesBaseDir(){
		
		return ApplicationInfo.AWS_S3_PEOPLE_FILE_BASE_DIR + id + "/";
		
	}
	
	@Deprecated
	public String getProfilePhotoPath(){
		
		
		return picture.getPath();
		
	}
	
	public Object getProfilePicture(){
		
		
		
		return null;
	}
	
	/**
	 * @return boolean informando se a pessoa já avaliou seu bairro
	*/
	public boolean didIAvaliatedMyNeighborhood(){
		
		// TODO Extrair isto para um servico
		/*if( this.evaluations != null ){
			for( Evaluation e : this.evaluations ){
				if( e.getNeighborhood().equals( this.neighborhood ) ){
					return true;
				}
				
			}
			
		}*/
		
		return false;
		
	}
	
	public boolean isAdmin(){
		if( this.role != null && this.role.equals(Role.ADMIN) ){
			return true;
		}
		
		return false;
	}
	
	public boolean isUser(){
		if( this.role != null && this.role.equals(Role.USER) ){
			return true;
		}
		
		return false;
	}
	
}
