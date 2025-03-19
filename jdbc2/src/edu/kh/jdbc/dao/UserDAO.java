package edu.kh.jdbc.dao;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 클래스명.메서드명() 이 아닌 메서드명()만 작성해도 호출 가능하게 함.
// 원래 해당 클래스에 있는 메서드를 호출하는 방식임. 그래서 가독성이 떨어지는 단점이 있음.
// 다른 클래스의 메서드를 호출하는 것임을 구분하려면
// 코드가 기울어져있는지 확인하면 됨. 
import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.dto.User;

// (Model 중 하나)DAO (Data Access Object)
// DB와 소통하는 객체(데이터가 저장된 곳에 접근하는 용도의 객체)
// -> !! DB에 접근하여 Java에서 원하는 결과를 얻기 위해
// 	  SQL을 수행하고 결과를 반환 받는 역할 !! 
public class UserDAO {

	// 필드
	// -DB 접근 관련한 JDBC 객체 참조 변수 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	// Connection은 Service에 선언할 예정. ??????
	
	// 메서드
	/** 전달받은 Connection을 이용해서 DB에 접근하여
	 * 전달받은 아이디(input)와 일치하는 User 정보를 DB에서 조회하기
	 * 
	 * @param conn : Service 에서 생성한 Connection 객체
	 * @param input : View 에서 입력받은 아이디
	 * @return 아이디가 일치하는 회원의 User 객체 또는 일치하는 아이디가 없다면 null
	 */
	public User selectId(Connection conn, String input) {
			
		// DAO 에서 과정
		// 1. 결과 저장용 변수 선언
		User user = null;
		
		try {
			
			// 2. SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? (위치홀더)에 알맞은 값 세팅
			pstmt.setString(1, input);
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있을 경우
			// + 중복되는 아이디가 없다고 가정
			// -> 1행만 조회되기 때문에 while문 보다는 if문을 사용하는게 효과적
			if(rs.next()) {
				// 첫 행에 데이터가 존재한다면
				
				// 각 컬럼의 값 얻어오기
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
			
				//java.sql.Date
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				// 조회된 컬럼값을 이용해서 User 객체 생성
				user = new User(userNo,
						userId,
						userPw, 
						userName, 
						enrollDate.toString());
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 사용한 JDBC 객체 자원 반환(close)하기
			close(rs);		//JDBCTemplate.close(rs);
			close(pstmt);   //JDBCTemplate.close(pstmt);
			
			// Connection 객체는 생성된 Service 에서 close!
			
		}
		
		return user;	// 결과 반환 (생성된 User 객체 또는 null)
	}

	/** 1. User 등록하는 DAO
	 * @param conn : DB 연결 정보가 담겨있는 Connection 객체
	 * @param user : view단에서부터 입력 받은 id,pw,name이 세팅된 User 객체
	 * @return INSERT한 결과 행의 개수
	 */
	public int insertUser(Connection conn, User user) throws Exception {
													// 아래 pstmt = conn.prepareStatement(sql); 에서 발생한 예외를 던지기
													// 이 메서드를 호출한 쪽으로 던짐.
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		// catch 안한 이유 : SQL 수행 중 발생하는 예외를 catch로 처리하지 않고,
		// throws를 이용해서 호출부로 던져 처리하려고.
		
		
		try {
			// 2. SQL 작성
			String sql = """
					INSERT INTO TB_USER 
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT )""";
			
			// 3. 위에서 위치홀더 사용했으므로 PreparedStatement 객체 생성하기
			pstmt = conn.prepareStatement(sql);
			// catch를 작성 안해서 컴파일 에러뜸. 
			
			// 4. ? (위치홀더) 알맞은 값 대입
			pstmt.setString(1, user.getUserId()); 
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdqte()) 후 결과(삽입된 행의 갯수->int) 반환 받기
			// 위에서 선언한 결과 저장용 변수 result에 담아주기
			result = pstmt.executeUpdate();
			
			
			
		} finally {
			// 6. 사용한 jdbc 객체 자원 반환(close)
			close(pstmt);	// JDBCEMplate 임포트 시 static, * 작성했으므로 클래스.메서드()가 아닌
			// 메서드() 형태로만 작성해도 알맞게 작동됨.
			
			
		}
		 
		// 결과 저장용 변수에 저장된 값을 해당 메서드 호출한 곳으로 반환
		return result;
	}

