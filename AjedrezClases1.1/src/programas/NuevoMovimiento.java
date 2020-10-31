package programas;

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
		
		Vector<State> states = new Vector<State>();
		
		int i = 0;
		
		//state.move(new Movimiento((byte) 6,(byte) 3,(byte) 2,(byte) 3, state.getBoard()[2][3]));
		
		state.print();
		
		/*for(Movimiento m1 : state.next_states()) {
			state.move(m1);
			state.print();
			for(Movimiento m2 : state.next_states()) {
				state.move(m2);
				state.print();
				state.unmove(m2);
			}
			state.unmove(m1);
			System.out.println("===========================");
			System.out.println("===========================");
			System.out.println("===========================");
		}*/
		long time = System.currentTimeMillis();
		
		for(Movimiento m1 : state.next_states()) {
			state.move(m1);
			
			for(Movimiento m2 : state.next_states()) {
				state.move(m2);
				
				for(Movimiento m3 : state.next_states()) {
					state.move(m3);
					
					for(Movimiento m4 : state.next_states()) {
						state.move(m4);
						
						i++;				
						state.unmove(m4);
					}	
					state.unmove(m3);
				}	
				state.unmove(m2);
			}
			state.unmove(m1);
		}
		state.print();
			
		System.out.println(i);
		
		/*Movimiento moves[] = new Movimiento[4];
		
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
		state.print();*/
			
		System.out.println(i);
		
		
		//state.getMinMax(4).print();
		
		System.out.println(System.currentTimeMillis() - time);

		
		// 4897256 Hay que hacer que los peones capturables al paso dejen de serlo a partir de un turno porque no coincide el número de estados con otros proyectos (pero por poco :3)
		
		// 4.896.998 Estos son los estados que hay en profundidad 5 si no existe la captura al paso (Coincide con los estados sin captura al paso de proyectos anetriores :3 )
		
		/* 
		 * 120.909.581 Igual que la linea de arriba pero en p6 (no he probado con otros proyectos porque peta pero este ha tardado solo 17 seg :33333) 
		 * Luego ha tardado 30 seg dos veces... :(( 
		 * Pero luego he reiniciado y ha tardado 18 seg!! :33
		 */
	}

}
