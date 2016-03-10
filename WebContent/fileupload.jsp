<%@ page language="java" contentType="text/html; charset=UTF-8"
import = "java.util.*,java.io.*"
    pageEncoding="UTF-8"%>
    <%@page import = "utils.FileUploadUtils"
     %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<link rel = "stylesheet" href = "css/fileupload.css" type = "text/css"/>
<script type="text/javascript">
function addfiles(){
var filesDiv = document.getElementById("files");
var fileInput = document.createElement("input");

fileInput.type="file";
fileInput.name="浏览";

var delButton = document.createElement("input");
delButton.type="button";
delButton.value="删除";

delButton.onclick=function del(){
this.parentNode.parentNode.removeChild(this.parentNode);

}
var div = document.createElement("div");
div.appendChild(fileInput);
div.appendChild(delButton);

filesDiv.appendChild(div);
}
</script>

</head>
<body>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<%
out.clear();
out = pageContext.pushBody();
response.setContentType("image/jpeg");
String imgName = request.getParameter("fileName");
FileUploadUtils.show(request,response);
%>
<div id = "wrapper">
 <form id = "form" action="FileUploadAction" method="post" enctype="multipart/form-data">
     <input type="file" name="浏览"/>
     <div id="files"></div>
     <div id="uploadandadd">
     <input type="submit" value="上传"/>
     <input id = "addfile" type="button" value="添加文件" onclick="addfiles()"/>
     </div>
    </form>
    </div>
        ${message}  
</body>
</html>