package programas;

import java.util.Vector;

import sources.*;

public class NuevoMovimiento {

	public static void main(String[] args) {

		State state = State.INITIAL_STATE();

		char table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'k', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'o', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'K', '_', '_', '_', '_', '_', '_', '_'}};		

		//State state = new State(table, true);	
		
		Vector<Movimiento> moves = new Vector<Movimiento>();
		Vector<State> states = new Vector<State>();
		
		int i = 0;
		
		/*state.move(new Movimiento((byte) 6,(byte) 3,(byte) 2,(byte) 3));
		
		state.print();
		
		for(Movimiento m1 : state.next_states()) {
			char a1 = state.getBoard()[m1.getX2()][m1.getY2()];
			state.move(m1);
			state.print();
			state.unmove(m1, a1);
		}*/
		for(Movimiento m1 : state.next_states()) {
			state.move(m1);
			for(Movimiento m2 : state.next_states()) {
				state.move(m2);
				
				for(Movimiento m3 : state.next_states()) {
					state.move(m3);
					
					try {Thread.sleep(30);} catch (InterruptedException e) {e.printStackTrace();}
					state.print();
					i++;
					states.add(state.clone());
					
					state.unmove(m3);
					state.print();
					System.out.println("");
					System.out.println("");
					System.out.println("");
				}
				state.unmove(m2);
			}
			state.unmove(m1);
		}
		
			
		System.out.println(i);
	
		
		//4897256
		
		/*
		 * for(Movimiento m4 : state.next_states()) {
						state.move(m4);
						for(Movimiento m5 : state.next_states()) {
							state.move(m5);
							moves.add(m5);
							state.unmove(m5);
						}
						state.unmove(m4);
					}
		 */
	}

}
