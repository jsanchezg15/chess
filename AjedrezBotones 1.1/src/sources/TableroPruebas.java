package sources;

public class TableroPruebas {

	public static void main(String[] args) {

		char table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		
		
		State state = new State(table, true);
		
		Tablero tablero = new Tablero(state);
		
	}

}
