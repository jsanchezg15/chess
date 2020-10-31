package sources;

import java.util.Vector;

public class State {

	private Vector<Vector<String>> board;
	private double equilibrium;
	private boolean white_moves;
	private boolean[] has_moved; // left_white_rook, right_white_rook, white_king, left_black_rook, right_black_rook, black_king, double_movement_pawn;
	private State parent;
	private Vector<State> children;
	
	public State(Vector<Vector<String>> board, boolean white_moves) {
		this.board = board;
		this.board.trimToSize();
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		for(int i = 0; i < 7; i++)
			this.has_moved[i] = false;
	}
	
	public State(Vector<Vector<String>> board, boolean white_moves, boolean[] has_moved) {
		this.board = board;
		this.board.trimToSize();
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		for(int i = 0; i < 7; i++)
			this.has_moved[i] = has_moved[i];
	}
	
	public State(Vector<Vector<String>> board, boolean white_moves, boolean[] has_moved, int moved) {
		this.board = board;
		this.board.trimToSize();
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		for(int i = 0; i < 7; i++)
			this.has_moved[i] = has_moved[i];
		
		this.has_moved[moved] = true;
	}
	
	public State(Vector<Vector<String>> board, boolean white_moves, boolean[] has_moved, boolean moved) {
		this.board = board;
		this.board.trimToSize();
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		for(int i = 0; i < 7; i++)
			this.has_moved[i] = has_moved[i];
		
		this.has_moved[6] = moved;
	}
	
	public void setParent(State parent) {this.parent = parent; }
	
	public Vector<Vector<String>> getBoard() { return this.board; }
	
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
		
		if(!white_king)
			return 0;
		else if(!black_king)
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
					white_points = white_points + 3;
				else if(str.equals("-Q-"))
					if(i == 3 && j == 4)
						white_points = white_points + 100;
					else
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
			//return 0;
			return Double.MAX_VALUE;
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
		
		System.out.println("    +---+---+---+---+---+---+---+---+");
		System.out.println("    | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |");
		System.out.println("+---+---+---+---+---+---+---+---+---+");

