<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.model.StudentModel"%>
<%@page import="in.co.rays.proj4.Bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student List</title>
<script src="<%=ORSView.APP_CONTEXT %>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT %>/js/checkbox.js"></script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.StudentBean"
		scope="request"></jsp:useBean>
	 <%@include file="Header.jsp"%>
    <center>
        <h1>Student List</h1>
        
        <%
		List<StudentBean> studList = (List<StudentBean>)request.getAttribute("StudentList"); 
		%>
        <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
            <table width="100%">
                <tr>
                    <td align="center"><label> FirstName :</label> <%=HTMLUtility.getList("firstName", String.valueOf(bean.getId()), studList)  %>
                        <label>LastName :</label> <input type="text" name="lastName" placeholder="Enter Last Name"
                        value="<%=ServletUtility.getParameter("lastName", request)%>"><label>Email_Id
                            :</label> <input type="text" name="email" placeholder="Enter Email"
                        value="<%=ServletUtility.getParameter("email", request)%>">
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>"> &nbsp;
        	         <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET%>"></td>
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
                <tr>
                    <th><input type="checkbox" id="select_all" name="select">Select All.</th>
                    <th>S.No.</th>
                    <th>College.</th>
                    <th>First Name.</th>
                    <th>Last Name.</th>
                    <th>Date Of Birth.</th>
                    <th>Mobile No.</th>
                    <th>Email ID.</th>
                    <th>Edit</th>
                </tr>
                <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                    List list = ServletUtility.getList(request);
                    Iterator<StudentBean> it = list.iterator();
                    while (it.hasNext()) {
                         bean = it.next();
                %>
                <tr>
                    <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>"></td>
                    <td><%=index++%></td>
                    <td><%=bean.getCollegeName()%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><%=bean.getEmail()%></td>
                    <td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td><%if(pageNo == 1){ %>
                    <td><input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_PREVIOUS%>">
       				<%}else{ %>
       				<td><input type="submit" name="operation"  value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
       				<%} %></td>	
                        <td><input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE %>"></td> 
		<td><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW %>"></td> 
                    <% StudentModel model = new StudentModel();                  
                  %> 
                  <!-- if(list.size() < pageSize || model.nextPk()-1 == bean.getId()){  -->
                    <% if(list.size() < pageSize) { %>
                  <td align="right"> <input type="submit" name="operation" disabled="disabled" value="<%=StudentListCtl.OP_NEXT%>"></td>
  					<%}else{ %>                   
  				  <td align="right"> <input type="submit" name="operation"  value="<%=StudentListCtl.OP_NEXT%>"></td>
   					<%} %>   </td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
    </center>
</body>
</html>