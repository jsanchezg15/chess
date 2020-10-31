package sources;

public class TableroPruebas {

	public static void main(String[] args) {
		
		char table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'k', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'o', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', 'O', '_', '_'},
							{'K', '_', '_', '_', '_', '_', 'T', '_'},};		

		State st = new State(table, true);
		
		Tablero tablero = new Tablero(st);
		
	}

}
