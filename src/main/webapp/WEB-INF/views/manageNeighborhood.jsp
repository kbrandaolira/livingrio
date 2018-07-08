<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<script src="http://malsup.github.com/jquery.form.js"></script>

<head><title>Gerenciar Bairro</title></head>

<div class="row" style="font-size: 20px;">
		<div class="col-md-12">
			Gerenciar Bairro 
			<c:if test="${model.id != null && model.name != null && model.name != ''}">
				<font style="color: gray;"><i>(${model.name})</i></font>
			</c:if>
			
		</div>
</div>

<hr style="margin-top:10px;">

<form:form action="${pageContext.request.contextPath}/manageNeighborhood/submit" modelAttribute="model" method="post" id="form_manageNeighborhood" enctype="multipart/form-data">
	<c:choose>
		<c:when test="${response.ok == true}">
			<div class="row">
				<div class="alert alert-success" role="alert"><c:out value="${response.message}" escapeXml="false"/></div>
				
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${response.ok == false}">
				<div class="row">
					<div class="alert alert-danger" role="alert"><c:out value="${response.message}" escapeXml="false"/></div>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>

	<div class="row">
		<div class="col-md-4">
			<p><font style="color:red">*</font> <b>Estado:</b> <select name="stateId" id="select_state" class="form-control"></select> </p>
		</div>
		<div class="col-md-4">
			<p><font style="color:red">*</font> <b>Cidade:</b> <select name="cityId" disabled="disabled" id="select_city" class="form-control"></select> </p>
		</div>
		<div class="col-md-4">
			<p><b>Região:</b> <select name="regionId" disabled="disabled" id="select_region" class="form-control"></select> </p>
		</div>
	</div>
	
    <div class="control-group form-group">
        <div class="controls">
            <label><font style="color:red">*</font> Bairro:</label>
            <input type="text" placeholder="Copacabana" name="name" value="${model.name}" class="form-control" id="name">
            <p class="help-block"></p>
        </div>
    </div>
    
    <div class="control-group form-group">
        <div class="controls">
            <label><font style="color:red">*</font> Descrição:</label>
            <textarea name="description" rows="10" placeholder="Localização e beleza privilegiada, possui uma das praias mais belas do mundo..." class="form-control" id="description">${model.description}</textarea>
        </div>
    </div>
    
    <div class="control-group form-group">
		<div class="controls">
        	<label>Foto(s):</label>
			<input name="multipartFiles" multiple="multiple" type="file" /><br/>
		</div>
	</div>
	
	<c:if test="${model.pictures != null && model.pictures.size() > 0}">
		<div class="row">
			<div class="col-md-1"><b>Principal</b></div>
			<div class="col-md-2"><b>Prévia</b></div>
			<div class="col-md-2"><b>Ações</b></div>
		</div>
		
		<hr>
	</c:if>
	
	<c:forEach varStatus="status" var="picture" items="${model.pictures}">
		<div id="rowPicture_${picture.id}" class="row">
			<div class="col-md-1">
				<c:choose>
					<c:when test="${picture.main == true}">
						<input id="checkbox_picture_${picture.id}" checked="checked" type="checkbox"/>
					</c:when>
					<c:otherwise>
						<input id="checkbox_picture_${picture.id}" type="checkbox"/>
					</c:otherwise>
				</c:choose>
				
			</div>
			<div class="col-md-2">
				<img style="width: 150px; height: 100px;" src="<c:url value='/picture/find/${picture.id}'/>"/>
			</div>
			<div class="col-md-1">
				<a id="viewPicture_${picture.id}" href="#">Visualizar</a>
			</div>
			<div class="col-md-1">
				<a id="deletePicture_${picture.id}" href="#">Excluir</a>
			</div>
		</div>
		
		<p>
	</c:forEach>
	
	
	<p><font style="color:red; font-size: 11px;"><b>* Dados Obrigatórios</b></font></p>
	
	<input type="submit" class="btn btn-primary" value="Salvar"/>
    
    <input id="id" name="id" type="hidden" value="${model.id}">
    <input id="picture" type="hidden" value="${model.pictures}"/>
    <input id="state_id" type="hidden" value="${model.stateId}"/>
    <input id="city_id" type="hidden" value="${model.cityId}"/>
    <input id="region_id" type="hidden" value="${model.regionId}"/>
    
    
</form:form>


<script>

