<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<style>

.btn-good-look{
	
	width: 100% !important;
	display: block !important;
	background-color: #FAF2E1 !important;
	border-radius:0px;
	border-bottom:1px solid #E0D3B4 !important;
	color: #000;
	
}


.btn:focus {
  outline: none;
}

#borderTopFix{
	
	border-bottom:1px solid #E0D3B4;
	
}

.btn-good-look:hover {
	
	background-color: #FAEFD7 !important;
	
}

</style>
<script src="http://malsup.github.io/min/jquery.form.min.js" type="text/javascript"></script>
<script>


	$(document).ready(function(){
		
			$("#cif_submitBtn").click(function(){
				$("#cif_inputFile").click();
			});
		



	 $("body").on('change','#cif_inputFile' , function(){  
		 
		 $("#cif_submitBtn").blur();
		 
		 $("#cif_submitBtn").html("<img src=\"http://imagem.buscape.com.br/bp7/ajax_loader.gif\" />");
		 
		 $("#profilePicture").fadeTo( "fast" , 0.5, function() {});
		
		 $("#changeImageForm").ajaxForm({
			    success:function(data) { 

			    	 $("#profilePicture").attr("src", $("#profilePicture").attr("src") +"?"+new Date().getTime());
			    	 
			    	 $("#profilePicture").fadeTo( "slow" , 1.0, function() {});
			    	 
			    	 $("#cif_submitBtn").html("Alterar");
			    	 
			    	 
			     },
			     dataType:"text"
			   }).submit();
	 
	 });
	 
	}); 
	

</script>

<div class="col-md-2" style="background-color:#fff;padding-right:15px;padding-left:0px;text-align:center;margin-right:0px">

			<img id="profilePicture" style="width: 200px;height:176px; display: block; margin-bottom: 10px;"
				class="img-thumbnail"
				src="<c:url value= "/profile/${model.id}/photo/m" />" />



			<c:if test="${userAuthenticated.id eq model.id}">
				<button id="cif_submitBtn" style="position:absolute;top:140px;right:20px;border-radius:0px;" type="button" class="btn btn-default">Alterar</button>
								
				<form id="changeImageForm" method="post" action="<c:url value='/profile/photo/upload'/>" enctype="multipart/form-data">

					<input name="file" style="display:none;" id="cif_inputFile"  type="file" />
				
				</form>
				
			</c:if>
			
			<b><font style="font-size: 18px;">${model.name}</font></b>
			<br><br>
			
			<div id="borderTopFix"></div>
			<c:if test="${userAuthenticated.id eq model.id}">
			
				<a class="btn btn-good-look" href="<c:url value="/profile/${userAuthenticated.id}/edit" />">Minha Conta</a>
				
				<a class="btn btn-good-look" href="<c:url value="/profile/${userAuthenticated.id}" />">Minhas Avaliações</a>

				<c:if test="${iHaveQuestions}">
				  	
					<a class="btn btn-good-look" href="<c:url value="/profile/${userAuthenticated.id}/questions" />">Minhas Perguntas</a>
			  		
	  			</c:if>
				
				<c:if test="${hasQuestionsWithoutAnswer}">
				  	
					<a class="btn btn-good-look" href="<c:url value="/profile/${userAuthenticated.id}/questions/answer" />">Responder Perguntas</a>
			  		
	  			</c:if>
	  			
			</c:if>
			
			<%-- <c:if test="${userAuthenticated.id ne model.id}">
			
				<button type="button" class="btn btn-good-look">Enviar Mensagem</button>
			</c:if> --%>
			
		</div>