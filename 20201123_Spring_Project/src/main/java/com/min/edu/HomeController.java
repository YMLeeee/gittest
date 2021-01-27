package com.min.edu;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.Member_DTO;
import com.min.edu.model.Member_IService;

/**
 * Handles requests for the application home page.
 */
//@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private Member_IService iService;
	
	@RequestMapping(value = "/memberList.do" , method = RequestMethod.GET)
	public String memberList() {
	 List<Member_DTO> lists= iService.memList();
	 for(Member_DTO dto : lists) {
		 System.out.println(dto);
	 }
	return null;	
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(@RequestParam Map<String,Object> map) {
		System.out.println(map.get("id"));
		System.out.println(map.get("pw"));
		Member_DTO mdto = iService.loginMember(map);
		System.out.println(mdto);
		return null;
	}
	
	//"^[a-zA-Z0-9]*$" // 첫글자가 a-z A-Z 0~9 까지 여러번 반복
	@RequestMapping(value = "/idCheck.do", method=RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String idCheck(String id) {
		String regex = "^[a-zA-Z0-9]*$";
		boolean isc = id.matches(regex);
		boolean isc2  = iService.idDuplicateCheck(id);
		System.out.println(isc2);
		return isc2 ? "중복된 아이디":"사용 가능한 아이디 입니다.";
	}
	
	@RequestMapping(value = "/signUp.do", method = RequestMethod.POST)
	public String signUp(Member_DTO dto) {
		boolean isc = iService.signupMember(dto);	
		System.out.println(isc);
	return null;
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
}
