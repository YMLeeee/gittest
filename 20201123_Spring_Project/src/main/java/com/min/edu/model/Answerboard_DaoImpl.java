package com.min.edu.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.edu.dtos.Answerboard_DTO;
import com.min.edu.dtos.RowNum_DTO;

@Repository
public class Answerboard_DaoImpl implements Answerboard_IDao {

	private final String NS = "com.min.edu.model.Answerboard_IDao.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public boolean writeBoard(Answerboard_DTO dto) {
		int cnt = sqlSession.insert(NS+"writeBoard",dto);
		return cnt>0?true:false;
	}

	@Override
	public boolean replyBoardUp(Answerboard_DTO dto) {
		int cnt = sqlSession.update(NS+"replyBoardUp", dto);
		return cnt>0?true:false;
	}
	
	@Override
	public boolean replyBoardIn(Answerboard_DTO dto) {
		int cnt = sqlSession.update(NS+"replyBoardIn", dto);
		return cnt>0?true:false;
	}


	@Override
	public Answerboard_DTO getOneBoard(String seq) {
		return sqlSession.selectOne(NS+"getOneBoard", seq);
	}

	@Override
	public void readCountBoard(String seq) {

		sqlSession.update(NS+"readCountBoard", seq);
	}

	@Override
	public boolean modifyBoard(Answerboard_DTO dto) {
		int cnt = sqlSession.update(NS+"modifyBoard", dto);
		return cnt>0?true:false;
	}

	@Override
	public boolean delflagBoard(Map<String, String[]> map) {
		int cnt = sqlSession.update(NS+"delflagBoard", map);
		return cnt>0?true:false;
	}
	
	

	@Override
	   public List<Answerboard_DTO> deleteBoardSel(String seq) {
	      return sqlSession.selectList(NS+"deleteBoardSel", seq);
	   }
	
	@Override
	public boolean deleteBoard(List<String> seqs) {
		int cnt = sqlSession.delete(NS+"deleteBoard",seqs);
		return cnt>0?true:false;
	}

	@Override
	public List<Answerboard_DTO> userBoardList() {
		
		return sqlSession.selectList(NS+"userBoardList");
	}

	@Override
	public List<Answerboard_DTO> userBoardListRow(RowNum_DTO row) {
		return sqlSession.selectList(NS+"userBoardListRow",row);
	}

	@Override
	public List<Answerboard_DTO> adminBoardList() {
		
		return sqlSession.selectList(NS+"adminBoardList");
	}

	@Override
	public List<Answerboard_DTO> adminBoardListRow(RowNum_DTO row) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NS+"adminBoardListRow",row);
	}

	@Override
	public int userBoardListTotal() {
		return sqlSession.selectOne(NS+"userBoardListTotal");
	}

	@Override
	public int adminBoardListTotal() {
		
		return sqlSession.selectOne(NS+"adminBoardListTotal");
	}

	@Override
	public boolean modifyBoard2(Answerboard_DTO dto) {
		int cnt = sqlSession.update(NS+"modifyBoard2", dto);
		return cnt>0?true:false;
	}
}
