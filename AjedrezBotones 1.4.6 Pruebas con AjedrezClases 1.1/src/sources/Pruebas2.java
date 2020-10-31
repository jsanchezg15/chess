package sources;

import java.util.Vector;

public class Pruebas2 {

	public static void main(String[] args) {

		State state = State.INITIAL_STATE();
		
		Vector<State> states = new Vector<State>();
		
		int i = 0;
		
		long time = System.currentTimeMillis();
		
		/*for(State st1 : state.next_states())
			for(State st2 : st1.next_states())
				for(State st3 : st2.next_states())
					for(State st4 : st3.next_states())
						for(State st5 : st4.next_states())
							i++;
		
		System.out.println(i);*/
		
		state.getMinMaxSinPoda(3).print();
		state.getMinMax(3).print();
		
		System.out.println(System.currentTimeMillis() - time);
		
		//184329
		
		//state = state.move(7, 5, 5, 5);
		
		//state.print();
		
		/*long time = System.currentTimeMillis();
		
		State min_max = state.getMinMax(4);
		
		state.print();
		min_max.print();

		time = System.currentTimeMillis() - time;
		
		System.out.println("Tiempo: " + time);
		
		time = System.currentTimeMillis();
		
		state.print();

		State min_max2 = state.getMinMax(4);
		
		min_max2.print();

		time = System.currentTimeMillis() - time;
		
		System.out.println("Tiempo: " + time);*/
		
		//min_max = min_max.getMove();
		
		//min_max.print();
		
		
		//Jugador jugador = new Jugador(state);
		
		//jugador.move(10).print();
		
		
	}

}
