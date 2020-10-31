package sources;

import java.util.Scanner;

public class Partida {

	public static void main(String[] args) {
		
		char table[][] = {  {'_', 'C', 'a', '_', 'k', '_', 'c', 't'},
							{'_', '_', '_', '_', '_', '_', 'o', 'o'},
							{'_', '_', '_', '_', 'o', 'o', '_', '_'},
							{'T', 'O', '_', '_', '_', '_', '_', '_'},
							{'_', '_', 'c', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'O', '_'},
							{'_', '_', '_', '_', '_', 'O', 'A', 'O'},
							{'_', '_', '_', 'T', '_', '_', 'K', '_'}};		

		
		State st = new State(table, true);
		
		Tablero tablero = new Tablero(true, true);		
		
	}

}
