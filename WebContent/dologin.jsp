<%@ page language="java" import = "java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
System.out.println("hello");
	String errorInfo = "hello";         // 获取错误属性
	if(errorInfo != null) {
%>
	<script type="text/javascript" language="javascript">
		alert("<%=errorInfo%>");                                            // 弹出错误信息
	</script>	
<%
	}



String path = request.getContextPath();

String username = "";
String password = "";
request.setCharacterEncoding("utf-8");

username = request.getParameter("username");
password = request.getParameter("password");	
if(isNameLegal(username)&&isPasswordLegal(password)){
request.getRequestDispatcher("RegisterAction").forward(request,response);
}else{
	
}



//if the user is logical
%>
<%!
public boolean isNameLegal(String username){
	if(username==null||username.length()==0){
		//alert a window to tell the username is null
		%>
		<script type="text/javascript" language="javascript">
		alert("用户名为空");                                            // 弹出错误信息
	</script>
	<%! return false;
	}else if(username.length()>16){%>
		<script type="text/javascript" language="javascript">
		alert("请检查用户名的长度是否在1-16之间");                                            // 弹出错误信息
	</script>	
	<%! 
		//alert a window
		return false;
	}
	return true;
}

public boolean isPasswordLegal(String password){
	if(password==null||password.length()==0){
		%>
		<script type="text/javascript" language="javascript">
		alert("密码为空");                                            // 弹出错误信息
	</script>
	<%! 
	}else if(password.length()<6){%>
		<script type="text/javascript" language="javascript">
		alert("密码长度不能小于6");                                            // 弹出错误信息
	</script><%! 
	}else if(password.length()>16){%>
		<script type="text/javascript" language="javascript">
		alert("密码长度不能大于16");                                            // 弹出错误信息
	</script>
	<%! 
	}
	return true;
}
%>
</body>
</html>