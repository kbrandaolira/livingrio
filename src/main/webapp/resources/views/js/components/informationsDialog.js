
	$(document).ready(function() {
		
		
		$('button[id^="see_more_evaluation"]').click(function(){
			
			$('#modal_moreInformations').modal('show');
			
		});
		
	
		$('#modal_moreInformations').on('show.bs.modal',function(e) {
		
			var id = $(e.relatedTarget).data('book-id');
			
			$.ajax({
				url : $("#BASE_URL").val() + "evaluationById?id=" + id,
				data : JSON.stringify(id),
				type : 'GET',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(result) {
					var mySpan = ""
					
					if ($("#overall_" + id).val() >= 3.5) {
						mySpan = mySpan + "<span class='label label-success'>" + $("#overall_"+id).val() + "</span>"
					} else if ($("#overall_"+ id).val() <= 3.4 && $("#overall_"+ id).val() >= 2.5) {
						mySpan = mySpan + "<span class='label label-warning'>"+ $("#overall_"+id).val() +"</span>"
					} else {
						mySpan = mySpan + "<span class='label label-danger'>"+ $("#overall_"+id).val() +"</span>"
					}

					var myTitle =  "<div class='row'>" +
										"<div class='col-md-9' style='margin-top:5px;'>"+
											"Avaliação de " + $("#person_name_"+id).val() + " sobre " + $("#neighborhood_name").val() +
										"</div>"+
										"<div class='col-md-2' style='margin-top:5px;'>"+
											mySpan+
										"</div>"
									"</div>";
					
					$("#moreInformations_title").html(myTitle);
							
					myHtml = "";

					$.each(result.evaluationCriterionList,function(i,obj) {
						
						myHtml = myHtml
						+ '<div class="row">'
							+ '<div class="col-md-2">'
								+ '<img style="width: 32px; height: 32px;" src="' + obj.iconLocation +'"/>'
							+ '</div>'
							+ '<div class="col-md-3"><b>'
								+ obj.criterionName
							+ '</b></div>'
							+ '<div class="col-md-3"><div class="stars" id="stars_criterion_'+ obj.criterionId +'" data-average="'+ obj.grade +'"></div></div>'
						+ '</div>'
						+ '<div class="row">'
							+ '<div class="col-md-12">'
							+ obj.comment
							+ '</div>'
						+ '</div>';

						if (i + 1 != $("#criterion_size_"+ id).val()) {
							myHtml = myHtml
									+ "<hr>";
						}

					});

					$("#moreInformations_body").html(myHtml);
					
					$('.stars').jRating({
						showRateInfo : false,
						canRateAgain : false,
						length : 5,
						rateMax : 5,
						isDisabled : true,
						decimalLength : 1,
						nbRates : 0,
					
					});
					

					}

			});

		});

});