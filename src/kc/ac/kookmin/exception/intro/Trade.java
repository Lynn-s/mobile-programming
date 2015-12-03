package kc.ac.kookmin.exception.intro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*  a.text 파일을 읽는 메소드를 가진 클래스를 만들어보자.  
파일은 각자 생성
finally엔 close를 해주자. close 함수를 만들어서 그 안에서 파일을 close하는 작업이랑 close를 위한 try-catch를 해보자
단 throws를 사용하지 않기.
클래스 명, 함수 명 사용할 라이브러리는 마음대로
String path = AAA.class.getResource("").getPath(); 를 해주면 
현재 디렉토리 위치를 리턴하기 때문에 이를 이용하여 file 위치를 가져올 수 있다.
*/

class AAA {

	public void readFile() {
		
		BufferedReader input = null;
		
		try {
			String line;
			
			String path = AAA.class.getResource("").getPath();
			input = new BufferedReader(new FileReader(path + "a.txt"));

			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)	input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

public class Trade {
    public static void main(String args[]) {
    	AAA a = new AAA();
    	a.readFile();
    }
} 
