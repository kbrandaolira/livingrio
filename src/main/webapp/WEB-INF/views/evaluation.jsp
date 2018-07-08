<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<head><title>Avaliar ${neighborhood.name}</title></head>

<link href="<c:url value='/resources/views/evaluation.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/jRating.jquery.css'/>" rel="stylesheet">

    <!-- for Boostrap -->
    <script src="<c:url value="/resources/js/jquery.js" />"></script>

    
    <!-- for jQuery UI -->
    <script src="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.js'/>"></script>
    
    
    <style>
    
    	.required {
    	
    	
    		color: red;
    		
    	
    	}
    	
    	.evaluationUserInfoMessageBox{
    	
    		font-size:10px;
    		float:right;
    	
    	}
    
    </style>
    
<script src="<c:url value='/resources/js/jRating.jquery.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	

      
	$('div[id^="stars_criterion_"]').jRating({
       step:true,
       showRateInfo:false,
       canRateAgain:true,
        length : 5, // nb of stars
        rateMax: 5,
        decimalLength: 1,
        nbRates:2000,
        onClick : function(element,rate) {
        	
        	
      		id = element.id.substring(16); 
      		
      		
      		$("#grade_" + id).val(rate);
        	
        }
        
      });
      

 
    
});
</script>


<div class="row ">
	<div class="col-md-12">
	
		<div class="pageTitleBox">
			<span class="pageTitleText">Faça sua <span style="color:#FFD67D;">avaliação</span></span>
			
			<span class="pageTitleDescription">&nbsp;&nbsp;${userAuthenticated.name}, avalie o bairro ${neighborhood.name} - ${neighborhood.city.name} - ${neighborhood.city.state.acronym}</span>
			
		</div>
		
			<span class="pageTitleDescriptionMobile" style="display:none;">${userAuthenticated.name}, avalie o bairro ${neighborhood.name} - ${neighborhood.city.name} - ${neighborhood.city.state.acronym}</span>
			
		
	</div>
</div>


<div class="evaluationUserInfoMessageBox">

	<span style="font-size:12px;font-weight:bold;">Significados: </span>&nbsp; &nbsp; &nbsp;

	Péssimo: <img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /> 
	 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Ruim: <img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /> 
	 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Regular: <img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /> 
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Bom: <img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /> 
	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Excelente:<img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /><img src="https://cdn4.iconfinder.com/data/icons/small-n-flat/24/star-16.png" /> 
</div>

<div class="clearfix"></div>

<c:if test="${not empty messages}">
<div class="alert alert-danger">
<c:forEach varStatus="status" var="message" items="${messages}">

	[<b>${message.type}</b>] ${message.message} <br>
	
</c:forEach>
</div>
</c:if>

<form:form action='' method="post" modelAttribute="form">


	<form:hidden path="neighborhoodId"   />
	<form:hidden path="evaluationId"   />
	
	


	<div class="criteria">
	
	
	
	
	
		<c:forEach varStatus="status" var="evaluationCriterion" items="${form.evaluationCriterionList}">
			
			<div class="criterionBox row">
				
				<input id="evaluationCriterionList${status.index}.evaluationCriterionId" name="evaluationCriterionList[${status.index}].evaluationCriterionId" value="${evaluationCriterion.evaluationCriterionId}" type="hidden">
					
				
				<div class="col-md-3" style="margin-top:23px;">
				
					<img style="display:inline;width:64px;height:64px;" src="<c:url value='/${evaluationCriterion.criterionIcon}'/>" />
					
					<input type="hidden" name="evaluationCriterionList[${status.index}].criterionIcon" value="${evaluationCriterion.criterionIcon}" />
									
					<span class="criterionName textBold" style="display:inline;" >
						
						${evaluationCriterion.criterionName}
						
						<input type="hidden" name="evaluationCriterionList[${status.index}].criterionName" value="${evaluationCriterion.criterionName}" />
						
					</span>
					<input id="evaluationCriterionList${status.index}.criterionId" name="evaluationCriterionList[${status.index}].criterionId" value="${evaluationCriterion.criterionId}" type="hidden">
					
				</div>
				
					<div class="col-md-2" style="margin-top:32px;">
					
					<span style="font-size:14px;"> Avaliação: <span class="required">*</span> </span>
	
					<input id="grade_${evaluationCriterion.criterionId}" name="evaluationCriterionList[${status.index}].grade" value="${evaluationCriterion.grade}" type="hidden">
						
					<div id="stars_criterion_${evaluationCriterion.criterionId}" data-average="${evaluationCriterion.grade}"></div> <br>
					
					
				</div>
				
				<div class="col-md-7">
					Comentário: <span style="font-size:11px;">(Opcional)</span>
					<form:textarea maxlength="10000" path="evaluationCriterionList[${status.index}].comment" name="evaluationCriteria[${status.index}].comment" class="full" placeholder="${evaluationCriterion.criterionDescription}"></form:textarea>
				</div>
				
			
	
			</div>
	
		</c:forEach>
		
		<div class="criteria">
		<div class="criterionBox row">
					<div class="col-md-3 generalComment" style="margin-top:23px;">
				
					<img style='width:64px;height:64px;' src="<c:url value='/resources/images/icons/criterion/general_comment.png'/>" />
										
					<span class="criterionName textBold" style="position:absolute; margin-left:10px;margin-top:12px">
						
						Comentário geral
						
					</span>
					
				</div>
				
				<div class="col-md-9">
					<form:textarea maxlength="10000" path="comment" name="comment" class="full" placeholder="Meu nome é João, moro neste bairro a mais de 20 anos e posso dizer que este bairro é um dos melhores do Rio de Janeiro."></form:textarea>
				</div>
				
				
	
			</div>
		</div>
		
		
		<div class="modal-footer">
		
			<input class="btn btn-primary" value="Enviar" type="submit">
		
		</div>
		
	</div>
</form:form>


