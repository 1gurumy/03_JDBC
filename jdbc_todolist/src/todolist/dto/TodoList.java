package todolist.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoList {

	// TB_TODO
	private String category;	// 제목 코드
	private String toDo;		// 제목(할일)	- 집안일(설거지/빨래/청소), 학업(벨로그 작성/강의 듣기/과제 풀기), 쇼핑(장보기/기타 물건 구매), 운동(에어로빅, 필라테스, 수영) 취미/자기계발(요리/악기연주/그림그리기/독서)
	private String details;		// 세부사항(할일이 구체적으로 뭔지)
	private Date setDate; 		// 작성일		(DEFUALT : SYSDATE)
	private char status; 		// 완료 여부	(DEFAULT : N)
}
