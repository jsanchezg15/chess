package sources;

import java.util.Vector;

public class PruebasTorre {

	public static void main(String[] args) {
		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

		String table1[][] = { 	{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"-K-", "   ", "   ", "<T>", "   ", "   ", "<K>", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "-O-", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "}};
		
		
		for(int i = 0; i < 8; i++) {
            table.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	table.get(i).add(table1[i][j]);
        }
		
		State s1 = new State(table, false);
		
		s1.print_small();
		System.out.println();
		
		for(State state : s1.rook_movement(2, 3)) {
			state.print_small();
			System.out.println();
		}
		
	}

}
