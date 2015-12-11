package kc.ac.kookmin.exception.transaction;

/*
 예외처리를 할 때 실행 중인 함수안에서 예외처리 하는게 대체로 좋지만 
때에 따라서 throws를 사용하여 호출한 함수에 넘겨주는 경우도 있다.
아래 bank 클래스의 oneqTrade 함수는 A,B,C 계좌에서 출금을 한번에 해주는 함수이다.
bank클래스의 balance(잔여금액)은 1000원이다. 
A,B,C 계좌에서 각각 100, 200, 300원을 출금하는 작업을 한번에 해준다고 가정해보자.
이 함수는 A, B, C 계좌에서 돈을 꺼내는 과정에서 오류가 발생하면 그 이전 작업은 취소를 해줘야 한다.
아래 예제에서 tradeWithC 에서 300원을 출금하고 exception를 발생시켰다.
그러면 잔여금액은 이전 작업의 취소로 1000원이 되어야 하지만 여기선 C 계좌만 취소됐기 때문에 700원이 남아있다. 
700원이 남으면 안되고 다시 1000원으로 돌려야 한다. 

이를 throws를 이용하여 현재 try-catch로 작성된 코드를 throw를 이용하는 방법으로 바꾸자.
*/

class Bank {
	private int balance = 1000;
	public void oneqTrade() {
		try{
			tradeWithA();
			tradeWithB();
			tradeWithC();
		} catch(Exception e) {
        		System.out.println("거래중 에러 발생, 전체 취소");
			cancelA();
        		cancelB();
        		cancelC();
		}
		System.out.println("잔여 금액 : " + balance); // 에러 발생시 잔여금액은 처음 금액과 같게 하고 싶다.
	}
	
	public void tradeWithA () {
		int m = 100; // A계좌에서 출금할 금액
		try {
			System.out.println("A 계좌에서 출금 - " + m);
			balance -= m;
		} catch(Exception e) {
                        System.out.println("A계좌 거래 에러 발생");
			cancelA();
		}
		
	}
	public void tradeWithB() {
		int m = 200;
		try {
			System.out.println("B 계좌에서 출금 - " + m);
			balance -= m;
		} catch(Exception e) {
                        System.out.println("B계좌 거래 에러 발생");
			cancelB();
		}
	}
	public void tradeWithC() throws Exception {
		int m = 300;
		System.out.println("C 계좌에서 출금 - " + m);
		balance -= m;
		Exception ex = new Exception();
		throw ex;
	}
  	public void cancelA() {
		System.out.println("A 계좌 거래 취소  ");
		balance += 100;
	}
  	public void cancelB() {
		System.out.println("B 계좌 거래 취소  ");
		balance += 200;
	}
	public void cancelC() {
		System.out.println("C 계좌 거래 취소  ");
		balance += 300;
	}
}

public class Test {
    public static void main(String args[]) {
    	Bank b = new Bank();
    	b.oneqTrade();
    }
} 
