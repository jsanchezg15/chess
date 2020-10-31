package sources;

import java.util.Scanner;

public class PruebasMovimientosDesaparecen {

	public static void main(String[] args) {

		char table[][] = { 	{'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};


		State state = new State(table, true);
				
		state.print_small();
		
		System.out.println("=================================================");

		
		while(true) {
			
			System.out.println("==================== BLANCAS ===================");
			
			Scanner sc = new Scanner(System.in);
			
			for(State st : state.next_states())
				st.print_small();
			
			System.out.println(state.next_states().size());
			 
			int i1, i2, j1, j2;
			
			do {
				System.out.println("Te toca mover.");
				i1 = sc.nextInt();
				j1 = sc.nextInt();
				i2 = sc.nextInt();
				j2 = sc.nextInt();
				
				if(state.exist_move(state.move(i1, j1, i2, j2)))
					state = state.move(i1, j1, i2, j2);
				else {
					System.out.println("Ese movimiento no es posible");
					i1 = 0;
				}
					
			} while(i1 < 1 || i2 < 1 || j1 < 1 || j2 < 1 || i1 > 8 || i2 > 8 || j1 > 8 || j2 > 8);
			
			state.print_small();

			System.out.println("==================== NEGRAS ====================");

			state = state.next_states().get(0);
			
			state.print_small();
			
			System.out.println("=================================================");

		}
	}

}
