package jdbc.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
	
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private Date enrollDate;
	
	
}