$(document).ready(function(){
	
	var state_id = $("#state_id").val();
	var city_id = $("#city_id").val();
	var region_id = $("#region_id").val();
	
	loadStates();
	
	if( city_id != "" && region_id != "" ){
		loadCities();
		loadRegions();
		
	}
	
	 $(':checkbox').bind('change', function() {
        var thisClass = $(this).attr('class');
        if ($(this).attr('checked')) {
            $(':checkbox.' + thisClass + ":not(#" + this.id + ")").removeAttr('checked');
        } else {
            $(this).attr('checked', 'checked');
        }
    });

	<c:if test="${model.id != null && model.pictures != null && model.pictures.size() > 0}">
	 
	$("a[id^=deletePicture_]").click(function(){
		id =  $(this).attr("id");
		id = id.substring(14);
		
		
		$.ajax({
			url : $("#BASE_URL").val() + "manageNeighborhood/deletePicture/" + id,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'text',
			success : function(result) {

				
				if (parseInt(result) >= 0){
				
					$("#rowPicture_"+id).remove();
					$("#checkbox_picture_" + result).prop("checked", true);
					
				}else{
					
					alert("Não foi possível deletar a imagem, tente novamente.");
					
				}
				
			},
			error: function (xhr, ajaxOptions, thrownError) {
			      alert(xhr.status);
			      alert(thrownError);
			}
			
		});
		
	});
		
	$("a[id^=viewPicture_]").click(function(){
		id =  $(this).attr("id");
		id = id.substring(12);
		window.open($("#BASE_URL").val() + "picture/find/" + id, "_blank");
		
	});
	
	$("input[id^=checkbox_picture_]").click(function(){
		id =  $(this).attr("id");
		id = id.substring(17);
		
		$.ajax({
			url : $("#BASE_URL").val() + "manageNeighborhood/changeMainPicture/" + id,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'text',
			success: function(result){
				if( result == "success" ){
					$("input[id^=checkbox_picture_]").each(function(){
						id_aux = $(this).attr("id");
						id_aux = id_aux.substring(17);
						
						if( $(this).is(":checked") && id != id_aux ){
							$("#checkbox_picture_"+id_aux).prop( "checked", false );
						}
						
						
					});
					
				} else {
					alert("Erro ao efetuar a ação.");
					
				}
				
			}
			
		});
		
	});
	
	</c:if>
	
	$("#select_state").change(function(){
		if( $("#select_state").val() == 0 ){
			disableCities(); 
			removeCitiesOptions();
			
			disableRegions(); 
			removeRegionsOptions();
			
		}else{
			removeCitiesOptions();
			loadCities();
			
		}
		
	});
	
	$("#select_city").change(function(){
		if( $("#select_city").val() == 0 ){
			disableRegions();
			removeRegionsOptions();
			
		}else{
			removeRegionsOptions();
			loadRegions();
			
		}
		
	});
	
	function loadStates(){
		$.ajax({
			url: $("#BASE_URL").val() + "dynamic/place/states",
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				$.each(result, function(i, state){
					$("#select_state").append("<option value='"+state.id+"'>"+state.acronym+"</option>");
					
					
				});
				
				if( state_id != "" ){
					$("#select_state").val(state_id).find("option[value=" + state_id + "]").attr('selected', true);
					
				}else{
					$("#select_state").val(0);
				}
				
			}
			
			
		});
	
	}
	
	function loadCities(){
		var idState = null;

		if( $("#select_state").val() != null ){
			idState = $("#select_state").val();
		}else{
			idState = state_id;
		}
		
		$.ajax({
			url: $("#BASE_URL").val() + "dynamic/place/cities/by/" + idState,
			data: JSON.stringify(idState),
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				$.each(result, function(i, city){
					$("#select_city").append("<option value='"+city.id+"'>"+city.name+"</option>");
					
				});
				
				if( city_id != "" ){
					$("#select_city").val(city_id);
				}else{
					$("#select_city").val(0);
				}
				
				
			}
			
			
		});
		
		enableCities();
		
	}
	
	function loadRegions(){
		var idCity = null;

		if( $("#select_city").val() != null ){
			idCity = $("#select_city").val();
		}else{
			idCity = city_id;
		}
		
		$.ajax({
			url: $("#BASE_URL").val() + "dynamic/place/regions/by/" + idCity,
			data: JSON.stringify(idCity),
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				$.each(result, function(i, region){
					$("#select_region").append("<option value='"+region.id+"'>"+region.name+"</option>");
					
				});
				
				if(region_id != ""){
					$("#select_region").val(region_id);
				}else{
					$("#select_region").val(0);
				}
				
			}
			
			
		});
		
		enableRegions();
		
	}
	
	function enableCities(){
		$("#select_city").prop("disabled", false);
	}
	
	function enableRegions(){
		$("#select_region").prop("disabled", false);
	}
	
	function disableCities(){
		$("#select_city").prop("disabled", true);
	}
	
	function disableRegions(){
		$("#select_region").prop("disabled", true);
	}
	
	function removeCitiesOptions(){
		$("#select_city").find("option").remove();
	}
	
	function removeRegionsOptions(){
		$("#select_region").find("option").remove();
	}
	
});

</script>