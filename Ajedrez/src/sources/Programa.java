package sources;

public class Programa {

	public static void main(String[] args) {
		
		
		Cell celda1 = new Cell();
		
		System.out.println(celda1.hasPieza());
		
		celda1.setPieza(new Torre(1,1,'T'));
		
		System.out.println(celda1.getPieza().getChar());
		
	}
}
