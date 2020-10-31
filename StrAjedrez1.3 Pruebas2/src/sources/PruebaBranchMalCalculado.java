package sources;

import java.util.Scanner;

public class PruebaBranchMalCalculado {

	public static void main(String[] args) {


		char table[][] = {  {'_', 'C', 'a', '_', 'k', '_', 'c', 't'},
							{'_', '_', '_', '_', '_', '_', 'o', 'o'},
							{'_', '_', '_', '_', 'o', 'o', '_', '_'},
							{'T', 'O', '_', '_', '_', '_', '_', '_'},
							{'_', '_', 'c', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'O', '_'},
							{'_', '_', '_', '_', '_', 'O', 'A', 'O'},
							{'_', '_', '_', 'T', '_', '_', 'K', '_'}};		

		// Amenacé con el alfil al rey y me ponía que solo tenía un branch2 de 7.25????

		char table2[][] = { {'_', 'C', 'a', '_', 'k', '_', 'c', 't'},
							{'_', '_', '_', '_', '_', '_', 'o', 'o'},
							{'_', '_', 'A', '_', 'o', 'o', '_', '_'},
							{'T', 'O', '_', '_', '_', '_', '_', '_'},
							{'_', '_', 'c', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'O', '_'},
							{'_', '_', '_', '_', '_', 'O', '_', 'O'},
							{'_', '_', '_', 'T', '_', '_', 'K', '_'}};		
		
		State st = new State(table2, false);
		
		int branch1 = st.next_states().size();
		
		int branch2 = 0;
		
		for(State state : st.next_states()) {
			branch2 += state.next_states().size();
			
			System.out.println("=============================================");
			System.out.println("=============================================");
			System.out.println("=============================================");
			state.print_small();
			System.out.println("=============================================");
			System.out.println("=============================================");
			
			for(State state2 : state.next_states())
				state2.print_small();
			
			System.out.println(branch2);
			
			Scanner sc = new Scanner(System.in);
			
			String s = sc.next();
		}
			
		
		System.out.println(branch1 + "\n" + branch2);
		
	}

}
