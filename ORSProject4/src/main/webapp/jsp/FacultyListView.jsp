<%@page import="in.co.rays.proj4.model.FacultyModel"%>
<%@page import="in.co.rays.proj4.controller.FacultyListCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.Bean.FacultyBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Faculty List View</title>
<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/checkbox.js"></script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.FacultyBean" scope="request"></jsp:useBean>
<%@include file ="Header.jsp"%>
  <%List flist= (List) request.getAttribute("FList"); %>
<center>
<form action="<%=ORSView.FACULTY_LIST_CTL %>" method="post">
	
	<div align="center">
			<h1>Faculty List</h1>
			<h3><font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font> </h3>
			<h3><font style="color: red"><%=ServletUtility.getErrorMessage(request) %></font> </h3>
	</div>
	
	<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	
	int index=(pageNo-1)*pageSize+1;
	List list = ServletUtility.getList(request);
	Iterator<FacultyBean> it = list.iterator();
	
		if(list.size() !=0){
	%>
	
	<table width ="100%" align="center">
		<tr><th></th>
		<td align="center">
		<label>First Name :</label>
		  <%=HTMLUtility.getList("firstname", String.valueOf(bean.getId()), flist) %>
		<label>Last Name :</label>
		<input type="text" name="lastname" placeholder="Enter Last Name" value=<%=ServletUtility.getParameter("lastname", request) %>>		
		<label>Login_ID</label>
		<input type="text" name="login" placeholder="Enter Login_id" value=<%=ServletUtility.getParameter("login", request) %>>				
		&nbsp;
		<input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH%>">
		&nbsp;
		<input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET %>">
		</td></tr>
	</table>
	<br>	
	<table border="1" width="100%" align="center" cellpadding=8px cellspacing=".2">
		<tr>
		
			<th ><input type="checkbox" id="select_all" name ="Select">Select All.</th>
			
			<th>S.No.</th>
			<th>First Name.</th>
			<th>Last Name.</th>
			<th>Login_id.</th>
			<th>Qualification.</th>
			<th>Mobile No.</th>
			<th>Date of Joining.</th>
			<th>College Name.</th>
			<th>Subject Name.</th>
			<th>Edit</th>
		</tr>
		
		<%
		while(it.hasNext()){
	 	bean =it.next();	
		%>
		
	<tr>
		<td><input type = "checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>"></td>
		<td><%=index++%></td>
		<td><%=bean.getFirst_Name() %></td>
		<td><%=bean.getLast_Name() %></td>
		<td><%=bean.getEmail_id() %></td>
		<td><%=bean.getQualification() %></td>
		<td><%=bean.getMobile_No() %></td>
		<td><%=bean.getDOJ()%></td>
		<td><%=bean.getCollege_Name() %></td>
		<td><%=bean.getSubject_Name() %></td>
		<td><a href="FacultyCtl?id=<%=bean.getId()%>">Edit </a></td>
	<%-- 	<td><a href="FacultyCtl?id=<%=bean1.getId() %>"> Edit</a></td> --%>
	</tr>
		<% 
		}
		%>
	</table>
	<table width = "100%">
		<tr><th></th>
		<% if(pageNo == 1){ %>
		<td align="left" ><input type="submit" name="operation" disabled="disabled" value="<%=FacultyListCtl.OP_PREVIOUS %>"></td>
		 <%}else{ %>
		 <td align="left" ><input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS %>"></td>
		 <%} %>
			<td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
			<td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>	
			
			<%
				FacultyModel model = new FacultyModel();
	
			%>		
		<% if(list.size() < pageSize || model.nextPk() - 1 == bean.getId()) { %>		
		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=FacultyListCtl.OP_NEXT %>"></td>
		<%}else{ %>
				<td align="right"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT %>"></td>
		<%} %>
		</tr>
	</table>
	
			<%}if(list.size() == 0){ %>
            		<td align="center"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_BACK%>"></td>	
            		<% } %>
            		
	
		<input type="hidden" name="operation" value="<%=pageNo %>">
		<input type="hidden" name="operation" value="<%=pageSize%>">
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