package sources;

import java.util.Vector;

public class State {

	private Vector<Vector<String>> board;
	private double equilibrium;
	private boolean white_moves;
	private State parent;
	private Vector<State> children;
	
	public State(Vector<Vector<String>> board, boolean white_moves) {
		this.board = board;
		//this.equilibrium = calculate_equilibrium();
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
	}
	
	public void setParent(State parent) {this.parent = parent; }
	
	public double getEquilibium() { 
		if(this.equilibrium < 0) {
			this.equilibrium = calculate_equilibrium();
		}
		return this.equilibrium; 
	}

	
	private double calculate_equilibrium() {
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(Vector<String> vector : this.board)
			for(String str : vector) {
				if(str.equals("-O-"))
					white_points = white_points + 1;
				else if(str.equals("-T-"))
					white_points = white_points + 5;
				else if(str.equals("-C-"))
					white_points = white_points + 3;
				else if(str.equals("-A-"))
					white_points = white_points + 3;
				else if(str.equals("-Q-"))
					white_points = white_points + 9;
				else if(str.equals("-K-"))
					white_king = true;
				else if(str.equals("<O>"))
					black_points = black_points + 1;
				else if(str.equals("<T>"))
					black_points = black_points + 5;
				else if(str.equals("<C>"))
					black_points = black_points + 3;
				else if(str.equals("<A>"))
					black_points = black_points + 3;
				else if(str.equals("<Q>"))
					black_points = black_points + 9;
				else if(str.equals("<K>"))
					black_king = true;
			}
		/*if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else*/
		if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}

	public double heuristic() {
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				String str = this.board.get(i).get(j);
				
				if(str.equals("-O-"))
					white_points = white_points + 1;
				else if(str.equals("-T-"))
					white_points = white_points + 5;
				else if(str.equals("-C-"))
					white_points = white_points + 3;
				else if(str.equals("-A-"))
					if(i == 6 && j == 1)
						white_points = white_points + 100;
					else
						white_points = white_points + 3;
				else if(str.equals("-Q-"))
					white_points = white_points + 9;
				else if(str.equals("-K-"))
					white_king = true;
				else if(str.equals("<O>"))
					black_points = black_points + 1;
				else if(str.equals("<T>"))
					black_points = black_points + 5;
				else if(str.equals("<C>"))
					black_points = black_points + 3;
				else if(str.equals("<A>"))
					black_points = black_points + 3;
				else if(str.equals("<Q>"))
					black_points = black_points + 9;
				else if(str.equals("<K>"))
					black_king = true;
			}		
			
		if(!white_king)
			return 0;
		else if(!black_king)
			return 0;
			//return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}
	
	
	public void print() {
		
		System.out.println("+-------+-------+-------+-------+-------+-------+-------+-------+");

		for(Vector<String> vector : this.board) {
			System.out.println("|       |       |       |       |       |       |       |       |");

			for(String str : vector) {
				System.out.print("|  " + str + "  ");
			}
			
			
			System.out.println("|");
			System.out.println("|       |       |       |       |       |       |       |       |");
			System.out.println("+-------+-------+-------+-------+-------+-------+-------+-------+");
		}			
	}
	
