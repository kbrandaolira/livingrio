<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="shortcut icon" href="<c:url value='/resources/images/LR_LOGO.fw.png'/>"> 
    <title>Living RIO - <decorator:title/></title>


	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.structure.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.theme.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jRating.jquery.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/views/main.css'/>" >
	
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/views/responsible/css/under400px.css'/>" >
	
	<link href='http://fonts.googleapis.com/css?family=Gloria+Hallelujah|Merienda+One|Kaushan+Script|Berkshire+Swash|Leckerli+One|Meddon|Molengo' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Jockey+One|Viga|Acme|Molengo' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/css?family=Oswald|Ubuntu|Bitter|Josefin+Sans' rel='stylesheet' type='text/css'>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.js'/>"/></script>
	<script type="text/javascript" src="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.js'/>"/></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js'/>"></script>
	<script src="<c:url value='/resources/js/jRating.jquery.js'/>"></script>
	
	
	 <script src="<c:url value="/resources/views/js/components/autocomplete.js" />"></script>

	<script type="text/javascript">
	$(document).ready(function(){
		$.ajaxSetup({ 
			cache: false ,
			scriptCharset: "iso-8859-1"
		});
		
		
	});
	
	
	function removeAccents(text){
		
		text = replaceAll(text,'á','a');
		text = replaceAll(text,'é','e');
		text = replaceAll(text,'ó','o');
		text = replaceAll(text,'í','i');
		text = replaceAll(text,'ú','u');
		text = replaceAll(text,'ã','a');

		text = replaceAll(text,'õ','o');
		text = replaceAll(text,'â','o');
		text = replaceAll(text,'ç','c');
		
		return text;
	}
	
	function replaceAll(string, token, newtoken) {
		while (string.indexOf(token) != -1) {
	 		string = string.replace(token, newtoken);
		}
		return string;
	}
		
	</script>
	
		<script type="text/javascript">
	
	
		configureAutoComplete("",function callback(selectedElement){
			window.location = $(selectedElement).attr("href");
		},0);
	
		
	</script>



    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<input id="BASE_URL" type="hidden" value="${pageContext.request.contextPath}/"/>
	<input id="isAuthenticated" type="hidden" value="${userAuthenticated != null}"/>
	
	<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="<c:url value='/' />" class="navbar-brand" href="#">Living<span style="color:#FFD67D">RIO</span></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input id="input_autocomplete" autocomplete="off" type="text"  placeholder="Encontre o seu bairro..." class="form-control">
          <div style="display:none;" id="result_autocomplete"></div>
        </div>

      </form>
      <ul class="nav navbar-nav navbar-right">
       
       <c:if test="${not empty userAuthenticated}">
        	
        
         	<li class="dropdown profileMenu">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
				
					<img id="profileImage" class="img-rounded"  src="<c:url value= "/profile/${userAuthenticated.id}/photo/s" />" >
					
					${userAuthenticated.name} <span class="caret"></span>
					
					<c:if test="${questionsToAnswer > 0}">
						&nbsp; <span class="label label-danger">${questionsToAnswer}</span>					
					</c:if>
					
				</a>
				
				<ul class="dropdown-menu" role="menu">
				  	<li><a href="<c:url value="/profile/${userAuthenticated.id}/edit" />"><i class="glyphicon glyphicon-user"></i> Minha Conta</a></li>
					
					<li class="divider"></li>
					
					<c:if test="${userAuthenticated.role == 'ADMIN'}">
						<li><a style='color: #246F9E;' href="<c:url value="/neighborhood/evaluations/validation" />"><i class="glyphicon glyphicon-check"></i>   Aprovar Avaliações</a></li>
						
						<li class="divider"></li>
						
						<li><a style='color: #246F9E;' href="<c:url value="/manageNeighborhood/init/0" />"><i class="glyphicon glyphicon-plus-sign"></i> Cadastrar Bairro</a></li>
						
						<li class="divider"></li>
					</c:if>
					
					<li><a href="<c:url value="/profile/${userAuthenticated.id}" />"><i class="glyphicon glyphicon-star"></i> Minhas Avaliações</a></li>
				  	
				  	<li class="divider"></li>
				  	
				  	<c:if test="${iHaveQuestions}">
					  	<li><a href="<c:url value="/profile/${userAuthenticated.id}/questions" />"><i class="glyphicon glyphicon-list"></i> Minhas Perguntas</a></li>

						<li class="divider"></li>				  	
				  	</c:if>
				  	
				  	<c:if test="${hasQuestionsWithoutAnswer}">
			  			<li><a href="<c:url value="/profile/${userAuthenticated.id}/questions/answer" />"><i class="glyphicon glyphicon-pencil"></i> Responder Perguntas
			  			<c:if test="${questionsToAnswer > 0}">
			  				&nbsp; <span class="label label-danger">${questionsToAnswer}</span>
			  			</c:if>
			  			
			  		</a></li>
			  		
			  			<li class="divider"></li>
			  		</c:if>
			  		
			  		<li><a href="<c:url value="/contact" />"><i class="glyphicon glyphicon-envelope"></i> Fale Conosco</a></li>
					
					<li class="divider"></li>
					
					<li><a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-log-out"></i> Sair</a></li>
				</ul>
				
        	</li>
       
		</c:if>
          
          <c:if test="${ empty userAuthenticated }">    
        	 <li id="loginBtn"><a  style="background-color:#62a8d1;color:#fff !important" href="<c:url value="/login" />">Entrar</a></li>
        	 <li id="loginMobileBtn" style="display:none;"><a href='<c:url value="/facebookAuthentication" />'> <img src= "https://cdn1.iconfinder.com/data/icons/logotypes/32/circle-facebook_-20.png" /> Entrar com Facebook</a></li>
        </c:if>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
	
	
	<%-- 
	<!-- <HEADER> -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
        	<a class="navbar-brand" href="#">LIVINGRIO</a>
        </div>
        <div id="navbar" class="navbar">
        	
        	
        	<form class="navbar-form navbar-left" role="search">
  <div class="form-group">
   
   
  </div>

