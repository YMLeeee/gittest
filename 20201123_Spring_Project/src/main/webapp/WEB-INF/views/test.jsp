<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션확인</title>
</head>
<body>
<h1>${sessionScope.mem}</h1>
<hr>
<h1>${requestScope.detail}</h1>
<hr>
<h1>${uTotal}</h1>
<a href="./logout.do">로그아웃</a>
</body>
</html>





