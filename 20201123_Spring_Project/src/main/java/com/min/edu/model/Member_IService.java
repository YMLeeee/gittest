package com.min.edu.model;

import java.util.List;
import java.util.Map;

import com.min.edu.dtos.Member_DTO;

public interface Member_IService {

	/**
	 * memberList : 전체회원 조회
	 */
	public List<Member_DTO> memList();
	
	/**
	 * signupMember : 회원가입
	 */
	public boolean signupMember(Member_DTO dto);
	
	/**
	 * idDuplicateCheck : 아이디 중복 체크 
	 */
	public boolean idDuplicateCheck(String id);
	
	/**
	 * loginMember : 로그인
	 */
	public Member_DTO loginMember(Map<String, Object> map);
}









