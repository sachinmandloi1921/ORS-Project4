<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
		<%@include file="Header.jsp" %>
		
		<h1 align="center" style="margin-top: 100px"><font color="red">Welcome To ORS</font></h1>
		<h1><%=ServletUtility.getSuccessMessage(request) %></h1>
		
		<%
			UserBean beanUserBean = (UserBean) session.getAttribute("user");
			if (beanUserBean != null) {
				if (beanUserBean.getRoleId() == 2) {
		%>

		<h2 align="Center">
			<a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your Marksheet </a>
		</h2>

		<%
			}
			}
		%>
		
		
	</form>
	<%@include file="Footer.jsp" %>
</body>
</html>