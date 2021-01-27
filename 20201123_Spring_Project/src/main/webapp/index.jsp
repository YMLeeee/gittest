<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>처음페이지</title>
</head>
<body>
		<jsp:forward page="./loginform.do"/>
	<h1>
		<a href="./loginform.do">로그인 폼으로 이동</a>		
	</h1>
	
<hr>
<div>
로그인된사용자 : ${sessionScope.mem} 
</div>
<hr>
<fieldset>
	<legend>은솔테스트</legend>
	<div>
		<ul>
			<li>회원가입
				<form action="./signUp.do" method="post">
					<input type="text" name="id"><br>
					<input type="text" name="name"><br>
					<input type="text" name="pw"><br>
					<input type="submit" value="전송">
				</form>
			</li>
			<li><a href="./idCheck.do?id=TEST02">아이디 확인</a></li>
			<li><a href="./login.do?id=USER01&pw=USER01">로그인</a></li>
			<li><a href="./memberList.do">회원리스트</a></li>
		</ul>
	</div>
</fieldset>

<fieldset>
	<legend>Answerboard</legend>
	<div>
		<ul>
			<li><a href="./test.do">테스트</a></li>
			<li>
				<a href="./writeBoard.do?id=USER01&title=글작성&content=글작성">Root작성하기</a>
			</li>
			<li>
				<a href="./reply.do?seq=1&id=ADMIN01&title=답글&content=test답글">답글달기</a>
			</li>
			<li>
				<a href="./getOneBoard.do?seq=1">상세글</a>
			</li>
			<li>
				<a href="./readCountBoard.do?seq=1">조회수 증가</a>
			</li>
			<li>
				<a href="./modifyBoard.do?seq=3&title=글수정&content=글수정">글 수정</a>
			</li>
			<li>
				<a href="./modifyBoard2.do?seq=3&title=글수정2&content=글수정2">글 수정2</a>
			</li>
			<li>
				<a href="./delflagBoard.do?seq=1&seq=3">글삭제 delflag</a>
			</li>
			<li>
				<a href="./deleteBoardSel.do?seq=10">삭제 선택</a>
			</li>
			<li>
				<a href="./deleteBoard.do?seq=3">진짜 삭제</a>
			</li>
			<li>
				<a href="./userBoardListTotal.do">사용자가 볼 수 있는 글의 갯수</a>
			</li>
			<li>
				<a href="./adminBoardListTotal.do">관리자가 볼 수 있는 글의 갯수</a> 
			</li>
			<li>
				<a href="./userBoardListRow.do">사용자가 볼 수 있는 글</a> 
			</li>
			<li>
				<a href="./adminBoardListRow.do">관리자가 볼 수 있는 글</a> 
			</li>
			
		</ul>
	</div>
</fieldset>

</body>
</html>






