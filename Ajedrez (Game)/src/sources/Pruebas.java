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
		
		String table2[][] = {   {"   ", "   ", "   ", "<T>", "<K>", "   ", "   ", "<T>"},
								{"   ", "<O>", "<O>", "   ", "   ", "<O>", "   ", "<O>"},
				   				{"   ", "   ", "<C>", "   ", "   ", "   ", "<O>", "   "},
				   				{"   ", "   ", "   ", "   ", "<O>", "   ", "   ", "   "},
				   				{"-O-", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "-Q-", "   "},
				   				{"   ", "   ", "   ", "<Q>", "   ", "-O-", "-O-", "-O-"},
				   				{"-T-", "   ", "   ", "   ", "   ", "-K-", "-C-", "-T-"}};
		
		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

		for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table1[i][j]);
        }
		
		State state = new State(table, true);
		State oposite_state = new State(table, false);
		
		state.print_small();
		
		System.out.println("=================================================");

		
		while(true) {
			
			long time = System.currentTimeMillis();
				
			System.out.println("Next states: " + state.next_states().size() + " - " + oposite_state.next_states().size());
			
			// L�ETE LO QUE HACE ESTA FUNCI�N PORUQE PUEDE SER �TIL!!
			// state.next_states().trimToSize();		(.trimToSize())
			
			
			
			//int n = (int) Math.floor(  (1.4 + 13.7) / Math.log10(  state.next_states().size() * oposite_state.next_states().size()  )  );
			//(1'4 + 13'7) == 2 * log(5 * 7.000.000)
			
			int n = (int) Math.floor(  (1.8 + 13.7) / Math.log10(  state.next_states().size() * oposite_state.next_states().size()  )  );

			if(state.next_states().size() * oposite_state.next_states().size() == 1)
				n = 1;
			
			
			System.out.println("Iterations: " + n);
			
			State min_max;
			
			try {
				min_max = state.getMinMax(n);
				if(System.currentTimeMillis() - time < 1000) {
					System.out.println("Iterations: " + (n + 1));
					min_max = state.getMinMax(n + 1);
				}
			} 
			catch(OutOfMemoryError e) {
				System.out.println("Iterations: " + (n - 1));
				min_max = state.getMinMax(n - 1);
			}
			
			state = min_max.getMove();
			
			state.print_small();
			
			System.out.println("=================================================");
	
			
			System.out.println("Milisec: " + (System.currentTimeMillis() - time));
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Te toca mover.");
 
			int i1 = sc.nextInt();
			System.out.println("=");
			int j1 = sc.nextInt();
			System.out.println("=");
			int i2 = sc.nextInt();
			System.out.println("=");
			int j2 = sc.nextInt();
			System.out.println("=");
			
			state = state.move(i1, j1, i2, j2);
			oposite_state = new State(state.getBoard(), false);
			
			state.print_small();
			
			System.out.println("=================================================");

		}
		
		
	}

}