		int i = 1;
		for(Vector<String> vector : this.board) {

			System.out.print("| " + i++ + " " + "|");

			for(String str : vector) {
				System.out.print(str + "|");
			}
			
			
			System.out.println();
			System.out.println("+---+---+---+---+---+---+---+---+---+");
		}			
	}
	
	
	public Vector<State> next_states() {
			
		if(!this.children.isEmpty())
			return this.children;
		
		
		Vector<State> next_states = new Vector<State>();
		
		boolean white_king = false;
		boolean black_king = false;
		
		for(Vector<String> vector : this.board)
			for(String str : vector) {
				if(str.equals("-K-"))
					white_king = true;

				else if(str.equals("<K>"))
					black_king = true;
			}
		
		if(!white_king) {
			State state = new State( State.blak_wins(), !this.white_moves );
			state.setParent(this);
			next_states.add(state);
			
		}
		else if(!black_king) {
			State state = new State( State.white_wins(), !this.white_moves );
			state.setParent(this);
			next_states.add(state);
		}
		
		else {
			if(this.white_moves) {
				
				for(int i = 0; i < 8; i++) {
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
						else if(str.equals("-Q-"))
							next_states.addAll(queen_movement(i, j));
						else if(str.equals("-K-"))
							next_states.addAll(king_movement(i, j));
					}
				}
			}
			else {
				for(int i = 0; i < 8; i++) {
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
						else if(str.equals("<Q>"))
							next_states.addAll(queen_movement(i, j));
						else if(str.equals("<K>"))
							next_states.addAll(king_movement(i, j));
					}
				}
			}
		}
		next_states.trimToSize();
		
		this.children = next_states;
		
		return next_states;
	}
	
		
	public Vector<State> king_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			if(i != 0) {
				
				if( !this.board.get(i - 1).get(j).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i - 1).setElementAt("-K-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved, 2));
				}
				
				if(j != 0) {
					if( !this.board.get(i - 1).get(j - 1).contains("-") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 1).setElementAt("-K-", j - 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false, has_moved, 2));
					}
				}
				
				if(j != 7) {
					if( !this.board.get(i - 1).get(j + 1).contains("-") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 1).setElementAt("-K-", j + 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false, has_moved, 2));
					}
				}
			}
			if(i != 7) {
				if( !this.board.get(i + 1).get(j).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i + 1).setElementAt("-K-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved, 2));
				}
				
				if(j != 0) {
					if( !this.board.get(i + 1).get(j - 1).contains("-") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 1).setElementAt("-K-", j - 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false, has_moved, 2));
					}
				}
				
				if(j != 7) {
					if( !this.board.get(i + 1).get(j + 1).contains("-") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 1).setElementAt("-K-", j + 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false, has_moved, 2));
					}	
				}
			}
			if(j != 0) {
				if( !this.board.get(i).get(j - 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i).setElementAt("-K-", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved, 2));
				}	
			}
			if(j != 7) {
				if( !this.board.get(i).get(j + 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i).setElementAt("-K-", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved, 2));
				}	
			}
		}
		else {
			if(i != 0) {
				
				if( !this.board.get(i - 1).get(j).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i - 1).setElementAt("<K>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved, 5));
				}
				
				if(j != 0) {
					if( !this.board.get(i - 1).get(j - 1).contains("<") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 1).setElementAt("<K>", j - 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true, has_moved, 5));
					}
				}
				
				if(j != 7) {
					if( !this.board.get(i - 1).get(j + 1).contains("<") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 1).setElementAt("<K>", j + 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true, has_moved, 5));
					}
				}
			}
			if(i != 7) {
				if( !this.board.get(i + 1).get(j).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i + 1).setElementAt("<K>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved, 5));
				}
				
				if(j != 0) {
					if( !this.board.get(i + 1).get(j - 1).contains("<") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 1).setElementAt("<K>", j - 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true, has_moved, 5));
					}
				}
				
				if(j != 7) {
					if( !this.board.get(i + 1).get(j + 1).contains("<") ) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 1).setElementAt("<K>", j + 1);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true, has_moved, 5));
					}	
				}
			}
			if(j != 0) {
				if( !this.board.get(i).get(j - 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i).setElementAt("<K>", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved, 5));
				}	
			}
			if(j != 7) {
				if( !this.board.get(i).get(j + 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					new_state.get(i).setElementAt("<K>", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved, 5));
				}	
			}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;	
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
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("<")) {
					new_state.get(i + k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("<")) {
					new_state.get(i + k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("<")) {
					new_state.get(i - k).setElementAt("-A-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(i - k).contains("<")) {
					new_state.get(i - k).setElementAt("-A-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("-")) {
					new_state.get(i + k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("-")) {
					new_state.get(i + k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("-")) {
					new_state.get(i - k).setElementAt("<A>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j - k).contains("-")) {
					new_state.get(i - k).setElementAt("<A>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i > 0 && j < 6) 
				if( !this.board.get(i - 1).get(j + 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("-C-", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i < 7 && j < 6) 
				if( !this.board.get(i + 1).get(j + 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("-C-", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i < 6 && j < 7) 
				if( !this.board.get(i + 2).get(j + 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("-C-", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i < 6 && j > 0) 
				if( !this.board.get(i + 2).get(j - 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("-C-", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i < 7 && j > 1) 
				if( !this.board.get(i + 1).get(j - 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("-C-", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i > 0 && j > 1) 
				if( !this.board.get(i - 1).get(j - 2).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("-C-", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
			if(i > 1 && j > 0) 
				if( !this.board.get(i - 2).get(j - 1).contains("-") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("-C-", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}		
		}
		else {
			if(i > 1 && j < 7) 
				if( !this.board.get(i - 2).get(j + 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("<C>", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i > 0 && j < 6) 
				if( !this.board.get(i - 1).get(j + 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("<C>", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i < 7 && j < 6) 
				if( !this.board.get(i + 1).get(j + 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("<C>", j + 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i < 6 && j < 7) 
				if( !this.board.get(i + 2).get(j + 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("<C>", j + 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i < 6 && j > 0) 
				if( !this.board.get(i + 2).get(j - 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 2).setElementAt("<C>", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i < 7 && j > 1) 
				if( !this.board.get(i + 1).get(j - 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i + 1).setElementAt("<C>", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i > 0 && j > 1) 
				if( !this.board.get(i - 1).get(j - 2).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 1).setElementAt("<C>", j - 2);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
			if(i > 1 && j > 0) 
				if( !this.board.get(i - 2).get(j - 1).contains("<") ) {
					new_state = (Vector<Vector<String>>) this.clone_board();
	
					new_state.get(i - 2).setElementAt("<C>", j - 1);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-T-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-T-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<T>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<T>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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

	public Vector<State> queen_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<String>> new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("-Q-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-Q-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("-Q-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(k).get(j).contains("<")) {
					new_state.get(k).setElementAt("-Q-", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("-Q-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-Q-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("-Q-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
				}
				else if(this.board.get(i).get(k).contains("<")) {
					new_state.get(i).setElementAt("-Q-", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}	
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j + k).equals("   ")) {
					new_state.get(i + k).setElementAt("-Q-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("<")) {
					new_state.get(i + k).setElementAt("-Q-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j - k).equals("   ")) {
					new_state.get(i + k).setElementAt("-Q-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("<")) {
					new_state.get(i + k).setElementAt("-Q-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j + k).equals("   ")) {
					new_state.get(i - k).setElementAt("-Q-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("<")) {
					new_state.get(i - k).setElementAt("-Q-", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j - k).equals("   ")) {
					new_state.get(i - k).setElementAt("-Q-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(i - k).contains("<")) {
					new_state.get(i - k).setElementAt("-Q-", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));
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
					new_state.get(k).setElementAt("<Q>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<Q>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(k).get(j).equals("   ")) {
					new_state.get(k).setElementAt("<Q>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(k).get(j).contains("-")) {
					new_state.get(k).setElementAt("<Q>", j);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("<Q>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<Q>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i).get(k).equals("   ")) {
					new_state.get(i).setElementAt("<Q>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
				}
				else if(this.board.get(i).get(k).contains("-")) {
					new_state.get(i).setElementAt("<Q>", k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}	
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j + k).equals("   ")) {
					new_state.get(i + k).setElementAt("<Q>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j + k).contains("-")) {
					new_state.get(i + k).setElementAt("<Q>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i + k).get(j - k).equals("   ")) {
					new_state.get(i + k).setElementAt("<Q>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i + k).get(j - k).contains("-")) {
					new_state.get(i + k).setElementAt("<Q>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j + k).equals("   ")) {
					new_state.get(i - k).setElementAt("<Q>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j + k).contains("-")) {
					new_state.get(i - k).setElementAt("<Q>", j + k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = (Vector<Vector<String>>) this.clone_board();

				if(this.board.get(i - k).get(j - k).equals("   ")) {
					new_state.get(i - k).setElementAt("<Q>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
					k++;
				}
				else if(this.board.get(i - k).get(j - k).contains("-")) {
					new_state.get(i - k).setElementAt("<Q>", j - k);
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));
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
					new_state.get(i - 1).setElementAt("-Q-", j);
				else 
					new_state.get(i - 1).setElementAt("-O-", j);
				
				new_state.get(i).setElementAt("   ", j);
				new_states.add(new State(new_state, false, has_moved));
				
				if(i == 6) 
					if(this.board.get(i - 2).get(j).equals("   ")) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i - 2).setElementAt("-O-", j);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, false, has_moved));	
					}
			} 
			if(j != 0) 
				if(this.board.get(i - 1).get(j - 1).contains("<")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt("-Q-", j - 1);
					else 
						new_state.get(i - 1).setElementAt("-O-", j - 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));	
				}
			if(j != 7) 
				if(this.board.get(i - 1).get(j + 1).contains("<")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt("-Q-", j + 1);
					else 
						new_state.get(i - 1).setElementAt("-O-", j + 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, false, has_moved));	
				}
			
		} else {
			
			if(this.board.get(i + 1).get(j).equals("   ")) {
				new_state = (Vector<Vector<String>>) this.clone_board();
				
				if(i == 6) 
					new_state.get(i + 1).setElementAt("<Q>", j);
				else 
					new_state.get(i + 1).setElementAt("<O>", j);
				
				new_state.get(i).setElementAt("   ", j);
				new_states.add(new State(new_state, true, has_moved));
				
				if(i == 1) 
					if(this.board.get(i + 2).get(j).equals("   ")) {
						new_state = (Vector<Vector<String>>) this.clone_board();
						new_state.get(i + 2).setElementAt("<O>", j);
						new_state.get(i).setElementAt("   ", j);
						new_states.add(new State(new_state, true, has_moved));	
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
					new_states.add(new State(new_state, true, has_moved));	
				}
			if(j != 7) 
				if(this.board.get(i + 1).get(j + 1).contains("-")) {
					new_state = (Vector<Vector<String>>) this.clone_board();
					
					if(i == 6) 
						new_state.get(i + 1).setElementAt("<Q>", j + 1);
					else 
						new_state.get(i + 1).setElementAt("<O>", j + 1);
					
					new_state.get(i).setElementAt("   ", j);
					new_states.add(new State(new_state, true, has_moved));	
				}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}
	
	
	
	public Vector<Vector<String>> clone_board() {
		Vector<Vector<String>> clone = new Vector<Vector<String>>();
		
		for(int i = 0; i < 8; i++) {
			clone.add(new Vector<String>());
			for(int j = 0; j < 8; j++)
				clone.get(i).add(this.board.get(i).get(j));
		}
		
		for(int i = 0; i < 8; i++) 
			clone.get(i).trimToSize();
		
		clone.trimToSize();
		
		return clone;		
	}
	
	public void show_path() {
		print_small();
		if(this.parent != null)
			this.parent.show_path();
	}
	
	public State getMinMax(int iteration) {
		if(iteration == 1) {
			if(this.white_moves) {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State max = next.remove(0);
					
					for(State state : next) 
						if(max.getEquilibium() < state.getEquilibium())
							max = state;
					
					return max;
				}
			} 
			else {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State min = next.remove(0);
					
					for(State state : next) 
						if(min.getEquilibium() > state.getEquilibium())
							min = state;
					
					return min;
				}
			}
		}
		else {
			if(this.white_moves) {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State max = next.remove(0).getMinMax(iteration - 1);
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, max.getEquilibium());
						if(max.getEquilibium() < state.getEquilibium())
								max = state;
					}
					
					return max;
				}
			}
			else {
				
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State min = next.remove(0).getMinMax(iteration - 1);
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, min.getEquilibium());
						if(min.getEquilibium() > state.getEquilibium())
							min = state;
					}
					
					return min;
				}
			}
		}	
		return State.void_state();
	}

	private State getMinMax(int iteration, double limit) {
		if(iteration == 1) {
			if(this.white_moves) {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State max = next.remove(0);
					
					if(max.getEquilibium() >= limit)
						return max;
					
					for(State state : next) {
						if(state.getEquilibium() >= limit)
							return state;
						
						if(max.getEquilibium() < state.getEquilibium())
							max = state;
					}
				return max;
				}
			} 
			else {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State min = next.remove(0);
					
					if(min.getEquilibium() <= limit)
						return min;
					
					for(State state : next) {
						if(state.getEquilibium() <= limit)
							return state;
						
						if(min.getEquilibium() > state.getEquilibium())
							min = state;
					}
					return min;
				}	
			}
		}
		else {
			if(this.white_moves) {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State max = next.remove(0).getMinMax(iteration - 1);
					
					if(max.getEquilibium() >= limit)
						return max;
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, max.getEquilibium());
						
						if(state.getEquilibium() >= limit)
							return state;
						
						if(max.getEquilibium() < state.getEquilibium())
							max = state;
					}
					return max;
				}
				
			}
			else {
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State min = next.remove(0).getMinMax(iteration - 1);
					
					if(min.getEquilibium() <= limit)
						return min;
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, min.getEquilibium());
						
						if(state.getEquilibium() <= limit)
							return state;
						
						if(min.getEquilibium() > state.getEquilibium())
							min = state;
					}
					return min;
				}
			}
		}
		return State.void_state();
	}

	public static State void_state() {
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	vector.get(i).add("   ");
        }
		return new State(vector, true);
	}
	
	public static Vector<Vector<String>> blak_wins() {
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	vector.get(i).add("   ");
        }
		vector.get(2).setElementAt(" B ", 1);
		vector.get(2).setElementAt(" L ", 2);
		vector.get(2).setElementAt(" A ", 3);
		vector.get(2).setElementAt(" C ", 4);
		vector.get(2).setElementAt(" K ", 5);
		
		vector.get(4).setElementAt(" W ", 2);
		vector.get(4).setElementAt(" I ", 3);
		vector.get(4).setElementAt(" N ", 4);
		vector.get(4).setElementAt(" S ", 5);

		vector.get(0).setElementAt("<K>", 0);
		vector.get(0).setElementAt("<K>", 7);
		vector.get(7).setElementAt("<K>", 0);
		vector.get(7).setElementAt("<K>", 7);

		return vector;
	}
	
	public static Vector<Vector<String>> white_wins() {
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<String>());
            for(int j = 0; j < 8; j++)
            	vector.get(i).add("   ");
        }
		vector.get(2).setElementAt(" W ", 1);
		vector.get(2).setElementAt(" H ", 2);
		vector.get(2).setElementAt(" I ", 3);
		vector.get(2).setElementAt(" T ", 4);
		vector.get(2).setElementAt(" E ", 5);
		
		vector.get(4).setElementAt(" W ", 2);
		vector.get(4).setElementAt(" I ", 3);
		vector.get(4).setElementAt(" N ", 4);
		vector.get(4).setElementAt(" S ", 5);
		
		vector.get(0).setElementAt("-K-", 0);
		vector.get(0).setElementAt("-K-", 7);
		vector.get(7).setElementAt("-K-", 0);
		vector.get(7).setElementAt("-K-", 7);
		
		return vector;
	}


	public State getMove() {
		if(this.parent != null) {
			if(this.parent.parent == null)
				return this;
			else
				return this.parent.getMove();
		}
		return null;
	}

	public State move(int i1, int j1, int i2, int j2) {
		Vector<Vector<String>> clone = clone_board();
		
		String pieza = clone.get(i1 - 1).get(j1 - 1);
		clone.get(i2 - 1).setElementAt(pieza, j2 - 1);
		clone.get(i1 - 1).setElementAt("   ", j1 - 1);
		
		return new State(clone, !this.white_moves);
	}
	
}









