<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<header>
	<title>Minha Conta</title>
</header>

<div style="margin-top: 10px;" class="container">


	<script type="text/javascript">
	
	
		configureAutoComplete("createAccount_",function callback2(selectedElement){
			$("#createAccount-neighborhood-id").val(selectedElement.attr("id").substring(39));
			
			$("#bairroSelecionadoContent").html(selectedElement.html());
			$("#bairroSelecionadoPanel").fadeIn();
			$("#inputGroupAutoComplete").fadeOut();
			
		},0);
		
		$(document).ready(function(){
			
			
			$("#btn_alterar_meu_bairro").click(function(){
				$("#createAccount-neighborhood-id").val("");
				$("#createAccount_input_autocomplete").val("");
				$("#bairroSelecionadoContent").html("");
				$("#bairroSelecionadoPanel").fadeOut();
				$("#inputGroupAutoComplete").fadeIn();
			});
			
		});
	
		
	</script>

	<style>
	
		.createAccount_autocomplete_img{
		
			height: 120px !important;
		
		}
	
		.panel {
		
				border:0px;
			  border-bottom: 1px solid #ccc;
			  border-radius:0px;
		
		}
		
		.panel-heading {
		
			border-radius:0px;
		
		}
		
		.panel-heading,#btn-fblogin {
		
			font-size:12px !important;
			text-transform: uppercase;
		
		}
		
		span.input-group-addon {
	  		border-radius: 0px;
	  		padding: 12px;
		}
		
		input#login-password,input#login-confirm-password,#createPerson-dataNascimento,#createAccount_input_autocomplete,input#login-username {
		  padding: 12px;
		  height: 43px !important;
		  border-radius: 0px;
		  border: 1px solid #ccc;
		}
	
	
	
	</style>

	<div class="row">

		<%@include file="profileSideBar.jsp"%>

		<div class="col-md-10">
		
		<div class="row " style="margin-top:-2px;margin-bottom:4px;">
				<div class="col-md-12" style="margin-left:-15px!important;">
				
				<div class="pageTitleBox">
					<span class="pageTitleText">Minha <span style="color:#FFD67D;">Conta</span></span>
				</div>
				
			</div>
			</div>
		<div class="row ">
			<div class="panel panel-default ">

			<div class="panel-body">
			
			<c:if test="${not empty messages}">
						<div class="msg msg-info">
							<c:forEach varStatus="status" var="message" items="${messages}">
							
								[<b>${message.type}</b>] ${message.message} <br>
								
							</c:forEach>
						</div>
					</c:if>
			
				<form:form action='editProcess' method="post" modelAttribute="createAccountForm">


					<form:hidden id="login-id"
							 path="id" value=""
							></form:hidden>
							
					


					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <form:input id="login-username"
							type="text" class="form-control" path="name" value=""
							placeholder="Nome" readonly="true"></form:input>
					</div>

					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon glyphicon-envelope"></i></span> <form:input id="login-username"
							type="text" class="form-control" path="email" value=""
							placeholder="E-mail" readonly="true"></form:input>
					</div>
					
					<div style="margin-bottom: 25px" class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon glyphicon-calendar"></i></span> <form:input id="createPerson-dataNascimento"
							type="text" class="form-control" path="birthDate" value=""
							placeholder="Data de nascimento - Ex: 21/03/1976" readonly="true"></form:input>
					</div>
					
						
					<div class="row" style="margin-bottom:25px; margin-left:0px">
						
						<img src="<c:url value="/resources/images/gendermale24.png" />" />
			
					
						<form:radiobutton path="gender" value="MALE" cssStyle="margin-left:3px;margin-right:10px;"/> 
					
				
						<img src="<c:url value="/resources/images/genderfemale24.png" />" />
					
					
						<form:radiobutton path="gender" value="FEMALE"  cssStyle="margin-left:3px;margin-right:10px;"/>
						
					</div>
					
					<div id="inputGroupAutoComplete" style="margin-bottom: 25px; <c:if test="${not empty createAccountForm.neighborhoodId}"> display:none; </c:if>" class="input-group" >
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
								
							
					</div>
	
						<div style="display:none;" id="createAccount_result_autocomplete"></div>
						
						
						
						<div id="bairroSelecionadoPanel" class="panel panel-default well" 
						
						<c:if test="${empty createAccountForm.neighborhoodId}">
						
							style="display:none;" </c:if> >
							<div class="panel-heading">
								<div class="panel-title">Bairro Residencial: </div>
								
							</div>
							<div class="panel-body" id="bairroSelecionadoContent">
							
								
								<div class="col-md-3">
									<img class="img-thumbnail createAccount_autocomplete_img" style="width: 100%; height: 80px;" src="/neighborhood/${createAccountForm.neighborhoodId}/main/s">
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
						
					
					
			

					<div style="float:right;margin-top: 40px; margin-right:20px;" class="form-group">
					
							<input style="float:right;" class="btn btn-primary" class="btn btn-primary" type="submit" value="Finalizar">
					
					</div>
					
					</form:form>
			</div>
		</div>
			</div>
		
		
		</div>

	</div>

</div>
<%-- 
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
					
					 --%>