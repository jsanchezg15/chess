package sources1;

import sources141.Jugador141;
import sources141.State141;
import sources146.Jugador146;
import sources146.State146;

public class Partida {

	public static void main(String[] args) {

		State141 st1 = new State141(State146.INITIAL_STATE().getBoard(), true);
		State146 st2 = null;
		
		Jugador141 j1 = new Jugador141(st1);
		Jugador146 j2 = new Jugador146(st2);
		
		int i = 0; 
		
		while(i < 100 || st1.calculate_equilibrium() == 0 || st1.calculate_equilibrium() == Double.MAX_VALUE  || st2.calculate_equilibrium() == 0 || st2.calculate_equilibrium() == Double.MAX_VALUE) {
			
			st2 = new State146( j1.move(st1, 10).clone_board(), false );
			
			st2.print();
			
			st1 = new State141( j2.move(st2, 10).clone_board(), true );
			
			st1.print_small();
			
			System.out.println("Turno: " + i++);
		}
		
	}

}
