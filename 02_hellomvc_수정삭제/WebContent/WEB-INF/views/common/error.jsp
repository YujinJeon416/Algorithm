<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%-- 
	isErrorPage속성을 true로 지정하면, 
	던져진 예외객체에 exception키워드로 선언없이 접근 가능 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error</title>
</head>
<body style="text-align: center;">
	<h1>이용에 불편을 드려 죄송합니다.</h1>
	<p style="color: red;"><%= exception.getMessage() %></p>
	<p><a href="<%= request.getContextPath() %>">메인페이지로 돌아가기</a></p>

</body>
</html>