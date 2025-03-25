package todolist.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Member {

	// TB_MEMBER
	private int memberNo; 		// 번호	(PK, 시퀀스)
	private String memberName;	// 유저 이름	
	private String toDoCode;	// 제목 코드
	private String memberId;	// 유저아이디
	private String memberPw;	// 유저비밀번호
	

	
	
}
