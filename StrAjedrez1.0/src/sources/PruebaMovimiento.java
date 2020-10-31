package sources;

import java.util.Vector;

public class PruebaMovimiento {

public static void main(String[] args) {
		
		
		String table1[][] = {   {"<t", "", "", "", "<k", "", "", "<t"},
								{"<O", "", "", "", "", "", "", "<O"},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"-O", "", "", "", "", "", "", "-O"},
				   				{"-t", "", "", "", "-k", "", "", "-t"}};
		
		String table2[][] = {   {"<k", "", "", "", "", "<t", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", "<A"},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"", "", "", "", "", "", "", ""},
				   				{"-t", "", "", "", "-k", "", "", "-t"}};
		
		String table3[][] = {   {"", "<T", "<A", "<Q", "<k", "<A", "<C", "<t"},
								{"<O", "<O", "<O", "<O", "<O", "<O", "<O", "<O"},
				   				{"", "", "", "", "", "", "", ""},
				   				{"<C", "", "", "", "", "", "", ""},
				   				{"-O", "-O", "", "", "", "", "", ""},
				   				{"", "", "-O", "", "", "", "", ""},
				   				{"", "", "", "-O", "-O", "-O", "-O", "-O"},
				   				{"-t", "-C", "-A", "-Q", "-k", "-A", "-C", "-t"}};
		
		String table4[][] = {   {"<t", "<t", "<t", "<t", "<t", "<t", "<t", "<t"},
				{"<t", "<k", "<t", "<k", "<k", "<t", "<k", "<t"},
   				{"", "", "", "", "", "", "", ""},
   				{"", "", "", "", "", "", "", ""},
   				{"", "", "", "", "", "", "", ""},
   				{"", "", "", "", "", "", "", ""},
   				{"-t", "-k", "-t", "-k", "-k", "-t", "-k", "-t"},
   				{"-t", "-t", "-t", "-t", "-t", "-t", "-t", "-t"}};
		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

		for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table4[i][j]);
        }
		
		State state = new State(table, true);		
		
		state.print_small();
		
		System.out.println("=================================================");

		int i = 0;
		
		for(State state1 : state.next_states())
			for(State state2 : state1.next_states())
				for(State state3 : state2.next_states())
					for(State state4 : state3.next_states())
						i++;
		
		System.out.println(i);
	
		/*for(State st : state.next_states())
			st.print_small();

		for(int i = 0; i < 8; i++) {
			System.out.println();
	
			for(int j = 0; j < 8; j++)
				System.out.print(state.is_menaged(i, j, true) ? "1 " : "0 ");
		}*/
	}	
}
