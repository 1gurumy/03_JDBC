package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	
	public static void main(String[] args) {
		
		/*
		 * JDBC (Java DataBase Connectivity) 
		 * 
		 * - Java에서 DB에 연결할 수 있게 해주는
		 * 	 Java API(Java에서 제공하는 코드)이다.
		 * -> java.sql 패키지에 존재함
		 * 
		 * 
		 * 
		 * 
		 * */
	
		// Java 코드를 이용해
		// EMPLOYEE 테이블에서
		// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회 후
		// 이클립스 콘솔에 출력해보기
		//////// 드라이버 종류, DB를 사용하는 컴퓨터의 ip주소, 포트번호, dbmsd의 종류, employee 테이블이 있는 계정과 비번
	
		/* 1. JDBS 객체를 참조할 참조용 변수 선언하기 */
		
		// java.spl.Connection 필요함.
		// Connection은 특정 DBMS와 연결하기 위한 정보를 저장하고 있는 객체이다.
		// == DBeaver에서 사용하는 DB 연결과 같은 역할의 객체이다.
		// (Connection은 DB의 서버 주소, 포트번호, DB 이름, 계정명, 비밀번호 등을 가지고 있음.)
		Connection conn = null;
		
		// java.sql.Statement 
		// - 1) Statement의 역할 : SQL을 Java -> DB에 전달
		//   2) DB에서 SQL을 수행한 결과를 반환하여 다시 Java로 받아옴.(DB -> Java)
		Statement stmt = null;
		
		// java.spl.ResultSet
		// - SELECT 조회 결과를 저장하는 객체를 ResultSet이라고 함.
		ResultSet rs = null;
		
		/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
		// DriverManager는 java.sql.DriverManager에 있음
		// - DB 연결 정보와 JDBC 드라이버를 이용해서
		// 	   원하는 DB와 연결할 수 있는 Connection 객체를 생성하는 객체
		
		// 2-1) Oracle JDBC Driver 객체를 메모리에 로드해두기
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 괄호 안에 오타있을 시 ClassNotFoundException 예외 발생함
			// Class.forName("패키지명 + 클래스명"); 
			// Class.forName() 메서드는 ()안의 해당 클래스를 읽어 메모리에 적재(로드)하는 역할이다.
			// -> JVM이 프로그램 동작에 사용할 객체를 생성하는 구문이다.
			// 클래스명.메소드명() -> 해당 메서드는 static메서드. 
			
			// oracle.jdbc.driver.OracleDriver
			// - Oracle DBMS 연결 시 필요한 코드가 담겨있는 클래스
			// 	ojdbc 라이브러리 파일 내에 존재함.
			//  -> 자바가 아니라 Oracle에서 만들어서 제공하는 클래스
			//    즉, Classpath에 ojdbc 드라이브를 추가하지 않으면 classnotfounder Exception이 발생함.
			
			// 2-2) DB 연결 정보 작성하기
			String type = "jdbc:oracle:thin:@";	// 드라이버의 종류
			
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인주소 
									   // localhost == 현재 컴퓨터
			
			String port = ":1521"; 	// 프로그램 연결을 위한 port 번호
			
			String dbName = ":XE"; // DBMS 이름 (XE == eXpress Edition)
			
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String userName = "kh";		// 사용자 계정명
			String password = "kh1234";	// 계정 비밀번호
			
			// 2-3) DB 연결 정보와 DriverManager 객체를 이용해서 Connection 객체 생성하기
			conn = DriverManager.getConnection(type + host + port + dbName,
						userName,
						password);
			
			// Connection 객체가 잘 생성되었는지 확인하기 (객체 주소 반환)
			System.out.println(conn);
			
			/* 3. SQL 작성 */
			// !!주의사항!!
			// -> JDBC 코드에서 SQL 작성 시 
			// 	  세미콜론(;)을 작성하면 안된다!!
			// -> ; 작성하면 "sql 명령어가 올바르게 종료되지 않았습니다" 예외 발생
			
			// EMPLOYEE 테이블에서 
			// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE "
					+ "FROM EMPLOYEE" ;
			
			/* 4. Statement 객체 생성하기 */	// Connectotin은 통로 역할, Statement 는 셔틀버스역할
			stmt = conn.createStatement();
			// 연결된 DB에 SQL을 전달하고 결과를 반환 받을 역할을 할
			// Statement 객체를 생성해둠.
	
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			// 1) ResultSet Statement.executeQuery(sql)
			//		-> sql이 SELECT 문일 때 결과로 ResultSet을 객체로 반환한다.
			
			// 2) int Statemen.executeUpdate(sql);
			// -> 전달한 sql이 DML(INSERT, UPDATE, DELETE)일 때 실행해주는 메서드 
			// -> 결과로 int 반환 (삽입, 수정, 삭제된 행의 갯수를 리턴)
			rs = stmt.executeQuery(sql); // ResultSet Statement.executeQuery(sql)
			
			/* 6. 조회 결과가 담겨있는 ResultSet을 
			 * 	  커서(cursor)를 이용해서
			 * 	  1행 씩 접근해 각 행에 작성된 컬럼 값 얻어오기
			 * */
			// boolean ResultSet.next() : 
			// 커서를 다음 행으로 이동시킨 후 
			// 이동된 행에 값이 있으면 true, 없으면 false 반환
			// 맨 처음 호출 시 1행 부터 시작
			while(rs.next()) {
				// 자료형 ResultSet.get자료형(컬럼명 | 컬럼의 순서);
				// - 현재 행에서 지정된 컬럼의 값을 얻어와 반환
				// -> 지정된 자료형 형태로 값이 반환됨
				// (자료형을 잘못 지정하면 예외 발생)
				
				// [java]				  [db]
				// String 			   CHAR, VARCHAR2
				// int, long		   NUMBER (정수만 지정된 컬럼)
				// floate, doble	   NUMBER (정수 + 실수)
				// java.spl.Date	   DATE
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				int salary = rs.getInt("SALARY");
				Date hireDate = rs.getDate("HIRE_DATE"); 
		
				System.out.printf("사번 : %s / 이름 : %s / 부서코드 : %s / 직급코드 : %s / "
						+ "급여 : %d / 입사일 : %s \n",
							empId, empName, deptCode, jobCode, salary, hireDate.toString()
						);
				
			}

		 } catch (ClassNotFoundException e) {
			System.out.println("해당 Class를 찾을 수 없습니다.");
			// Class.forName("oracle.jdbc.driver.OracleDriver"); ()안에 오타가 있었다면 println 안의 문자열 출력됨.
			e.printStackTrace();
			
		} catch (SQLException e) {
			// SQLException : DB 연결과 관련된 모든 예외의 최상위 부모
			e.printStackTrace();
		
		} finally {
			/* 7. 사용 완료된 JDBC 객체 자원 반환 (close) */
			// -> 자원반환을 하지 않으면 DB와 연결된 Connection이 그대로 남아있어서
			// 	  다른 클라이언트(DB에 연결하려는 모든 것 ex. Java 프로그램)가 추가적으로 연결되지 못하는 
			//	  문제가 발생될 수도 있다.
			// -> DBMS는 최대 Connection 수 개수 제한을 하기 때문이다.
			
			try {
				
				// 객체 생성의 역순으로 close 수행하는 것이 권장된다.
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
		
		
		
		
		
		
		
		
		
		
	}
}
