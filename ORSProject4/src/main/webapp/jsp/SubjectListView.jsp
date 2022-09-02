<%@page import="in.co.rays.proj4.model.SubjectModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.controller.SubjectListCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.Bean.CourseBean"%>
<%@page import="in.co.rays.proj4.Bean.SubjectBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Subject List</title>
<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/checkbox.js"></script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.SubjectBean" scope="request"></jsp:useBean>
<center>
<%@include file="Header.jsp" %>
<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
	
	<div align="center">
    			<h1>Subject List</h1>
                <h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
                <h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>    
	</div>
	
	<%
		List<SubjectBean> slist = (List<SubjectBean>)request.getAttribute("subjectList");
		List<CourseBean> clist =  (List<CourseBean>) request.getAttribute("courseList");
	%>
		<% 
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		
		List list = ServletUtility.getList(request);
		Iterator<SubjectBean> it = list.iterator();
			if(list.size() !=0) {
			%>
	
	
	<table width="100%" align="center">
		<tr>
		 <td align="center"><label>Subject Name :</label>
		 <%=HTMLUtility.getList("subjectname", String.valueOf(bean.getId()), slist) %>
			
		&nbsp;
		<label>Course Name :</label> <input type="text" name="courseName"
						placeholder="Enter Course Name"
						value="<%=ServletUtility.getParameter("courseName", request)%>"> 
		&nbsp;
		<input type="submit" name="operation" value="<%=SubjectListCtl.OP_SEARCH%>">			
		
		&nbsp;
		<input type="submit" name="operation" value="<%=SubjectListCtl.OP_RESET %>">
			</td>
	</tr>
	</table>
	<br>
	
	<table border="1" width="100%" align="center" cellpadding=6px cellspacing=".2">
		<tr>
			<th><input type="checkbox" id="select_all" name ="select"> Select All</th>
			
			<th>S.No.</th>
			<th>Subject Name</th>
			<th>Description</th>
			<th>CourseName</th>
			<th>Edit</th>							
		</tr>
		
		<%
			while (it.hasNext())
			{
				bean = it.next();	
		%>
		
		
		<tr align="center">
		<td> <input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
		<td><%=index++ %></td>		
		<td><%=bean.getSubject_Name() %></td>
		<td><%=bean.getDiscription() %></td>
		<td><%=bean.getCourse_Name() %></td>
		<td><a href ="SubjectCtl?id=<%=bean.getId() %>" >Edit</a></td>
		</tr>
		
		<%
		}
		%>
	</table>
	
	<table width="100%">
		<tr>
		<% if(pageNo == 1){ %>
		<td align="left" ><input type="submit" name="operation" disabled="disabled" value="<%=SubjectListCtl.OP_PREVIOUS %>"></td>
		 <%}else{ %>
		 <td align="left" ><input type="submit" name="operation" value="<%=SubjectListCtl.OP_PREVIOUS %>"></td>
		 <%} %>
		<td><input type="submit" name="operation" value="<%=SubjectListCtl.OP_DELETE %>"></td> 
		<td><input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEW %>"></td> 
		
		<%
			SubjectModel model = new SubjectModel();
		%>
			
		<% if(list.size() < pageSize || model.nextPk() - 1 == bean.getId()) { %>		
		<td align="right"><input type="submit" name="operation" disabled="disabled"  value="<%=SubjectListCtl.OP_NEXT %>"></td>
		<%}else{ %>
				<td align="right"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEXT %>"></td>
		<%} %>
		</tr>
	</table>
					<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_BACK%>"></td>
            		
            		<% } %>
            		
	<input type="hidden" name="pageno" value="<%=pageNo%>">
	<input type="hidden" name="pagesize" value="<%=pageSize%>">
</form>
</center>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
	<%@include file = "Footer.jsp" %>
</body>
</html>