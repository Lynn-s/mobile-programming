package project;

public class Rectangle extends Shape{
	private int w, h;
	
	//Implement here
	public Rectangle(Point center, int w, int h){
		this.center = center;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle getBounds() {
		return this;
	}
	
	public int getHeight(){
		return h;
	}
	
	public int getWidth(){
		return w;
	}
	
	@Override
	public void draw(Graphics g) {
		g.draw(this);
	}
	@Override
	public String toString() {
		return "Rectangle";
	}
}

