package com.min.edu.model;

import java.util.List;
import java.util.Map;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.RowNum_DTO;

public interface Answerboard_IService {

	/**
	 * 글입력
	 */
	public boolean writeBoard(Answerboard_DTO dto);
	
	/**
	 * 답글 입력(Dao에서 update와 insert를 조합해주는 메소드)
	 */

	public boolean reply(Answerboard_DTO dto);
	
	/**
	 * 상세글 조회
	 */
	public Answerboard_DTO getOneBoard(String seq);
	
	/**
	 * 조회수 증가
	 */
	public void readCountBoard(String seq);
	
	/**
	 * 글 수정
	 */
	public boolean modifyBoard(Answerboard_DTO dto);
	
	/**
	 * 글 삭제(DelFlag 변경)
	 */
	
	public boolean delflagBoard(Map<String, String[]> map);
	
	public List<Answerboard_DTO> deleteBoardSel(String seq);
	
	/**
	 *  DB삭제
	 */
	public boolean deleteBoard(List<String> seqs);
	
	/**
	 * 글 조회(전체-사용자)
	 */
	public List<Answerboard_DTO> userBoardList();
	
	/**
	 * 글 조회(전체-페이징-사용자)
	 */
	public List<Answerboard_DTO> userBoardListRow(RowNum_DTO row);

	/**
	 * 글 조회(전체-관리자)
	 */
	public List<Answerboard_DTO> adminBoardList();
	
	/**
	 * 글 조회(전체-페이징-관리자)
	 */
	public List<Answerboard_DTO> adminBoardListRow(RowNum_DTO row);
	
	/**
	 * 글 갯수(전체-사용자)
	 */
	public int userBoardListTotal();
	
	/**
	 * 글 갯수(전체-관리자)
	 */
	public int adminBoardListTotal();
	
	public boolean modifyBoard2(Answerboard_DTO dto);
}
