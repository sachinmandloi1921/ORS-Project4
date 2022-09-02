<%@page import="in.co.rays.proj4.controller.StudentCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.Bean.CollegeBean"%>
<%@page import="in.co.rays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj4.Bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Registration</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.StudentBean" scope="request"></jsp:useBean>
    	
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">
    <%@include file="Header.jsp"%>
    <% 
    	List <CollegeBean> clist = (List <CollegeBean>)request.getAttribute("collegeList");
    
    %>
    
    <center>
        <h1>
        	<%
        		if( bean != null && bean.getId()>0){
        	%> 
        	<tr><th><font>Update Student</font></th></tr>
        	<% }else{%>
        	<tr><th><font>Add Student</font></th></tr>
        	<% }%>
        </h1>
        
		<div>
		<h1><font style="color: green"><%=ServletUtility.getSuccessMessage(request) %></font></h1>
		<h1><font style="color: red"><%=ServletUtility.getErrorMessage(request) %></font>
		</h1>
		</div>
		
		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
		<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">

	<table>
	
		<tr>
		<th align="left">CollegeName <span style="color: red">*</span></th>
		<td><%=HTMLUtility.getList("collegename", String.valueOf(bean.getCollegeId()), clist) %>
		<td style="position: fixed"><font color="red" ><%=ServletUtility.getErrorMessage("collegename", request)%></font>
		</td>
		</tr>
	  <tr><th style="padding: 3px"></th></tr>    	
		<tr>
		<th align="left">FirstName <span style="color: red">*</span></th>
		<td><input type="text" name="firstname" placeholder="Enter First Name" size="20" value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("firstname", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		<tr>
		<th align="left" >LastName <span style="color: red">*</span></th>
		<td><input type="text" name="lastname" placeholder="Enter Last Name" size="20" value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
		<td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("lastname", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		<tr>
					<th align="left">Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" id="datepicker" size="20"
						placeholder="Enter Date Of Birth" readonly="readonly"
						
						value="<%=DataUtility.getDateString(bean.getDob())%>">
						&nbsp;<font style="position: fixed;" color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
						 <tr><th style="padding: 3px"></th></tr>    
		<tr>
		<th align="left">MobileNo <span style="color: red">*</span></th>
		<td><input type="text" name="mobile" maxlength="10" placeholder="Enter Mobile No" size="20" value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
		<td style="position: fixed" ><font color="red"><%=ServletUtility.getErrorMessage("mobile", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		<tr>
		<th align="left">Email-Id <span style="color: red">*</span></th>
		<td><input type="text" name="email" placeholder="Enter Email_Id" size="20" value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
		<td style="position: fixed" ><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
		</td>
		</tr>
		  <tr><th style="padding: 3px"></th></tr>    
		

	<tr>
	<th></th>
		<%
		if(bean.getId() > 0){ %>
		<td>
		<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>">&nbsp;
		<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">
		</td>
		<%}else{ %>
		<td>
		<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>"> &nbsp;&nbsp;
		<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>"></td>
	
		<%} %>
	</tr>
	
	</table>
</form>
</center>
<%@ include file = "Footer.jsp" %>
</body>
</html>