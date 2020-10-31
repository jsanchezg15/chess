package sources;

import java.util.Scanner;
import java.util.Vector;

public class Pruebas {

	public static void main(String[] args) {
		
		
		String table1[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "<C>", "<T>"},
								{"<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>", "<O>"},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "-O-", "   ", "   ", "   "},
				   				{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
				   				{"-O-", "-O-", "-O-", "-O-", "   ", "-O-", "-O-", "-O-"},
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
		
		String table4[][] = {   {"<T>", "<C>", "<A>", "<Q>", "<K>", "<A>", "   ", "<T>"},
								{"<O>", "   ", "   ", "   ", "   ", "<O>", "<O>", "<O>"},
								{"   ", "<O>", "   ", "   ", "   ", "<C>", "   ", "   "},
								{"   ", "   ", "   ", "   ", "<O>", "   ", "   ", "   "},
								{"   ", "   ", "   ", "<O>", "   ", "   ", "   ", "   "},
								{"   ", "-O-", "-O-", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "-O-", "-O-", "-O-", "-O-", "-O-"},
								{"-T-", "-C-", "-A-", "-Q-", "-K-", "-A-", "-C-", "-T-"}};
		
		String table5[][] = {   {"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "<K>"}};
		
		String table7[][] = {   {"<K>", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "<O>", "-O-", "   ", "   "},
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
		
		
		while(true) {
			
			long time = System.currentTimeMillis();

			Vector<Vector<String>> table = new Vector<Vector<String>>();

	        for(int i = 0; i < 8; i++) {
	            table.add(new Vector<String>());
	            for(int j = 0; j < 8; j++)
	            	table.get(i).add(table1[i][j]);
	        }
	        
	        State state = new State(table, false);
	        State oposite_state = new State(table, true);
				
			System.out.println("Next states: " + state.next_states().size());
			
					
			/*if(state.next_states().size() + oposite_state.next_states().size() <= 20)
				state.getMinMax(9).show_path();
			else if(state.next_states().size() + oposite_state.next_states().size() <= 30)
				state.getMinMax(8).show_path();
			else if(state.next_states().size() + oposite_state.next_states().size() <= 40)
				state.getMinMax(7).show_path();
			else if(state.next_states().size() + oposite_state.next_states().size() <= 50)
				state.getMinMax(6).show_path();
			else if(state.next_states().size() + oposite_state.next_states().size() <= 60)
				state.getMinMax(5).show_path();
			else if(state.next_states().size() + oposite_state.next_states().size() <= 70)
				state.getMinMax(4).show_path();
			else
				state.getMinMax(3).show_path();*/

			state.getMinMax(5).show_path();
			
			System.out.println("================================================================");
			System.out.println("Milisec: " + (System.currentTimeMillis() - time));
			
			Scanner sc = new Scanner(System.in);
			
			int i1 = sc.nextInt();
			System.out.println("=");
			int j1 = sc.nextInt();
			System.out.println("=");
			int i2 = sc.nextInt();
			System.out.println("=");
			int j2 = sc.nextInt();
			System.out.println("=");
			
			table1[i2 - 1][j2 - 1] = table1[i1 - 1][j1 - 1];
			table1[i1 - 1][j1 - 1] = "   ";
			
			i1 = sc.nextInt();
			System.out.println("=");
			j1 = sc.nextInt();
			System.out.println("=");
			i2 = sc.nextInt();
			System.out.println("=");
			j2 = sc.nextInt();
			System.out.println("=");
			
			table1[i2 - 1][j2 - 1] = table1[i1 - 1][j1 - 1];
			table1[i1 - 1][j1 - 1] = "   ";
			
		}
		
		
	}

}
