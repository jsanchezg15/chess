package sources141;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Jugador141 {

	
	private State141 state;
	
	public Jugador141(State141 state) {
		this.state = state;
	}
	
	public State141 move(State141 state, double timer) {
		this.state = state;
		return this.move(timer);
	}
	
	public State141 move(double timer) {
		
		MiBoolean stop = new MiBoolean(false);			
		
		calcular_movimiento(2, stop, this.state, System.currentTimeMillis());

		try {Thread.sleep((long) (timer * 1000));} catch (InterruptedException e1) {e1.printStackTrace();}
		
		stop.value = true;
		
		System.out.println("===========");
		
		return this.state;
	}

	public void calcular_movimiento(int profundidad, MiBoolean stop, State141 state, long time) {
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		State141 s = new State141(state.clone_board(), state.white_moves);
				
		exec.submit(() -> {				
			State141 min_max = s.getMinMax(profundidad, stop);
			
			this.state = new State141(min_max.getMove().clone_board(), state.white_moves);
			
			System.out.println("Tiempo: " + (System.currentTimeMillis() - time) + "\tProfundidad: " + profundidad);
			
			if(((System.currentTimeMillis() - time) < 500) && (profundidad < 14)) 
				calcular_movimiento(profundidad + 2, stop, state, time);
		});		
	}
	
	
}
