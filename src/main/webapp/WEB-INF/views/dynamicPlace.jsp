<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" charset="UTF-8"
	src="<c:url value='/resources/views/dynamicPlace.js'/>"></script>

<div class="row">

	<div class="col-md-12" style="font-size: 20px;">


		<div class="pageTitleBox" style="margin-top: 5px;">
			<span class="pageTitleText">Melhores <span
				style="color: #FFD67D;">avaliados</span></span>



			<c:if
				test="${not fn:containsIgnoreCase(requestScope['javax.servlet.forward.request_uri'], 'ranking')}">
				<a id="completeRankingBtn" style="float: right; margin-left: 20px;"
					href="<c:url value='/ranking'/>"><i
					class="glyphicon glyphicon-sort-by-attributes-alt"></i> Ranking
					Completo</a>
			</c:if>


			<a id="dp_filtro_link" style="float: right; cursor: pointer;"><i
				class="glyphicon glyphicon-filter"></i> Filtrar</a> <a
				id="dp_filtro_link_fechar"
				style="float: right; display: none; cursor: pointer;"><i
				class="glyphicon glyphicon-remove"></i> Fechar Filtro</a>

		</div>
	</div>


</div>



<div class=" well" style="display: none;" id="dp_filtro">

	<div class="col-md-2">
		<p class="filterItem">
			Estado: <select id="select_state" class="form-control"></select>
		</p>
	</div>
	<div class="col-md-2">
		<p class="filterItem">
			Cidade: <select disabled="disabled" id="select_city"
				class="form-control"></select>
		</p>
	</div>
	<div class="col-md-2">
		<p class="filterItem">
			Região: <select disabled="disabled" id="select_region"
				class="form-control"></select>
		</p>
	</div>

	<div class="col-md-3">
		<input id="btn_filter" type="button"
			style="margin-top: 17px; border-radius: 0px; width: 60px;"
			value="Filtrar" class="btn btn-primary" />
	</div>

	<div class=" clearfix"></div>

</div>

<div class="row" id="loading">

	<img style="width: 61px;"
		src="<c:url value='/resources/images/loading-bar.gif'/>" /><br>
	<br>

</div>

<div id="best_evaluations">
	<!-- Will be created in dynamicPlace.js -->
</div>