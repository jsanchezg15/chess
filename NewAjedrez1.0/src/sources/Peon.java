package sources;

import java.util.Vector;

public class Peon extends Pieza {

	protected PiezaEnum tipo = PiezaEnum.PEON;
	protected boolean hasMoved;
	
	public Peon(Color color) {
		super(color);
		this.hasMoved = false;
	}

	public Vector<Movimiento> get_movimientos() {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		return null;
	}
	
}
