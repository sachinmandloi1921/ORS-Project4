<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.CourseCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Course Registrtion View</title>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.CourseBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.COURSE_CTL %>" method="post">	
		<%@include file ="Header.jsp"%>
	
	<center>	
			<h1> 
			<%
				if(bean != null && bean.getId() > 0 )
				{
			%>
				<tr><th>Update Course</th></tr>
			<%}else{ %>
				<tr><th>Add Course</th></tr>
			<% } %>
			</h1>
	<div align="center">	
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>	
		</h2>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>	
		</h2>
	</div>
	
	<input type ="hidden" name="id" value="<%=bean.getId() %>">
	<input type ="hidden" name="createdby" value="<%=bean.getCreatedBy() %>">
	<input type ="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
	<input type ="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime()) %>">
	<input type ="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime()) %>">

<table>
	<tr>
	<th align="left">Course Name <span style="color: red">*</span></th>
	<td><input type="text" name ="name" placeholder="Enter Course Name" size="23" value="<%=DataUtility.getStringData(bean.getCourse_Name()) %>">
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("name", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>	
	
	<tr>
	<th align="left">Duration <span style="color:red">*</span></th>
	<td>
	<%
	LinkedHashMap map = new LinkedHashMap();
	//HashMap map = new HashMap();
		map.put("", "---------------Select----------------");
		map.put("1 Year", "1 Year");
    	map.put("2 Year", "2 Year");
    	map.put("3 Year", "3 Year");
    	map.put("4 Year", "4 Year");
    	map.put("5 Year", "5 Year");
    	
  	  String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
	%> 
	<%=htmlList%>
	
	</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("duration", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>	
	<tr>
	<th align="left">Description <span style="color: red" >*</span></th>
	<td><input type="text" name ="description" placeholder="Enter Description" size="23" value="<%=DataUtility.getStringData(bean.getDiscription())%>">
	</td><td style="position: fixed"  ><font color="red"><%=ServletUtility.getErrorMessage("description", request) %></font>
	</td>
	</tr>
<tr><th style="padding: 3px"></th></tr>
	<tr><th></th>
	<%
	if(bean.getId() > 0){
	%>
	<td>
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_UPDATE %>">&nbsp;
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_CANCEL %>">
	</td>
	<%}else{ %>
	<td>
	<input type="submit" name ="operation" value="<%=CourseCtl.OP_SAVE %>"> &nbsp;&nbsp;
		<input type="submit" name ="operation" value="<%=CourseCtl.OP_RESET %>">
	</td>
	<%} %>
	</tr>
</table>
</form>	
	</center>

	<%@include file ="Footer.jsp"%>
</body>
</html>