package edu.kh.jdbc.service;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;

// (Model 중 하나)Service : 비즈니스 로직을 처리하는 계층,
// 데이터를 가공하고 트랜잭션(commit, rollback) 관리 수행
public class UserService {
	
	// 필드
	private UserDAO dao = new UserDAO();

	// 메서드
	/** 전달받은 아이디와 일치하는 User 정보 반환하는 서비스
	 * @param input (View 에서 입력된 아이디)
	 * @return 아이디가 일치하는 회원 정보가 담긴 User 객체
	 * 			없으면 null 반환
	 */
	public User selectId(String input) {
		
		// 서비스에서 메서드 만들 때 과정
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection(); // JDBCTemplate의 static 메서드 getConnection() 호출하여 
		
		// 2. 데이터 가공(할게 없으면 2번 과정 건너뛰어도 됨)
		// 3. DAO 메서드 호출 결과 반환
		User user = dao.selectId(conn, input);
		
		
		// 4. DML(commit/rollback) DML이 아니라 SELECT였다면 4번 과정 건너뛰어도 됨
		
		
		// 5. 다 쓴 커넥션 자원 반환
		JDBCTemplate.close(conn);
		
		
		// 6. 결과를 view 에게 리턴
		return user;
		
	}
	
	
}
