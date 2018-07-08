<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>


<head>
<title>Login</title>
</head>

<!-- for Boostrap -->
<script src="<c:url value="/resources/js/jquery.js" />"></script>
<script src="<c:url value="/resources/js/jquery.mask.js" />"></script>


<!-- for jQuery UI -->
<script
	src="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.js'/>"></script>

<script>
	$(document)
			.ready(
					function() {

						$("form").keypress(
								function(e) {
									if ((e.which && e.which == 13)
											|| (e.keyCode && e.keyCode == 13)) {
										$(this).submit();
										return false;
									} else {
										return true;
									}
								});

						configureAutoComplete("createAccount_",
								function callback2(selectedElement) {
									$("#createAccount-neighborhood-id").val(
											selectedElement.attr("id")
													.substring(39));

									$("#bairroSelecionadoContent").html(
											selectedElement.html());
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

						$("#createAccount_input_autocomplete")
								.focus(
										function() {

											$(
													"#createAccount_result_autocomplete")
													.html(
															"Este é um campo auto completável, escreva o nome ou parte do nome do bairro e aguarde a lista ser carregada. Quandos as opções estiverem disponíveis, clique na desejada.");
											$(
													"#createAccount_result_autocomplete")
													.fadeIn();

										});

						$("#btn-login").click(function() {
							$("#loginForm").submit();

						});

						$("#createPerson-dataNascimento").mask('00/00/0000');

					});
</script>


<style>

#btn-fblogin {
	margin-bottom: 10px;
	padding: 12px;
	border-radius: 0px;
	border: 0px !important;
	width: 100%;
	height: 43px;
	margin-top: 20px;
}

.panel {
	border: 0px;
	border-bottom: 1px solid #ccc;
	border-radius: 0px;
}

.panel-heading {
	border-radius: 0px;
}

#captchaBox {
	background-color: #fff;
	border-radius: 0px;
	border: 1px solid #ccc;
	padding: 0px;
}

.recaptchatable #recaptcha_image #recaptcha_challenge_image {
	display: inline !important;
	margin-top: 0px !important;
	border: 1px solid #ccc !important;
}

.panel-heading, #btn-fblogin {
	font-size: 12px !important;
	text-transform: uppercase;
}

span.input-group-addon {
	border-radius: 0px;
	padding: 12px;
}

.recaptchatable #recaptcha_response_field {
	width: 153px !important;
	position: relative !important;
	bottom: 7px !important;
	padding: 7px !important;
	margin: 15px 0px 0px !important;
	font-size: 11pt;
	padding: 12px;
	height: 43px;
	border-radius: 0px;
	border: 1px solid #ccc;
}

.recaptchatable .recaptcha_r4_c4, .recaptchatable .recaptcha_r3_c2,
	.recaptchatable .recaptcha_r1_c1, .recaptchatable .recaptcha_r3_c1,
	.recaptchatable .recaptcha_r2_c1, .recaptchatable .recaptcha_r7_c1,
	.recaptchatable .recaptcha_r3_c3, .recaptchatable .recaptcha_r2_c2,
	.recaptchatable .recaptcha_r7_c1, .recaptchatable .recaptcha_r4_c2,
	.recaptchatable .recaptcha_r8_c1, .recaptchatable .recaptcha_r4_c1 {
	background: none !important;
}

.recaptcha_theme_red #recaptcha_response_field {
	border: 1px solid #ccc;
	border: 1px solid #CCC;
	margin-left: -7px !important;
	display: inline;
	position: relative !important;
	margin-top: 8px !important;
}

#recaptcha_switch_audio, #recaptcha_whatsthis {
	display: none;
}

input#login-password, input#login-confirm-password,
	#createPerson-dataNascimento, #createAccount_input_autocomplete, input#login-username
	{
	padding: 12px;
	height: 43px !important;
	border-radius: 0px;
	border: 1px solid #ccc;
}

.btn {
	margin-bottom: 10px;
	padding: 12px;
	border-radius: 0px;
	border: 0px !important;
	height: 40px;
	margin-top: 0px;
	font-size: 12px !important;
	text-transform: uppercase;
}

#authLinks {
	display: none !important;
}

#registerInfo {
	text-align: center;
	font-size: 17px;
	width: 100%;
}

#registerInfo a, #recoverPasswordInfo a {
	text-decoration: underline;
}

#recoverPasswordInfo {
	margin-top: 35px;
	display: block;
}
</style>


