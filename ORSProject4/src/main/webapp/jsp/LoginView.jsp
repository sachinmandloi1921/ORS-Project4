<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login View</title>
</head>
<body>
<form action="<%=ORSView.LOGIN_CTL%>" method="post">
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.UserBean" scope="request">
</jsp:useBean>


<center>
<h1>Login</h1>
<h2><font color="Green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
<h2><font color="Red"><%=ServletUtility.getErrorMessage(request) %>
		<%=DataUtility.getString((String)request.getAttribute("message"))%>
</font></h2>

			  <input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
              <input type="hidden" name="URI" value="<%=request.getAttribute("uri") %>">
  <table>
  	<tr>
  		<th align="left">LoginId<font color="Red">*</font></th>
  			<td><input type="text" name="login" placeholder="Enter Email" size="30" value="<%=DataUtility.getStringData(bean.getLogin())%>">
                  </td>      <td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
  	</tr>
  	
  	<tr>
                    <th align = "left">Password<font color="red" >*</th>
                    <td><input type="password" name="password" 
                     placeholder=" Enter Password"size=30 value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                        <td style="position: fixed"> <font
						color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				
     </tr>
     
     <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=LoginCtl.OP_SIGN_IN %>"> &nbsp; <input type="submit"
                        name="operation" value="<%=LoginCtl.OP_SIGN_UP %>" > &nbsp;</td>
                </tr>
                <tr><th></th>
                <td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password</b></a>&nbsp;</td>
     </tr>
  </table> 
</center>
 </form>
<%@include file="Footer.jsp" %>
</body>
</html>