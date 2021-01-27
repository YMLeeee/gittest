package com.min.edu;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.Member_DTO;
import com.min.edu.dtos.RowNum_DTO;
import com.min.edu.model.Answerboard_IService;
import com.min.edu.model.Member_IService;

@Controller
public class TogetherController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Member_IService iService;
	@Autowired
	private Answerboard_IService aiSerivce;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@RequestParam Map<String, Object> map, HttpSession session) {
		Member_DTO mDto = iService.loginMember(map);
		session.setAttribute("mem", mDto);
		return "redirect:/boardList.do";
	}
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		Member_DTO mDto = (Member_DTO)session.getAttribute("mem");
		if(mDto != null) {
			session.removeAttribute("mem");
		}
		return "redirect:/loginform.do";
	}
	

	@RequestMapping(value = "/loginCheckText.do", method = RequestMethod.POST,
			produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String loginCheckText(String id, String pw) {
		return "성공";
	}
	
	@RequestMapping(value = "/loginCheckMap.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginCheckMap(String id, String pw){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> iMap = new HashMap<String, Object>();
		iMap.put("id", id);
		iMap.put("pw", pw);
		Member_DTO mDto = iService.loginMember(iMap);
		System.out.println("로그인 된 값 : \t "+ mDto);
		if(mDto == null) {
			map.put("isc", "실패");
		}else {
			map.put("isc", "성공");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/encoding.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		// 화면에서 request처리시 한글이 깨짐 처리
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "loginform";
	}
	
	@RequestMapping(value = "/loginform.do", method = RequestMethod.GET)
	public String loginform() {
		return "loginform";
	}
	
	@RequestMapping(value = "/signUpform.do", method = RequestMethod.GET)
	public String signUpform() {
		logger.info("welcome signUpform.do : \t {}", new Date());
		return "signUpform";
	}

	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> idCheck(String id){
		Map<String, String> map = new HashMap<String, String>();
		logger.info("welcome idCheck.do : \t {}", id);
		boolean isc = iService.idDuplicateCheck(id);
		logger.info("welcome idCheck.do 결과 : \t {}", id);
		map.put("isc", String.valueOf(isc));
		
		return map;
	}
	
	@RequestMapping(value = "/signUp.do", method = RequestMethod.POST)
	public String signUp(Member_DTO dto, @RequestParam("password") String pw) {
		dto.setPw(pw);
		logger.info("welcome signUp.do  : \t {}", dto);
		boolean isc = iService.signupMember(dto);
		logger.info("welcome signUp.do 결과 : \t {}", dto);
		return isc? "redirect:/loginform.do":"redeirect:/signUpform.do";
	}
	
	@RequestMapping(value = "/memberListMAV.do" , method = RequestMethod.GET)
	public ModelAndView  memberListMAV() {
		logger.info("welcome memberListMAV.do 결과 : \t {}", new Date());
		// Model(전달 값) + view(화면이동) 같은 객체에 담아서 사용할 수 있다.
		ModelAndView mav = new ModelAndView();
		mav.setViewName("memberList");
		List<Member_DTO> mList = iService.memList();
		mav.addObject("mList", mList);
		
		return mav;
	}
	
	
	
	// 게시판 관련 -----------------------------------------
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(Model model, HttpSession session) {
		
		// 결과 값을 담음
		List<Answerboard_DTO> lists = null;
		
		// 페이지 값을 담음
		RowNum_DTO rowDto = null;
		
		// 로그인된 정보를 확인한다.
		// U 혹은 A 권한에 따라서 볼 수 있는 정보가 다름
		Member_DTO mem = (Member_DTO)session.getAttribute("mem");
		logger.info("welcome boardList.do  : \t {}", mem);
		
		if(session.getAttribute("row") == null) {
			rowDto = new RowNum_DTO();
		}else {
			rowDto = (RowNum_DTO)session.getAttribute("row");
		}
		
		// RowDto에 해당하는 page의 글을 가져옴
		if(mem.getAuth().equalsIgnoreCase("U")) {
			rowDto.setTotal(aiSerivce.userBoardListTotal());
			lists = aiSerivce.userBoardListRow(rowDto);
//			lists = aiSerivce.userBoardList();
		}else if(mem.getAuth().equalsIgnoreCase("A")) {
			rowDto.setTotal(aiSerivce.adminBoardListTotal());
			lists = aiSerivce.adminBoardListRow(rowDto);
//			lists = aiSerivce.adminBoardList();
			
		}
		model.addAttribute("row", rowDto);
		model.addAttribute("lists",lists);
		
		return "boardList";
//		return "boardTopMenu";
	}
	
	@RequestMapping(value = "/boardWriteForm.do", method = RequestMethod.GET)
	public String boardWriteForm() {
		logger.info("welcome boardWriteForm.do  : \t {}", new Date());
		
		return "boardWriteForm";
	}
	
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String write(Answerboard_DTO dto, HttpSession session) {
		Member_DTO mDto = (Member_DTO)session.getAttribute("mem");
		dto.setId(mDto.getId());
		logger.info("welcome write.do  : \t {}", dto);
		boolean isc = aiSerivce.writeBoard(dto);
		
		return isc?"redirect:/boardList.do":"redirect:/boardWriteForm.do";
	}
	
	// produces를 걸지 않으면 json형태의 text가 전송되지 않음
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyForm.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8;")
	@ResponseBody
	public String modifyForm(String seq) {
		Answerboard_DTO dto = aiSerivce.getOneBoard(seq);
		JSONObject json = new JSONObject();
		json.put("seq", dto.getSeq());
		json.put("id", dto.getId());
		json.put("title", dto.getTitle());
		json.put("content", dto.getContent());
		json.put("regdate", dto.getRegdate());
		
//		json.put("seq", "값");
		System.out.println(json.toString());
//		return json.toString();
//		return "{\"seq\":\"1\"}";
		return json.toString();
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.POST)
	public String modify(Answerboard_DTO dto) {
		logger.info("welcome modifyForm.do : \t {}",dto);
		boolean isc = aiSerivce.modifyBoard(dto);
		
		return isc?"redirect:/boardList.do":"redirect:/logout.do";
	}
	
	@RequestMapping(value = "/replyForm.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> replyForm(String seq, HttpSession session){
		logger.info("welcome replyForm.do : \t {}",seq);
		
		// 로그인된 사용자 정보
		Member_DTO mDto = (Member_DTO)session.getAttribute("mem");
		// 원본글의 정보
		Answerboard_DTO aDto = aiSerivce.getOneBoard(seq); // 원본글
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", aDto);
		map.put("sessionId", mDto.getId());
		
		logger.info("welcome replyForm.do 결과 : \t {}",map.get("obj"));
		logger.info("welcome replyForm.do 결과 : \t {}",map.get("sessionId"));
		return map;
	}
	
	
	@RequestMapping(value = "/reply.do" , method = RequestMethod.POST)
	public String reply(Answerboard_DTO dto, HttpSession session) {
		dto.setId(((Member_DTO)session.getAttribute("mem")).getId());
		logger.info("welcome reply.do : \t {}",dto);
		boolean isc = aiSerivce.reply(dto);
			
		return isc?"redirect:/boardList.do":"redirect:/logout.do";
	}
	
	@RequestMapping(value = "/del.do", method = RequestMethod.GET)
	public String del(String[] seq) {
		logger.info("welcome del.do : \t {}",Arrays.toString(seq));
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", seq);
		boolean isc = aiSerivce.delflagBoard(map);
		return isc?"redirect:/boardList.do":"redirect:/logout.do";
	}
	
	@RequestMapping(value = "/multiDel.do", method = RequestMethod.POST)
	public String multiDel(@RequestParam ArrayList<String> chkVal) {
		logger.info("welcome multiDel.do : \t {}",chkVal);
		
		boolean isc = aiSerivce.deleteBoard(chkVal);
		
		return isc?"redirect:/boardList.do":"redirect:/logout.do";
	}
	
	////////// ---------- page 처리 ---------- ///////////////////
	@RequestMapping(value = "/page.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8;")
	@ResponseBody
	public String paging(HttpSession session , RowNum_DTO rowDto) {
		logger.info("welcome page.do : \t row 세션 확인 {}",session.getAttribute("row"));
		logger.info("welcome page.do : \t row 전달받은 화면값 {}",rowDto);
		
		// 로그인 된 세션값
		Member_DTO mDto = (Member_DTO)session.getAttribute("mem");
		logger.info("welcome page.do : \t row 로그인 세션 {}",mDto);
		JSONObject json = null;
		
		if(mDto.getAuth().equalsIgnoreCase("U")) { // 사용자
			rowDto.setTotal(aiSerivce.userBoardListTotal());
			json = objectJSON(aiSerivce.userBoardListRow(rowDto),rowDto,mDto);
			
		}else { // 관리자
			rowDto.setTotal(aiSerivce.adminBoardListTotal());
			 json = objectJSON(aiSerivce.adminBoardListRow(rowDto),rowDto,mDto);
			
		}
		
		session.removeAttribute("row"); // session을 지운다음에 다시 담을 거
		session.setAttribute("row", rowDto);
//		json.put("test", "test");
		return json.toString();
//		return "{\"test\":\"happy\"}"; // 이런식으로 해도 넘어감
	}
	
	// 받아온 글의 리스트를 json형태로 변환해주는  메소드
	// { {"page":pageDto} , {"글",[{},{}]} } 페이지관련, 글 리스트로 받을때 글개수는 여러개니까
	
	@SuppressWarnings("unchecked")
	private JSONObject objectJSON(List<Answerboard_DTO> lists, RowNum_DTO row, Member_DTO mem) {
		JSONObject json = new JSONObject(); // 전체 껍데기 json {}
		JSONArray jlists = new JSONArray(); // []
		JSONObject jdto = null; // [{},{}] array안에 쓰이는 json {}

		// 글을 List에서 가져와서 JSONObject 만들고 JSONArray에 넣어줌
		// DTO -> list.add(DTO)
		for (Answerboard_DTO dto : lists) {
			jdto = new JSONObject();
			jdto.put("seq", dto.getSeq());
			jdto.put("id", dto.getId());
			jdto.put("title", dto.getTitle());
			jdto.put("content", dto.getContent());
			jdto.put("readcount", dto.getReadcount());
			jdto.put("regdate", dto.getRegdate());
			jdto.put("meid", mem.getId());
			jlists.add(jdto);
		}
		logger.info("JSON 넘겨주는 객체  글 \n {}",jlists.toString());
		
		// 페이징 관련 DTO
		jdto = new JSONObject();
		jdto.put("pageList", row.getPageList());
		jdto.put("index", row.getIndex());
		jdto.put("pageNum", row.getPageNum());
		jdto.put("listNum", row.getListNum());
		jdto.put("total", row.getTotal());
		jdto.put("count", row.getCount());
				
		logger.info("JSON 넘겨주는 객체 페이지 \n {}",jdto.toString());
		
		json.put("lists", jlists);
		json.put("row", jdto);
		
		return json;
	}
	
}








