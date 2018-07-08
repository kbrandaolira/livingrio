<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Validação de Avaliações</title>



</head>
<body>

<style>


	.box{
	
	
		border-bottom: 1px solid #eee;
		padding-bottom: 20px;
		border-radius:0px;
	
	
	}
	
	.neighborhood {
	
	
		font-size: 16px;
	
	}
	
	.person{
	
		font-size: 12px;
		font-weight: lighter;
	
	}
	
	.comment{
	
		font-size: 12px;
		font-weight: lighter;
	
	}
	
	.criterion{
	
		font-size: 12px;
		font-weight: lighter;
		font-weight: bold;
	
	}
	
	.criterion{
	
		font-size: 12px;
		font-weight: lighter;
		font-weight: bold;
	
	}
	
	.grade{
	
		font-size: 12px;
		font-weight: lighter;
		font-weight: normal !important;
	
	}


	.margin-top-3{
		margin-top: 6px;
	}
	
	.title {
	
	
		font-weight: lighter;
		font-family:verdana;
		font-size: 18px;
		
		border-bottom: 1px solid #eee;
		margin-bottom: 10px;
	
	}

</style>
	
	<div class="title">Avaliações aguardando validação</div>
	
	<c:choose>
	
		<c:when test="${evaluations != null && evaluations.size() > 0}">
		
			<c:forEach var="evaluation" items="${evaluations}">
				
				<div class="box well">
					<div class="row">
						<div class="col-md-9">
							<div class="row">
					  			<div class="col-md-12 neighborhood">${evaluation.neighborhoodName}</div>
							</div>
							<div class="row">
					  			<div class="col-md-12 person">${evaluation.creatorName}</div>
							</div>
							
							<c:if test="${evaluation.comment != null && evaluation.comment != ''}">
								<div class="row">
									<div class="col-md-12" style="font-size: 11px;">Comentário: "${evaluation.comment}"</div>
								</div>
							</c:if>
							
							<c:forEach var="criterion" items="${evaluation.evaluationCriterionList}" >
							
								<div class="row margin-top-3">
					  				<div class="col-md-2 criterion">${criterion.criterionName}: <span class="grade">${criterion.grade} </span></div>
					  				
								</div>
								
								<div class="row">
					  				<div class="col-md-12 comment">${criterion.comment}</div>
								</div>
							
							</c:forEach>
						</div>
						<div class="col-md-3">
						
							<form action="changeStatus" method="post">
							
								<input type="hidden" name="evaluationId" value="${evaluation.evaluationId}" />
								<input type="hidden" name="newStatus" value="VALIDATED" />
							
								<p><button class="btn btn-success">Aprovar</button></p>
							</form>
							
							<p>
							<form action="changeStatus" method="post">
							
								<input type="hidden" name="evaluationId" value="${evaluation.evaluationId}" />
								<input type="hidden" name="newStatus" value="DENIED" />
							
								<p><button class="btn btn-danger">Recusar</button></p>
							</form>
							
						</div>
					</div>
				</div>
			
			</c:forEach>
			
		</c:when>

		<c:otherwise>
			<div style="width: 100% !important;" class="jumbotron">Nenhuma avaliação esperando validação.</div>
		</c:otherwise>

	</c:choose>
	
</body>
</html>