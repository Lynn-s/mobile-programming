package kr.ac.kookmin.exception.fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 original.txt 파일을 읽은 다음 각각의 줄에 라인 넘버를 추가하여 numbered.txt파일로 저장하는 프로그램을 작성하시오.
 kr.ac.kookmin.exception.fileio 패키지를 생성 후에 AddLineNumber 클래스를 추가하여 프로그램을 작성한다. 
 
 Makes numbered.txt the same as original.txt, but with each line numbered.
*/

public class AddLineNumber
{
   public static void main(String[] args)
   {
      String path = AddLineNumber.class.getResource("").getPath();
	   
      try
      {
         
    	BufferedReader inputStream = 
               new BufferedReader(new FileReader(path+"original.txt"));
        PrintWriter outputStream = 
               new PrintWriter(new FileOutputStream(path+"numbered.txt"));
        int lineNum = 1;

        String line;
         
        while ((line = inputStream.readLine()) != null) {
             outputStream.println(lineNum + " " + line);
             lineNum++;
         
        }
         
        inputStream.close( );
        outputStream.close( );
    
      } catch (IOException e) {
	e.printStackTrace();
      } 

   }
}
