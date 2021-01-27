<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!-- <html> -->
<!-- <head> -->
<!-- <title>안녕하세요</title> -->
<!-- <html> -->
<!-- <!-- include시 head부분은 겹치지 않음 body만 겹쳐져 -->
<!-- <head> -->
<!-- 덜 중요한 부분들을 밑으로 순서가 바뀐다면 충돌이 일어날 수 있음 bootstrap> sweetalert -->

<link type="text/css" rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="./css/bootstrap-theme.css">
<link type="text/css" rel="stylesheet" href="./css/sweetalert.css">
<link type="text/css" rel="stylesheet" href="./css/common.css">
<link type="text/css" rel="stylesheet" href="./css/BoardList.css">
<!-- </head> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<script type="text/javascript" src="./js/boardList.js"></script>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      
      <a class="navbar-brand" href="#">
		<img src="./images/고라파덕.png" width="30px" height="30px">
		</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
    	<ul class="nav navbar-nav navbar-left">
    	<c:if test="${fn:trim(mem.auth) eq 'U'}">
    		<li>
    			<a href="./boardWriteForm.do" class="navbar-brand">글작성</a>
    		</li>    	
    	</c:if>
    	
    		<li>
    			<a href="./boardList.do" class="navbar-brand">게시판</a>
    		</li>
    	<c:if test="${fn:trim(mem.auth) eq 'A' }">
    		<li>
    			<a href= "./memberListMAV.do" class="navbar-brand">회원리스트</a>
    		</li>
    	</c:if>	
    		
    	</ul>
      
      <ul class="nav navbar-nav navbar-right">
      	<li>
      		<a href="#">${mem.name}님 환영합니다.(${mem.auth})</a>
      	</li>
    	<li>
    		<a href="./logout.do">logout</a>
    	</li>
      </ul>
    </div>
  </div>
</nav>

<!-- </body> -->
<!-- </html> -->