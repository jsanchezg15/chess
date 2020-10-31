package sources;

public class Torre extends Pieza {

	private boolean hasMoved;
	
	public Torre(int x, int y, char color) {
		super(x, y, color);
		this.hasMoved = false;
	}
	
	public int getValor() {
		return 5;
	}

	
	public boolean hasMoved() {
		return this.hasMoved;
	}


	public char getChar() {
		return 'T';
	}

}
