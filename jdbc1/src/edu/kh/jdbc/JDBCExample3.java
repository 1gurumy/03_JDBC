package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {

	public static void main(String[] args) {

		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		// -> 조회 결과를 이클립스 콘솔창에 출력
		
		// [실행화면]
		// 최소 급여 : 1000000
		// 최대 급여 : 3000000
		
		// 사번 / 이름 / 급여
		// 사번 / 이름 / 급여
		// 사번 / 이름 / 급여
		// ...
		
		// 1. JDBS 객체를 참조할 참조용 변수 선언하기
		Scanner sc = null;	// 스캐너
			
		Connection conn = null;	// DB 연결정보 저장 객체
		Statement stmt = null;	//	SQL 수행, 결과 반환용 객체
		ResultSet rs = null; // SELECT 수행 결과 저장 객체

		
		// 2. DriverManager 객체를 이용해서 Connection 객체 생성하기
		try {
			// 2-1) Oracale JDBC Driver 객체를 메모리에 로드해두기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2) DB 연결 정보 작성하기
			String DBconnInfo = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh";
			String password = "kh1234";
			
			// 2-3) DB 연결 정보와 DriverManager 객체를 이용해서 Connection 객체 생성하기
			conn = DriverManager.getConnection(DBconnInfo, userName, password);
			
			// 필수 아님) Connection 객체가 잘 생성되었는지 확인하기 (객체 주소 반환)
			System.out.println(conn);
			
			// 3. SQL 작성
			sc = new Scanner(System.in);
			System.out.print("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int max = sc.nextInt();
			/*
			String sql  = "SELECT EMP_ID, EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY BETWEEN " + min + " AND" + max + " ORDER BY SALARY DESC";
			*/
		// Java 13버전부터 지원하는 text Block(""") 문법. 
		// 자동으로 개행 포함 + 문자열 연결이 처리됨.
		// 기존처럼 + 연산자로 문자열을 연결할 필요가 없음.
		// 단, 중간에 변수를 입력해야 한다면 + 연산자 작성해야함.
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN
					""" + min + " AND " + max + " ORDER BY SALARY DESC";
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			
			// 6. 1행 씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d \n",
								empId, empName, salary
						);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("오타가 있는 것 같습니다.");
			
			// 7. 사용 완료한 jdbc 객체 자원 반환
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
