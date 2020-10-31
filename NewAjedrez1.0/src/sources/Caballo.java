package sources;

import java.util.Vector;

public class Caballo extends Pieza {

	private PiezaEnum tipo = PiezaEnum.CABALLO;

	public Caballo(Color color) {
		super(color);
	}
	
	public Vector<Movimiento> get_movimientos() {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(x > 0) {
			if(y < 6)
				movimientos.add(new Movimiento((byte)-1, (byte) 2, this));
			if(y > 1) 
				movimientos.add(new Movimiento((byte)-1, (byte)-2, this));
			
			if(x > 1) {
				if(y < 7)
					movimientos.add(new Movimiento((byte)-2, (byte) 1, this));
				if(y > 0) 
					movimientos.add(new Movimiento((byte)-2, (byte)-1, this));
			}
		}
		if(x < 7) {
			if(y < 6)
				movimientos.add(new Movimiento((byte)1, (byte)2, this));
			if(y > 1) 
				movimientos.add(new Movimiento((byte)1, (byte)-2, this));
			
			if(x < 6) { 
				if(y < 7)
					movimientos.add(new Movimiento((byte)2, (byte)1, this));
				if(y > 0) 
					movimientos.add(new Movimiento((byte)2, (byte)-1, this));
			}
		}
		
		return movimientos;
	}

	
}





/*if(x > 1 && y < 7) 
movimientos.add(new Movimiento((byte)-2, (byte)1, this));
if(x > 0 && y < 6) 
movimientos.add(new Movimiento((byte)-1, (byte)2, this));
if(x < 7 && y < 6) 
movimientos.add(new Movimiento((byte)1, (byte)2, this));
if(x < 6 && y < 7) 
movimientos.add(new Movimiento((byte)2, (byte)1, this));
if(x < 6 && y > 0) 
movimientos.add(new Movimiento((byte)2, (byte)-1, this));
if(x < 7 && y > 1) 
movimientos.add(new Movimiento((byte)1, (byte)-2, this));
if(x > 0 && y > 1) 
movimientos.add(new Movimiento((byte)-1, (byte)-2, this));
if(x > 1 && y > 0) 
movimientos.add(new Movimiento((byte)-2, (byte)-1, this));	*/