<div class="row">

	<div id="login_web">


		<div class="col-xs-4 col-md-offset-2">


			<div class="row">

				Várias avaliações já foram feitas. <br> Faça a sua também.

			</div>


		</div>


		<div class="col-xs-4">



			<div class="panel panel-default well">
				<div class="panel-heading">
					<div class="panel-title">Faça o Login</div>

				</div>

				<a id="btn-fblogin" href='<c:url value="/facebookAuthentication" />'
					class="btn btn-primary"><img
					style="margin-top: -6px; margin-right: 5px"
					src="https://cdn0.iconfinder.com/data/icons/social-set-2/512/UberCons_SocialPack_Facebook-32.png" />
					Entrando com Facebook</a>


				<div id="orDiv">
					<span>OU</span>
				</div>

				<span id="loginEmailMsg">Utilizando seu login:</span>

				<style>
#loginEmailMsg {
	margin-top: 10px;
	display: block;
}

#orDiv {
	text-align: center;
	position: relative;
	color: #ccc;
}

#orDiv span {
	display: inline-block;
	margin-top: 6px;
}

#orDiv span:before, #orDiv span:after {
	border-top: 1px solid #ccc;
	display: block;
	height: 1px;
	content: " ";
	width: 40%;
	position: absolute;
	left: 0;
	top: 1.2em;
}

#orDiv span:after {
	right: 0;
	left: auto;
}
</style>


				<div style="padding-top: 30px" class="panel-body">

					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<form:form action='processLogin' method="post"
						modelAttribute="loginForm">

						<c:if test="${not empty loginFormValidationMessages}">
							<div class="alert alert-danger">
								<c:forEach varStatus="status" var="message"
									items="${loginFormValidationMessages}">
								
									[<b>${message.type}</b>] ${message.message} <br>

								</c:forEach>
							</div>
						</c:if>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-envelope"></i></span>
							<form:input id="login-username" type="text" class="form-control"
								path="email" value="" placeholder="E-mail"></form:input>
						</div>

						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span>
							<form:input id="login-password" type="password"
								class="form-control" path="password" placeholder="Senha"></form:input>
						</div>

						<div style="margin-top: 10px" class="form-group">


							<a id="btn-login" href="#" style="float: right;"
								class="btn btn-primary">Entrar </a>




						</div>



					</form:form>

					<div id="recoverPasswordInfo">
						<a>Esqueci minha senha</a>
					</div>

				</div>


				<div id="registerInfo">
					Novo? <a href="register">Registre-se</a>
				</div>

			</div>

			<%-- <div class="panel panel-default well">
				<div class="panel-heading">
					<div class="panel-title">Recuperar senha</div>
					
				</div>
	
				<div style="padding-top: 30px" class="panel-body">
	
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
	
					<form:form action='getPassword' method="post" modelAttribute="getPasswordForm">
	
						<c:if test="${not empty getPasswordFormSuccessMessages}">
							<div class="alert alert-info">
								<c:forEach varStatus="status" var="message" items="${getPasswordFormSuccessMessages}">
								
									[<b>${message.type}</b>] ${message.message} <br>
									
								</c:forEach>
							</div>
						</c:if>
						
						<c:if test="${not empty getPasswordFormErrorMessages}">
							<div class="alert alert-danger">
								<c:forEach varStatus="status" var="message" items="${getPasswordFormErrorMessages}">
								
									[<b>${message.type}</b>] ${message.message} <br>
									
								</c:forEach>
							</div>
						</c:if>
	
						<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-envelope"></i></span> <form:input id="login-username"
								type="text" class="form-control" path="email" value=""
								placeholder="E-mail"></form:input>
						</div>
	
				
						<div style="margin-top: 10px" class="form-group">
						
								
							<input style="float:right;"  class="btn btn-primary" type="submit" value="Recuperar">
						
						</div>
					
					</form:form>
	
				</div>
				
			</div> --%>

		</div>

	</div>

</div>

