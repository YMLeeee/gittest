package com.min.edu.bean;

import java.util.List;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.Member_DTO;

public class InputList {

	private List<Answerboard_DTO> lists;
	private Member_DTO mem;
	
	public void setLists(List<Answerboard_DTO> lists) {
		this.lists = lists;
	}
	public void setMem(Member_DTO mem) {
		this.mem = mem;
	}
	
	// 전체 리스트
	public String getListForm() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lists.size(); i++) {
			sb.append(listForm(lists.get(i)));
		}
		return sb.toString();
	}
		
	
	private String titleFormat(String d) {
		StringBuffer sb = new StringBuffer();
		int depth = Integer.valueOf(d);
		for (int i = 0; i < depth; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		if(depth != 0) {
			sb.append("<img src='./images/reply.png'>");
		}
		
		return sb.toString();
	}
	
	private String dateFormat(String date) {
		return date.substring(0, date.indexOf(" "));
	}
	
	// 리스트 출력 폼
	public String listForm(Answerboard_DTO dto) {
		StringBuffer sb = new StringBuffer();
		
		int n = 5;
		
		sb.append("	<tr>");
		if(mem.getAuth().equalsIgnoreCase("A")) {
			sb.append("	<td><input type='checkbox' name='chkVal' value='"+dto.getSeq()+"'></td>");
		}		
		sb.append("	<td>"+dto.getSeq()+"</td>");
		sb.append("	<td>                                                                                                       ");
		sb.append("		<div class='panel-heading'>                                                                            ");
		sb.append("			<a data-toggle='collapse' data-parent='#accordion' href='#collapse"+dto.getSeq()+"' onclick='collapse(\""+dto.getSeq()+"\")'>");
		sb.append(				titleFormat(dto.getDepth()) + dto.getTitle());
		sb.append("			</a>                                                                                               ");
		sb.append("		</div>                                                                                                 ");
		sb.append("	</td>                                                                                                      ");
		sb.append("	<td>                                                                                                       ");
		sb.append(dto.getId());
		sb.append("	</td>                                                                                                      ");
		sb.append("	<td>                                                                                                       ");
		sb.append(dto.getReadcount());
		sb.append("	</td>                                                                                                      ");
		if(mem.getAuth().equalsIgnoreCase("A")) {
			sb.append("<td>"+dto.getDelflag()+"</td>");
			n=7;
		}
		sb.append("	<td>                                                                                                       ");
		sb.append(dateFormat(dto.getRegdate()));
		sb.append("	</td>                                                                                                      ");
		sb.append("</tr>                                                                                                       ");
		sb.append("<tr>                                                                                                        ");
		sb.append("	<td colspan='"+n+"'>                                                                                           ");
		sb.append("		<div id='collapse"+dto.getSeq()+"' class='panel-collapse collapse'>                                                ");
		sb.append("			<div class='form-group'>                                                                           ");
		sb.append("				<label>내용</label><br>                                                                        ");
		sb.append("				<textarea rows='7' class='form-control' readonly>"+dto.getContent()+"</textarea>                        ");
		sb.append("			</div>                                                                                             ");
		sb.append("			<div class='form-group'>                                                                           ");
		
		if(mem.getId().equalsIgnoreCase(dto.getId())) {
			sb.append("<input type='button' class='btn btn-primary' value='글수정' onclick='modify(\""+dto.getSeq()+"\")'>");
		}
		if(mem.getId().equalsIgnoreCase(dto.getId()) || mem.getAuth().equalsIgnoreCase("A")) {
			sb.append("<input type='button' class='btn btn-primary' value='글삭제' onclick='del(\""+dto.getSeq()+"\")'>");
			
		}
		if(mem.getAuth().equalsIgnoreCase("U")) {
			sb.append("<input type='button' class='btn btn-primary' value='글답글' onclick='reply(\""+dto.getSeq()+"\")'>");
			
		}
		
		sb.append("			</div>                                                                                             ");
		sb.append("		</div>                                                                                                 ");
		sb.append("	</td>	                                                                                                   ");
		sb.append("</tr>                                                                                                       ");
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
}
