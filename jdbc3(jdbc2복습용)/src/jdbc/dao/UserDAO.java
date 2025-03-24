package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import jdbc.dto.User;
import jdbc.template.JDBCTemplate;

public class UserDAO {

	User user = new User();

	public int UserService(User user, Connection conn) {
		// TODO Auto-generated method stub
		
		int result = 0;
		
		String sql = """
				INSERT INTO TB_USER 
				VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
				""";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "userId");
			pstmt.setString(2, "userPw");
			pstmt.setString(3, "userName");
			
		} catch (Exception e) {
			System.out.println("회원가입 진행 중 예외 발생..");
			e.printStackTrace();
		
		}
		
		return 0;
	}
	
}
