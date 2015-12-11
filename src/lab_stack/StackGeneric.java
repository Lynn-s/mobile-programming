package lab_stack;

//lab_05 stack using generic
//20143073 신예린

public class StackGeneric {
	public static void main(String arg[]){
	      Stack<Integer> stackInteger = new Stack<Integer>();   
	      stackInteger.push(1);
	      stackInteger.push(2);
	      System.out.println(stackInteger.pop());
	      System.out.println(stackInteger.pop());
	      
	      Stack<String> stackString = new Stack<String>();   
	      stackString.push("Kookmin University");
	      stackString.push("Korea Seoul");
	      System.out.println(stackString.pop());
	      System.out.println(stackString.pop());
	}
}

class Stack<T> {
	Node<T> top;
	   
	public T pop() {
	   T data = top.getData();
	   top = top.getNext();
	   return data;
	}
	   
	public void push(T data) {
	   if(top == null){
	      top = new Node<T>(data);
	   }
	   else{
	      Node<T> nextNode = new Node<T>(data);
	      nextNode.setNext(top);
	      top = nextNode;
	   }
	}
}

class Node<T> {
	Node<T> next;
	T data;
	public Node(T data) {
	    this.data = data;
	}
	public T getData() {
	   return this.data;
	}
	public Node<T> getNext() {
	   return this.next;
	}
	public void setNext(Node<T> next) {
	   this.next = next;
	}
}
	
