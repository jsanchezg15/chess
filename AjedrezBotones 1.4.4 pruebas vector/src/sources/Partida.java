package sources;

import java.util.Scanner;
import java.util.Vector;

public class Partida {

	public static void main(String[] args) {
				
		char table[][] = { {'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'k', '_'},
							{'_', '_', '_', '_', '_', 't', 'o', '_'},
							{'_', '_', '_', '_', '_', 'O', 'K', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', 'A', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'}};		

		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();
		
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add(table[i][j]);
		}
		
		State st = new State(vector, true);
		
		Tablero tablero = new Tablero(true, true);		
		
	}

}
