package com.min.edu.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.RowNum_DTO;

@Service
public class Answerboard_ServiceImpl implements Answerboard_IService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Answerboard_IDao iDao;
	
	@Override
	public boolean writeBoard(Answerboard_DTO dto) {
		logger.info("글입력 wirteBoard {}",dto);
		return iDao.writeBoard(dto);
	}

	@Transactional
	@Override
	public boolean reply(Answerboard_DTO dto) {
		logger.info("답글입력 reply(){}",dto);
		boolean isc1 = iDao.replyBoardUp(dto);
		boolean isc2 = iDao.replyBoardIn(dto);
		return (isc1||isc2)?true:false;
	}

	@Override
	public Answerboard_DTO getOneBoard(String seq) {
		logger.info("글 상세조회 getOneBoard(){} ", seq);
		return iDao.getOneBoard(seq);
	}

	@Override
	public void readCountBoard(String seq) {
		logger.info("조회수 증가 readCountBoard() {}",seq);
		iDao.readCountBoard(seq);

	}

	@Override
	public boolean modifyBoard(Answerboard_DTO dto) {
		logger.info("");
		return iDao.modifyBoard(dto);
	}

	@Override
	public boolean delflagBoard(Map<String, String[]> map) {
		logger.info("조회수 증가 readCountBoard() {}",map);
		return iDao.delflagBoard(map);
	}

	@Override
	public List<Answerboard_DTO> deleteBoardSel(String seq) {
		logger.info(" deleteBoardSel(){}",seq);
		return iDao.deleteBoardSel(seq);
	}
	
	@Override
	public boolean deleteBoard(List<String> seqs) {
		logger.info("글 삭제 deleteBoard() {}",seqs);
		return iDao.deleteBoard(seqs);
	}

	@Override
	public List<Answerboard_DTO> userBoardList() {
		logger.info("글 조회(전체-사용자)  userBoardList ");
		return iDao.userBoardList();
	}

	@Override
	public List<Answerboard_DTO> userBoardListRow(RowNum_DTO row) {
		logger.info("글 조회(전체-페이지-사용자) userBoardListRow");
		return iDao.userBoardListRow(row);
	}

	@Override
	public List<Answerboard_DTO> adminBoardList() {
		logger.info("글 조회(전체-관리자)  adminBoardList ");
		return iDao.adminBoardList();
	}

	@Override
	public List<Answerboard_DTO> adminBoardListRow(RowNum_DTO row) {
		logger.info("글 조회(전체-페이지-관리자) adminBoardListRow");
		return iDao.adminBoardListRow(row);
	}

	@Override
	public int userBoardListTotal() {
		logger.info("글갯수(전체-사용자) userBoardListTotal");
		return iDao.userBoardListTotal();
	}

	@Override
	public int adminBoardListTotal() {
		logger.info("글갯수(전체-관리자) adminBoardListTotal");
		return iDao.adminBoardListTotal();
	}

	@Override
	public boolean modifyBoard2(Answerboard_DTO dto) {
		logger.info("글수정 id auth 확인 modifyBoard2{}", dto);
		return iDao.modifyBoard2(dto);
	}
}
