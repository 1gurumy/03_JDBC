package jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {
		// XML (eXtemsible Markup Language) : 단순화된 데이터 기술 형식
		// XMLFile -> DB연결 계정과 같은 보안과 관련된 정보를 따로 작성하는 용도
		
		// XML에 저장되는 데이터 형식 Key : Value (Collection - Map과 저장 형식 동일함.)
		// -> Key, Value 모두 String(문자열) 형식
		
		// XML 파일을 읽고, 쓰기 위한 IO 관련된 클래스 필요
		
		// * Properties 컬렉션 객체 *
		// - Map 의 후손 클래스
		// - Key, Value 모두 String(문자열 형식)
		// - XML 파일을 읽고, 쓰는데 특화된 메서드 제공
		
		try {
			
			Scanner sc = new Scanner(System.in);
			
			// XML 파일로 데이터를 저장하기 위해 Properties 객체 생성
			// prop은 Properties의 객체
			Properties prop = new Properties();
			 
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();	// 공백 방지를 위해 next()
			// 사용자로부터 파일 이름을 입력받아 fileName 변수에 저장
			
			
			// FileOutputStream(데이터를 바이트 단위로 출력하는 클래스) 생성
			// fos는 파일 출력을 담당할 FileOutputStream의 객체
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			// 사용자가 입력한 파일 이름 뒤에 .xml 확장자를 추가하여 실제로 XML 파일을 생성할 경로를 만듦.
			
			
			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName + ".xml file!!!");
			// Properties.storeToXML(OutputStream os, comment);
			// : Properties 객체에 저장된 데이터를 XML 형식으로 파일에 저장하는 메서드
			// os -> 출력 스트림의 참조변수 겸 객체인, os에 저장된 파일 경로를 출력스트림을 통해 XML문서로 출력함.
			// comment : xml파일의 주석 부분
			
			// 첫 번째 매개변수 fos -> 데이터를 쓸 파일 경로
			// 두 번째 매개변수 fileName + ".xml file!!!" -> XML 파일의 주석
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
			
		} catch (Exception e) {
			System.out.println("XML 파일 생성 중 예외발생");
			e.printStackTrace();
			
		}
		
		
	}

}
