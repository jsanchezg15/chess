package sources;

import java.util.Scanner;
import java.util.Vector;

public class Pruebas {

	public static void main(String[] args) {
		
		
		String table1[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "<C>", "<T>"},
								{"<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>"},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"-O-", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-"},
				   				{"-T-", "-C-", "-A-", "-Q-", "-K-", "-A-", "-C-", "-T-"}};
		

		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

        for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table1[i][j]);
        }
		
		
		State state = new State(table, true);
						
		state.print_small();
		
		System.out.println("=================================================");

		
		while(true) {
			
			long time = System.currentTimeMillis();
			
			Vector<State> states = new Vector<State>();
			
			for(State state_1 : state.next_states()) {
				for(State state_2 : state_1.next_states())
					for(State state_3 : state_2.next_states())
						for(State state_4 : state_3.next_states())
							states.add(state_4);
			}
			
			State min_max = state.getMinMax();
			
			state = min_max.getMove();
			
			state.print_small();
			
			System.out.println("=================================================");

			System.out.println("Milisec: " + (System.currentTimeMillis() - time));
			
			Scanner sc = new Scanner(System.in);
 
			int i1, i2, j1, j2;
			
			do {
				System.out.println("Te toca mover.");
				i1 = sc.nextInt();
				j1 = sc.nextInt();
				i2 = sc.nextInt();
				j2 = sc.nextInt();
			} while(i1 < 1 || i2 < 1 || j1 < 1 || j2 < 1 || i1 > 8 || i2 > 8 || j1 > 8 || j2 > 8);
			
			state = state.move(i1, j1, i2, j2);
			
			state.print_small();
			
			System.out.println("=================================================");

		}
		
	}

}
