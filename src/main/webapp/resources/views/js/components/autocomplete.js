
function configureAutoComplete(baseId,callbackClick,imgColsParam){

	var textCols = 9;
	var imgCols = 3;
	
	if (imgColsParam > 0){
		imgCols = imgColsParam;
		textCols = 12 - imgCols;
	}
	
	
	
	// Ao selecionar o item, qual ação o sistema irá tomar.
	
	$(document).on('click',"div[id^='"+ baseId +"autocomplete_result_item_']",function(){

		
		callbackClick($(this));
	
	});

	
	// Quando retirar o mouse dos resultados, que ação o sistema irá tomar.
	
	$("#"+ baseId +"result_autocomplete").mouseout(function(){
		
		
		
	});


	// Quando o foco sair dos resultados, que ação o sistema irá tomar.
	
	$(document).on('focusout',"#"+ baseId + "input_autocomplete",function(e){
		 var delay = (function() {
			var timer = 0;
			return function(callback, ms) {
				clearTimeout(timer);
				timer = setTimeout(callback, ms);
			};
		 })();
		
		 
		 delay(function(){
			 
			$("#"+ baseId + "result_autocomplete").fadeOut("fast");
			 $("#"+ baseId + "result_autocomplete").html("");
			 
		 }, 300);
		 
	 }); 

 
 $(document).on('keydown',"#"+ baseId + "input_autocomplete",function(e){

	var delay = (function() {
		var timer = 0;
		return function(callback, ms) {
			clearTimeout(timer);
			timer = setTimeout(callback, ms);
		};
	})();
	
	if(e.which == 8){
		$("#"+ baseId + "result_autocomplete").fadeOut();
		$("#"+ baseId + "result_autocomplete").html("");				
	}
	
	if(e.which == 13){
		e.preventDefault();
		
	}

	delay(function(){
		if($("#"+ baseId + "input_autocomplete").val().length > 2){
		
			$.ajax({
				type: "GET",
				contetType: 'application/json; charset=ISO-8859-1',
				dataType: 'json',
				url: $("#BASE_URL").val() + 'autocomplete?name=' + removeAccents($("#"+ baseId + "input_autocomplete").val()),
				data: JSON.stringify(name),
				success : function(result){

					$("#"+ baseId + "result_autocomplete").html("");			
					
					$(result).each(function(i){
														
						var resultAutocomplete = "<div class='row autocompleteResultItem' href=\""+ $("#BASE_URL").val() + result[i].url + "\" id=\""+ baseId +"autocomplete_result_item_" + result[i].id + "\">";
						
						resultAutocomplete += "<div class=\"col-md-"+ imgCols +"\">";
						
						resultAutocomplete += "<img  class=\"img-thumbnail "+ baseId +"autocomplete_img\" style=\"width: 100%; height: 80px;\" src=\""+ $("#BASE_URL").val() + "neighborhood/" + result[i].id +"/main/s\"/>";
						
						resultAutocomplete += "</div>";
						
						resultAutocomplete += "<div class=\"col-md-"+ textCols +" autocompleteNeighborhoodInfo \">";
						
						resultAutocomplete += "<p><b>"+ result[i].neighborhood +"</b><br>";
						
						resultAutocomplete += "<font style=\"font-size: 10px;\"><i>"+ result[i].specification +"</i></font></p>";
						
						resultAutocomplete += "</div></div>";							
					
						$("#"+ baseId + "result_autocomplete").append(resultAutocomplete);
					
					});
					
					if ($("#"+ baseId + "result_autocomplete").html() == ""){
						$("#"+ baseId + "result_autocomplete").append("<br>Ops! N&atilde;o encontramos nenhum resultado.<br><br><span style=\"font-size:11px;\">N&atilde;o encontrou seu bairro? Tente realizar a busca sem utilizar qualquer tipo de acento. Caso n&atilde;o consiga mesmo assim, entre em contato conosco <a style=\"color:blue;\" href=\""+ $("#BASE_URL").val() +"contact\">clicando aqui</a>.</span>");
					}
					
					$("#"+ baseId + "result_autocomplete").fadeIn("fast");
				}
				
				
			});	
		}else{
			$("#"+ baseId + "result_autocomplete").fadeOut("fast");
			
			$("#"+ baseId + "result_autocomplete").html("");
		}
		
	}, 500);
	
});

	
}
