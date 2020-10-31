package sources;

import java.util.Scanner;
import java.util.Vector;

public class PodaVsNormal {

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
		State state2 = new State(table, true);		
		
		state.print_small();
		
		System.out.println("=================================================");

		
		while(true) {
			
			System.out.println("==================== BLANCAS ===================");

			
			long time = System.currentTimeMillis();
				
			State min_max, min_max2;
			
			min_max = state.getMinMax(4);
				
			state = min_max.getMove();
			
			state.print_small();

			System.out.println("Milisec: " + (System.currentTimeMillis() - time));

			time = System.currentTimeMillis();
			
			System.out.println("=================================================");
	
			//min_max2 = state2.getMinMaxSinPoda(4);
						
			Vector<State> states = new Vector<State>();
						
			for(State state_1 : state2.next_states()) 
				for(State state_2 : state_1.next_states())
					for(State state_3 : state_2.next_states())
						for(State state_4 : state_3.next_states())
							states.add(state_4);
			
				
			min_max2 = state2.getMinMaxSinPoda();
			
			state2 = min_max2.getMove();
			state2.print_small();
			
			System.out.println("Milisec: " + (System.currentTimeMillis() - time));
			
			System.out.println("==================== NEGRAS ====================");

			Scanner sc = new Scanner(System.in);
 
			int i1, i2, j1, j2;
			
			do {
				System.out.println("Te toca mover.");
				
				/*System.out.println("=================================================");
				min_max.show_path();
				System.out.println("=================================================");
				min_max2.show_path();
				System.out.println("=================================================");*/

				i1 = sc.nextInt();
				j1 = sc.nextInt();
				i2 = sc.nextInt();
				j2 = sc.nextInt();
				
				if(i1 == 10) {
					System.out.println("=================================================");
					min_max.show_path();
					System.out.println("=================================================");
					min_max2.show_path();
				}
					
			} while(i1 < 1 || i2 < 1 || j1 < 1 || j2 < 1 || i1 > 8 || i2 > 8 || j1 > 8 || j2 > 8);
			
			state = state.move(i1, j1, i2, j2);
			state2 = state2.move(i1, j1, i2, j2);
			
			state.print_small();
			
			System.out.println("=================================================");

		}
		
	}

}