	public void print_small() {
		
		System.out.println("+---+---+---+---+---+---+---+---+");

		for(Vector<String> vector : this.board) {

			for(String str : vector) {
				System.out.print("|" + str);
			}
			
			
			System.out.println("|");
			System.out.println("+---+---+---+---+---+---+---+---+");
		}			
	}
	
	
	public Vector<State> next_states() {
			
		if(!this.children.isEmpty())
			return this.children;
		
		
		Vector<State> next_states = new Vector<State>();
		
		if(this.white_moves)
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++) {
					
					String str = this.board.get(i).get(j);
					
					if(str.equals("-O-")) 
						next_states.addAll(pawn_movement(i, j));
					else if(str.equals("-T-"))
						next_states.addAll(rook_movement(i, j));
					else if(str.equals("-C-"))
						next_states.addAll(knight_movement(i, j));
					else if(str.equals("-A-"))
						next_states.addAll(bishop_movement(i, j));
					/*else if(str.equals("-Q-"))
						white_points = white_points + 9;
					else if(str.equals("-K-"))
						white_king = true;*/
				}
		else {
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++) {
					
					String str = this.board.get(i).get(j);
					
					if(str.equals("<O>")) 
						next_states.addAll(pawn_movement(i, j));
					else if(str.equals("<T>"))
						next_states.addAll(rook_movement(i, j));
					else if(str.equals("<C>"))
						next_states.addAll(knight_movement(i, j));
					else if(str.equals("<A>"))
						next_states.addAll(bishop_movement(i, j));
					/*else if(str.equals("<Q>"))
						white_points = white_points + 9;
					else if(str.equals("<K>"))
						white_king = true;*/
				}
		}
		this.children = next_states;
		
		return next_states;
	}
	
		
	public Vector<State> bishop_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j + k).equals("   ")) {
					new_state.get(i + k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("<")) {
					new_state.get(i + k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j - k).equals("   ")) {
					new_state.get(i + k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("<")) {
					new_state.get(i + k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j + k).equals("   ")) {
					new_state.get(i - k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("<")) {
					new_state.get(i - k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j - k).equals("   ")) {
					new_state.get(i - k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					k++;
				}
				else if(this.board.get(i - k).get(i - k).contains("<")) {
					new_state.get(i - k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}		
		}
		else {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j + k).equals("   ")) {
					new_state.get(i + k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("-")) {
					new_state.get(i + k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j - k).equals("   ")) {
					new_state.get(i + k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("-")) {
					new_state.get(i + k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j + k).equals("   ")) {
					new_state.get(i - k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("-")) {
					new_state.get(i - k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j - k).equals("   ")) {
					new_state.get(i - k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					k++;
				}
				else if(this.board.get(i - k).get(j - k).contains("-")) {
					new_state.get(i - k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	public Vector<State> knight_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			if(i > 1 && j < 7) 
				if( !this.board.get(i - 2).get(j + 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("-C-", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j < 6) 
				if( !this.board.get(i - 1).get(j + 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("-C-", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j < 6) 
				if( !this.board.get(i + 1).get(j + 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("-C-", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j < 7) 
				if( !this.board.get(i + 2).get(j + 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("-C-", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j > 0) 
				if( !this.board.get(i + 2).get(j - 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("-C-", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j > 1) 
				if( !this.board.get(i + 1).get(j - 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("-C-", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j > 1) 
				if( !this.board.get(i - 1).get(j - 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("-C-", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 1 && j > 0) 
				if( !this.board.get(i - 2).get(j - 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("-C-", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}		
		}
		else {
			if(i > 1 && j < 7) 
				if( !this.board.get(i - 2).get(j + 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("<C>", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j < 6) 
				if( !this.board.get(i - 1).get(j + 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("<C>", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j < 6) 
				if( !this.board.get(i + 1).get(j + 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("<C>", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j < 7) 
				if( !this.board.get(i + 2).get(j + 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("<C>", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j > 0) 
				if( !this.board.get(i + 2).get(j - 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("<C>", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j > 1) 
				if( !this.board.get(i + 1).get(j - 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("<C>", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j > 1) 
				if( !this.board.get(i - 1).get(j - 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("<C>", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
			if(i > 1 && j > 0) 
				if( !this.board.get(i - 2).get(j - 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("<C>", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	public Vector<State> rook_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}	
		} 
		else {
			for(int k = i + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}	
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	public Vector<State> pawn_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			
			if(this.board.get(i - 1).get(j).equals("   ")) {
				new_state = (Vector<Vector<String>>) this.clone_board();
				
				if(i == 1) 
					new_state.get(i - 1).setElementAt("-T-", j);
				else 
					new_state.get(i - 1).setElementAt("-O-", j);
				
				new_state.get(i).setElementAt("   ", j);
				new_states.add(new State(new_state, false));
				
				if(i == 6) 
					if(this.board.get(i - 2).get(j).equals("   ")) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 2).setElementAt("-O-", j);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false));	
					}
			} 
			if(j != 0) 
				if(this.board.get(i - 1).get(j - 1).contains("<")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt("-T-", j - 1);
					else 
						new_state.get(i - 1).setElementAt("-O-", j - 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));	
				}
			if(j != 7) 
				if(this.board.get(i - 1).get(j + 1).contains("<")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt("-Q-", j + 1);
					else 
						new_state.get(i - 1).setElementAt("-O-", j + 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false));	
				}
			
		} else {
			
			if(this.board.get(i + 1).get(j).equals("   ")) {
				new_state = (Vector<Vector<String>>) this.clone_board();
				
				if(i == 6) 
					new_state.get(i + 1).setElementAt("<Q>", j);
				else 
					new_state.get(i + 1).setElementAt("<O>", j);
				
				new_state.get(i).setElementAt("   ", j);
				new_states.add(new State(new_state, true));
				
				if(i == 1) 
					if(this.board.get(i + 2).get(j).equals("   ")) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 2).setElementAt("<O>", j);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true));	
					}
			} 
			if(j != 0) 
				if(this.board.get(i + 1).get(j - 1).contains("-")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 6) 
						new_state.get(i + 1).setElementAt("<Q>", j - 1);
					else 
						new_state.get(i + 1).setElementAt("<O>", j - 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));	
				}
			if(j != 7) 
				if(this.board.get(i + 1).get(j + 1).contains("-")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 6) 
						new_state.get(i + 1).setElementAt("<Q>", j + 1);
					else 
						new_state.get(i + 1).setElementAt("<O>", j + 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true));	
				}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}
	
	
	
	private Vector<Vector<String>> clone_board() {
		Vector<Vector<String>> clone = new Vector<Vector<String>>();
		
		for(int i = 0; i < 8; i++) {
			clone.add(new Vector<String>());
			for(int j = 0; j < 8; j++)
				clone.get(i).add(this.board.get(i).get(j));
		}
		
		return clone;		
	}
	
	public void show_path() {
		print_small();
		if(this.parent != null)
			this.parent.show_path();
	}
	
	public State getMinMax() {
		if(this.children.isEmpty())
			return this;
		else {
			if(this.white_moves) {
				Vector<State> next = new Vector<State>();
				
				for(State state : this.children)
					next.add(state.getMinMax());
				
				State max = next.get(0);
				
				for(State state : next)
					if(max.getEquilibium() < state.getEquilibium())
						max = state;
				
				return max;
			}
			else {
				Vector<State> next = new Vector<State>();

				for(State state : this.children)
					next.add(state.getMinMax());
				
				State min = next.get(0);
				
				for(State state : next)
					if(min.getEquilibium() > state.getEquilibium())
						min = state;
				
				return min;
			}
		}
	}

	
}
