package jugadores;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sources.MiBoolean;
import sources.State;

public class Jugador11 {

	public State state;
	public Vector<State> states;
	public Vector<Double> valores;
	public Vector<Integer> ramas;
	public Vector<Double> valor_de_la_rama;
	public Vector<Integer> ramas_posibles;
	
	public Jugador11() {
		this.states = new Vector<State>();
		this.valores = new Vector<Double>();
		this.ramas = new Vector<Integer>();
		this.valor_de_la_rama = new Vector<Double>();
		this.ramas_posibles = new Vector<Integer>();
	}
	
	public State move(State state, float timer) {
		this.state = state;
		this.states = new Vector<State>();
		this.valores = new Vector<Double>();
		this.ramas = new Vector<Integer>();
		this.valor_de_la_rama = new Vector<Double>();
		this.ramas_posibles = new Vector<Integer>();
		return this.move(timer);
	}
	
	public State move(float timer) {
		
		long time = System.currentTimeMillis();
		
		MiBoolean stop = new MiBoolean(false);			
		
		for(int i = 0; i < 16; i++)
			calcular_movimiento(5, stop, this.state, System.currentTimeMillis(), i + 1);
		
		for(int i = 0; i < 16; i++)
			confirmar_movimiento(3, stop, this.states.get(i), System.currentTimeMillis());

		for(Integer i : this.ramas)
			if(!this.ramas_posibles.contains(i)) 
				this.ramas_posibles.add(i);
		
		
		for(int i = 0; i < this.ramas_posibles.size(); i++) {
			double val = 1.0;
			
			for(int j = 0;  j < this.ramas.size(); j++) 
				if(this.ramas_posibles.get(i) == this.ramas.get(j)) 
					val *= this.valores.get(j);
				
			this.valor_de_la_rama.add(val);
		}
		
		int i = this.ramas_posibles.get(0);
		double d = this.valor_de_la_rama.get(0);
		
		for(int j = 1; j < this.valor_de_la_rama.size(); j++) {
			if(this.valor_de_la_rama.get(j) > d) {
				d = this.valor_de_la_rama.get(j);
				i = this.ramas_posibles.get(j);
			}
		}
		
		//try {Thread.sleep((long) (timer * 1000));} catch (InterruptedException e1) {e1.printStackTrace();}
		
		//stop.value = true;
		
		System.out.println("===========");
		
		System.out.println(System.currentTimeMillis() - time);
		
		System.out.println(i);
		
		return this.state.next_states().get(i);
	}

	public void calcular_movimiento(int profundidad, MiBoolean stop, State state, long time, int iteracion) {
								
		State min_max = state.getMinMax(profundidad, stop);
			
		this.states.add(min_max);
			
		min_max.getParent().getParent().next_states().removeElement(min_max.getParent());
			
		System.out.println(iteracion);
		
		//System.out.println("Tiempo: " + (System.currentTimeMillis() - time) + "\tProfundidad: " + profundidad);
	}
	
	public void confirmar_movimiento(int profundidad, MiBoolean stop, State state, long time) {
				
		State min_max = state.getMinMax(profundidad, stop);
			
		this.valores.add(  min_max.getEquilibium()  );
		
		State rama = min_max.getMove();
		
		int i = 0;
		for(State st : this.state.next_states()) {
			if(st.board_equals(rama)) {
				this.ramas.add(i);
				break;
			}
			i++;
		}

				
	}
	
	/*public void confirmar_movimiento(int profundidad, MiBoolean stop, State state, long time) {
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		State s = new State(state.clone_board(), state.white_moves);
				
		exec.submit(() -> {				
			State min_max = s.getMinMax(profundidad, stop);
			
			this.states.add(  new State(  min_max.getMove().clone_board(), state.white_moves  )  );
			
			min_max.getParent().getParent().next_states().removeElement(min_max.getParent());
			
			System.out.println("Tiempo: " + (System.currentTimeMillis() - time) + "\tProfundidad: " + profundidad);
		});		
	}*/
	
	
}