<!-- UNDER 400px -->
<div id="login_mobile" class="row" style="display: none;">

	<!--  Entrar pelo Facebook -->
	<div class="row">
		<a id="btn-fblogin-mobile" style="width: 100%;"
			href='<c:url value="/facebookAuthentication" />'
			class="btn btn-primary">Entrar com Facebook</a>
	</div>

	<!-- Entrar pelo Sistema -->
	<div class="row">
		<div class="panel panel-default well">
			<div class="panel-heading">
				<div class="panel-title">Entrar</div>

			</div>

			<div style="padding-top: 30px" class="panel-body">

				<div style="display: none" id="login-alert"
					class="alert alert-danger col-sm-12"></div>

				<form:form action='processLogin' method="post"
					modelAttribute="loginForm">

					<c:if test="${not empty loginFormValidationMessages}">
						<div class="alert alert-danger">
							<c:forEach varStatus="status" var="message"
								items="${loginFormValidationMessages}">
							
								[<b>${message.type}</b>] ${message.message} <br>

							</c:forEach>
						</div>
					</c:if>

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-envelope"></i></span>
						<form:input id="login-username" type="text" class="form-control"
							path="email" value="" placeholder="E-mail"></form:input>
					</div>

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span>
						<form:input id="login-password" type="password"
							class="form-control" path="password" placeholder="Senha"></form:input>
					</div>

					<div style="margin-top: 10px" class="form-group">

						<a id="btn-login" href="#" style="float: right;"
							class="btn btn-primary">Entrar </a>

					</div>

				</form:form>

			</div>

		</div>
	</div>

	<!-- Registre-se -->
	<div class="row">

		<%-- <div class="panel panel-default well">
			<div class="panel-heading">
				<div class="panel-title">Novo? Registre-se</div>
				
			</div>
			<div class="panel-body">
				<form:form action='createAccount' method="post" modelAttribute="createAccountForm">

					<c:if test="${not empty createAccountValidationMessages}">
						<div class="alert alert-danger">
							<c:forEach varStatus="status" var="message" items="${createAccountValidationMessages}">
							
								[<b>${message.type}</b>] ${message.message} <br>
								
							</c:forEach>
						</div>
					</c:if>


					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <form:input id="login-username"
							type="text" class="form-control" path="name" value=""
							placeholder="Nome"></form:input>
					</div>

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon glyphicon-envelope"></i></span> <form:input id="login-username"
							type="text" class="form-control" path="email" value=""
							placeholder="E-mail"></form:input>
					</div>
					
					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon glyphicon-calendar"></i></span> <form:input id="createPerson-dataNascimento"
							type="text" class="form-control" path="birthDate" value=""
							placeholder="Data de nascimento - Ex: 21/03/1976"></form:input>
					</div>
					
						
					<div class="row" style="margin-bottom:25px; margin-left:0px">
			
						
								<img title="Masculino" src="<c:url value="/resources/images/gendermale24.png" />" />
					
							
								<form:radiobutton path="gender" value="MALE" cssStyle="margin-left:3px;margin-right:10px;" /> 
							
						
								<img title="Feminino" src="<c:url value="/resources/images/genderfemale24.png" />" />
							
							
								<form:radiobutton path="gender" value="FEMALE"  cssStyle="margin-left:3px;margin-right:10px;" />
							
					
			
						
					</div>
					
					<div id="inputGroupAutoComplete" style="margin-bottom: 25px;<c:if test="${not empty createAccountForm.neighborhoodId}"> display:none; </c:if>" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-home"></i></span> 
							
							<form:input 
								id="createAccount-neighborhood-id"
								type="hidden" 
								class="form-control" 
								path="neighborhoodId" 
								value=""
								></form:input>
							
							<form:input 
							autocomplete="off"
								id="createAccount_input_autocomplete"
								type="text" 
								class="form-control" 
								path="neighborhoodName" 
								value=""
								placeholder="Bairro"></form:input>
								
								
							<div style="display:none;" id="createAccount_result_autocomplete"></div>
								
							
					</div>
					
					<div id="bairroSelecionadoPanel" class="panel panel-default well" 
						
					
						
							<c:if test="${empty createAccountForm.neighborhoodId}">
						
							style="display:none;" </c:if> >
							<div class="panel-heading">
								<div class="panel-title">Bairro residencial:</div>
								
							</div>
							<div class="panel-body" id="bairroSelecionadoContent">
							
								
								<div class="col-md-4">
									<img class="img-thumbnail createAccount_autocomplete_img" style="width: 100%; height: 80px;" src="neighborhood/${createAccountForm.neighborhoodId}/main/s">
								</div>
								
								<div class="col-md-8">
									<p>
										<b>${createAccountForm.neighborhoodName}</b>
										<br>
										<font style="font-size: 10px;">
											<i>${createAccountForm.neighborhoodSpecification}</i>
										</font>
									</p>
								</div>
							
							
							</div>
							
							<a id="btn_alterar_meu_bairro" class="btn btn-info" style="float:right;">Alterar</a>
							
							<div class="clearfix"></div>
							
						</div>
	
					
					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <form:input id="login-password"
							type="password" class="form-control" path="password"
							placeholder="Senha"></form:input>
					</div>
				
					
					
					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <form:input id="login-confirm-password"
							type="password" class="form-control" path="passwordConfirm"
							placeholder="Confirmar Senha"></form:input>
					</div>
					
					<div style="float:right;" class="form-group">
					
							<input style="float:right;" class="btn btn-primary" class="btn btn-primary" type="submit" value="Registrar-se">
					
					</div>
					
					</form:form>
			</div>
			
		</div>
	 --%>
	</div>

</div>