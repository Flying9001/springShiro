<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SpringShiro</title>
</head>
<body>
<h2>UserLogin</h2>
<h2><%=basePath %></h2>

<form action="/api/user/login" method="post">
    登录: <input type="text" name="account" id="account"/><br>
    密码: <input type="password" name="passcode" id="passcode"/><br>
    <input type="submit" value="提交"/>
</form>



</body>
</html>
