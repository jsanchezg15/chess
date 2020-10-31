package sources;

public class Cell {

	private Pieza pieza;
	
	public Cell() {}
	
	public boolean hasPieza() {
		if(this.pieza == null)
			return false;
		else
			return true;
	}
	
	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
	}
	
	public Pieza getPieza() {
		return this.pieza;
	}
	
}
