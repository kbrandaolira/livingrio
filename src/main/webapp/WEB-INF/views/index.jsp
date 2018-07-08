<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="<c:url value='/resources/slider/jquery.bxslider.js'/>" /></script>
<script type="text/javascript" charset="UTF-8" src="<c:url value='/resources/views/index.js'/>" /></script>

<head>
<title>Conhecendo os melhores bairros do Rio de Janeiro</title>
</head>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/slider/jquery.bxslider.css'/>">
<link href='http://fonts.googleapis.com/css?family=Denk+One|Marcellus+SC|Acme|Ubuntu|Paprika|Libre+Baskerville|Gloria+Hallelujah|Exo+2|Berkshire+Swash|Capriola|Nova+Square|Carter+One|Baumans' rel='stylesheet' type='text/css'>
<link href="<c:url value='/resources/views/index.css'/>" rel="stylesheet">

<script>
	$(document).ready(function() {
		$('.bxslider').bxSlider({
			auto : true,
			captions : true,
			pause : 7000,
			touchEnabled : false,
			controls : false,
			pager : false
		});

	});
</script>

<c:if test="${userAuthenticated != null && userAuthenticated.neighborhood != null && didAuthUserAvaliatedHisNeighborhood == false}">
	<div class="row">
		<div class="col-md-12">
			<div class="msg msg-clear" role="alert" style="font-size: 14px;">
				<span class="glyphicon glyphicon-edit"></span> &nbsp; <strong>${userAuthenticated.name}</strong>, <a href="<c:url value='/neighborhood/${userAuthenticated.neighborhood.id}/evaluate'/>">Clique aqui</a> para avaliar o bairro de ${userAuthenticated.neighborhood.name}.
			</div>
		</div>
	</div>
</c:if>

<div class="row" id="slider">
	<div class="col-md-12">
		<ul class="bxslider">
			<li><img style="width: 100%; height: 160px;" src="<c:url value='/resources/slider/LIV01.jpg'/>" />
				<div class="caption1">
					<span class="banner1_msg_h1" style="margin-top: 30px;"><b>Seja bem-vindo a rede social do seu bairro</b></span>
					<div class="caption2" style="margin-top: 10px;">Mais de 100 avaliações já foram feitas, já fez a sua?</div>

				</div></li>
			<li><img style="width: 100%; height: 160px;" src="<c:url value='/resources/slider/LIV02.jpg'/>" />
				<div class="caption1">
					<div class="banner1_msg_h1" style="">
						<b>Procurando informações sobre bairros?</b>
					</div>
					<div class="caption2" style="">Temos avaliações de moradores do Rio de Janeiro para ajudá-lo.</div>
				</div></li>
		</ul>
	</div>
</div>

<c:if test="${userAuthenticated == null && model.profilesModels != null}">
	<div id="call_to_register" class="row" style="margin-top: 15px;">

		<div class="col-md-4">
			<c:forEach items="${model.profilesModels}" var="profile">
				<img class="img-rounded" style="height: 48px; width: 48px;margin-top:4px;" src="<c:url value="/profile/${profile.id}/photo/s" />" title="${profile.name}">
			</c:forEach>
		</div>
		<div class="col-md-6" style="margin-top: 10px; font-size: 18px; margin-left: -20px; margin-right: 20px;"><span id="msgHome">Faça como eles, cadastre-se e participe da nossa rede.</span></div>
		<div class="col-md-2">
			<input id="link_newuser" style="font-size: 16px; padding: 7px; float: right;" class="btn btn-success" type="button" value="Clique aqui e Cadastre-se!" />
			<span id="orDiv">OU</span>
			<input id="link_newuser_facebook" style="font-size: 16px; padding: 7px; float: right; margin-bottom: 5px;" class="btn btn-primary" type="button" value="Cadastre-se com facebook" /> 
			
			
		</div>

	</div>
</c:if>

<!-- Melhores Avaliações -->
<input id="TYPE_FILTER" type="hidden" value="index" />
<%@ include file="/WEB-INF/views/dynamicPlace.jsp"%>

<div class="row" style="margin-top: 25px;">
	<div class="col-md-12" style="font-size: 20px; margin-bottom: 10px;">
		<div class="pageTitleBox">
			<span class="pageTitleText">Últimas <span style="color: #FFD67D;">avaliações</span></span>
		</div>
	</div>
</div>

<div class="row">
	<c:forEach varStatus="status" var="evaluation" items="${model.evaluationsModels}">
		<div class="col-md-3">
			<div class="row">
				<div class="col-md-4">
					<img class="img-rounded" style="width: 90px; height: 80px;" src="<c:url value="/profile/${evaluation.creatorId}/photo/s" />" />
				</div>
				<div class="col-md-8 userInfo">
					<p>
						<b><a id="person_${evaluation.creatorId}" href="#">${evaluation.creatorName}</a></b>
					</p>
					<p>
						<font style="font-size: 10px;"><i><b>${evaluation.neighborhoodName}</b><br>${evaluation.neighborhoodSpecification}</i></font>
					</p>

				</div>
			</div>
			<div class="row userComment">
				<p class="text-justify" style="color: gray; margin-left: 10px; margin-right: 10px; margin-top: 8px;">
					<i>${evaluation.comment}</i>
				</p>
			</div>
		</div>
	</c:forEach>

	<div class="col-md-3 indexAdsense" style="padding-left: 50px;">
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<!-- Home - Right -->
		<ins class="adsbygoogle" style="display: inline-block; width: 200px; height: 200px" data-ad-client="ca-pub-5399991536746056" data-ad-slot="6266152126"></ins>
		<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
		</script>
	</div>

</div>

<script>
	$(document).ready(function() {
		$("a[id^=person_]").click(function() {
			var id = $(this).attr("id");
			id = id.substring(7);

			window.location.href = $("#BASE_URL").val() + "profile/" + id;

		});

		$("#link_newuser").click(function() {
			window.location.href = $("#BASE_URL").val() + "register";

		});

	});
</script>