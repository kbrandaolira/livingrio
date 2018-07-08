<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Responder Pergunta(s)</title>
</head>


<link href="<c:url value='/resources/css/jRating.jquery.css'/>"
	rel="stylesheet">

<!-- for Boostrap -->
<script src="<c:url value="/resources/js/jquery.js" />"></script>


<!-- for jQuery UI -->
<script
	src="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.js'/>"></script>




<body class="container">

<style>
.required {
	color: red;
}

.eee {

	background-color: #eee !important;

}

</style>

	<script src="<c:url value='/resources/js/jRating.jquery.js'/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$('button[id^="formSubmitBtn_"]').click(function() {
	
				var id = $(this).attr("id").substring(14);
				
				$("#formSubmitBtn_" + id).fadeOut();
				$("#question_loader_" + id).fadeIn();

				var frm = $($('form[id="form_' + id + '"]'));

				var data = {}
				var Form = frm;

				data["questionId"] = id;
				data["answerText"] = $("#answerText_" + id).val();

				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : frm.attr('method'),
					url : $("#BASE_URL").val() + "profile/${userAuthenticated.id}" + "/questions/answer",
					dataType : 'json',
					data : JSON.stringify(data),
					success : function(callback) {
						$("#form_" + id).fadeOut();
						$("#questionsSize").val($("#questionsSize").val() - 1);
						
						if ($("#questionsSize").val() == 0){
							$("#thanksMessage").fadeIn();
						}
						
						location.reload();
						
						
					},
					error : function() {
						alert("Houve um erro mas já estamos trabalhando para consertá-lo :)");
					}
				});

			});

		});
	</script>


	<div style="margin-top: 10px;" class="container">

		<div class="row">

			<%@include file="profileSideBar.jsp"%>

			<div class="col-md-10">
			
			<div class="row " style="margin-top:-2px;margin-bottom:4px;">
				<div class="col-md-12" style="margin-left:-15px!important;">
				
				
				
				<div class="pageTitleBox">
				
					
				
						<span class="pageTitleText">Responder <span style="color:#FFD67D;">Perguntas</span></span>
					
				
					
				</div>
			</div>
			</div>
			
			

				<blockquote id="thanksMessage"
					<c:if test="${questions.size() > 0}"> style="display:none;" </c:if>>
					<p>Não existem perguntas para serem respondidas no momento.</p>
					<footer>A equipe LIVING RIO agradece sua colaboração.</footer>
				</blockquote>

				<input type="hidden" id="questionsSize" value="${questions.size()}" />
				<c:forEach varStatus="status" var="question" items="${questions}">

					<form id="form_${question.id}" action="#" method="post">


						<div  class="criteria ">

							<div class="criterionBox row ${status.index % 2 == 0 ? 'eee' : 'fff'}">

								<div class="col-md-2" style="margin-top: 23px; text-align: center;">
									
									<img class="img-thumbnail" style="display: inline;width: 150px; height: 120px;"
										src="<c:url value="/profile/${question.responsible.id}/photo/s"/>" />

									<span class="responsibleName" style="  text-align: center;font-size: 16px;margin-top: 2px;display: block;">

										${question.responsible.name} </span>

								</div>

								<div class="col-md-4" style="margin-top: 23px;">

									<blockquote>
										<p>${question.text}</p>
										<footer>
											Referente a sua avaliação ao bairro <cite
												title="Source Title">${question.evaluation.neighborhood.name} em ${question.getCreationDateFormatted()}</cite>
										</footer>
									</blockquote>

								</div>

								<div class="col-md-4">
									<textarea id="answerText_${question.id}" name="form.answerText"
										maxlength="10000" class="full" style="margin-top: 6px; width: 330px; height: 200px;"></textarea>
								</div>

								<div class="col-md-2" style="margin-top: 23px;text-align:right;">

									<button style="border-radius:0px;" type="button" id="formSubmitBtn_${question.id}"
										class="btn btn-success">Responder</button>
										
										<img style="display:none;" id="question_loader_${question.id}" width="32px" style="width:32px" src="<c:url value='/resources/images/loading-bar.gif'/>" \>


								</div>

							</div>

						</div>

					</form>

				</c:forEach>


			</div>
		</div>
	</div>
</body>
</html>