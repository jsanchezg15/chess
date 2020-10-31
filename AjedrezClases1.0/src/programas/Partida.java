package programas;

import java.util.Scanner;

import sources.State;
import sources.Tablero;

public class Partida {

	public static void main(String[] args) {
		
		char table[][] = {  {'k', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', 'o', '_', 'o'},
							{'o', 'O', '_', '_', '_', '_', '_', '_'},
							{'_', 'A', '_', 'c', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', 'O', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', 'K', '_', '_', '_'}};

		
		State st = new State(table, true);
		
		//Tablero tablero = new Tablero(st, false, true);		
		Tablero tablero = new Tablero(true, true);		
		
	}

}
