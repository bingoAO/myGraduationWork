<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Healer 后台管理系统</title>
<link rel = "stylesheet" href = "css/login.css" type = "text/css"/>
</head>
<body>

<div id ="wrapper">
<div id = "header">
<div id = "logotext"><h1>Healer</h1></div>
</div>

<br/>
<br/>

<div id = "login">
<form action = "dologin.jsp" method = "post">
<label>用户名：</label>
<input name = "username" value = "">&nbsp;&nbsp;&nbsp;
<label>密码：</label>
<input type = "password" name = "password" value = ""><br/><br/>
<input type="submit" value = "登录" id = "login_button">
</form>
</div>
</div>

<script> 
//取出传回来的参数error并与yes比较
  var errori ='<%=request.getParameter("error")%>';
  if(errori=='yes'){
   alert("登录失败!");
  }
</script>
 
</body>
</html>