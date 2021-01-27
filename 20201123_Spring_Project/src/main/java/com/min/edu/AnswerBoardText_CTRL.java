package com.min.edu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.Member_DTO;
import com.min.edu.dtos.RowNum_DTO;
import com.min.edu.model.Answerboard_IService;

//@Controller
public class AnswerBoardText_CTRL {

	@Autowired
	private Answerboard_IService iService;
	
	@RequestMapping(value = "/writeBoard.do", method=RequestMethod.GET)
	public String writeBoard(Answerboard_DTO dto) {
		System.out.println("처음받은 DTO : \t"+dto);
		boolean isc = iService.writeBoard(dto);
		System.out.println("실행 후 DTO : \t"+dto);
		return "redirect:/getOneBoard.do?seq="+dto.getSeq();
	}
	
	@RequestMapping(value ="/reply.do", method = RequestMethod.GET )
	public String reply(Answerboard_DTO dto) {
		System.out.println("부모의 seq : \t"+dto.getSeq());
		System.out.println("답글의 입력값 : \t{}"+dto.getTitle());
		boolean isc = iService.reply(dto);
		System.out.println(isc);
		return null;
	}
	
	@RequestMapping(value = "/getOneBoard.do" , method=RequestMethod.GET)
	public String getOneBoard(String seq , Model model) {
		System.out.println("상세글 seq : \t"+seq);
		Answerboard_DTO dto = iService.getOneBoard(seq);
		System.out.println("상세글 결과 값 : \t {}"+dto);
		model.addAttribute("detail",dto);
		return "test";
	}
	
	@RequestMapping(value = "/readCountBoard.do", method= RequestMethod.GET)
	public void readCountBoard(String seq) {
		iService.readCountBoard(seq);
	}
	
	@RequestMapping(value = "/modifyBoard.do" , method = RequestMethod.GET)
	public String modifyBoard(Answerboard_DTO dto, HttpServletResponse response) throws IOException {
		System.out.println("입력받은 값"+dto);
		boolean isc = iService.modifyBoard(dto);
		if(isc) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('처리가 완료되어 있습니다.'); location.href='./getOneBoard.do?seq="+dto.getSeq()+"';</script>");
			out.flush();
			return null;
		}else{
			return null;			
		}
	}
	
	@RequestMapping(value = "/modifyBoard2.do", method = RequestMethod.GET)
	public String modifyBoard2(Answerboard_DTO dto, HttpSession session) {
		Member_DTO mDto = (Member_DTO)session.getAttribute("mem");
		dto.setRefer(mDto.getId()); // 세션 아이디 입력
		dto.setStep(mDto.getAuth()); // 세션 권한 입력
		dto.setId(iService.getOneBoard(dto.getSeq()).getId()); // 글의 id를 입력
		System.out.println(dto);
		boolean isc = iService.modifyBoard2(dto);
		System.out.println(isc);
		return null;
	}
	
	
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	@ResponseBody
	public String Test(HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('처리가 완료되어 있습니다.'); location.href='./'</script>");
		out.flush();
		
		return null;
	}
	
	// ArrayList의 경우 []만 나오는데 requestParam은 객체를 mapping하기 때문에  ArrayList값 가져올수 있음
	@RequestMapping(value = "/delflagBoard.do", method = RequestMethod.GET)
	public String delflagBoard(String[] seq) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", seq);
		boolean isc = iService.delflagBoard(map);
		System.out.println(isc);
		// 변경된 row의 갯수만 표시됨
		
		// map의 경우 키(seq)가 중복이 되면 처음 값만 가져오게됨
//		parameter @RequestParam Map<String,String> seq
//		System.out.println(seq.get("seq"));
//		System.out.println(seq.size());
		
		// ArrayList로 값을 바인딩 할때 반드시 @RequestParam을 사용해야 한다.
//		System.out.println(Arrays.toString(seq));
//		boolean isc = iService.delflagBoard(seq);
		
		return null;
	}
	
	@RequestMapping(value = "/deleteBoardSel.do", method=RequestMethod.GET)
	public String deleteBoardSel(String seq) {
		List<Answerboard_DTO> lists = iService.deleteBoardSel(seq);
		List<String> seqList = new ArrayList<String>();
		for (Answerboard_DTO d : lists) {
			System.out.println(d);
			seqList.add(d.getSeq());
		}
		boolean isc = iService.deleteBoard(seqList);
		System.out.println("진짜 삭제 : " +isc);
		System.out.println("삭제될 seqs : "+ seqList);
		return null;
	}
	
	@RequestMapping(value = "/userBoardListTotal.do", method = RequestMethod.GET)
	public String userBoardListTotal(Model model) {
		int total = iService.userBoardListTotal();
		model.addAttribute("uTotal",total);
		return "test";
	}
	
	@RequestMapping(value = "/adminBoardListTotal.do", method = RequestMethod.GET)
	public String adminBoardListTotal(Model model) {
		int total = iService.adminBoardListTotal();
		model.addAttribute("uTotal",total);
		return "test";
	}
	
	@RequestMapping(value = "/userBoardListRow.do", method = RequestMethod.GET)
	public String userBoardListRow(Model model) {
		RowNum_DTO rowDto = null;
		List<Answerboard_DTO> lists = null;
		
		// 세션에 담게 되면 계속 값이 유지가 되기 때문에 new를 통해 초기화
		rowDto = new RowNum_DTO();
		
		rowDto.setTotal(iService.userBoardListTotal());
		System.out.println(rowDto);
		rowDto.setIndex(6);
		rowDto.setPageNum(6);
		System.out.println(rowDto.getStart());
		System.out.println(rowDto.getLast());
		
		System.out.println(rowDto.getCount());
		
		return "test";
	}
	
	
	
}
