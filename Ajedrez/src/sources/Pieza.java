package sources;

public abstract class Pieza  {
	
	private int x;
	private int y;
	private char color;
	
	
	protected Pieza(int x, int y, char color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	
	public char getColor() {
		return this.color;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}


	public abstract int getValor();
	public abstract char getChar();

}
