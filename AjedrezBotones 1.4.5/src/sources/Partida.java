package sources;

import java.util.Scanner;

public class Partida {

	public static void main(String[] args) {
		
		char table[][] = {  {'k', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', 'o', '_', 'o'},
							{'o', 'O', '_', '_', 'a', '_', '_', '_'},
							{'_', 'A', '_', 'c', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', 'O', '_', '_', '_'},
							{'_', '_', '_', '_', '_', 'O', 'O', 'O'},
							{'T', 'C', 'A', '_', 'K', 'A', 'C', 'T'}};		

		
		State st = new State(table, true);
		
		//Tablero tablero = new Tablero(st, false, true);		
		Tablero tablero = new Tablero(true, true);		
		
	}

}
