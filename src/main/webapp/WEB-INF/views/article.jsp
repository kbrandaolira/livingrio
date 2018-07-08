<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="<c:url value='/resources/js/pinterest.js'/>" /></script>
<script src="<c:url value='/resources/views/article.js'/>" charset="UTF-8"></script>


<script type="text/javascript" src="http://malsup.github.io/jquery.blockUI.js" /></script>

<link href="<c:url value='/resources/views/article.css'/>" rel="stylesheet">

<!-- IDs das Pessoas na Tela -->
<input id="evaluationsIds" type="hidden" value=""/>

<div id="messages" style="width: 100% !important;display:none;background-color:#FFF;" class="jumbotron"></div>

<div id="loading" style="margin-top:20px;font-size:16px;">

<div style="text-align:center;color:#2D7BCF;">

	<br><br>
	<img style="width:61px;" src="<c:url value='/resources/images/loading-bar.gif'/>" /><br><br>
	
	</div>
	<div style="color:#999;text-align:right;"><i></i></div>
	

</div>

<section id="pinBoot" style="margin-top: 0px;">

</section>

	


<style>

#seeMoreEvaluations{

	border-radius:0px;
	font-size:16px;
	padding: 10px;
	width: 120px;
	margin-bottom: 40px;

}

	.modal-body {
	  position: relative;
	  padding: 15px;
	  overflow: scroll;
	  height: 600px;
	}
	
.col-md-2, .col-md-10{
    padding:0;
}
.panel{
    margin-bottom: 0px;
}
.chat-window{
    bottom:0;
    position:fixed;
    float:right;
    margin-left:10px;
}
.chat-window > div > .panel{
    border-radius: 5px 5px 0 0;
}
.icon_minim{
    padding:2px 10px;
}
.msg_container_base{
  background: #e5e5e5;
  margin: 0;
  padding: 0 10px 10px;
  max-height:300px;
  overflow-x:hidden;
}
.top-bar {
  background: #666;
  color: white;
  padding: 10px;
  position: relative;
  overflow: hidden;
}
.msg_receive{
    padding-left:0;
    margin-left:0;
}
.msg_sent{
    padding-bottom:20px !important;
    margin-right:0;
}
.messages {
  background: white;
  padding: 10px;
  border-radius: 2px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  max-width:100%;
}
.messages > p {
    font-size: 13px;
    margin: 0 0 0.2rem 0;
  }
.messages > time {
    font-size: 11px;
    color: #ccc;
}
.msg_container {
    padding: 10px;
    overflow: hidden;
    display: flex;
}
.img {
    display: block;
    width: 100%;
}
.avatar {
    position: relative;
}
.base_receive > .avatar:after {
    content: "";
    position: absolute;
    top: 0;
    right: 0;
    width: 0;
    height: 0;
    border: 5px solid #FFF;
    border-left-color: rgba(0, 0, 0, 0);
    border-bottom-color: rgba(0, 0, 0, 0);
}

.base_sent {
  justify-content: flex-end;
  align-items: flex-end;
  
}
.base_sent > .avatar:after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 0;
    border: 5px solid white;
    border-right-color: transparent;
    border-top-color: transparent;
    box-shadow: 1px 1px 2px rgba(black, 0.2); // not quite perfect but close
}

.msg_sent > time{
    float: right;
}



.msg_container_base::-webkit-scrollbar-track
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
    background-color: #F5F5F5;
}

.msg_container_base::-webkit-scrollbar
{
    width: 12px;
    background-color: #F5F5F5;
}

.msg_container_base::-webkit-scrollbar-thumb
{
    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
    background-color: #555;
}

.btn-group.dropup{
    position:fixed;
    left:0px;
    bottom:0;
}

.angry{
	background-image: url("<c:url value='/resources/images/icons/other/angry_unfilled.png'/>");
}

.angry:HOVER {
	cursor: pointer !important;
	background-image: url("<c:url value='/resources/images/icons/other/angry_filled.png'/>");
}

.happy{
	background-image: url("<c:url value='/resources/images/icons/other/happy_unfilled.png'/>");
}

.happy:HOVER {
	cursor: pointer !important;
	background-image: url("<c:url value='/resources/images/icons/other/happy_filled.png'/>");
}

</style>

<!-- MODAL para mostrar mais informa��es sobre a Avaliação -->
<div class="modal fade" id="modal_moreInformations" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="moreInformations_title" class="modal-title">
					<!-- Est� sendo escrito por jQuery -->
				</h4>
			</div>
			<div id="moreInformations_body" class="modal-body">
				<!-- Est� sendo escrito por jQuery -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>

<!-- MODAL para mostrar mais informações sobre a Avaliação -->
<div class="modal fade" id="modal_questions" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="questions_title" class="modal-title">
					Perguntas / Respostas
				</h4>
			</div>
			<div id="questions_body" class="modal-body" style="width:100%;">

                    
                    
			</div>
			<div class="modal-footer">
				<form:form commandName="model"  action="<c:url value='/evaluations/questions/new' />" method="post" id="submitQuestionForm" cssStyle="display:none;"> 
										
					<input type="hidden" id="modalQuestions_evaluationId" name="evaluationId" value="" />
					
					<p><textarea id="submitQuestionForm_question" style="width:100%;height:120px;" name="question" placeholder="Quer saber mais? Fa&ccedil;a uma pergunta ao avaliador."></textarea></p>
					
					<p><button id="submitQuestionForm_btn" type="SUBMIT" class="btn btn-primary">Enviar</button></p>
					
				</form:form>
				
				<div id="questionOptionsMessages" style="display:none;"></div>
				
			</div>
		</div>
	</div>
</div>