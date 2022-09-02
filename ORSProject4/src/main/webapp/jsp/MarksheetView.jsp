<%@page import="in.co.rays.proj4.Bean.StudentBean"%>
<%@page import="in.co.rays.proj4.controller.MarksheetCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Marksheet View</title>
</head>

<body>
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.MarksheetBean"
			scope="request"></jsp:useBean>
		<%
			List<StudentBean> l = (List<StudentBean>) request.getAttribute("studentList");
		%>

		<center>
			 <h1>
        	<%
        		if( bean != null && bean.getId()>0){
        	%> 
        	<tr><th><font>Update Marksheet</font></th></tr>
        	<% }else{%>
        	<tr><th><font>Add Marksheet</font></th></tr>
        	<% }%>
        </h1>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>


			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th align="left">Rollno<font color="red"> *</font></th>
					<td><input type="text" name="rollNo" placeholder="Enter Roll No." size="20"
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>><td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td></td>
				</tr>
				<tr>
					<th align="left">Name<font color="red"> *</font></th>
					<td><%=HTMLUtility.getList("sname", String.valueOf(bean.getStudentId()), l) %> <td style="position: fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("sname", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Physics<font color="red"> *</font></th>
					<td><input type="text" name="physics" placeholder="Enter Physics Marks" size="20"
						value="<%=(DataUtility.getStringData(bean.getPhysics())).equals("0")?"":DataUtility.getStringData(bean.getPhysics())%>"><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td></td>
				</tr>
				<tr>
						<th align="left">Chemistry<font color="red"> *</font></th>
					<td><input type="text" name="chemistry" placeholder="Enter Chemistry Marks" size="20"
						value="<%=(DataUtility.getStringData(bean.getChemistry())).equals("0")?"":DataUtility.getStringData(bean.getChemistry())%>"><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td></td>
				</tr>
				<tr>
					<th align="left">Maths<font color="red"> *</font></th>
					<td><input type="text" name="maths" placeholder="Enter Maths Marks" size="20"
						value="<%=(DataUtility.getStringData(bean.getMaths())).equals("0")?"":DataUtility.getStringData(bean.getMaths())%>"><td style="position: fixed"><font
						color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td></td>

				</tr>
				<tr>
					<th></th>
					 <td colspan="2">
<%
 	if (bean.getId() > 0) {
 %>
 
 <input type="submit" name="operation" value="<%=MarksheetCtl.OP_UPDATE%>"> &nbsp;  
 <input type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"></td>
<%
 	}else{
 %> 
 <input type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE%>">&nbsp; 
 <input type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET%>"></td>
 <%} %>
	</tr>    
	</table>
	</form>
	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>