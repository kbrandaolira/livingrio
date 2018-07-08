<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<title>${model.name} - ${model.getSpecification()}</title>
</head>

<script type="text/javascript" charset="UTF-8"
	src="<c:url value='/resources/views/neighborhood.js'/>"></script>
<link href="<c:url value='/resources/views/neighborhood.css'/>"
	rel="stylesheet">
	


<input id="identifier" type="hidden" value="${model.id}" />
<input id="type" type="hidden" value="NEIGHBORHOOD" />

<div class="row">
	<div class="col-md-3">
		<div class="row" style="margin-top: -15px !important;">
			<div class="col-md-12">
				<img class="img-thumbnail" style="heigth: 180px; width: 100%;"
					src='<c:url value="/neighborhood/${model.id}/main/m" />' />
			</div>
		</div>



		<div class="row" style="text-align: center; margin-top: 5px;">
			<h3 style="margin: 0px; font-family: 'Molengo', sans-serif;; display: inline">${model.name}</h3>
			<c:if test="${userAuthenticated != null && userAuthenticated.isAdmin()}">
				&nbsp; <a id="link_editNeighborhood" href="#"><img style="width: 20px; height: 20px;" src="<c:url value='/resources/images/icons/other/pencil_edit.png'/>" title="Editar"/></a>
			</c:if>
		</div>

		<div class="row" style="text-align: center;">

			<font style="font-size: 11px;">${model.getSpecification()}</font>

		</div>

		<div class="row inner">


			<div class="stars" id="stars_criterion_m1"
				data-average="${model.overall}"></div>


			<div style="margin: 0px; font-size: 11px; text-align: center">
				(${model.overall})</div>
				
		</div>
		
		
		
		<div class="row" style="">
			<center>
				<c:if test="${model.overall >= 3.5}">
					<span class="label label-success">Recomendado</span>
				</c:if>
				<c:if test="${model.overall < 2.5 && model.overall != 0.0}">
					<span class="label label-danger">Não recomendado</span>
				</c:if>

			</center>

		</div>
		
		<div class="row" style="margin-top:15px;margin-bottom: 20px;">
			<div class="col-md-12">
				<button id="btn_evaluate" style="width:100%" class="btn btn-info pageTitle"><span class="glyphicon glyphicon-edit"></span>&nbsp; Fazer Avaliação</button>
			</div>
		</div>


		<div id="mediaAvaliacoesBairrosBox" class=""
			style="margin-top: 8px; border-top: 1px solid #eee;">
			<c:forEach var="criterionModel" varStatus="status"
				items="${model.criterionModels}">

				<div class="row" style="margin-left:4px !important;">

					<div class="col-md-2">

						<img style="margin-top: 10px;width:44.5px;height:44.5px;"
							src="<c:url value='/${criterionModel.iconLocation}'/>" />

					</div>

					<div class="col-md-10">



						<div class="row ncm_text">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<span class="textBold">${criterionModel.name}:</span><span
									style="font-size: 11px;"> (${criterionModel.overall})</span>
							</div>
						</div>

						<div class="row ncm_stars" style="margin-top: -8px;">
							<div class="col-md-1"></div>
							<div class="col-md-10">
								<div class="stars" id="stars_criterion_${criterionModel.id}"
									data-average="${criterionModel.overall}"></div>

							</div>
						</div>

					</div>

				</div>

				<div class="row"></div>
			</c:forEach>

		</div>
		
		<div class="row"></div>
		
		<div class="row" style="margin-top: 5px;margin-left:0px;margin-bottom:15px;">

		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<!-- Bottom Left Bar -->
		<ins class="adsbygoogle"
		     style="display:inline-block;width:250px;height:250px"
		     data-ad-client="ca-pub-5399991536746056"
		     data-ad-slot="1148281726"></ins>
		<script>
		(adsbygoogle = window.adsbygoogle || []).push({});
		</script>
		
		</div> 
		
	

	</div>

	<div class="col-md-9">
		<div class="row">
			<div class="row " style="margin-top: -25px;">
				<div class="col-md-12">
				
				<div class="pageTitleBox">
					<span class="pageTitleText">Últimas <span style="color:#FFD67D;">Avaliações</span></span>
				</div>
			</div>
				
		</div>

			<c:set var="evaluationId" value="${}"></c:set>

			<%@include file="article.jsp"%>

	</div>
	</div>
</div>

<!-- Modal das Imagens do Bairro -->
<div class="modal fade" id="modal_pictures" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Foto(s):
					${model.name}</h4>
			</div>

			<div class="modal-body-picture">

				<div class="row">
					<div class="col-md-1"></div>
					<div id="body_picture" class="col-md-10">
						<!-- BODY -->
					</div>
					<div class="col-md-1"></div>
				</div>

			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal da Descrição do Bairro -->
<div class="modal fade" id="modal_description" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Descrição:
					${model.name}</h4>
			</div>
			<div class="modal-body">${model.description}</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>