<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ranking por Bairros</title>
</head>

<input id="TYPE_FILTER" type="hidden" value="ranking"/>

<%@ include file="/WEB-INF/views/dynamicPlace.jsp" %>

<div class="row" id="best_evaluations">
	<!-- Will be created in dynamicPlace.js -->
</div>

<style>

.line_table{
	margin-left:15px;
	margin-top:9px;
	font-size:17px;
	position:absolute;
}

</style>