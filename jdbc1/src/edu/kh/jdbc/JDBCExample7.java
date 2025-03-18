package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
				
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
				
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부

		// 1. JDBC 객체를 참조할 참조용 변수 선언
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		Scanner sc = null;
		
		// 2. DriverManager를 통해 Connection 객체 생성
		try {
			// 2-1) 메모리에 OracleDriver 로드해두기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2-2) DB 연결 정보 작성하기
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String id = "kh";
			String pw = "kh1234";
			
			// 2-3) DB 연결 정보와 DriverManager객체를 이용해 Connection 객체 생성
			conn = DriverManager.getConnection(url, id, pw);
			
			// 3. SQL 작성 + Autocommit 끄기
			conn.setAutoCommit(false);
			
			System.out.print("조회할 성별(F/M) : ");
			String gender = sc.next().toUpperCase();
			//gender.toUpperCase().charAt(0); // 대문자로 변환
			
			System.out.println("급여 범위(최소, 최대 순서로 작성) : ");
			int minSal = sc.nextInt();
			int maxSal = sc.nextInt();
			
			System.out.print("급여 정렬(1. ASC, 2.DESC) : ");
			int input = sc.nextInt();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME,
					DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') GENDER,
					SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
					FROM EMPLOYEE
					JOIN JOB USING(JOB_CODE)
					LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)
					WHERE DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY SALARY""";
					
			// 급여의 오름차순인지 내림차순인지 조건에 따라 sql 보완하기
			if(input == 1) sql += " ASC";
			else 		   sql += " DESC";
			
			
			
			// 4. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 5. ?(위치홀더) 에 알맞은 값 세팅
			pstmt.setString(1, gender);
			pstmt.setInt(2, minSal);
			pstmt.setInt(3, maxSal);
			
			// 6. SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 7. 커서를 이용하여 한 행씩 접근하여 컬럼 값 얻어오기
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("---------------------------------------------------------");
			
			boolean flag = true;	// true : 조회결과가 없음, false: 조회결과 존재
			
			while(rs.next()) {	// 조회 결과가 있다면
				flag = false;	// while문이 1회 이상 반복함 == 조회결과가 1행이라도 있다.
											// getString()에 입력 가능한 것들 : 컬럼명, 별칭, 조회된 컬럼순서 가능
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gen = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
					empId, empName, gen, salary, jobName, deptTitle);
				
			}
			
			if(flag) {	// flag == true인 경우 -> while문 안쪽 수행 X => 조회결과가 1행도 없음
				System.out.println("조회 결과 없음");
			}
				
		} catch (Exception e ) {
			e.printStackTrace();
		} finally {
			// 8. 사용한 JDBC 객체 자원 반호나
			
			try {
				if(sc != null) sc.close();
				
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
			
			
			/*
			if(gender.equals("f") && input == 1) {	// 여자, 오름차순
				String sql = """
						SELECT EMP_ID 사번, EMP_NAME 이름, EMP_NO 성별, SALARY 급여, JOB_NAME 직급명, DEPT_TITLE 부서명
						CASE WHEN SUBSTR(EMP_NO, 8, 1) = '1' TEHN 'F'
								ELSE 'M'
						FROM EMPLOYEE E
						JOIN DEPARTMENT D ON (E.DEPT_ID = D.DEPT_CODE)
						JOIN ON JOB J ON (E.JOB_CODE = J.JOB_CODE)
						WHERE SUBSTR(EMP_NO, 8, 1) = '1'
						ORDER BY SALARY
						""";
			} else if (gender.equals("f") && input == 2) {	// 여자, 내림차순
				String sql = """
						SELECT EMP_ID 사번, EMP_NAME 이름, EMP_NO 성별, SALARY 급여, JOB_NAME 직급명, DEPT_TITLE 부서명
						CASE WHEN SUBSTR(EMP_NO, 8, 1) = '1' TEHN 'F'
								ELSE 'M'
						FROM EMPLOYEE E
						JOIN DEPARTMENT D ON (E.DEPT_ID = D.DEPT_CODE)
						JOIN ON JOB J ON (E.JOB_CODE = J.JOB_CODE)
						WHERE SUBSTR(EMP_NO, 8, 1) = '1'
						ORDER BY SALARY DESC
						""";
			} else if(gender.equals("m") && input == 1) {	// 남자, 오름차순
				String sql = """
						SELECT EMP_ID 사번, EMP_NAME 이름, EMP_NO 성별, SALARY 급여, JOB_NAME 직급명, DEPT_TITLE 부서명
						CASE WHEN SUBSTR(EMP_NO, 8, 1) = '1' TEHN 'F'
								ELSE 'M'
						FROM EMPLOYEE E
						JOIN DEPARTMENT D ON (E.DEPT_ID = D.DEPT_CODE)
						JOIN ON JOB J ON (E.JOB_CODE = J.JOB_CODE)
						WHERE SUBSTR(EMP_NO, 8, 1) = '2'
						ORDER BY SALARY
						""";
				
			} else if(gender.equals("m") && input == 2) {	// 남자, 내림차순
				String sql = """
						SELECT EMP_ID 사번, EMP_NAME 이름, EMP_NO 성별, SALARY 급여, JOB_NAME 직급명, DEPT_TITLE 부서명
						CASE WHEN SUBSTR(EMP_NO, 8, 1) = '1' TEHN 'F'
								ELSE 'M'
						FROM EMPLOYEE E
						JOIN DEPARTMENT D ON (E.DEPT_ID = D.DEPT_CODE)
						JOIN ON JOB J ON (E.JOB_CODE = J.JOB_CODE)
						WHERE SUBSTR(EMP_NO, 8, 1) = '2'
						ORDER BY SALARY DESC
						""";
	
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
			*/
			/*
			String sql = """
					SELECT EMP_ID 사번, EMP_NAME 이름, EMP_NO 성별, SALARY 급여, JOB_NAME 직급명, DEPT_TITLE 부서명
					CASE WHEN SUBSTR(EMP_NO, 8, 1) = '1' TEHN 'F'
							ELSE 'M'
					FROM EMPLOYEE E
					JOIN DEPARTMENT D ON (E.DEPT_ID = D.DEPT_CODE)
					JOIN ON JOB J ON (E.JOB_CODE = J.JOB_CODE)
					WHERE SUBSTR(EMP_NO, 8, 1) = '1'
					ORDER BY SALARY ?
					""";
			
			
			
			
			
			
			// 4.  Statement 객체 생성
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			
		}
		*/
		
		
		
		
		
		
	}

}
