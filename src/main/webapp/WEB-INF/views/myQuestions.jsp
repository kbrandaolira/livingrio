<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Avaliar ${neighborhood.name}</title>
</head>


<link href="<c:url value='/resources/css/jRating.jquery.css'/>"
	rel="stylesheet">

<!-- for Boostrap -->
<script src="<c:url value="/resources/js/jquery.js" />"></script>


<!-- for jQuery UI -->
<script
	src="<c:url value='/resources/jquery-ui-1.11.3.custom/jquery-ui.js'/>"></script>




<body class="container">


<style>
.well {

	border-left:none;
	border-right:none;
}
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
  background: white !important;
  padding: 10px;
  border-radius: 2px;
  /* box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2) !important; */
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
	background-image: url("<c:url value='/resources/images/icons/angry_unfilled.png'/>");
}

.angry:HOVER {
	cursor: pointer !important;
	background-image: url("<c:url value='/resources/images/icons/angry_filled.png'/>");
}

.happy{
	background-image: url("<c:url value='/resources/images/icons/happy_unfilled.png'/>");
}

.happy:HOVER {
	cursor: pointer !important;
	background-image: url("<c:url value='/resources/images/icons/other/happy_filled.png'/>");
}
</style>


	<div style="margin-top: 10px;" class="container">

		<div class="row">

			<%@include file="profileSideBar.jsp"%>

			<div class="col-md-10">
			
			<div class="row " style="margin-top:-2px;margin-bottom:4px;">
			<div class="col-md-12">
			
				<div class="pageTitleBox">
					<span class="pageTitleText">Minhas <span style="color:#FFD67D;">Perguntas</span></span>
					</div>
				
				</div>
			</div>

			<c:forEach varStatus="status" var="question" items="${questions}">
			
				<div class="well">
				
					<div class="row msg_container base_sent">
						<div class="col-md-2 col-xs-2 avatar">
							<img style="width:120px;height:90px;margin-left:50px;margin-top:3px;;" src="<c:url value="/profile/${question.questionResponsibleId}/photo/s" />" class=" img-thumbnail img ">
						</div>
							
						<div class="col-xs-10 col-md-10" style="margin-right:50px;margin-top:-3px;margin-left:25px;">	
							<div class="messages msg_sent">
								<p>
									${question.question }
								</p>
								<time>Enviado por ${question.questionResponsibleName } em  ${question.questionCreationDate} </time>
							</div>	
						</div>	
					</div>
				
				
					<c:if test="${question.answerResponsibleId != null}">
						<div class="row msg_container base_receive">
					       	
				      		<div class="col-xs-10 col-md-10">
				      			<div class="messages msg_receive" style="margin-left:50px;">
				      				<p> ${question.answer} </p>
				      				<time>Enviado por ${question.answerResponsibleName} em ${question.answerCreationDate}</time>
				      			</div>
				      		</div>	
				      		<div class="col-md-2 col-xs-2 avatar">
				      			<img style="width:120px;height:90px;" src="<c:url value="/profile/${question.answerResponsibleId}/photo/s" />" class="img-thumbnail img ">
				      		</div>	
				      		
				      	</div>
					      		
		   			</c:if>	
		   			
				</div>
					
				<br>
			
			</c:forEach>


			</div>
		</div>
	</div>
</body>
</html>