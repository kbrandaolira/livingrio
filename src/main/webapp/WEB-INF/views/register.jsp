<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<link href="<c:url value='/resources/views/register.css'/>" rel="stylesheet">

<script src="<c:url value="/resources/js/jquery.js" />"></script>
<script src="<c:url value="/resources/js/jquery.mask.js" />"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#createPerson-dataNascimento").mask('00/00/0000');

		configureAutoComplete("createAccount_",
				function callback2(selectedElement) {
					$("#createAccount-neighborhood-id").val(selectedElement.attr("id").substring(39));
					$("#bairroSelecionadoContent").html(selectedElement.html());
					$("#bairroSelecionadoPanel").fadeIn();
					$("#inputGroupAutoComplete").fadeOut();

				}, 4);

		$("#btn_alterar_meu_bairro").click(function() {
			$("#createAccount-neighborhood-id").val("");
			$("#createAccount_input_autocomplete").val("");
			$("#bairroSelecionadoContent").html("");
			$("#bairroSelecionadoPanel").fadeOut();
			$("#inputGroupAutoComplete").fadeIn();
		});

		$("#createAccount_input_autocomplete").focus(function() {

			$("#createAccount_result_autocomplete").html("Este é um campo auto completável, escreva o nome ou parte do nome do bairro e aguarde a lista ser carregada. Quandos as opções estiverem disponíveis, clique na desejada.");
			$("#createAccount_result_autocomplete").fadeIn();

		});

	});
</script>

<div class="panel panel-default well" style="width: 50%; margin: 0 auto;">


	<div class="panel-heading">
		<div class="panel-title">Registre-se</div>
	</div>


	<div class="panel-body">

		<form:form action='processRegister' method="post" modelAttribute="createAccountForm">

			<c:if test="${not empty createAccountValidationMessages}">
				<div class="alert alert-danger">
					<c:forEach varStatus="status" var="message" items="${createAccountValidationMessages}">
								
						[<b>${message.type}</b>] ${message.message} <br>

					</c:forEach>
				</div>
			</c:if>


			<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
				<form:input id="login-username" type="text" class="form-control" path="name" value="" placeholder="Nome"></form:input>
			</div>

			<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon glyphicon-envelope"></i></span>
				<form:input id="login-username" type="text" class="form-control" path="email" value="" placeholder="E-mail"></form:input>
			</div>

			<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon glyphicon-calendar"></i></span>
				<form:input id="createPerson-dataNascimento" type="text" class="form-control" path="birthDate" value="" placeholder="Data de nascimento - Ex: 21/03/1976"></form:input>
			</div>


			<div class="row" style="margin-bottom: 25px; margin-left: 0px; background-color: #fff; border-radius: 0px; border: 1px solid #ccc; padding-top: 20px; padding-bottom: 20px; padding-left: 20px; margin-right: 0px;">


				<form:radiobutton path="gender" value="MALE" cssStyle="margin-left:3px;margin-right:10px;" />
				<span id="genderMaleText">Masculino </span>

				<form:radiobutton path="gender" value="FEMALE" cssStyle="margin-left:3px;margin-right:10px;" />
				<span id="genderFemaleText">Feminino </span>


			</div>

			<div id="inputGroupAutoComplete" style="margin-bottom: 25px;<c:if test="${not empty createAccountForm.neighborhoodId}"> display:none; </c:if>" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>

				<form:input id="createAccount-neighborhood-id" type="hidden" class="form-control" path="neighborhoodId" value=""></form:input>

				<form:input autocomplete="off" id="createAccount_input_autocomplete" type="text" class="form-control" path="neighborhoodName" value="" placeholder="Bairro"></form:input>


				<div style="display: none;" id="createAccount_result_autocomplete"></div>


			</div>

			<div id="bairroSelecionadoPanel" class="panel panel-default well" <c:if test="${empty createAccountForm.neighborhoodId}">
							
								style="display:none;" </c:if>>
				<div class="panel-heading">
					<div class="panel-title">Bairro residencial:</div>

				</div>
				<div class="panel-body" id="bairroSelecionadoContent">


					<div class="col-md-4">
						<img class="img-thumbnail createAccount_autocomplete_img" style="width: 100%; height: 80px;" src="neighborhood/${createAccountForm.neighborhoodId}/main/s">
					</div>

					<div class="col-md-8">
						<p>
							<b>${createAccountForm.neighborhoodName}</b> <br> <font style="font-size: 10px;"> <i>${createAccountForm.neighborhoodSpecification}</i>
							</font>
						</p>
					</div>


				</div>

				<a id="btn_alterar_meu_bairro" class="btn btn-info" style="float: right;">Alterar</a>

				<div class="clearfix"></div>

			</div>


			<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				<form:input id="login-password" type="password" class="form-control" path="password" placeholder="Senha"></form:input>
			</div>



			<div style="margin-bottom: 25px" class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				<form:input id="login-confirm-password" type="password" class="form-control" path="passwordConfirm" placeholder="Confirmar Senha"></form:input>
			</div>

			<div style="float: right;" class="form-group">

				<input style="float: right;" class="btn btn-primary" class="btn btn-primary" type="submit" value="Registrar-se">

			</div>

		</form:form>
	</div>
</div>