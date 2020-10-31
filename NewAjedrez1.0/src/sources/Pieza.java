package sources;

public abstract class Pieza {

	protected Color color;
	protected byte x;
	protected byte y;
	
	public Pieza(Color color) {
		this.color = color;
	}
	
	public byte get_x() {  return this.x;  }
	public byte get_y() {  return this.y;  }
		
	public void set_x(byte x) {  this.x = x;  }
	public void set_y(byte y) {  this.y = y;  }
	
	public void set_position(byte x, byte y) {  this.x = x;  this.y = y;  }
	
	public Color get_color() {  return this.color;  }

	
	
	
}
