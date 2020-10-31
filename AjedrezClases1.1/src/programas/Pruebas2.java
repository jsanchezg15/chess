package programas;

import jugadores.Jugador11;
import sources.State;

public class Pruebas2 {

	public static void main(String[] args) {

		State state = State.INITIAL_STATE();
		
		state = state.move(7, 5, 5, 5);
		
		
		/*long time = System.currentTimeMillis();
		
		state.print();

		State min_max = state.getMinMax(5);
		
		min_max.print();
		min_max.getMove().print();

		min_max.getParent().next_states().removeElement(min_max);
		
		time = System.currentTimeMillis() - time;
		
		System.out.println("Tiempo: " + time);
		
		System.out.println("==============================================");
		System.out.println("==============================================");
		System.out.println("==============================================");
		
		time = System.currentTimeMillis();
		
		state.print();

		min_max = state.getMinMax(5);
		
		min_max.print();
		min_max.getMove().print();

		time = System.currentTimeMillis() - time;
		
		System.out.println("Tiempo: " + time);*/
		
		Jugador11 j1 = new Jugador11();
		
		j1.move(state, 10);
		
		/*for(State i : j1.states)
			i.print();*/
		
		
		for(Double d : j1.valor_de_la_rama)
			System.out.println(d);
		
		for(Integer i : j1.ramas)
			System.out.println(i);

	}

}
