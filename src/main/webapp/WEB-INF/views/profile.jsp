<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<head><title>${model.name}</title></head>

<!-- CSS -->

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/views/profile.css'/>" >

<!-- Javascript -->

<div class="container">


	<div class="row">

		<%@include file="profileSideBar.jsp" %>
		
		<div class="col-md-10">

			<div class="row " style="margin-top:-13px;margin-bottom:4px;">
				<div class="col-md-12">
				
				<div class="pageTitleBox">
				
					<c:if test="${userAuthenticated.id eq model.id}">
				
						<span class="pageTitleText">Minhas <span style="color:#FFD67D;">Avaliações</span></span>
					
					</c:if>
					<c:if test="${userAuthenticated.id ne model.id}">
						<span class="pageTitleText">Avalições <span style="color:#FFD67D;">Enviadas</span></span>
					</c:if>
					
				</div>
			</div>
			</div>

			<input id="identifier" type="hidden" value="${model.id}" />
			<input id="type" type="hidden" value="PERSON"/>

			<%@include file="article.jsp" %>

		</div>

	</div>


</div>


<!-- Modal More Informations-->
<div class="modal fade" id="modal_moreInformations" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="moreInformations_title" class="modal-title">
					<!-- Está sendo escrito por jQuery -->
				</h4>
			</div>
			<div id="moreInformations_body" class="modal-body">
				<!-- Está sendo escrito por jQuery -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>



<!-- Questions Modal -->
<div class="modal fade" id="modal_questions" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="moreInformations_title" class="modal-title">
					<!-- Está sendo escrito por jQuery -->
				</h4>
			</div>
			<div id="moreInformations_body" class="modal-body">
				<!-- Está sendo escrito por jQuery -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>



