package todolist.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import todolist.dto.Member;
import todolist.service.ToDoListService;

public class TodoListView {

	 private ToDoListService service = new ToDoListService();
	 private Scanner sc = new Scanner(System.in);
	
	
	public void mainMenu() {
			
		int input = 0;
		
		do {
			try {
			System.out.println("==========메인화면입니다.=========");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 내 TODO 전체 조회 (번호, 제목, 완료여부, 작성일)");
			System.out.println("4. 새로운 Todo 추가");
			System.out.println("5. Todo 수정 (제목, 내용)");
			System.out.println("6. 완료여부변경 (Y <-> N)");
			System.out.println("7. Todo 삭제");
			System.out.println("8. 로그아웃");
			System.out.println("0. 프로그램 종료");
			
			System.out.println("원하는 기능에 해당하는 번호를 입력하세요");
			System.out.print("번호 입력 : ");
				input = sc.nextInt();
			
			switch(input) {
				case 1 : signUP(); break;
				case 2 : logIn(); break;
				case 3 : select(); break;
				case 4 : addToDo(); break;
				case 5 : update(); break;
				case 6 : updateStatus(); break;
				case 7 : deleteToDo(); break;
				case 8 : logOut(); break;
				case 0 : offApp(); break;
				default : System.out.println("메뉴에 있는 번호만 입력하세요.");
			}
			
		
	
	} catch (InputMismatchException e) {
		System.out.println("잘못 입력하셨습니다.");
		
	} catch (Exception e) {
		e.printStackTrace();
	}
			
	} while(input != 0);
		
	}

	

	/** 0. 프로그램 종료
	 * 
	 */
	private void offApp() {
		// TODO Auto-generated method stub
		
	}



	/** 8. 로그아웃
	 * 
	 */
	private void logOut() {
		// TODO Auto-generated method stub
		
	}



	/** 7. Todo 삭제
	 * 
	 */
	private void deleteToDo() {
		// TODO Auto-generated method stub
		
	}



	/** 6. 완료여부변경 (Y <-> N)
	 * 
	 */
	private void updateStatus() {
		// TODO Auto-generated method stub
		
	}



	/** 5. Todo 수정 (제목, 내용)
	 * 
	 */
	private void update() {
		// TODO Auto-generated method stub
		
	}


	/** 4. 새로운 Todo 추가
	 * 
	 */
	private void addToDo() {
		// TODO Auto-generated method stub
		
	}


	/** 3. 내 TODO 전체 조회 (번호, 제목, 완료여부, 작성일)
	 * 
	 */
	private void select() {
		// TODO Auto-generated method stub
		
	}


	/** 2. 로그인 view
	 * @throws Exception 
	 * 
	 */
	private void logIn() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("\n===로그인 페이지입니다.===\n");
		
		System.out.print("id : ");
		String id = sc.next();
		
		System.out.print("pw : ");
		String pw = sc.next();
		
		// 1행의 조회 결과를 담기 위해서 Member 객체 1개 사용
		Member member = service.logIn(id, pw);
	
		if(member == null) {
			System.out.println("아이디 또는 비밀번호를 잘못 입력하셨습니다.");
		} else System.out.println("로그인 성공");
		
		
		
	}


	/** 1. 회원 가입 view
	 * 
	 */
	private void signUP() throws Exception {
		System.out.println("\n=====회원 가입 페이지 입니다.=====\n");
		
		System.out.print("이름 : ");
		String name = sc.next();
		
		System.out.print("id : ");
		String id = sc.next();
		
		System.out.print("pw : ");
		String pw = sc.next();
		
		System.out.print("pw 확인 : ");
		String pw2 = sc.next();
		
		if(pw.equals(pw2)) {
			Member member = new Member();
			member.setMemberName(name);
			member.setMemberId(id);
			member.setMemberPw(pw);
			
			int result = service.signUp(member);
			if(result > 0) System.out.println("회원가입이 완료되었습니다.");
			else System.out.println("회원가입 실패");
			
			
		} else {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
	}

	
}
