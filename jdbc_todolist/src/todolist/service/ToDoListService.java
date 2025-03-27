package todolist.service;

import static jdbc.common.JDBCTemplate.*;
import java.sql.Connection;

import todolist.dao.TodoListDAO;
import todolist.dto.Member;

public class ToDoListService {

	//필드
	TodoListDAO dao = new TodoListDAO();
	
	
	
	/**	회원가입 service
	 * @param member
	 * @return insert 결과 행의 값
	 * @throws Exception 
	 */
	public int signUp(Member member) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.signUp(member, conn);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
			System.out.println("회원가입 실패. 사유: 중복된 아이디입니다.");
		}
		
		close(conn);
		
		return result;
	}



	/** 로그인 service
	 * @param id
	 * @param pw
	 * @return
	 * @throws Exception 
	 */
	public Member logIn(String id, String pw) throws Exception {
		// TODO Auto-generated method stub
		
		Connection conn = getConnection();
		
		Member member = dao.logIn(conn, id, pw);
		
		close(conn);
				
		return member;
	}
}
