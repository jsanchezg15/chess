package sources;

import java.util.Scanner;

public class Partida {

	public static void main(String[] args) {
		
		char table[][] = {  {'_', '_', '_', 'k', '_', '_', 't', '_'},
							{'o', '_', '_', '_', 'c', '_', '_', 'o'},
							{'_', '_', '_', 'C', 'A', 'Q', '_', '_'},
							{'_', '_', '_', '_', 'o', '_', 'q', '_'},
							{'_', '_', '_', '_', 'O', '_', '_', '_'},
							{'O', '_', '_', '_', '_', '_', '_', '_'},
							{'_', 'O', '_', 'K', '_', 'O', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'}};		

		
		State st = new State(table, true);
		
		Tablero tablero = new Tablero(false, true);		
		//Tablero tablero = new Tablero(st, false, true);		
		
	}

}
