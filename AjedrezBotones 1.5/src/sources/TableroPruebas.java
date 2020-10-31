package sources;

import java.util.Scanner;

public class TableroPruebas {

	public static void main(String[] args) {
		
		char table[][] = {  {'_', 'C', 'a', '_', 'k', '_', 'c', 't'},
							{'_', '_', '_', '_', '_', '_', 'o', 'o'},
							{'_', '_', '_', '_', 'o', 'o', '_', '_'},
							{'T', 'O', '_', '_', '_', '_', '_', '_'},
							{'_', '_', 'c', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'O', '_'},
							{'_', '_', '_', '_', '_', 'O', 'A', 'O'},
							{'_', '_', '_', 'T', '_', '_', 'K', '_'}};		

		// Hay que probar a hacer 2 threads a la vez (uno con profundidad 4 y otro 5) 
		// Si despues de x tiempo no ha acabado el de p5 se usa la respuesta que haya dado el de p4
		
		State st = new State(table, true);
		
		Tablero tablero = new Tablero();		
		
	}

}
