package sources;

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
		
		String table2[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "   ", "<T>"},
								{"<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "-O-", "<O>"},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "<A>", "   ", "   ", "   ", "   "},
								{"   ", "   ", "-O-", "   ", "   ", "<A>", "   ", "   "},
								{"   ", "   ", "   ", "<A>", "   ", "   ", "   ", "   "},
								{"-O-", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-"},
								{"-T-", "-C-", "-A-", "-Q-", "-K-", "-A-", "-C-", "-T-"}};
        
		String table3[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "<C>", "<T>"},
								{"<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>"},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "<O>", "   ", "   ", "   ", "   "},
								{"   ", "   ", "-O-", "   ", "   ", "<O>", "   ", "   "},
								{"   ", "   ", "   ", "<O>", "   ", "   ", "   ", "   "},
								{"-O-", "<O>", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-"},
								{"-T-", "   ", "-A-", "-Q-", "-K-", "-A-", "-C-", "-T-"}};
		
		String table4[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "<C>", "<T>"},
								{"<O>", "   ", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>"},
								{"   ", "<O>", "   ", "   ", "   ", "   ", "   ", "   "},
								{"-O-", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-", "-O-"},
								{"-T-", "-C-", "-A-", "-Q-", "-K-", "-A-", "-C-", "-T-"}};
		
		String table5[][] = {   {"<K>", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "-O-", "   ", "   "},
								{"   ", "   ", "   ", "   ", "<O>", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "-T-", "   ", "   ", "   ", "   ", "   ", "-K-"}};
		
		String table6[][] = {   {"   ", "   ", "   ", "   ", "<K>", "   ", "   ", "<T>"},
								
								{"<O>", "<T>", "   ", "   ", "   ", "<O>", "<O>", "<O>"},
								
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								
								{"   ", "   ", "<A>", "   ", "   ", "<A>", "   ", "   "},
								
								{"-T-", "   ", "-O-", "   ", "<O>", "<Q>", "   ", "   "},
								
								{"   ", "   ", "-K-", "   ", "   ", "   ", "   ", "   "},
								
								{"   ", "-O-", "   ", "   ", "-Q-", "   ", "-O-", "-O-"},
								
								{"   ", "-C-", "-A-", "   ", "   ", "<C>", "-C-", "-T-"}};
		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

        for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table1[i][j]);
        }
		
		
		State s1 = new State(table, true);
		
		double i = s1.getEquilibium();
		
		System.out.println(i);
		
		s1.print_small();
		
		/*for(State state : s1.next_states()) {
			state.print_small();
			System.out.println();
		}*/
		
		
		
		
		// Prueba de cuanto tardaría en anticiparse un solo movimiento
		
		long time = System.currentTimeMillis();
		
		Vector<State> states = new Vector<State>();
		
		//State best = s1;
		
		for(State state_1 : s1.next_states()) {
			for(State state_2 : state_1.next_states())
				for(State state_3 : state_2.next_states())
					for(State state_4 : state_3.next_states())
						//for(State state_5 : state_4.next_states())
							states.add(state_4);
							//for(State state_6 : state_5.next_states())
								//for(State state_7 : state_6.next_states())
									//if(state_5.getEquilibium() > best.getEquilibium())
										//best = state_5;
		}
		
		System.out.println(states.size());
		System.out.println("Milisec: " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		
		/*State best = states.get(0);
		for(State state : states)
			if(state.getEquilibium() > best.getEquilibium())
				best = state;
		
		//best.show_path();
		
		System.out.println(best.getEquilibium());
		System.out.println("Milisec: " + (System.currentTimeMillis() - time));*/
		
		// Intentado poner la reina blanca en (E5) 
		/*State best = states.get(0);
		for(State state : states)
			if(state.heuristic() > best.heuristic())
				best = state;
		
		best.show_path();
		
		System.out.println(best.getEquilibium());
		System.out.println("Milisec: " + (System.currentTimeMillis() - time));

		/*Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("================================================================");
			states.get(sc.nextInt()).show_path();
		}*/
		
		
		s1.getMinMax().show_path();
		System.out.println("Milisec: " + (System.currentTimeMillis() - time));
		
	}

}
