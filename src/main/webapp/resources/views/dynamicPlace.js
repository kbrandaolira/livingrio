$(document).ready(function(){
	
	loadStates();
	
	filter();
	
	$("#btn_filter").click(function(){
		filter();
	});
	
$("#dp_filtro_link").click(function(){
		
		$(this).fadeOut();
		$("#dp_filtro_link_fechar").fadeIn();
		$("#dp_filtro").fadeIn();
		
	});

$("#dp_filtro_link_fechar").click(function(){
	
	$(this).fadeOut();
	$("#dp_filtro_link").fadeIn();
	$("#dp_filtro").fadeOut();
	
});
	
	function filter(){
		$("#best_evaluations").fadeOut();
		$("#loading").fadeIn();
		
		var idState = 0;
		var idCity = 0;
		var idRegion = 0;
		
		if( $("#select_state").val() != null ){
			idState = $("#select_state").val();
		}
		
		if( $("#select_city").val() != null ){
			idCity = $("#select_city").val();
		}
		
		if( $("#select_region").val() != null ){
			idRegion = $("#select_region").val();
		}
		
		var urlAjax = "";
		
		if( $("#TYPE_FILTER").val() == "index" ){
			urlAjax = $("#BASE_URL").val() + "dynamic/place/filter?idState=" + idState + "&idCity=" + idCity + "&idRegion=" + idRegion;
			
		} else { //ranking
			urlAjax = $("#BASE_URL").val() + "ranking/search?idState=" + idState + "&idCity=" + idCity + "&idRegion=" + idRegion;
			
		}
		
		$.ajax({
			url: urlAjax,
			data: JSON.stringify([idState, idCity, idRegion]),
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				
				var myHtml = "";
				
				var myTableHeader = "<table class='table'>" +
										"<thead>" + 
											"<tr>" +
												"<th>Pos.</th>" +
												"<th style='text-align:center;'>Bairro</th>";

				var myTable = "";
				
				var resultSize = Object.keys(result).length;
				
				if( resultSize > 0 ){
					var count = 0;
					var fechou = 0;
					$.each(result, function(i, neighborhood){
						
						if( $("#TYPE_FILTER").val() == 'index' ){
						
							if (count == 0 || count % 4 == 0){
								
								myHtml += "<div class='row'>";
								fechou = 0;
								
							}
							
							myHtml += "<div class='col-md-3'>" +
											"<div class='row' id='neighborhood_" + neighborhood.id + "'>" +
												"<div class='col-md-4'>"+
													"<img class='img-rounded' style='width:90px;height:80px;' src='" + $("#BASE_URL").val() + "neighborhood/" + neighborhood.id + "/main/s'/>"+
												"</div>"+
												"<div class='col-md-8 neighborhoodInfo'>"+
													"<p><b><a href='"+ $("#BASE_URL").val() + neighborhood.url +"'>"+ neighborhood.name +"</a></b><br>"+
													"<font style='font-size: 10px;'><i>"+ neighborhood.specification +"</i></font><br>" +
													createSpanHTML( neighborhood.average ) + "</p>" +
												"</div>"+
											"</div>"+
										"</div>";
							
						
							
						} else { //ranking
							
							var lineClass="";
							
							if (count % 2 == 0 ){
						
								lineClass="active";
							}
							
							myTable += 	
											"<tr class=\""+ lineClass +"\">" +
												"<td><font class='line_table'>"+ (i+1) +"</font></td>" +
												"<td> " + 
													"<div class='row'>" +
														"<div class='col-md-4'>" +
															"<img class='img-rounded' style='width:95px;height:50px;' src='"+ $("#BASE_URL").val() + "neighborhood/" + neighborhood.id +"/main/s'/>" +
														"</div>" +
														"<div class='col-md-8 ' style='font-size: 17px;'><a href='"+ $("#BASE_URL").val() + neighborhood.url +"'>" +
															neighborhood.name +"<br>" +
															"</a><font style='font-size:10px;font-color:gray;'><i>" + neighborhood.specification + "</i></font>" +
														"</div>" +
													"</div>" +
												"</td>";
												
												$.each(neighborhood.criterionModels, function(k, crit){
													if( i == 0 ){
														myTableHeader += 
																"<th> <img style='width:36px;height:36px;' title='"+ crit.name +"' src='"+ $("#BASE_URL").val() + crit.iconLocation +"' </th>";
														
													}
													
													myTable +=
														"<td><font class='line_table'>"+ crit.overall +"</font></td>";
													
												});
												
												if( i == 0 ){
													myTableHeader +=
																"<th style='text-align:center;'>Média</th>" +
															"</tr>" +
														"</thead>";
												}
							
												myTable += 
												"<td><font class='line_table'>"+ neighborhood.average +"</font></td>" +
											"</tr>";
							
							
						}
						
						count++;
						
						if ($("#TYPE_FILTER").val() == 'index' && count % 4 == 0){
							
							myHtml += "</div>";
							fechou = 1;
							
						}
						
						
					});
					
					
					if ($("#TYPE_FILTER").val() == 'index' && fechou == 0){
						
						myHtml += "</div>";
						fechou = 0;
						
					}
					
					
					myTable += "</table>";
					
				}else{
					myHtml += '<div style="width: 100% !important;" class="jumbotron">Nenhuma avaliação foi encontrada em nossa base de dados.</div>';
					
				}
				
				if( myHtml != "" ){
					$("#best_evaluations").html(myHtml);
					
				} else {
					$("#best_evaluations").html(myTableHeader += myTable);
					
				}
				
				
				$("#loading").fadeOut();
				$("#best_evaluations").fadeIn();
			}
			
		});
		
	}
	
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
					if( i == 0 ){
						$("#select_state").append("<option SELECTED value='"+state.id+"'>"+state.acronym+"</option>");
					} else{
						$("#select_state").append("<option value='"+state.id+"'>"+state.acronym+"</option>");
					}
					
					
				});
				
				
			}
			
			
		});
	
	}
	
	function loadCities(){
		var idState = $("#select_state").val();
		
		$.ajax({
			url: $("#BASE_URL").val() + "dynamic/place/cities/by/" + idState,
			data: JSON.stringify(idState),
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				$.each(result, function(i, city){
					if( i == 0 ){
						$("#select_city").append("<option selected='selected' value='"+city.id+"'>"+city.name+"</option>");
					}else{
						$("#select_city").append("<option value='"+city.id+"'>"+city.name+"</option>");
					}
					
				});
				
				
			}
			
			
		});
		
		enableCities();
		
	}
	
	function loadRegions(){
		var idCity = $("#select_city").val();
		
		$.ajax({
			url: $("#BASE_URL").val() + "dynamic/place/regions/by/" + idCity,
			data: JSON.stringify(idCity),
			type: "GET",
			contentType : 'application/json; charset=utf-8',
			dataType: "json",
			success: function(result){
				$.each(result, function(i, region){
					if( i == 0 ){
						$("#select_region").append("<option selected='selected' value='"+region.id+"'>"+region.name+"</option>");
					}else{
						$("#select_region").append("<option value='"+region.id+"'>"+region.name+"</option>");
					}
					
					
				});
				
				
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
	
	function createSpanHTML( average ){
		mySpanHTML = "";
		
		if( average >= 3.5 ){
			mySpanHTML += "<span class='label label-success' style='  font-size: 12px;'>Média: " + average + "</span>";
		} else if( average < 3.5 && average >= 2.5 ){
			mySpanHTML += "<span class='label label-warning' style='  font-size: 12px;'>Média: " + average + "</span>";
		} else{
			mySpanHTML += "<span class='label label-danger' style='  font-size: 12px;'>Média: " + average + "</span>";
		}
		
		return mySpanHTML;
	}
	
});