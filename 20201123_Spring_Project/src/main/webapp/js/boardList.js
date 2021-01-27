function modify(val){
	ajaxModify(val);
	$('#modify').modal();
}

var ajaxModify = function(val){
	$.ajax({
		url : './modifyForm.do',
		method : 'post',
		data:"seq="+val,
		dataType : 'json',
		success : function(v){
			console.log(v.seq, v.id, v.title, v.content, v.regdate)
			 html = "<div class='form-group'>";
         html += "<input type='hidden' value='"+v.seq+"' name='seq'/>";
         html += "<label for='id'>아이디</label>";
         html += "<p class='form-control'><strong>"+v.id+"</strong></p>";
         html += "</div>";
         
         html += "<div class='form-group'>";
         html += "<label for='regdate'>작성일</label>";
         html += "<p class='form-control'><strong>"+v.regdate+"</strong></p>";
         html += "</div>";
            
         html += "<div class='form-group'>";
         html += "<label for='title'>제목</label>";
         html += "<input class='form-control' type='text' id='title' name='title' value='"+v.title+"' required/>";
         html += "</div>"
            
         html += "<div class='form-group'>";
         html += "<label for='content'>내용</label>";
         html += "<textarea class='form-control' row='5' id='content' name='content' required>"+v.content+"</textarea>";
         html += "</div>";   
            
         html += "<div class='modal-footer'>";
         html += "<input class='btn btn-success' type='button' value='글수정' onclick='update()'/>";
         html += "<input class='btn btn-info' type='reset' value='초기화'/>";
         html += "<button type='button' class='btn btn-default' data-dismiss='modal'>취소</button>";
         html += "</div>";

	        
		    $('#frmModify').html(html);
//		    html += "<button type="submit" class="btn btn-default">Submit</button>";
		    
		},
		error: function(){
			alert("잘못된 요청입니다.");
		}
	});
}

function update(){
//	alert("작동");
	var frm = document.getElementById('frmModify');
	frm.action = './modify.do';
	var title = $('#title').val();
//	alert(title);
	if(title == ''){
		swal("글수정오류","제목은 필수입니다.");
	}else{
		frm.submit();
	}
}

function reply(val){
//	alert(val);
	ajaxReply(val);
	$('#reply').modal();
}

var ajaxReply = function(v){
	console.log(v);
	$.ajax({
		url : "./replyForm.do",
		type : "post",
		data : "seq="+v,
		success : function(r){
			// 여기서 r은 replyForm.do에서 전달받은 값(현재 map형태로 가져왔음)을 가져온 parameter 
			// ["obj":dto, "sessionId":'TEST01'] 현재 이런식으로 들어가 있음 obj.seq -> dto에 등록되어있는 getSeq가져옴
			html = "<input type='hidden' value='"+r.obj.seq+"' name='seq'>    ";
			html += "<div class='form-group'>   ";
			html += "	<label>ROOT 번호</label>";
			html += "	<p class='form-control'>"+r.obj.seq+"</p>    ";
			html += "</div> ";
			html += "<div class='form-group'>   ";
			html += "	<label>ROOT 작성일</label>   ";
			html += "	<p class='form-control'>"+r.obj.readcount+"</p>";
			html += "</div> ";
			html += "<div class='form-group'>   ";
			html += "	<label>ROOT 작성일</label>   ";
			html += "	<p class='form-control'>"+r.obj.regdate+"</p> ";
			html += "</div> ";
			html += "<div class='form-group'>   ";
			html += "	<label>ROOT 작성자</label>   ";
			html += "	<p class='form-control'>"+r.obj.id+"</p>";
			html += "</div> ";
			html += "  ";
			html += "<div class='form-group'>   ";
			html += "	<label>작성자</label>   ";
			html += "	<p class='form-control'>"+r.sessionId+"</p> ";
			html += "</div> ";
			html += "  ";
			html += "<div class='form-group'>   ";
			html += "	<label id='titl'>제목(원본)</label>    ";
			html += "	<input type='text' class='form-control' id='title' name='title' value='"+r.obj.title+"'>  ";
			html += "</div> ";
			html += "  ";
			html += "<div class='form-group'>   ";
			html += "	<label id='con'>내&nbsp;&nbsp;&nbsp;&nbsp;용(원본)</label> ";
			html += "		<input type='hidden' id='hiddenContent' value='"+r.obj.content+"'>";
			html += "	<textarea rows='5' class='form-control' name='content' id='Txtarea' onclick='chk()'>"+r.obj.content+"</textarea>";
			html += "</div> ";
			html += "<div class='modal-footer'> ";
			html += "	<input class='btn btn-warning' type='button' value='답글작성' onclick='replyIn()'>    ";
			html += "	<span onclick='reset()'><input class='btn btn-success' type='reset' value='초기화'></span>    ";
			html += "	<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>    ";
			html += "</div> ";
			
			$('#frmReply').html(html);
		},
		error : function(){
			alert("잘못된 요청입니다.");
		}
	
	});
}

