<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.Bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.SubjectCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Subject Registration</title>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.SubjectBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

<%@include file ="Header.jsp"%>
	

	<%
		List<CourseBean> courseId =(List<CourseBean>) request.getAttribute("CourseList");
	%>
<center>
	<h1>
		<% if(bean != null && bean.getId() > 0 ) {%>
			<tr><th>Update Subject</th></tr>
		<%}else{ %>
			<tr><th>Add Subject</th></tr>
		<% }%>
	</h1>
<div>	
	<h1>
	<font style="color: green"><%=ServletUtility.getSuccessMessage(request) %>
	</font>
	</h1>
	
	<h1>
	<font style="color: red"><%=ServletUtility.getErrorMessage(request)%>
	</font>
	</h1>
</div>
	
	<input type="hidden" name="id" value="<%=bean.getId()%>">
 	<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
 	<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
 	<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
 	<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">
 	
	<table>
	
	<tr>
	<th align="center">CourseName  <span style="color: red">*</span> 
	<td><%=HTMLUtility.getList("coursename", String.valueOf(bean.getCourse_Id()), courseId) %>
	</td><td style="position: fixed"> <font  color="red"><%=ServletUtility.getErrorMessage("coursename",request) %>
	</font></td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	
	<tr><th align="left">Subject Name <span style="color: red">*</span>
	<td><input type="text" name="name" placeholder="Enter Subject Name" size="20" value="<%=DataUtility.getStringData(bean.getSubject_Name())%>">
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("name",request) %>
	</font>	</td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	<tr><th align="right">Description <span style="color: red">*</span>
	<td><input type="text" name="description" placeholder="Enter Description" size="20" value="<%=DataUtility.getStringData(bean.getDiscription())%>">
	</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %>
	</font></td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	 
	<tr><th></th>
	<%
		if(bean.getId() > 0){	%>
	<td>
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_UPDATE %>">&nbsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL %>">	
	</td>
	<%}else{ %>
	
	<td>
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE %>">
	&nbsp;&nbsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_RESET %>">	
	</td>
	<%} %>
	
	</tr>
	</table>
</center>

</form>
<%@include file ="Footer.jsp"%>
</body>
</html>