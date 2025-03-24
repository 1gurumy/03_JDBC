package todolist.dto;

// 롬복 왜 추가 안됨?? 적용도 안됨??
public class ToDoList {
	
	// TB_MEMBER
	private int memberNo; 		// 번호	(PK, 시퀀스)
	private String memberName;	// 이름	
	private String title;		// 제목(할일)	- 집안일(설거지/빨래/청소), 학업(벨로그 작성/강의 듣기/과제 풀기), 쇼핑(장보기/기타 물건 구매), 운동(에어로빅, 필라테스, 수영) 취미/자기계발(요리/악기연주/그림그리기/독서)
	private String titleCode;	// 제목 코드
	
	// TB_TODO
	private String tilteCode;	// 제목 코드
	private String details;		// 세부사항(할일이 구체적으로 뭔지)
	private date setDate; 		// 작성일		(DEFUALT : SYSDATE)
	private char status; 		// 완료 여부	(DEFAULT : N)
	
}