function chk(){
	// 라벨 정보
	var con = document.getElementById("con");
	var titl = document.getElementById("titl");
	
	// 값
	var title = document.getElementById('title');
	var txtarea = document.getElementById('Txtarea');
	var hiddenContent = document.getElementById("hiddenContent");
	
	if(txtarea.value == hiddenContent.value){
		con.innerHTML = "내&nbsp;&nbsp;&nbsp;&nbsp;용";
		titl.innerHTML = "제목";
		txtarea.value = "";
		title.value= "";
	}
	
}

function reset(){
	document.getElementById("con").innerHTML = "내&nbsp;&nbsp;&nbsp;&nbsp;용(원본)";
	document.getElementById("titl").innerHTML = "제목(원본)";
	
}

function replyIn(){
//	console.log("replyIn");
	var frm = document.getElementById("frmReply");
	frm.action = "./reply.do";
	var title = $("#title").val();
	
	if(title == ''){
		swal("답글 작성 오류","제목은 필수 값입니다.")
	}else{
//		var titl = $("#titl").val();
		var titl2 = $("#titl").html();
//		console.log(titl, "//"+titl2);
		// value는 form요소만 가져올 수 잇음 #titl은 label태그(마크업언어) 이기 때문에 값을 찍을 수 없음 text혹은 html로 찍어야 보임
		if(titl2 == '제목'){
			frm.submit();
		}else{
			swal("답글 작성 오류","답글을 작성해 주세요.");
		}
	
	}
}

function del(val){
	location.href="./del.do?seq="+val;
}

function checkAll(bool){
//	console.log(bool);
	var chkval = document.getElementsByName('chkVal'); // nodeList
	
	for (var i = 0; i < chkval.length; i++) {
		chkval[i].checked = bool;
		
	}
}

function chkbox(){
	var chkval = document.getElementsByName('chkVal');
	var n = 0;
	for (var i = 0; i < chkval.length; i++) {
		if(chkval[i].checked){
			n++;
		}
	}
	if(n>0){
		document.getElementById("frm").action = "./multiDel.do";
	}else{
		swal("삭제 오류","한개 이상의 삭제 값을 선택하세요");
		return false;
	}
		
}

// ---------- page 처리 ------------


// 이전페이지 < 눌렀을때
function pagePrev(num, pageList){
	if(0 < num-pageList){
		num -= pageList;
		
		var index = document.getElementById("index");
		var pageNum = document.getElementById("pageNum");
		
		pageNum.value = num;
		index.value = num-1;
	}
	pageAjax();
	
}

// 다음페이지 > 눌렀을때
function pageNext(num, total, listNum, pageList){
	var idx = Math.ceil(total/listNum); // 30/5 6
	var max = Math.ceil(idx/pageList); // 6/5 1
	
	while(max*pageList > num+pageList){
		num += pageList;
		
		var index = document.getElementById("index");
		var pageNum = document.getElementById("pageNum");
		
		index.value = num -1;
		pageNum.value= num;
		
	}
	
	pageAjax();	
	
}


// 마지막 페이지로 가는 >> 눌렀을때
// pageList("value.pageNum+","+value.total+","+value.listNum+","+value.pageList)
function pageLast(num, total, listNum, pageList){
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList;
	}
	
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	
	index.value = idx -1;
	pageNum.value= num;
	
	pageAjax();
}



// 처음페이지로 가는 << 눌렀을때
function pageFirst(){
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	index.value = 0;
	pageNum.value = 1;
	pageAjax();
}



// 페이지 보여주는 글의 줄 수를 변경할 때 select tag
function pageList(){
	console.log("작동");
	
	// 페이징에 관련된 값의 위치
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	var listNum = document.getElementById("listNum");
	
	// 줄 수(listNum)가 변경된다면 처음 페이지부터 동작
	index.value = 0;
	pageNum.value = 1;
	listNum.value = document.getElementById("list").value;
	
	console.log(index.value, pageNum.value, listNum.value);
	
	pageAjax();
}

