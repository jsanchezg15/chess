package sources;

public class Movimiento {

	private byte x1, y1, x2, y2;
	private char piece;
	private boolean a, pawn_capture;
	
	public Movimiento(byte x1, byte y1, byte x2, byte y2, char piece) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.piece = piece;
		this.a = false;
	}
	
	public Movimiento(byte x1, byte y1, byte x2, byte y2, char piece, boolean pawn_capture) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.piece = piece;
		this.a = true;
		this.pawn_capture = pawn_capture;
	}
	
	public byte getX1() { return this.x1; }
	public byte getY1() { return this.y1; }
	public byte getX2() { return this.x2; }
	public byte getY2() { return this.y2; }
	public char getPiece() { return this.piece; }
	public boolean getElse() { return this.a; }
	public boolean getPawnCapture() { return this.pawn_capture; }
	
}
