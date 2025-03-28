package todolist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static jdbc.common.JDBCTemplate.*;
import todolist.dto.Member;

public class TodoListDAO {

	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	
	
	/** 1. 회원가입 DAO
	 * @param member
	 * @param conn
	 * @return result
	 */
	public int signUp(Member member, Connection conn) throws Exception{
		// TODO Auto-generated method stub
		int result = 0;
		
		String sql = """
				INSERT INTO TB_MEMBER VALUES(SEQ_MEMBER_NO.NEXTVAL, ?, ?, ?)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberId());
			pstmt.setString(3, member.getMemberPw());
			
			result = pstmt.executeUpdate();
			
			if(member.getMemberId().equals(rs.getString("MEMBER_ID")) {
				
			}
			
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}



	/** 2. 로그인 DAO
	 * @param conn
	 * @param id
	 * @param pw
	 * @return
	 */
	public Member logIn(Connection conn, String id, String pw) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		
		
		
		String sql ="""
				SELECT * FROM TB_MEMBER
				WHERE MEMBER_ID = ?
				AND MEMBER_PW = ?
				""";
		// 실제 DB에서 조회할 때는 문자열 값에 홀따옴표 감싸야 하지만, PreparedStatement에서는 안 그래도 됨.
		// pstmt.setString() 메서드를 통해 자동으로 값이 따옴표로 감싸져서 쿼리 내에 삽입됨.
		// ==> 위치홀더를 홀따옴표로 감쌀 필요가 없음. 감싸면 에러남.
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			
			// 위 sql문의 resultset을 1번 행부터 순회하면서 값이 있으면 true, 없으면 false 반환
			if(rs.next()) {
				String memberId = rs.getString("MEMBER_ID");
				String memberPw = rs.getString("MEMBER_PW");
						
				
				// 사용자로부터 입력받은 id, pw와 일치하는 행이 있다면(1개이거나 없거나 둘중 하나라서 if문 사용. 행이 여러개라면 while문 써야함.) member 객체 생성
				if (memberId.equals(id) && memberPw.equals(pw)) {
					member = new Member();
					member.setMemberId(memberId);
					member.setMemberPw(memberPw);
					
			    }
			}
					
			
		} finally {
			close(pstmt);
			close(rs);
		}
		
		
		return member;
	}

}