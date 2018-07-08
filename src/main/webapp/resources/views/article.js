/* Hoje essa JSP é responsável por montar os articles das avaliações de duas maneiras:
	Primeira: Avaliações por Bairro
	Segunda: Avaliações por Pessoa
	
	@params
	#identifier (id of neighborhood or person)
	#type (text neighborhood or person)
	
 */
$(document).ready(function(){

	$("#evaluationsIds").val("");
	
	var identifier = $("#identifier").val();
	var type = $("#type").val();

	happy_filled_imgUrl = $("#BASE_URL").val() + "resources/images/icons/other/happy_filled.png";
	happy_unfilled_imgUrl = $("#BASE_URL").val() + "resources/images/icons/other/happy_unfilled.png";
	
	angry_filled_imgUrl = $("#BASE_URL").val() + "resources/images/icons/other/angry_filled.png";
	angry_unfilled_imgUrl = $("#BASE_URL").val() + "resources/images/icons/other/angry_unfilled.png";
	
	var firstRequest = true;
	
	
	
	 var sIndex = 11, offSet = 10, isPreviousEventComplete = true, isDataAvailable = true;
	  
	    $(window).scroll(function () {
	    	
	    	
	     if ($(document).height() - 50 <= $(window).scrollTop() + $(window).height()) {
	    
	      if (isPreviousEventComplete && isDataAvailable) {
	       
	        isPreviousEventComplete = false;
	        $.blockUI({ message: '<div style=\"margin-top:15px;margin-bottom:15px; !important;\"><span class=\"pageTitleText\">Carregando <span style=\"color:#FFD67D;\">avaliações</span></span><img src="'+ $("#BASE_URL").val() + "resources/images/loading-bar.gif" +'"  style=\"width:61px;\" /></div>' });

	       load();
	       
	       
	     

	      }
	     }
	    });
	    
	    
	//HTML a ser concatenado no pinBoot
	var myHtml = "";
	
	load();
	
	function load(){
		var filter = "ALL";
		var evaluationsIds = $("#evaluationsIds").val();
		
		var urlAjax = $("#BASE_URL").val() + "article/search?identifier=" + identifier + "&type=" + type + "&filter=" + filter + "&evaluationsIds=" + evaluationsIds;
		
		$.ajax({
			url : urlAjax,
			/*data : JSON.stringify([identifier,type,filter,evaluationsIds]),*/
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(result) {
				if( result.length > 0 ){
					$.each(result,function(i,ev) {
						var evIds = $("#evaluationsIds").val();
						evIds += ev.evaluationId + ",";
						$("#evaluationsIds").val(evIds);
						
						myHtml += "<article  class='white-panel'>"+
						
					/*	"<div class='' style='background-color:green'> . </div>" +
						*/
						
						
						"<div class='row'>";
						
						if ( type == "NEIGHBORHOOD"){
						
							myHtml +=		"<div class='col-md-12'>"+
												"<img class='img-rounded' style=\"width: 32px; height: 32px;margin-left:10px;\" src='"+  $("#BASE_URL").val() +  ev.criterionIconPath + "' />"+
												"<input id='criterion_size_"+ ev.evaluationId +"' type='hidden'/>"+
												"<input id='overall_" + ev.evaluationId + "' type='hidden'/>"+
												"<input id='person_name_" + ev.evaluationId + "' type='hidden'/>"+
												"<span style=\"font-size:13px;margin-left:5px;font-weight:bold\">" + ev.criterionName + "</span>" +
											"</div>";
											/*"<div class='col-md-7'>"+
												"<p><a style='font-size: 18px;' href='"+ $("#BASE_URL").val() + ev.criterionIconPath +"'>"+ ev.criterionName + "</a><br>";
												if( ev.creatorAge != null ){
													myHtml += "<font style='font-size: 13px;'>("+ ev.creatorAge +" anos)</font>";
												} 
												
												myHtml +=
												
												"</p>"+
												"<p>"+  createSpanObservationHTML(ev.overall) +"</p>"+
											"</div>";*/
												
												
												
						}else {
							
							
							myHtml +=		"<div class='col-md-5'>"+
												"<img class='img-rounded' src='"+  $("#BASE_URL").val() + "neighborhood/" + ev.neighborhoodId + "/main/s'/>"+
												"<input id='criterion_size_"+ ev.evaluationId +"' type='hidden'/>"+
												"<input id='overall_" + ev.evaluationId + "' type='hidden'/>"+
												"<input id='person_name_" + ev.evaluationId + "' type='hidden'/>"+
											"</div>"+
											"<div class='col-md-7'>"+
												"<p><a style='font-size: 18px;' href='"+ $("#BASE_URL").val() + ev.neighborhoodUrl +"'>"+ ev.neighborhoodName + "</a><br>"+
												"<font style='font-size: 13px;'>"+ ev.neighborhoodSpecification + "</font></p>"+
												"<p>"+  createSpanObservationHTML(ev.overall) +"<p>"+
											"</div>";
							
						}
												
												
												
												
							myHtml +=	"</div><hr>"+  createCommentHTML(ev.resumeComment) +
										/*	"<div class='row'>"+
												"<div class='col-md-4'><b>MÉDIA</b></div>"+
												"<div class='col-md-5'>"+
													"<div class='stars' id='stars_criterion_0' data-average='"+ev.overall+"'></div>"+
												"</div>"+
												"<div class='col-md-1'>(" + ev.overall + ")</div>"+
											"</div>"+*/
											/*"<div class='row'>"+
												createGradesHTML(ev.evaluationCriterionList) +
											"</div>"+*/
											
											"<hr>"+
											"<div class='row' style='margin-top:-20px;margin-left:2px'>"+
											"<div class='col-md-2'>"+
												
												"<img class='img-rounded' style=\"width: 32px; height: 32px;\" src='"+  $("#BASE_URL").val() + "profile/" + ev.creatorId + "/photo/s" + "' />" +
												"</div>"+
												"<div class='col-md-4'>"+
													"<span style='font-size:10px;margin-left:-25px;margin-top:-2px !important;display:block;'>Enviado por </span>" +
													"<span style='font-size:10px;margin-left:-25px;margin-top:2px !important;display:block;'>" + ev.creatorName + "</span>" +
													/*"<span style='font-size:10px;margin-left:-25px;margin-top:2px !important;display:block;'>Em: " + ev.evaluationDate + "</span>" +*/
												"</div>"+
												
												"<div class='col-md-7' style=\"margin-left:-23px;margin-top:3px;\">" +
												createLikedHTML(ev.liked, ev.likesSize, ev.dislikesSize, ev.evaluationId) +
												
												" &nbsp; " +
												"<img id='post_messages_" + ev.evaluationId +"' data-toggle='modal' data-book-id='"+ ev.evaluationId + "' data-target='#modal_questions' class=\"chatIcon\" style=\"width:20px;height:20px;\" src=\"https://cdn1.iconfinder.com/data/icons/anchor/128/chat.png\" /> " + " <font id='likesSize_'>" + 0 + "</font> &nbsp; " +
											"</div>" +
												

													/*" <button style='padding:10px!important;font-size:14px;' data-toggle='modal' data-book-id='"+ ev.evaluationId + "' data-target='#modal_moreInformations' class='btn btn-default small btn-article'>"+
													" <i class=\"glyphicon glyphicon-zoom-in\"></i> Veja mais</button>"+
													
													" <button style='padding:10px!important;font-size:14px;' data-toggle='modal' data-book-id='"+ ev.evaluationId + "' data-target='#modal_questions' class='btn btn-default small btn-article'>"+
													" <i class=\"glyphicon glyphicon-question-sign\"></i> Perguntas</button>"+*/
													
											"</div>" +
											
										
												
												"</div>"+
										"</article>"
									;
							
							
							
									
					});
	
					
				}else if(result.length == 0 && firstRequest == true){
		
					if( type == 'NEIGHBORHOOD' ){
						var evaluate_neighborhood_url = $("#BASE_URL").val() + "neighborhood/" + $("#identifier").val() + "/evaluate";
						$("#messages").html('<div style="width: 100% !important;font-size:14px;" class="msg msg-info">Nenhuma avaliação foi enviada para o bairro.<br><br> <a href="'+ evaluate_neighborhood_url +'">Clique aqui</a> e seja o primeiro.</div>');
					} else if( type == 'PERSON' ){
						$("#messages").html('<div style="width: 100% !important;font-size:14px;" class="msg msg-info">Nenhuma avaliação foi enviada por esse usuário.<br></div>');
					}
					
					$("#messages").fadeIn();
					
				}else{
		                isDataAvailable = false;
					
				}
				
				//Adiciona os Articles ou a DIV informando que não há avaliações
				$("#pinBoot").html(myHtml);

				$("#loading").fadeOut();
				   $.unblockUI();
				
				$('.stars').jRating({
					showRateInfo : false,
					canRateAgain : false,
					length : 5,
					rateMax : 5,
					isDisabled : true,
					decimalLength : 1,
					nbRates : 0,
				
				});
				
				$("img[id^=angry_]").click(function(){

					var id = $(this).attr("id");
					id = id.substring(6);
					var liked = false;
					
					auxUrl_angry = $("#angry_" + id).attr("src");
					auxUrl_happy = $("#happy_" + id).attr("src");
					
					var change_flag = auxUrl_happy == happy_filled_imgUrl;
					
					if( $("#isAuthenticated").val() == 'true' ){
						if( auxUrl_angry != angry_filled_imgUrl ){
							didYouLike(id, liked, change_flag);
						}
						
					} else {
						window.location.href = $("#BASE_URL").val() + "notLogged/liked/"+id+"/"+liked;
						
					}
					
				});
				
				$("img[id^=happy_]").click(function(){
					
					var id = $(this).attr("id");
					id = id.substring(6);
					var liked = true;
					
					auxUrl_happy = $("#happy_" + id).attr("src");
					auxUrl_angry = $("#angry_" + id).attr("src");
					
					var change_flag = auxUrl_angry == angry_filled_imgUrl;
					
					//Evitando requisição pra ação já efetuada
					if( $("#isAuthenticated").val() == 'true' ){
						if( auxUrl_happy != happy_filled_imgUrl ){
							didYouLike(id, liked, change_flag);
						}
						
					} else {
						window.location.href = $("#BASE_URL").val() + "notLogged/liked/"+id+"/"+liked;
						
					}
					
					 
					
					
				});
				
				function didYouLike(evaluationId, liked, change_flag){
					$.ajax({
						url : $("#BASE_URL").val() + "liked?evaluationId="+evaluationId+"&liked="+liked,
						data : JSON.stringify([evaluationId,liked]),
						type : 'GET',
						contentType : 'application/json; charset=utf-8',
						dataType : 'text',
						success : function(result) {
							if( result == "ok" ){
								$("#happy_" + evaluationId).attr("src", happy_unfilled_imgUrl);
								$("#angry_" + evaluationId).attr("src", angry_unfilled_imgUrl);
								
								if( liked ){
									$("#happy_" + evaluationId).attr("src", happy_filled_imgUrl);
									$("#angry_" + evaluationId).attr("src", angry_unfilled_imgUrl);

									var strLikesSize = $("#likesSize_" + evaluationId).html();
									var intLikesSize = parseInt(strLikesSize) + 1;

									$("#likesSize_" + evaluationId).html(intLikesSize);
									
									if( change_flag ){
										var strDislikesSize = $("#dislikesSize_" + evaluationId).html();
										var intDislikesSize = parseInt(strDislikesSize) - 1;

										$("#dislikesSize_" + evaluationId).html(intDislikesSize);
										
									}
										
									
								} else {
									$("#happy_" + evaluationId).attr("src", happy_unfilled_imgUrl);
									$("#angry_" + evaluationId).attr("src", angry_filled_imgUrl);

									var strDislikesSize = $("#dislikesSize_" + evaluationId).html();
									var intDislikesSize = parseInt(strDislikesSize) + 1;
									
									$("#dislikesSize_" + evaluationId).html(intDislikesSize);

									if( change_flag ){
										var strLikesSize = $("#likesSize_" + evaluationId).html();
										var intLikesSize = parseInt(strLikesSize) - 1;

										$("#likesSize_" + evaluationId).html(intLikesSize);
										
									} 
																		
								}
								
							} else
								alert("Houve um erro mas já estamos trabalhando para consertá-lo :)");
								
							}
							
					});
					
				}
					
				
				if(firstRequest == false){
					
					  isPreviousEventComplete = true;
					
				}else{
					firstRequest = false;
				}
				
				
				
				
				
			}
			
		});
		
		
		
		
	}
	
	function createSpanObservationHTML(overall){
		if(overall >= 3.5){
			return "<span class='label label-success'>Avaliação Positiva</span>";
		} else if(overall < 3.5 && overall >= 2.5){
			return "<span class='label label-warning'>Avaliação Razoável</span>";
		} else{
			return "<span class='label label-danger'>Avaliação Negativa</span>";
		}
	}
	
	function createCommentHTML(comment){
		if(comment != null){
			return "<div class='row' style='color: gray;'>"+
						"<div class='col-md-12'><i>"+
							comment +
						"</div></i>"+
					"</div>"; 
		}
		
		return "";
	}
	
	function createLikedHTML(liked, likesSize, dislikesSize, evaluationId){
		var likedHTML = "";
		
		
		if( liked == null ){
			likedHTML += 
				"<img id='happy_"+ evaluationId  +"' class='happy' style='width:20px;height:20px;' src='"+ happy_unfilled_imgUrl + "'/> " + "<font id='likesSize_" + evaluationId + "'>" + likesSize + "</font> &nbsp; " +
				"<img id='angry_"+ evaluationId  +"' class='angry' style='width:20px;height:20px;' src='"+ angry_unfilled_imgUrl + "'/> " + "<font id='dislikesSize_" + evaluationId + "'>" + dislikesSize + "</font>" ;
			
		} else if( liked ){
			likedHTML += 
				likedHTML += 
					"<img id='happy_"+ evaluationId  +"' class='happy' style='width:20px;height:20px;' src='"+ happy_filled_imgUrl + "'/> " + "<font id='likesSize_" + evaluationId + "'>" + likesSize + "</font> &nbsp; " +
					"<img id='angry_"+ evaluationId  +"' class='angry' style='width:20px;height:20px;' src='"+ angry_unfilled_imgUrl + "'/> " + "<font id='dislikesSize_" + evaluationId + "'>" + dislikesSize + "</font>" ;
			
		} else if( !liked ){
			likedHTML += 
				"<img id='happy_"+ evaluationId  +"' class='happy' style='width:20px;height:20px;' src='"+ happy_unfilled_imgUrl + "'/> " + "<font id='likesSize_" + evaluationId + "'>" + likesSize + "</font> &nbsp; " +
				"<img id='angry_"+ evaluationId  +"' class='angry' style='width:20px;height:20px;' src='"+ angry_filled_imgUrl + "'/> " + "<font id='dislikesSize_" + evaluationId + "'>" + dislikesSize + "</font>" ;
			
		}
		
		return likedHTML;
	}
	
	function createGradesHTML(criterionList){
		var gradeHtml = ""
		
		$.each(criterionList,function(i,crit) {
			gradeHtml += "<div class='col-md-5' style=\"font-size:13px\">"+ crit.criterionName +"</div>"+
							"<div class='col-md-5'>"+
								"<div class='stars' id='stars_criterion_"+ crit.criterionId +"' data-average='"+ crit.grade +"'>" +
							"</div>" +
						"</div>";
		
		});
		
		return gradeHtml;
		
	}
	
	// Questions Modal on show event
	
	$('#modal_questions').on('show.bs.modal',function(e) {

/*		$("#submitQuestionForm_btn").attr("disabled","disabled");
		
		$("#questionOptionsMessages").fadeOut();
		
		$("#submitQuestionForm").fadeOut();
		
		$("#questions_body").html("Carregando...");
		
		*/
		
		var id = $(e.relatedTarget).data('book-id');
		
		alert(id);
		
	/*	var urlAjax = $("#BASE_URL").val() + "evaluation/questions?id=" + id;

		$.ajax({
			url : urlAjax,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(result) {
				renderQuestions(result);
			}
			
		});
		
		// Verifica se pode enviar pergunta
		
		urlAjax = $("#BASE_URL").val() + "evaluation/questions/verifyPostQuestionPermissions?id=" + id;
		
		$.ajax({
			url : urlAjax,
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(result) {
				
				$("#submitQuestionForm").hide();
				$("#questionOptionsMessages").hide();
				
				
				if(result.message == 'NEEDLOGIN'){
					
					$("#questionOptionsMessages").html("É necessário entrar no sistema para enviar uma mensagem.");
					$("#questionOptionsMessages").fadeIn();
					
				}else if (result.message == 'ANSWERQUESTIONS'){
										
					
					if(result.object.questionsWithoutAnswers > 1){
						$("#questionOptionsMessages").html(result.object.name + ", existem " + result.object.questionsWithoutAnswers + " perguntas para serem respondidas. Sua resposta será muito importante para ajudar as pessoas a decidirem sobre onde irão morar. Para respondê-las <a href=\"" + result.object.answerQuestionsURL + "\">clique aqui</a>.");
						
					}else{
						$("#questionOptionsMessages").html(result.object.name + ", existe " + result.object.questionsWithoutAnswers + " pergunta para ser respondida. Sua resposta será muito importante para ajudar as pessoas a decidirem sobre onde irão morar. Para respondê-la <a href=\"" + result.object.answerQuestionsURL +"\">clique aqui</a>.");
						
					}
					
					$("#questionOptionsMessages").fadeIn();
					
				}else if (result.message == 'FORMDISABLE'){
					
					$("#questionOptionsMessages").html("Aguardando a resposta...");
					$("#questionOptionsMessages").fadeIn();
					
				}else if (result.message == 'FORM'){
					
					$("#submitQuestionForm").fadeIn();
					
				}else if (result.message == 'THANKYOU'){
					
					$("#questionOptionsMessages").html("Todas as perguntas foram respondidas. Obrigado :)");
					$("#questionOptionsMessages").fadeIn();
					
				}
						
			}*/
			
	/*	});
		
				
		$("#submitQuestionForm").attr("action", $("#BASE_URL").val() + "evaluations/questions/new"); //this fails silently
		
		$("#modalQuestions_evaluationId").val(id);*/
		
	});
	
	$("#submitQuestionForm_question").keydown(function(){
		

		
		var length = $(this).val().length;
		

		if(length > 10){
			$("#submitQuestionForm_btn").removeAttr("disabled");
		}else{
			$("#submitQuestionForm_btn").attr("disabled","disabled");
		}
		
		
		
	});
	
	
	$("#submitQuestionForm").submit(function(e){
				
		$("#submitQuestionForm").fadeOut();
		
		$("#questionOptionsMessages").html("<span style=\"display:inline;\">Sua pergunta está sendo enviada...</span> <img width=\"32px\" style=\"width:32px\" src="+ $("#BASE_URL").val() + "resources/images/loading-bar.gif" +" \>");
		$("#questionOptionsMessages").fadeIn();
		
		var frm = $('#submitQuestionForm');
		
		e.preventDefault();

	    var data = {}
	    
	    var Form = this;

	    //Gather Data also remove undefined keys(buttons)
	    $.each(this, function(i, v){
	            var input = $(v);
	        data[input.attr("name")] = input.val();
	        delete data["undefined"];
	    });
	    
	    $.ajax({
	        contentType : 'application/json; charset=utf-8',
	        type: frm.attr('method'),
	        url: frm.attr('action'),
	        dataType : 'json',
	        data : JSON.stringify(data),
	        success : function(result) {
				renderQuestions(result);
				
				$("#submitQuestionForm").hide();
				$("#submitQuestionForm_question").val("");
				$("#questionOptionsMessages").html("Sua pergunta foi enviada com sucesso, quando o avaliador responder você será notificado.");
				$("#questionOptionsMessages").fadeIn();
				

			},
	        error : function(){
	        	alert("Houve um erro mas já estamos trabalhando para consertá-lo :)");
	        }
	    });
		
	});
	
	
/*	
<div class="row msg_container base_sent">
    <div class="col-xs-10 col-md-10">
        <div class="messages msg_sent">
            <p>that mongodb thing looks good, huh?
            tiny master db, and huge document store</p>
            <time datetime="2009-11-13T20:00">Timothy â€¢ 51 min</time>
        </div>
    </div>
    <div class="col-md-2 col-xs-2 avatar">
        <img src="http://www.bitrebels.com/wp-content/uploads/2011/02/Original-Facebook-Geek-Profile-Avatar-1.jpg" class=" img-responsive ">
    </div>
</div>*/
	
	
	//MODAL More Informations
	$('#modal_moreInformations').on('show.bs.modal',function(e) {

		var id = $(e.relatedTarget).data('book-id');
		
		$.ajax({
			url : $("#BASE_URL").val() + "article/evaluationById?id=" + id,
			data : JSON.stringify(id),
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			success : function(result) {
				var myOverall = ""
				
				if (result.overall >= 3.5) {
					myOverall = "<span class='label label-success'>" + result.overall + "</span>"
				} else if (result.overall <= 3.4 && result.overall >= 2.5) {
					myOverall = "<span class='label label-warning'>"+ result.overall +"</span>"
				} else {
					myOverall = "<span class='label label-danger'>"+ result.overall +"</span>"
				}

				var myTitle =  "<div class='row'>" +
									"<div class='col-md-9' style='margin-top:5px;'>"+
										"Avaliação de " + result.creatorName + " sobre " + result.neighborhoodName + " &nbsp; " + myOverall +
									"</div>"+
								"</div>";
				
				$("#moreInformations_title").html(myTitle);
						
				modalContent = "";

				if( result.comment != null && result.comment != '' ){
					modalContent = modalContent + 
										"<div class='row'>" +
											'<div class="col-md-12" style="color:gray;"><i>"' + result.comment + '"</i></div>' +
										"</div> <hr>"
				}
				
				$.each(result.evaluationCriterionList,function(i,ev_crit) {
					
					modalContent = modalContent
					+ '<div class="row">' 
						+ '<div class="col-md-1"></div>'
						+ '<div class="col-md-2">'
							+ '<img style="width: 32px; height: 32px; margin-left:20px" src="' + $("#BASE_URL").val() + ev_crit.iconLocation +'"/>'
						+ '</div>'
						+ '<div class="col-md-3"><b>'
							+ ev_crit.criterionName
						+ '</b></div>'
						+ '<div class="col-md-3"><div class="stars" id="stars_criterion_'+ ev_crit.criterionId +'" data-average="'+ ev_crit.grade +'"></div></div>'
					+ '</div>'
					+ '<div class="row">'
						+ '<div class="col-md-12">'
						+ ev_crit.comment
						+ '</div>'
					+ '</div>';

					
					if( i + 1 != result.criterionSize){
						modalContent = modalContent + "<hr>";
					}
					
				});

				$("#moreInformations_body").html(modalContent);
				
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

	
	$("#seeMoreEvaluations").click(function(){
		load();
	});
	
			
	function renderQuestions(callback){
		
		var html = "";
    	
		var hasResult = "0";
		
    	$.each(callback,function(i,ev) {
    		
    		hasResult = "1";
    		
    		html += "<div class=\"row msg_container base_sent\">";
    		html += 	"<div class=\"col-md-2 col-xs-2 avatar\">";
    		html += 		"<img style=\"width:120px;height:90px;\" src=\""+ $("#BASE_URL").val() + "profile/" + ev.questionResponsibleId + "/photo/s\" class=\" img-thumbnail img \">";
    		html += 	"</div>";	
    		html += 	"<div class=\"col-xs-10 col-md-10\">";	
    		html += 		"<div class=\"messages msg_sent\">";	
    		html += 			"<p>";
    		html +=					ev.question;
    		html += 			"</p>";	
    		html += 			"<time datetime=\"2009-11-13T20:00\">Enviado por " + ev.questionResponsibleName + " em " + ev.questionCreationDate + "</time>";	
    		html += 		"</div>";	
    		html += 	"</div>";	
    		html += "</div>";	
    		
    		
    		if(ev.answer != undefined){
    			
    			html += "<div class=\"row msg_container base_receive\">";
        	
        		html += 	"<div class=\"col-xs-10 col-md-10\">";	
        		html += 		"<div class=\"messages msg_receive\">";	
        		html += 			"<p>";
        		html +=					ev.answer;
        		html += 			"</p>";	
        		html += 			"<time datetime=\"2009-11-13T20:00\">Enviado por " + ev.answerResponsibleName + " em " + ev.answerCreationDate +"</time>";	
        		html += 		"</div>";	
        		html += 	"</div>";	
        		html += 	"<div class=\"col-md-2 col-xs-2 avatar\">";
        		html += 		"<img style=\"width:120px;height:90px;\" src=\""+ $("#BASE_URL").val() + "profile/" + ev.answerResponsibleId + "/photo/s\" class=\"img-thumbnail img \">";
        		html += 	"</div>";	
        		html += "</div>";	
    			
    		}
    		
    		html += "</hr>";	
    			   	       
    		
    	});
    	
    	
    	
    	if (hasResult == "0"){
    		
    		html = "Não existem perguntas cadastradas nesta avaliação."
    		
    	}
    	
    	$("#questions_body").html(html);
		
	}
	
});