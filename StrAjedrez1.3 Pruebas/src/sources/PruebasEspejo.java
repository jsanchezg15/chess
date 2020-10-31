package sources;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PruebasEspejo {

	public static void main(String[] args) {
	

		char table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};

		char table2[][] = {  	{'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r'},
								{'r', 'm', 'r', 'm', 'm', 'r', 'm', 'r'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'R', 'M', 'R', 'M', 'M', 'R', 'M', 'R'},
								{'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R'}};
		
		char table3[][] = {  	{'o', 'o', 'o', 'o', 'm', 'o', 'o', 'o'},
								{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
								{'O', 'O', 'O', 'O', 'M', 'O', 'O', 'O'}};
		
		State white, black;

		white = new State(table, true);
		
		white.print_small();

		int turnos = 1;		
		
		
		try {
			PrintWriter printer = new PrintWriter("espejo.txt");
			
			
			while(white.calculate_equilibrium() != Double.MAX_VALUE) {
				
				long time = System.currentTimeMillis();

				printer.println("==================== NEGRAS =====================");
								
				State min_max;
				
				min_max = white.getMinMax(4);
					
				white = min_max.getMove();
				
				black = new State(white.clone_board(), false);
							
				black.print_small();
				System.out.println(black.getEquilibium());

				printer.print(black.print_small_str());
				
				printer.println("Milisec: " + (System.currentTimeMillis() - time));
				
				System.out.println("Milisec: " + (System.currentTimeMillis() - time));
				
				printer.println("==================== BLANCAS ====================");

				time = System.currentTimeMillis();
								
				min_max = black.getMinMax(4);
				
				black = min_max.getMove();
				
				white = new State(black.clone_board(), true);

				white.print_small();
				System.out.println(white.getEquilibium());

				printer.print(white.print_small_str());

				printer.println("Milisec: " + (System.currentTimeMillis() - time));
				
				System.out.println("Milisec: " + (System.currentTimeMillis() - time));

				printer.println("==================== Turnos: " + turnos++ + " ====================");
			}
			
			
			printer.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		
		
	}

}
