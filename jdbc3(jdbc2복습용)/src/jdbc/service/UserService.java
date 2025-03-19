package jdbc.service;

import java.sql.Connection;

import jdbc.dao.UserDAO;
import jdbc.dto.User;
import jdbc.template.JDBCTemplate;

public class UserService {

	UserDAO dao = new UserDAO();

	public int insertUser(User user) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.UserService(user, conn);
		
		if(result > 0) conn.commit();
		else conn.rollback();
		
	
		conn.close();
		
		return result;
	}
	
}
