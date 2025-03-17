package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {
		
		// 부서명을 입력받아
		// 해당 부서에 근무하는 사원의 
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		// [실행화면]
		// 부서명 입력 : 총무부
		// 200 / 선동일 / 총무부 / 대표
		// 202 / 노옹철 / 총무부 / 부사장
		// 201 / 송종기 / 총무부 / 부사장
		
		// 부서명 입력 : 개발팀(존재하지 않는 부서명)
		// 일치하는 부서가 없습니다!
		
		// hint : SQL에서 문자열은 양쪽 '' (홀따옴표) 필요
		// ex) 총무부 입력 -> '총무부'
		
		
		// 1. JDBS 객체를 참조할 참조용 변수 선언하기
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Scanner sc = null;
		
		// 2. DriverManager 객체를 이용해서 Connection 객체 생성하기
		try {
			// 2-1) Oracale JDBC Driver 객체를 메모리에 로드해두기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2) DB 연결 정보 작성하기
			String type = "jdbc:oracle:thin:@";	// 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인주소 
			String port = ":1521"; 	// 프로그램 연결을 위한 port 번호
			String dbName = ":XE"; // DBMS 이름 (XE == eXpress Edition)
		
			String userName = "kh";		// 사용자 계정명
			String password = "kh1234";	// 계정 비밀번호
			
			// 2-3) DB 연결 정보와 DriverManager 객체를 이용해서 Connection 객체 생성하기
			conn = DriverManager.getConnection(type + host + port + dbName, 
												userName, password);
			
			// 필수 아님) Connection 객체가 잘 생성되었는지 확인하기 (객체 주소 반환)
			System.out.println(conn);
			
			// 3. SQL 작성
			System.out.print("부서명 입력 : ");
			sc = new Scanner(System.in);
			String input = sc.next();
			// 200 / 선동일 / 총무부 / 대표
			String sql = """
					SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME
					FROM EMPLOYEE E
					JOIN JOB J ON(E.JOB_CODE = J.JOB_CODE)
					LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)
					WHERE DEPT_TITLE =  '""" + input + "' ORDER BY E.JOB_CODE";

			
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);
			
			/* flag 이용법--------------------------------
			boolean flag = true; // 조회결과 있다면 false, 없으면 true
			// 6. 조회 결과가 담겨있는 ResultSet을 커서(cursor)를 이용해서 1행 씩 접근해 각 행에 작성된 컬럼 값 얻어오기
			
			while(rs.next()) {
				flag = false;
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%d / %s / %s / %s \n", 
								empId, empName, deptTitle, jobName);
			}
			if(flag) {
				System.out.println("일치하는 부서가 없습니다!");
			}
			*/
			
			// return 이용법
			if(!rs.next()) {	// 조회 결과가 없다면
				System.out.println("일치하는 부서가 없습니다!");
				return;	// return구문이 수행되면 바로 메서드 종료되어 아래의 코드는 수행되지 않지만, finally 구문은 수행하고 메서드 종료됨.
			}
			// while문으로 작성하면 선동일 행이 빠진 채 출력됨. 위에서 rs.next()가 수행되서 커서가 한 행 내려가서 그럼.
			do {
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%d / %s / %s / %s \n", 
								empId, empName, deptTitle, jobName);
				
			} while(rs.next());
				
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 7. 사용 완료된 JDBC 객체 자원 반환 (close)
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if(rs != null) rs.close();
				if(sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
