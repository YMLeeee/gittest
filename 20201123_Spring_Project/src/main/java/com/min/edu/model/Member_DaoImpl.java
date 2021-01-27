package com.min.edu.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.min.edu.dtos.Member_DTO;

@Repository
public class Member_DaoImpl implements Member_IDao {


	private final String NS = "com.min.edu.model.Member_IDao.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Member_DTO> memList() {
		return sqlSession.selectList(NS+"memberList");
	}

	@Override
	public boolean signupMember(Member_DTO dto) {
		
		// 12345678 -> 암호화 시키면?
		// $2a$10$Zvs50YKDT1YnIJ.R29K9celPevAoeWQKdTjI90tV7NFV7oIcO.c7y
		// 같은 값으로 한번 더 했을때 $2a$10$kHwc37lxz5fCs8uPt2Ye.ewnrwdQfRNtIcbuDDs2t0RkB0PTnwTlO
		// 값을 넣을때마다 값이 다름
		String enPassword = passwordEncoder.encode(dto.getPw());
		System.out.printf("============= %s ============= \n", enPassword);
		dto.setPw(enPassword); // pw가 암호화되서 변경됨
		
		int cnt =  sqlSession.insert(NS+"signupMember", dto);
		return (cnt>0)?true:false;
//		return true;
	}

	@Override
	public boolean idDuplicateCheck(String id) {
		int cnt = sqlSession.selectOne(NS+"idDuplicateCheck", id);
		return  (cnt>0)?true:false;
	}

	@Override
	public Member_DTO loginMember(Map<String, Object> map) {
//		System.out.println("로그인 실행 중");
//		return sqlSession.selectOne(NS+"loginMember", map);
		
		System.out.println("암호화 로그인 실행 중");
		Member_DTO mDto = null;
		System.out.printf("화면에서 전달받은 요청 값 : ============= %s ============= \n",map.get("pw") );
		System.out.printf("전달받은 값 암호화 : ============= %s ============= \n", passwordEncoder.encode((String)map.get("pw")));
		
		
		String dbPw = sqlSession.selectOne(NS+"selStringPw", map.get("id"));
		System.out.printf("DB에 입력되어 있는 값 : ============= %s ============= \n",dbPw );
		
		// 전달 받은 값을 Spring Security로 암호화하면 항상 다른 값으로 암호화 됨
		// 따라서 DB입력된 값과 비교하기 위해서 PasswordEncoder 클래스의 match를 사용해서 비교판단 해야함
		if(passwordEncoder.matches((String)map.get("pw"), dbPw)) {
			System.out.println("======== 비밀번호 일치 =========");
			mDto = sqlSession.selectOne(NS+"enLogin", map);
		}
		
		return mDto;
		
	}

}




