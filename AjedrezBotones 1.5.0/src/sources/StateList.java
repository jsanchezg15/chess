package sources;

public class StateList {

	private State[] list;
	
	public StateList(byte size) {
		this.list = new State[size];
	}
	
	public void push(State st) {
		
	}
	
	public State[] getList() { return this.list; }
}
