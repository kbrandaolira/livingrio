package br.com.livingrio.model;

import java.beans.Transient;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Evaluation {

	@Getter @Setter
	@Id @GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@ManyToOne
	@Getter @Setter private Person person;
	
	@ManyToOne
	@Getter @Setter private Neighborhood neighborhood;
	
	@Temporal( TemporalType.DATE )
	@Column( nullable = false )
	@Getter @Setter private Calendar date;
	
	@Column( nullable = false, length = 10000 )
	@Getter @Setter private String comment;
	
	@OneToMany( mappedBy="evaluation", targetEntity = EvaluationStatus.class, cascade=CascadeType.ALL )
	@Getter private List<EvaluationStatus> evaluationStatus;
	
	@OneToMany( mappedBy="evaluation", targetEntity = Post.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	@Getter  private Set<Post> evaluationCriterion;
	
	
	
	@Transient
	public void addEvaluationCriteria(Post evaluationCriteria){
		if(this.evaluationCriterion == null){
			this.evaluationCriterion = new HashSet<Post>();
		}
		
		this.evaluationCriterion.add(evaluationCriteria);
		
	}
	
	
	
	
	@OneToMany( mappedBy="evaluation", targetEntity = Question.class, cascade=CascadeType.ALL,fetch=FetchType.EAGER )
	@Getter private Set<Question> questions;
	
	@Transient
	public void changeStatusToWaitingForValidation(){
		
		EvaluationStatus status = changeStatus(EvaluationStatusName.WAITING_FOR_VALIDATION);
		
		this.evaluationStatus.add(status);
		
	}
	
	@Transient
	public void changeStatusToValidated(){
		
		EvaluationStatus status = changeStatus(EvaluationStatusName.VALIDATED);
		
		this.evaluationStatus.add(status);
		
	}
	
	@Transient
	public void changeStatusToDenied(){
		
		EvaluationStatus status = changeStatus(EvaluationStatusName.DENIED);
		
		this.evaluationStatus.add(status);
		
	}

	@Transient
	private EvaluationStatus changeStatus(EvaluationStatusName statusName) {
		if(this.evaluationStatus == null){
			this.evaluationStatus = new ArrayList<EvaluationStatus>();
		}
		
		EvaluationStatus status = new EvaluationStatus();
		status.setName(statusName);
		status.setDate(Calendar.getInstance());
		status.setEvaluation(this);
		return status;
	}

	@Transient
	public Post getEvaluationCriterionByCriterionId(Long id2) {
		
		if(evaluationCriterion == null){
			return null;
		}
		
		for (Post ec : evaluationCriterion){
			
			if(ec.getCriterion().getId().equals(id2)){
				return ec;
			}
			
		}
		
		return null;
	}
	
	
	@Transient
	public Post getEvaluationCriterionByEvaluationCriterionId(Long id2) {
		
		if(evaluationCriterion == null){
			return null;
		}
		
		for (Post ec : evaluationCriterion){
			
			if(ec.getId().equals(id2)){
				return ec;
			}
			
		}
		
		return null;
	}
	
	/**
	 * 
	 * Este método não faz mais sentido!!! Visto que não existirão mais as evaluations...
	 * 
	* @return Double A média geral da Avaliação da Pessoa ao Bairro
	* @exception ArithmeticException Pode ocorrer uma divisão de 0 por 0, caso o bairro não possua nenhuma avaliação
	*/
	@Deprecated
	public Double overall() throws ArithmeticException{
		Double overall = 0.0;
		int count = 0;
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		for(Post ev_crit : this.getEvaluationCriterion()){
			// TODO Ess
			if(ev_crit.getGrade() != null){
			
				overall = overall + ev_crit.getGrade();
				
			}
			count ++;
		}
		
		return new Double(df.format(overall / count).replace(",", ".")).doubleValue();
	}
	
	/**
	* @return Double A avaliação da pessoa ao bairro, pelo critério.
	*/
	public Double overall(Long criterion_id){
		DecimalFormat df = new DecimalFormat("0.##");
		
		for(Post ev_crit : this.getEvaluationCriterion()){
			if(ev_crit.getId().equals(criterion_id)){
				return new Double(df.format( Double.parseDouble(String.valueOf(ev_crit.getGrade())) ).replace(",", ".")).doubleValue();
				
			}
		}
		
		return null;
		
	}
	
	public String getSubstringComment(int begin, int end){
		if( this.comment != null && !StringUtils.isEmpty( this.comment ) ){
			if(this.comment.length() <= end){
				return '"'+ this.comment + '"';
			}else{
				return '"' + this.comment.substring(begin, end) + "..." + '"';
			}
			
			
		}
		
		return "";
	}
	
	/*public String getAverageResult(){
		if(this.overall() >= 3.5){
			return "positive";
		}else if( this.overall() < 3.5 && this.overall() >= 2.5){
			return "medium";
		}else{
			return "negative";
		}
		
	}*/

	public void addQuestion(Question question) {
	
		if(questions == null){
			questions = new HashSet<Question>();
		}
		
		questions.add(question);
		
	}

	public boolean hasQuestionsWithoutAnswer() {

		boolean response = false;
		
		if(questions == null){
			return response;
		}else{
			
			for (Question question : questions){
				
				if (question.getAnswer() == null){
					response = true;
					break;
				}
				
			}
			
		}
		
		return response;
	}

	public boolean hasQuestionsWithoutAnswerFromUser(Person authenticatedUser) {
		boolean response = false;
		
		if(questions == null){
			return response;
		}else{
			
			for (Question question : questions){
				
				if (question.getResponsible().getId().equals(authenticatedUser.getId()) && question.getAnswer() == null){
					response = true;
					break;
				}
				
			}
			
		}
		
		return response;
	}
	
}
