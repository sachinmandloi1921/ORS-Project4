<%@page import="in.co.rays.proj4.controller.UserCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
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
<title>User View</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
</head>
<body>
	   <form action="<%=ORSView.USER_CTL%>" method="post">
        <%@ include file="Header.jsp"%>
        <script type="text/javascript" src="../js/calendar.js"></script>
        <jsp:useBean id="bean" class="in.co.rays.proj4.Bean.UserBean"
            scope="request"></jsp:useBean>

        <%
            List l = (List) request.getAttribute("roleList");
        %>

        <center>
            <h1>  <%
            	if(bean!=null && bean.getId()>0){
            %>
            Update User
            <%}else{ %>
            
            
            Add User</h1>
            <%} %>

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
                    <th align="left">First Name<font color="red"> *</font></th>
                    <td><input type="text" name="firstName" placeholder="Enter First Name"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">Last Name<font color="red"> *</font></th>
                    <td><input type="text" name="lastName" placeholder="Enter Last Name"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">LoginId<font color="red"> *</font></th>
                    <td><input type="text" name="login" placeholder="Enter Login Id"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"
                        <%=(bean.getId() > 0) ? "readonly" : ""%>><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">Password<font color="red"> *</font></th>
                    <td><input type="password" name="password" placeholder="Enter Password"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">Confirm Password<font color="red"> *</font></th>
                    <td><input type="password" name="confirmPassword" placeholder="Enter Confirm Password"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("confirmPassword",
                    request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">Gender<font color="red"> *</font></th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                        	map.put("", "-------------Select-------------");
                            map.put("M", "Male");
                            map.put("F", "Female");
                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),map);
                        %> <%=htmlList%><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("gender",
                    request)%></font></td>
                    </td>
                </tr>
                <tr><th align="left">Role<font color="red"> *</font></th>
                <td><%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%><td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("roleId",
                    request)%></font></td></td>
                </tr>
                <tr>
                    <th align="left">Date Of Birth (mm/dd/yyyy)<font color="red"> *</font></th>
                    <td><input type="text" name="dob" placeholder="Enter DOB" id="datepicker" readonly="readonly"
                        value="<%=DataUtility.getDateString(bean.getDob())%>">
                    <td style="position: fixed"><font
                        color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td></td>
                </tr>
                <tr>
                    <th></th>
                    <td colspan="2"> 
                                    <%
 										if (bean.getId() > 0) {
 									%>
 
									 <input type="submit" name="operation" value="<%=UserCtl.OP_UPDATE%>">&nbsp; 
									 <input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>">
									<%
 										}else{
 									%> 
 									<input type="submit" name="operation" value="<%=UserCtl.OP_SAVE%>">&nbsp;
                                    <input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>"></td>
                                    <%} %>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>