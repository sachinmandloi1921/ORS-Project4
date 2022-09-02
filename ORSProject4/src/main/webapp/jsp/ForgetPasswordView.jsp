<%@page import="in.co.rays.proj4.controller.ForgetPasswordCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forget Password</title>

</head>
<body>
	<form action="<%=ORSView.FORGET_PASSWORD_CTL%>"method="post">
	<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.UserBean" scope="request"></jsp:useBean>
	
	<center>
            <h1>Forgot your password?</h1>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <table>
                 <lable>Submit your email address and we'll send you password.</lable>
                 <br>
                 <br>
                 
                <label>Email Id :</label>&emsp;
                <input type="text" name="login" placeholder="Enter ID Here"
                    value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
                <input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"><br>
                <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
            </table>
            
            
    </form>
    </center>
		
	</form>
   <%@include file="Footer.jsp" %>
</body>
	</html>