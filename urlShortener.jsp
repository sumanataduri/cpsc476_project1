<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	if((Boolean)request.getAttribute("urlNull"))
	{ %>
	<h4>Please enter Url</h4>
	<%
	}
 %>
<form method="POST" action="<c:url value="/url" />">
	<h2>Enter Long URL:</h2><br />
	<input type="text" name="longUrl" /><br /><br />
	<input type="submit" value="Generate Shorten URL" />
</form>

<%  String shortUrl= request.getAttribute("shortURL") != null ? request.getAttribute("shortURL").toString() : ""; 

	if(!shortUrl.isEmpty() && shortUrl != "false")
	{
%>	
	<h4>Short Url : <%= shortUrl%></h4>
	
	<a href= "<c:url value="<%=shortUrl %>" />" target="newtab"> <%=shortUrl %> </a>
			
  <%} %>
	 
	
</body>
</html>