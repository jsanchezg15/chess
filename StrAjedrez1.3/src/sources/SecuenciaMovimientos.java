package sources;

public class SecuenciaMovimientos {

	public static void main(String[] args) {

		char table1[][] = { {'k', '_', '_', '_', '_', 'A', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', 'o', '_', '_'},
							{'_', '_', '_', '_', 'o', '_', '_', '_'},
							{'_', '_', '_', 'o', 'O', '_', '_', 'o'},
							{'o', '_', '_', 'K', '_', 'O', '_', 'O'},
							{'a', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'}};
		
		char table2[][] = { {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', '_', 'o', 'o', 'o'},
							{'_', '_', '_', '_', 'o', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', 'O', '_', '_', '_', '_'},
							{'O', 'O', 'O', '_', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};


		State state = new State(table1, true);		

		State min_max = state.getMinMax(4);

		while(min_max.getParent() != null) {
			min_max.print_small();

			min_max = min_max.getParent();
		}
		
	}

}
