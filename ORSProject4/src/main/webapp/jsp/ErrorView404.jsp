<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error View 404</title>
</head>
<body>
<form action="<%=ORSView.ERROR_CTL%>" method="get"> 
 	
 	<div align="center">
 		<img src="<%=ORSView.APP_CONTEXT%>/img/404error.jpeg" width="550" height="250">
 		<h1 align="center" style="color: red"> Ooops! Something went wrong..</h1>
 		<font style="color: black ; font-size: 25px ">
 		<b>404</b> : Requested Page not available
 		</font>
 	<div style="width: 25% ; text-align: justify;">
 		<h3 >Try :</h3>
 		<ul>
 			<li> Server under Maintain please try after Some Time </li>
 			<li> Check the network cables , modem and router</li>
 			<li> Reconnect to network or wi-fi</li>
 		</ul>
 	</div>
 	</div>
	
		<h4 align="center">
			<font size="5px" color="black">
			<a href="<%=ORSView.WELCOME_CTL %>" style="color: silver">*Click here to Go Back*</a>			
			</font>
		</h4>
	</form>	
</body>
</html>