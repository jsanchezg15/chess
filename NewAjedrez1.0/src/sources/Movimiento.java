package sources;

public class Movimiento {

	public byte x, y;
	public Pieza pieza;
	
	public Movimiento(byte x, byte y, Pieza pieza) {
		this.x = x;
		this.y = y;
		this.pieza = pieza;
	}
	
}
