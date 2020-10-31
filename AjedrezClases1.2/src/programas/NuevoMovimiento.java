package programas;

import java.util.Scanner;
import java.util.Vector;

import sources.*;

public class NuevoMovimiento {

	public static void main(String[] args) throws InterruptedException {

		State state = State.INITIAL_STATE();

		char table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', 'o', '_', '_', '_'},
							{'k', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', 'O', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', 'o', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'K', '_', '_', '_', '_', '_', '_', '_'}};		

		//State state = new State(table, false);	
		
		long time = System.currentTimeMillis();

		Movimiento min_max[] = new Movimiento[4];
		
		Vector<Movimiento> next1 = new Vector<Movimiento>();
		
		min_max[0] = next1.remove(0);
				
		for(Movimiento m1 : state.next_states()) {
			state.move(m1);
			for(Movimiento m2 : state.next_states()) {
				state.move(m2);
				for(Movimiento m3 : state.next_states()) {
					state.move(m3);
					for(Movimiento m4 : state.next_states()) {				
						state.move(m4);
						
						state.unmove(m4);
					}
					state.unmove(m3);
				}
				state.unmove(m2);
			}
			state.unmove(m1);
		}
		
		
		//state.getMinMax(4).print();

		System.out.println(System.currentTimeMillis() - time);
	
		
	 /* En profundidad 6 tiene el mismo número de estados que la versión 1.1 pero tarda 20 seg
		
		int i = 0;
		
		for(Movimiento m1 : state.next_states()) {
			state.move(m1);
			for(Movimiento m2 : state.next_states()) {
				state.move(m2);
				for(Movimiento m3 : state.next_states()) {
					state.move(m3);
					for(Movimiento m4 : state.next_states()) {
						state.move(m4);
						for(Movimiento m5 : state.next_states()) {
							state.move(m5);
							for(Movimiento m6 : state.next_states()) {
								state.move(m6);
								i++;
								state.unmove(m6);
							}					
							state.unmove(m5);
						}					
						state.unmove(m4);
					}
					state.unmove(m3);
				}
				state.unmove(m2);
			}
			state.unmove(m1);
		}
		
		System.out.println(i);*/
		
	}


	
}









/*for(Movimiento m1 : state.next_states()) {
state.move(m1);

state.print();
System.out.println("Blancas: " + state.white_pts);
System.out.println("Negras:  " + state.black_pts);

state.unmove(m1);
System.out.println("Blancas: " + state.white_pts);
System.out.println("Negras:  " + state.black_pts);
System.out.println("\n\n");
}

state.print();
System.out.println("Blancas: " + state.white_pts);
System.out.println("Negras:  " + state.black_pts);*/


/*for(Movimiento m1 : state.next_states()) {
state.move(m1);
state.print();

for(Movimiento m2 : state.next_states()) {
	state.move(m2);
	state.print();
	state.unmove(m2);
}
state.unmove(m1);
System.out.println("=================================================================");
System.out.println("=================================================================");
System.out.println("=================================================================");
Scanner sc = new Scanner(System.in);
sc.next();
}*/