	/** 2. User 전체 조회 DAO
	 * @param conn
	 * @return userList
	 */
	public List<User> selecAll(Connection conn) throws Exception {
		
		// 1. 결과 저장용 변수 선언.	// 리스트 객체 생성(객체를 리스트에 추가해야 하므로)
		List <User> userList = new ArrayList<User>();
		
		try {
			// 2. SQL 작성하기
			String sql ="""
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? (위치홀더)에 알맞은 값 대입하기(없으므로 패스)
			
			// 5. SQL(SELECT) 수행(executeQuery) 후 결과 반환(ResultSet) 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기
			// 몇 행이 조회될지 모른다 -> while문
			// 무조건 1행만 조회된다 -> if문 
			
			while(rs.next()) { // 커서를 한칸씩(한 행씩) 내려가면서 값이 있으면 ture/ 없으면 false 반환
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// java.sql.Date 타입으로 값을 저장하지 않은 이유
				// -> SELECT 문에서 TO_CHAR()를 이용하여 문자열로 변환해 조회했기 때문에
				
				// 매개변수 생성자든 기본 생성자든 이용해서 User 객체를 새로 생성하여 위에서 구한 컬럼값 세팅하기
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				
				userList.add(user);	// 생성한 user 객체를 리스트에 추가
				
			}
			
			
			
		} finally {
			close(rs);
			close(pstmt);
			
			
			
		}
		
		
		return userList;	// 조회 결과가 담긴 List 반환
	}

	/** 3. 이름에 검색어가 포함되는 회원 모두 조회하는 DAO
	 * @param conn
	 * @return
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		List<User> searchList = new ArrayList<User>();
		
		
		try {
			// 2. SQL 작성하기
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO
					""";
				// || -> SQL문 문자열 이어쓰기
			
			// 3. Statement 또는 PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. PreparedStatement 생성했다면 위치홀더에 알맞은 값 대입
			pstmt.setString(1, keyword);
			
			// 5. SQL 수행 후 결과 반환 받기
			
			rs = pstmt.executeQuery();		
			// SELECT했으니까 executeQuery()
			
			
			// 6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기
			// 몇 행이 조회될지 모른다 -> while문
			// 무조건 1행만 조회된다 -> if문 
			while(rs.next()) {	// 
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				// 매개변수 생성자든 기본 생성자든 이용해서 User 객체를 새로 생성하여 위에서 구한 컬럼값 세팅하기
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				
				searchList.add(user);
				
			}
			
		} finally {
			// 7. 사용 완료한 JDBC 객체 자원 반환하기
			close(rs);;
			close(pstmt);
		}
		
		return searchList;
		
	}

	/** 4. USER_NO 를 입력받아 일치하는 User를 조회하는 DAO
	 * @param conn
	 * @param input
	 * @return user 객체 or null
	 */
	public User selectUser(Connection conn, int input) throws Exception {
		
		User user = null;
		
		try {
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				// 매개변수 생성자든 기본 생성자든 이용해서 User 객체를 새로 생성하여 위에서 구한 컬럼값 세팅하기
				user = new User(userNo, userId, userPw, userName, enrollDate);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		
		return user;
	}

	/** USER_NO를 입력 받아 일치하는 User 삭제하는 DAO
	 * @param conn
	 * @param input
	 * @return result
	 */
	public int deleteUser(Connection conn, int input) throws Exception {
		
		int result = 0;
		
		try {
			String sql = """
					DELETE FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			// DELETE(DML)이므로 executeupdate()
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
			
		}
		
		
		
		return result;
	}

	/** ID. PW가 일치하는 회원의 USER_NO를 조회하는 DAO
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return userNo
	 */
	public int selectUser(Connection conn, String userId, String userPw) throws Exception {
		
		// 결과 저장용 변수 선언
		int userNo = 0;
		
		try {
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ?
					AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			
			rs = pstmt.executeQuery();
			
			// 조회된 행은 1행만 나옴(USER_NO)가 고유키니까
			if(rs.next()) userNo = rs.getInt("USER_NO");
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo; // 조회 성공 시 USER_NO, 실패 시 0 반환
	}

	/** userNo가 일치하는 회원의 이름 수정 DAO
	 * @param conn
	 * @param userName
	 * @param userNo
	 * @return result(결과 행의 개수)
	 */
	public int updateName(Connection conn, String userName, int userNo) throws Exception {
		
		int result = 0;
		
		try {
			String sql = """
					UPDATE TB_USER
					SET USER_NAME = ?
					WHERE USER_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
			
		}
		
		
		
		return result;
	}

	/** 아이디 중복 확인해주는 DAO
	 * @param conn
	 * @param userId
	 * @return count
	 */
	public int idCheck(Connection conn, String userId) throws Exception {
		
		// 결과 저장용 변수
		
		int count = 0;
		
		try {
		String sql = """
				SELECT COUNT(*)
				FROM TB_USER
				WHERE USER_ID = ?
				""";
			
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
			
		rs = pstmt.executeQuery();	
		
		if(rs.next()) { // COUNT(*) -> 어파치 한 행나와서 if문
			 count = rs.getInt(1);  // Result set의 첫번째(순서) 컬럼의 컬럼값 얻어오기./ 별칭, 컬럼명을 입력해도 ok
		}
		
		} finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		
		return count;
	}
	
}
