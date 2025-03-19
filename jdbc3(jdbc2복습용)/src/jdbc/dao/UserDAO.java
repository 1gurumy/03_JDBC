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
		
		PreparedStatement pstmt = JDBCTemplate.getConnection(sql);
		
		
		return 0;
	}
	
}
