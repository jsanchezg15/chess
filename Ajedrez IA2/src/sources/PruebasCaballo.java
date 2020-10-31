package sources;

import java.util.Vector;

public class PruebasCaballo {

	public static void main(String[] args) {
		
		Vector<Vector<String>> table = new Vector<Vector<String>>();

		String table1[][] = { 	{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "<O>", "   ", "<O>", "   ", "   ", "   "},
								{"   ", "<O>", "   ", "   ", "   ", "<O>", "   ", "   "},
								{"   ", "   ", "   ", "-C-", "   ", "   ", "   ", "   "},
								{"   ", "<O>", "   ", "   ", "   ", "<O>", "   ", "   "},
								{"   ", "   ", "<O>", "   ", "<O>", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "},
								{"   ", "   ", "   ", "   ", "   ", "   ", "   ", "   "}};


		for(int i = 0; i < 8; i++) {
			table.add(new Vector<String>());
			for(int j = 0; j < 8; j++)
				table.get(i).add(table1[i][j]);
		}

		State s1 = new State(table, true);

		s1.print_small();
		System.out.println();

		for(State state : s1.knight_movement(3,3)) {
			state.print_small();
			System.out.println();
		}

	}

}
