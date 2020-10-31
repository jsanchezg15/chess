package sources;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

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
		
		
					
		int turnos = 1, blancas = 0, negras = 0, empate = 0;
		
		for(int i = 0; i < 1000; i++) {
			
			State white, black;

			white = new State(table, true);
			
			while(true) {
												
				State min_max;
				
				min_max = white.getMinMax(3);
					
				white = min_max.getMove();
				
				black = new State(white.clone_board(), false);
											
				//QWERTYU
												
				min_max = black.getMinMax(3);
				
				black = min_max.getMove();
				
				white = new State(black.clone_board(), true);

				if(white.calculate_equilibrium() == Double.MAX_VALUE) {
					blancas++;
					break;
				}
				else if(white.calculate_equilibrium() == 0) {
					negras++;
					break;
				}
				else if(turnos == 1000) {
					empate++;
					break;
				}
				
				turnos++;
			}
		
			turnos = 0;
			System.out.println(i);
		}
		
		System.out.println("Blancas: " + blancas);
		System.out.println("Negras:  " + negras);
		System.out.println("Empates: " + empate);
	}

}
