package project;

public class Ellipse extends Shape{
	//Implement here
	public int w, h;
	
	public Ellipse(Point center, int w, int h){
		this.center = center;
		this.w = w;
		this.h = h;
	}

  public Rectangle getBounds(){
		return new Rectangle(center, w*2, h*2);
	}
  
  public int getSemiMajorAxis(){
	  
	  return w>h?w*2:h*2;
  }
  
  public int getSemiMinorAxis(){
	  
	  return w>h?h*2:w*2;
  }

	@Override
	public void draw(Graphics g) {
		g.draw(this);
	}
	@Override
	public String toString() {
		return "Ellipse";
	}
}