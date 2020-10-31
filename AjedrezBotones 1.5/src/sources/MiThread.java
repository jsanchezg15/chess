package sources;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MiThread extends Thread {

	
	public boolean runnable;
	public State next, state;
	public int profundidad;
	
	public MiThread(State state, int profundidad) {
		this.runnable = true;
		this.next = null;
		this.state = state;
		this.profundidad = profundidad;
	}
	
	public void stop_thread() { this.runnable = false;} 

	
	public void run() {
		try {
			State min_max = this.state.getMinMax(this.profundidad, this);
			
			this.state = new State(min_max.getMove().clone_board(), true);
		} 
		catch (InterruptedException ex) {
			Logger.getLogger(MiThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
