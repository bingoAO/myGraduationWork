<%@ page language="java" import = "java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import = "beans.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
User user = new User();
%>
<jsp:useBean id="user1" class = "beans.User" ></jsp:useBean>
</body>
</html>