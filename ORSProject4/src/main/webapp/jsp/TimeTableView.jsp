<%@page import="in.co.rays.proj4.controller.TimeTableCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.Bean.TimeTableBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> TimeTable Registered</title>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

  <script>
  
  var d = new Date();
  function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
  }
  
   $( function() {
	  $("#udate5").datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'0:+2',
		  dateFormat:'mm/dd/yy',
// Disable for Sunday
		  beforeShowDay : disableSunday,		  
 //Disable for back date
		  minDate : 0   
	  });
  } );
 </script>


</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.proj4.Bean.TimeTableBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.TIMETABLE_CTL %>" method="post">

	<%@include file="Header.jsp" %>

<center>
	<%
	List <TimeTableBean> courseList =(List<TimeTableBean>)request.getAttribute("CourseList"); 
	List <TimeTableBean> subjectList = (List<TimeTableBean>)request.getAttribute("SubjectList");
	%>
	
	<input type="hidden" name="id" value="<%=bean.getId() %>">
	<input type="hidden" name="createdby" value="<%=bean.getCreatedBy() %>">
	<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy() %>">
	<input type="hidden" name="createddatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime()) %>">
	<input type="hidden" name="modifiedby" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
	
	<div align = "center">
		<h1 >
			<% if(bean != null && bean.getId() >0 ){ %>
		<tr><th>Update TimeTable</th></tr>		
			<% }else{ %>
		<tr ><th >Add TimeTable</th></tr>	
			<% } %>
		</h1>
	<h2 align="center"><font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font></h2>
	<h2 align="center"><font style="color: green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
	</div>

<table>
	<tr>
	<th align="left">Course <span style="color: red">*</span></th>
	<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourse_Id()), courseList) %></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId",request) %></font> 
	</td></tr>
	
 <tr><th style="padding: 3px"></th></tr>   
	
	<tr><th align="left">Subject <span style="color: red">*</span></th>
	<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubject_Id()), subjectList) %></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectId",request) %></font> 
	</td></tr>
	
 <tr><th style="padding: 3px"></th></tr>   
	
	<tr>
	<th align="left">Semester<span style="color: red">*</span></th>
	<td><%
		LinkedHashMap<String , String> map = new LinkedHashMap< String , String>();
		map.put("","-------------Select-------------");
		map.put("1","1st");
		map.put("2","2nd");
		map.put("3","3rd");
		map.put("4","4th");
		map.put("5","5th");
		map.put("6","6th");
		map.put("7","7th");
		map.put("8","8th");
		map.put("9","9th");
		map.put("10","10th");
	
		String htmlList = HTMLUtility.getList("semester",String.valueOf(bean.getSemester()), map);
	%>
	<%=htmlList %>
	</td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("semester",request) %></font> 
	</td></tr>
	
 <tr><th  style="padding: 3px"></th></tr>   
	<tr><th align="left">Exam Date <span style="color: red">*</span></th>
	<td> <input type="text" readonly="readonly" id="udate5" size="20" placeholder="Select Date" name="ExDate" value="<%=DataUtility.getDateString(bean.getExam_Date()) %>">
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ExDate",request) %></font> 
	</td></tr>
	
 <tr><th style="padding: 3px"></th></tr>   
	<tr><th align="left">Exam Time <span style="color: red">*</span></span></th>
	<td>
	<%
		LinkedHashMap<String , String > map1 = new LinkedHashMap<String  ,String >();
		map1.put("","------------Select--------------");
		map1.put("08:00 AM to 11:00 AM","08:00 AM to 11:00 AM");
		map1.put("12:00 PM to 03:00 PM","12:00 PM to 03:00 PM");
		map1.put("04:00 PM to 07:00 PM","04:00 PM to 07:00 PM");
	
		String htmlList1 = HTMLUtility.getList("ExTime", bean.getExam_Time(), map1);
	%>
	<%=htmlList1 %>
	</td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ExTime",request) %></font> 
	</td></tr>
	
<%-- 	<tr><th align = "left">Description <span style="color: red">*</span></th>
	<td><input type="text" name="description" placeholder="Enter Description" size="25" value="<%=DataUtility.getStringData(bean.getDescription())%>">
	
	<%
	if(bean != null && bean.getId()>0) {
	%>
	<%=DataUtility.getStringData(bean.getDescription()) %>
	<% } %>
	</td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request) %>	</font>	</td></tr>
	
 --%>
 
 	<tr><th style="padding: 3px"></th>
	<td></td></tr>
	
	<tr><th></th><td></td>
	</tr>
	
	<tr>	
	<%
	if(bean != null && bean.getId() > 0){
	%>
		<td colspan="2">&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&emsp;&emsp;&emsp;
		<input type="submit" name="operation" value="<%=TimeTableCtl.OP_UPDATE%>">&nbsp;
		<input type="submit" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>">
		</td>
		<%} else { %>
		
		<td colspan="2">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" name="operation" value="<%=TimeTableCtl.OP_SAVE%>">
		&nbsp;&nbsp;
		<input type="submit" name="operation" value="<%=TimeTableCtl.OP_RESET%>">
		</td>
		<% } %>
	</tr>
</table>
</form>	
</center>
	<%@include file="Footer.jsp" %>
</body>
</html>