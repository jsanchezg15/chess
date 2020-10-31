package jugadores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sources.MiBoolean;
import sources.State;

public class Jugador {

	
	private State state;
	
	public Jugador(State state) {
		this.state = state;
	}
	
	public State move(State state, float timer) {
		this.state = state;
		return this.move(timer);
	}
	
	public State move(float timer) {
		
		MiBoolean stop = new MiBoolean(false);			
		
		calcular_movimiento(2, stop, this.state, System.currentTimeMillis());

		try {Thread.sleep((long) (timer * 1000));} catch (InterruptedException e1) {e1.printStackTrace();}
		
		stop.value = true;
		
		System.out.println("===========");
		
		return this.state;
	}

	public void calcular_movimiento(int profundidad, MiBoolean stop, State state, long time) {
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		State s = new State(state.clone_board(), state.white_moves);
				
		exec.submit(() -> {				
			State min_max = s.getMinMax(profundidad, stop);
			
			this.state = new State(min_max.getMove().clone_board(), state.white_moves);
			
			System.out.println("Tiempo: " + (System.currentTimeMillis() - time) + "\tProfundidad: " + profundidad);
			
			if(((System.currentTimeMillis() - time) < 500) && (profundidad < 40)) 
				calcular_movimiento(profundidad + 2, stop, state, time);
		});		
	}
	
	
}