// 해당 페이지 번호를 누를때
function pageIndex(pageNum){
	var index = document.getElementById("index");
	index.value = pageNum-1;
	console.log(index.value);
	pageAjax();
}

var pageAjax = function(){
	$.ajax({
		url : "./page.do",
		type : "post",
		async : true,
		data : $("#frm").serialize(), // .serialize() "index=값" 이런식으로 보내줌
		dataType : "json",
		success : function(msg){
			console.log(msg.lists[0].seq);
			
			$.each(msg,function(key,value){ // {"lists" : [] , "row" : rowdto}
				var varHtml = "";
				var n = $(".table tr:eq(0) th").length;
				
				// 글
				if(key == "lists"){
				varHtml += "<tr>";
				if(n == 7){
					
					varHtml += "	<th><input type='checkbox' onclick='checkAll(this.checked)'></th>";				
				}
				varHtml += "	<th>글번호</th>";
				varHtml += "	<th>제목</th>";
				varHtml += "	<th>작성자</th>";
				varHtml += "	<th>조회수</th>";
				
				if(n == 7){ // admin					
					varHtml += "		<th>";
					varHtml += "			삭제여부";
					varHtml += "		</th>";
				}
				
				varHtml += "	<th>작성일</th>";
				varHtml += "</tr>";
				
				// [{"seq":"1","title":제목}, {"seq":"2","title":제목2}, {"seq":"3","title":제목3}] => JSONArray 형태
				$.each(value, function(k,v){
					varHtml += "<tr>" ;
					
					if(n == 7){						
						varHtml += "<td><input type='checkbox' name='chkVal' value='"+v.seq+"'></td>" ;
					}
					
					varHtml += "<td>"+v.seq+"</td>" +
					"<td>" +
					"<div class='panel-heading'>" +
					" <a data-toggle='collapse' data-parent='#accordion' href='#collapse"+v.seq+"' onclick='collapse(\""+v.seq+"\")'>"+v.title+"	</a>" +
					"</div>" +
					"</td>" +
					"<td>"+v.id+"</td>" +
					"<td>"+v.readcount+"</td>" ;
					if(n==7){
						varHtml += "<td>N</td>";
						
					}
					varHtml += "<td>"+v.regdate+"</td> </tr>";
					
					// 상세글
					varHtml += "<tr>";  
					varHtml += "	<td colspan='"+n+"'>"; 
					varHtml += "		<div id='collapse"+v.seq+"' class='panel-collapse collapse'>";
					varHtml += "			<div class='form-group'>";       		
					varHtml += "				<label>내용</label>";
					varHtml += "				<br>";      	
					varHtml += "				<textarea rows='7' class='form-control' readonly=''>"+v.content+"</textarea>";
					varHtml += "			</div>";           		
					varHtml += "			<div class='form-group'>";
					if(v.id == v.meid){
						varHtml += "				<input type='button' class='btn btn-primary' value='글수정' onclick='modify(\""+v.seq+"\")'>";
						
					}
					if(v.id == v.meid || n==7){
						
						varHtml += "				<input type='button' class='btn btn-primary' value='글삭제' onclick='del(\""+v.seq+"\")'>";
					}
					if(n!=7){
						
						varHtml += "				<input type='button' class='btn btn-primary' value='글답글' onclick='reply(\""+v.seq+"\")'>";
					}
					varHtml += "			</div>";                                                                                             		
					varHtml += "		</div>";                                                                                                 	
					varHtml += "	</td>";	                                                                                                   
					varHtml += "</tr>";
				
				});
				
				
					
				}else{ // 페이지 key=="row"
					
					varHtml += "<li><a href='#' onclick='pageFirst()'>&laquo;</a></li>";
					varHtml += "<li><a href='#' onclick='pagePrev("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for(var i = value.pageNum; i<=value.count; i++){
						varHtml += "<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
					
					varHtml += "<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					varHtml += "<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}
				
				// 만들어진 HTML 교체
				if(key =="lists"){
					$(".table > tbody").html(varHtml);
				}else{
					
					$(".pagination").html(varHtml);
				}
				
			})
		},
		error : function(){
			alert("잘못된 요청처리");
		}
	});
}