</form>
			
					
					
				
			
        </div><!--/.navbar-collapse -->
        
        
         <ul class="nav navbar-nav navbar-right">
        
       
      
        
        
        
       
      </ul>
       
        
      </div>
    </nav>
    
    <!-- </HEADER> --> --%>

	<!-- <BODY> -->
    <div id="myBody" class="container">

		<decorator:body/>
		
    </div>
    <!-- </BODY> -->

   
     <%--   --%>
      
      
      <footer class="footer" style="background-color:#246F9E;">
		<!-- 		<div class="container">

					Copyright
					<div class="row">
						<div class="col-sm-11 col-xs-10">
							<p class="copyright"></p>
						</div>
						<div class="col-sm-1 col-xs-2 text-right">
							<a href="#" class="scroll-top"><div class="footer-scrolltop-holder"><span class="ion-ios7-arrow-up footer-scrolltop"></span></div></a>
						</div>
					</div>/row
				</div>/container
				
				
				 -->
				
				<div class="container">
        <p id="fullWidthFooter" class="text-muted">© 2015 LivingRIO&#8482. Todos os direitos reservados.   	&#8756; <a href="<c:url value='/about'/>" style="color: white" >Quem Somos</a>  	&#8756;  <a style="color: white" href="<c:url value='/contact'/>">Fale Conosco</a></p>
       	<p id="under400pxFooter" style="display:none;" class="text-muted">  <a href="#" style="color: white" >LivingRIO&#8482</a> &#8756; <a href="<c:url value='/about'/>" style="color: white" >Quem Somos</a> &#8756; <a style="color: white" href="<c:url value='/contact'/>">Fale Conosco</a></p>
       
       
      </div>
				
			</footer>
      


<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-63523572-1', 'auto');
  ga('send', 'pageview');

</script>

   
	
</body>

</html>
