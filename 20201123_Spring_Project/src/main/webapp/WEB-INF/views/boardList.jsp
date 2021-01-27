<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체글 보기</title>
</head>
<body>
<%@ include file="./boardTopMenu.jsp" %>
<body>
<script type="text/javascript" src="./js/boardList.js"></script>

<div id="container" class="container">
	<!-- 게시판 리스트 폼 -->
	${row}
	<div id="select">
		<span>
			<select class="btn btn-primary" id="list" name="list" onchange="pageList()">
				<option value="5" >5</option>
				<option value="10" >10</option>
				<option value="15" >15</option>
				<option value="20" >20</option>
			</select>
		</span>
	</div>
	
	<form action="#" method="post" id="frm" name="frm" onsubmit="return chkbox();">
		<div class="panel-group" id="accordion">
			<table class="table table-bordered">
				<tr>
					<c:if test="${mem.auth eq 'A'}">					
						<th><input type="checkbox" onclick="checkAll(this.checked)"></th>
					</c:if>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<c:if test="${mem.auth eq 'A'}">
						<th>
							삭제여부
						</th>
					</c:if>
					<th>작성일</th>
				</tr>
				
				<jsp:useBean id="format" class="com.min.edu.bean.InputList" scope="page"/>
				<jsp:setProperty property="lists" name="format" value="${lists}"/>
				<jsp:setProperty property="mem" name="format" value="${mem}"/>
				<jsp:getProperty property="listForm" name="format"/>
				
			</table>
		</div>
	
		<!-- row -->
		<input type="text" name="index" id="index" value="${row.index}">
		<input type="text" name="pageNum" id="pageNum" value="${row.pageNum}">
		<input type="text" name="listNum" id="listNum" value="${row.listNum}">
		
		<div class="center">
			<ul class="pagination">
				<li><a href="#" onclick="pageFirst()">&laquo;</a></li>
				<li><a href="#" onclick="pagePrev(${row.pageNum},${row.pageList})">&lsaquo;</a></li>
				<!-- 페이지 숫자 시작 -->
				<c:forEach var="i" begin="${row.pageNum}" end="${row.count}" step="1">
					<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
				</c:forEach>
				<!-- 페이지 숫자 끝 -->
				<li><a href="#" onclick="pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&rsaquo;</a></li>
				<li><a href="#" onclick="pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})">&raquo;</a></li>
			</ul>
		</div>
		
		<c:if test="${mem.auth eq 'A'}">
		<div>
			<input type="submit" class="btn btn-danger" value="다중삭제">
		</div>
		</c:if>
	</form>
	
	<!-- Modal -->
	<div id="modify" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">게시글 수정</h4>
				</div>
				<div class="modal-body">
					<form action="#" class="form-margin" method="post" id="frmModify"></form>
				</div>
				
			</div>

		</div>
	</div>
	
	<!-- Modal 수정 -->
	<div id="reply" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">답글작성</h4>
				</div>
				<div class="modal-body">
					<form action="#" class="form-margin" method="post" id="frmReply">
						
					</form>
				</div>
			</div>
		</div>
	</div>
	

</div>
<script>
	$(document).ready(function() {
		var listNum = $('#listNum').val();
		// 		alert(listNum);
		// 여기서 option은 5 10 15 20
		$('#list option').each(function() {
			if (listNum == $(this).val()) {
				$(this).prop("selected", true);
			} else {
				$(this).prop("selected", false);
			}
		});
	});
</script>


</body>

</html>
