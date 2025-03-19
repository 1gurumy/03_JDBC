package edu.kh.jdbc.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDAO;
import edu.kh.jdbc.dto.User;
import oracle.sql.converter.JdbcCharacterConverters;

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

	/** 1. User 등록 서비스
	 * @param user : 입력받은 id, pw, name이 세팅된 객체
	 * @return 결과 행의 개수
	 */
	public int insertUser(User user) throws Exception {
							// 
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공 (할 게 없으면 넘어감)
		
		// 3. DAO 메서드를 호출 후 결과 반환받기
		int result = dao.insertUser(conn, user);
		 		// insertUser에서 발생한 예외를 
		
		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리
		if(result > 0) {	// INSERT 성공
			JDBCTemplate.commit(conn);
			
		} else {	// INSERT 실패
			JDBCTemplate.rollback(conn);
		}
		
		// 5. Connection 반환하기
		JDBCTemplate.close(conn);
		
		// 6. 결과를 view쪽으로 반환
		return result;
	}

	/** 2. User 전체 조회 서비스
	 * @return 조회된 User들이 담긴 List
	 */
	public List<User> selectAll() throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공( 할 거 없으니까 스킵)
		
		// 3. DAO 메서드 호출(SELECT) 후 결과 반환(List<User>) 받기
		List<User> userList = dao.selecAll(conn);
		
		// 4. DML 수행 했다면 트랜젝션 제어(DML안하고 SELECT만 했으니까 건너뜀)
		
		// 5. 사용한 JDBC 객체 자원 반환하기
		JDBCTemplate.close(conn);
		
		// 6. 결과 반환
		return userList;
	}

	/** 3. User 중 이름에 검색어가 표함된 회원을 조회하는 서비스
	 * @param keyword : view단에서 입력받은 키워드
	 * @return searchList : 조회된 회원 리스트
	 */
	public List<User> selectName(String keyword) throws Exception {
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공 ( 할 거 없어서 패스)
		
		// 3. DAO 메서드 호출 후 결과 반환 받기
		List<User> searchList = dao.selectName(conn, keyword);
		
		JDBCTemplate.close(conn);
		
		// 
		
		return searchList;
	}

	/** 4. USER_NO를 입력받아 일치하는 USER 조회 서비스
	 * @param input
	 * @return user (조회된 회원 정보 또는 null)
	 */
	public User selectUser(int input) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		User user = dao.selectUser(conn, input);
		
		JDBCTemplate.close(conn);
		
		return user;
		
	}

	/**	5. UWER_NO를 입력받아 일치하느 User를 삭제하는 서비스
	 * @param input
	 * @return result(삭제된 행의 개수)
	 */
	public int deleteUser(int input) throws Exception {
		
		// 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 데이터 가공 할거 없음
		
		// dao에서 수행한 SQL 결과값 반환
		int result = dao.deleteUser(conn, input);
		
		// 결과에 따른 트랜젝션 처리
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** ID와 PW가 일치하는 회원의 USER_NO를 조회하는 서비스
	 * @param userId
	 * @param userPw
	 * @return userNO
	 */
	public int selectUserNo(String userId, String userPw) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		 int userNo = dao.selectUser(conn, userId, userPw);
		
		JDBCTemplate.close(conn);
		 
		return userNo;
	}

	/** userNo가 일치하는 회원의 이름 수정 서비스
	 * @param userName
	 * @param userNo
	 * @return result(업데이트된 행의 개수)
	 */
	public int updateName(String userName, int userNo) throws Exception{

		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateName(conn, userName, userNo);
		
		if(result > 0) JDBCTemplate.commit(conn);
		else JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 아이디 중복을 확인하는 서비스
	 * @param userId
	 * @return count(중복이면 1, 아니면 0 반환)
	 */
	public int idCheck(String userId) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		JDBCTemplate.close(conn); //???????
	
		
		return count;
	}

	/** userList에 있는 모든 User 객체를 INSERT하는 서비스
	 * @param userList
	 * @return
	 */
	public int multiInsertUser(List<User> userList) throws Exception {
		
		// 다중 INSERT 하는 방법
		// 1) SQL을 이용한 다중 INSERT
		// 2) Java 반복문을 이용한 다중 INSERT (이거로 할 거임)
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = 0;	// 삽입 성공한 행의 개수를 누적할 변수
		
		// 반복문을 이용해 1행씩 삽입
		for(User user : userList) {
			int result = dao.insertUser(conn, user); // User 1명 등록하는 DAO 메서드 호출
			count += result;	// 삽입 성공한 행의 개수를 count에 누적
		}	
		
		// count--; -> test용. 
		// userList.size()와 count의 갯수가 다를 때 정말로 rollback이 수행되는지 test용
		
		// 트랜잭션 제어 처리
		// 전체 삽입이 성공 시에만 Commit / 아니면(일부만 삽입됬거나, 전체 실패한 경우) rollback
		
		if(count == userList.size()) {	// userList의 객체의 갯수와 삽입 성공한 행의 개수가 같다면
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return count;
	}
	
	
}
