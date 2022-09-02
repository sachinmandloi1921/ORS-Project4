<%@page import="in.co.rays.proj4.controller.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Get Marksheet</title>
</head>
<body>
 <%@ include file="Header.jsp"%>

    <jsp:useBean id="bean" class="in.co.rays.proj4.Bean.MarksheetBean"
        scope="request"></jsp:useBean>

    <center>
        <h1>Get Marksheet</h1>
 <H2>
        <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
        </font>
 </H2>
        <H2>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
            </font>
        </H2>

        <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <table>
           
                <td><label><b>Roll No</b><font color="red"> *</font></label>&emsp;
                <input type="text" name="rollNo" placeholder="Enter Roll No"
                    value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;</td>
                    <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></td><br>
                <tr>
                <td>&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;<input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">&emsp;
                <input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_RESET%>"></td></tr>
	</table>
                <%
                    if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
                %>
		<table border="1" width="30%">
                <tr >
                    <td>Rollno :
                    <td colspan="5"><%=DataUtility.getStringData(bean.getRollNo())%></td>
                </tr>
                <tr>
                    <td>Name :</td>
                    <td><%=DataUtility.getStringData(bean.getName())%></td>
                </tr>
                <tr>
                    <td>Physics :</td>
                    <td><%=DataUtility.getStringData(bean.getPhysics())%></td>
                </tr>
                <tr>
                    <td>Chemistry :</td>
                    <td><%=DataUtility.getStringData(bean.getChemistry())%></td>
                </tr>
                <tr>
                    <td>Maths :</td>
                    <td><%=DataUtility.getStringData(bean.getMaths())%></td>

                </tr>
                <tr>
                    </td>
                </tr>
            </table>
            <%
                }
            %>
        </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>