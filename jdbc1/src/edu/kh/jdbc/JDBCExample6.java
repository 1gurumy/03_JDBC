package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {

	public static void main(String[] args) {
		
		// 아이디, 비밀번호, 이름을 입력받아 
		// 아이디, 비밀번호가 일치하는 사용자의
		// 이름을 수정(UPDATE)
		
		// 1. PreparedStatement 이용하기
		// 2. commit/rollback 처리하기
		// 3. 성공 시 "수정 성공!" 출력 / 실패 시 "아이디 또는 비밀번호 불일치" 출력
		
		//1. JDBS 객체를 참조할 참조용 변수 선언하기 + 키보드 입력용 객체 sc 선언하기
		Scanner sc = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 2. DriverManager 객체를 이용해서 Connection 객체 생성하기
		// 2-1) Oracale JDBC Driver 객체를 메모리에 로드해두기
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2) DB 연결 정보 작성하기
			String url = "jdbc:oracle:thin:@localhost:1521:XE";	
			String userName = "kh";
			String password = "kh1234";
			
			// 2-3) DB 연결 정보와 DriverManager 객체를 이용해서 Connection 객체 생성하기
			conn = DriverManager.getConnection(url, userName, password);
			
			// 3. SQL 작성
			sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("수정할 이름 입력 : ");
			String name = sc.nextLine();
			
			
			String sql = """
					UPDATE TB_USER 
					SET USER_NAME = ?
					WHERE USER_ID = ?
					AND USER_PW = ?
					""";
			
		// 4. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
		// 5. ? 위치홀더 자리에 알맞은 값 대입
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
		// +  AutoCommit(SQL 수행될 때마다 자동으로 커밋되는 것)끄기!	
			conn.setAutoCommit(false);
			
		// 6. SQL 수행 후 결과 반환 받기
			int result = pstmt.executeUpdate();
			
		// 7. 성공 시 "수정 성공!" 출력 / 실패 시 "아이디 또는 비밀번호 불일치" 출력
			
		if(result > 0) {	// 수행 성공
			System.out.println("수정 성공!");
			conn.commit();
		} else {	 // 수행 실패
			System.out.println("아이디 또는 비밀번호 불일치");
			conn.rollback();
		}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 8. 사용한 JDBC 객체 자원 반환하기
			
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	
		
		
		
		
		
		
		
	}

}
