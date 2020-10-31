package sources;

import java.util.Scanner;

public class Partida {

	public static void main(String[] args) {
		
		char table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'k', '_'},
							{'_', '_', '_', '_', '_', 't', 'o', '_'},
							{'_', '_', '_', '_', '_', 'O', 'K', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', 'A', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'}};		

		
		State st = new State(table, true);
		
		Tablero tablero = new Tablero(true, true);		
		
	}

}
