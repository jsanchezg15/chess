package sources;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class PruebasEspejo {

	public static void main(String[] args) {
	

		String table1[][] = {   {"<t", "<C", "<A", "<Q", "<k", "<A", "<C", "<t"},
								{"<O", "<O", "<O", "<O", "<O", "<O", "<O", "<O"},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"-O", "-O", "-O", "-O", "-O", "-O", "-O", "-O"},
				   				{"-t", "-C", "-A", "-Q", "-k", "-A", "-C", "-t"}};

		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

		for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table1[i][j]);
        }
		
		State white, black;

		white = new State(table, true);
		
		white.print_small();

		int turnos = 1;		
		
		
		try {
			PrintWriter printer = new PrintWriter("espejo.txt");
			
			
			while(white.calculate_equilibrium() != Double.MAX_VALUE && white.calculate_equilibrium() != 0) {
				
				long time = System.currentTimeMillis();

				printer.println("==================== NEGRAS =====================");
								
				State min_max;
				
				min_max = white.getMinMax(4);
					
				white = min_max.getMove();
				
				black = new State(white.clone_board(), false);
							
				black.print_small();

				printer.print(black.print_small_str());
				
				printer.println("Milisec: " + (System.currentTimeMillis() - time));
				
				printer.println("==================== BLANCAS ====================");

				time = System.currentTimeMillis();
								
				min_max = black.getMinMax(4);
				
				black = min_max.getMove();
				
				white = new State(black.clone_board(), true);

				white.print_small();
				
				printer.print(white.print_small_str());

				printer.println("Milisec: " + (System.currentTimeMillis() - time));
				
				printer.println("==================== Turnos: " + turnos++ + " ====================");
			}
			
			
			printer.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		
		
	}

}
