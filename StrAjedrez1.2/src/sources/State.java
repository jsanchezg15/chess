package sources;

import java.util.Vector;

public class State {

	private char[][] board;
	private double equilibrium;
	private boolean white_moves;
	private State parent;
	private Vector<State> children;
	
	public State(char[][] board, boolean white_moves) {
		this.board = board;
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
	}
	
	public State getParent() {return this.parent;}
	
	public void setWhiteMoves(boolean wm) { this.white_moves = wm; }
	
	public void setParent(State parent) { this.parent = parent; }
	
	public char[][] getBoard() { return this.board; }
	
	public double getEquilibium() { 
		if(this.equilibrium < 0) {
			this.equilibrium = calculate_equilibrium();
		}
		return this.equilibrium; 
	}
	
	public double calculate_equilibrium() {
		double white_points = Math.random() / 10;
		double black_points = Math.random() / 10;
		//double white_points = 0;
		//double black_points = 0;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board[i][j];		
		
				if(piece == 'O')  					// Peón 
					white_points = white_points + 1;
				else if(piece == 'P') 				// Peón capturable al paso
					white_points = white_points + 1;
				else if(piece == 'R') 				// Torre
					white_points = white_points + 5;
				else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
					white_points = white_points + 5;
				else if(piece == 'C') 				// Caballo
					white_points = white_points + 3;
				else if(piece == 'A') 				// Alfil
					white_points = white_points + 3;
				else if(piece == 'Q')				// Reina
					white_points = white_points + 9;
				else if(piece == 'M') {				// Rey
					white_king = true;
					//white_points = white_points + 1;
				}
				else if(piece == 'K')	{			// Rey que ya ha hecho un movimiento
					white_king = true;	
					//white_points = white_points + 1;
				}
				else if(piece == 'o')
					black_points = black_points + 1;
				else if(piece == 'p')
					black_points = black_points + 1;
				else if(piece == 'r')
					black_points = black_points + 5;
				else if(piece == 't')
					black_points = black_points + 5;
				else if(piece == 'c')
					black_points = black_points + 3;
				else if(piece == 'a')
					black_points = black_points + 3;
				else if(piece == 'q')
					black_points = black_points + 9;
				else if(piece == 'm') {
					black_king = true;
					//black_points = black_points + 1;
				}
				else if(piece == 'k') {
					black_king = true;
					//black_points = black_points + 1;
				}
			}
		
		if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}

	
	public void print() {
		
		System.out.println("+-------+-------+-------+-------+-------+-------+-------+-------+");

		for(int i = 0; i < 8; i++) {
			System.out.println("|       |       |       |       |       |       |       |       |");

			for(int j = 0; j < 8; j++) 
				System.out.print("|  " + board[i][j] + "  ");
			
			System.out.println("|");
			System.out.println("|       |       |       |       |       |       |       |       |");
			System.out.println("+-------+-------+-------+-------+-------+-------+-------+-------+");
		}			
	}
	
	public void print_small() {
		
		System.out.println("    +---+---+---+---+---+---+---+---+");
		System.out.println("    | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |");
		System.out.println("+---+---+---+---+---+---+---+---+---+");

		int k = 1;
		for(int i = 0; i < 8; i++) {

			System.out.print("| " + k++ + " " + "|");

			for(int j = 0; j < 8; j++) 
				System.out.print((board[i][j] == '_' ? "   " : (board[i][j] < 90 ? ("-" + board[i][j] + "-") : ("<" + (char)(board[i][j] - 32) + ">"))) + "|");
			
			System.out.println("\n+---+---+---+---+---+---+---+---+---+");
		}		
		//System.out.println(this.white_moves);
	}

	public String print_small_str() {
		
		String strg = "    +---+---+---+---+---+---+---+---+\n";
		strg += "    | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |\n";
		strg += "+---+---+---+---+---+---+---+---+---+\n";

		int k = 1;
		for(int i = 0; i < 8; i++) {

			strg += "| " + k++ + " " + "|";

			for(int j = 0; j < 8; j++) 
				strg += (board[i][j] == '_' ? "   " : (board[i][j] < 90 ? ("-" + board[i][j] + "-") : ("<" + (char)(board[i][j] - 32) + ">"))) + "|";
			
			strg += "\n+---+---+---+---+---+---+---+---+---+\n";
		}		
								
		return strg;
	}
	
	public Vector<State> next_states() {
			
		if(!this.children.isEmpty())
			return this.children;
		
		Vector<State> next_states = new Vector<State>();
		
		if(this.getEquilibium() == 0) {
			State state = new State( State.blak_wins(), !this.white_moves );
			state.setParent(this);
			next_states.add(state);
			
		}
		else if(this.getEquilibium() == Double.MAX_VALUE) {
			State state = new State( State.white_wins(), !this.white_moves );
			state.setParent(this);
			next_states.add(state);
		}
		
		else {
			if(this.white_moves) {
				
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						
						char piece = board[i][j];
												
						if(piece == 'O')
							next_states.addAll(pawn_movement(i, j));
						else if(piece == 'T' || piece == 'R')
							next_states.addAll(rook_movement(i, j));
						else if(piece == 'C')
							next_states.addAll(knight_movement(i, j));
						else if(piece == 'A')
							next_states.addAll(bishop_movement(i, j));
						else if(piece == 'Q')
							next_states.addAll(queen_movement(i, j));
						else if(piece == 'M')
							next_states.addAll(king_movement(i, j, true));	
						else if(piece == 'K')
							next_states.addAll(king_movement(i, j, false));
					}
				}
			}
			else {
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						
						char piece = board[i][j];
						
						if(piece == 'o')
							next_states.addAll(pawn_movement(i, j));
						else if(piece == 't' || piece == 'r')
							next_states.addAll(rook_movement(i, j));
						else if(piece == 'c')
							next_states.addAll(knight_movement(i, j));
						else if(piece == 'a')
							next_states.addAll(bishop_movement(i, j));
						else if(piece == 'q')
							next_states.addAll(queen_movement(i, j));
						else if(piece == 'm')
							next_states.addAll(king_movement(i, j, true));	
						else if(piece == 'k')
							next_states.addAll(king_movement(i, j, false));
					}
				}
			}
		}
		next_states.trimToSize();
		
		this.children = next_states;
		
		return next_states;
	}
	
		
	public Vector<State> king_movement(int i, int j, boolean kingside) {
		Vector<State> new_states = new Vector<State>();
		char[][] new_state; 
		
		if(this.white_moves) {
			if(kingside) {
				if(board[7][0] == 'R' && board[7][1] == '_' && board[7][2] == '_' && board[7][3] == '_') {
					if(  !(is_menaged(7, 4, true) || is_menaged(7, 3, true) || is_menaged(7, 2, true)  )  ) {
						new_state =  this.clone_board();
						
						new_state[i][2] = 'K';
						new_state[i][3] = 'T';
						new_state[i][j] = '_';
						new_state[7][0] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
				if(board[7][7] == 'R' && board[7][6] == '_' && board[7][5] == '_') {
					if(  !(   is_menaged(7, 4, true) || is_menaged(7, 5, true) || is_menaged(7, 6, true)  )  ) {
						new_state =  this.clone_board();
						
						new_state[i][6] = 'K';
						new_state[i][5] = 'T';
						new_state[i][j] = '_';
						new_state[7][7] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
			}
					
			if(i != 0) {
				
				if( board[i - 1][j] > 90 ) {
					new_state =  this.clone_board();

					new_state[i - 1][j] = 'K';
					new_state[i][j] = '_';
							
					new_states.add(new State(new_state, false));
				}
				
				if(j != 0) {
					if( board[i - 1][j - 1] > 90 ) {
						new_state =  this.clone_board();

						new_state[i - 1][j - 1] = 'K';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
				
				if(j != 7) {
					if( board[i - 1][j + 1] > 90 ) {
						new_state =  this.clone_board();

						new_state[i - 1][j + 1] = 'K';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
			}
			if(i != 7) {
				if( board[i + 1][j] > 90 ) {
					new_state =  this.clone_board();

					new_state[i + 1][j] = 'K';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, false));
				}
				
				if(j != 0) {
					if( board[i + 1][j - 1] > 90 ) {
						new_state =  this.clone_board();

						new_state[i + 1][j - 1] = 'K';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
				
				if(j != 7) {
					if( board[i + 1][j + 1] > 90 ) {
						new_state =  this.clone_board();

						new_state[i + 1][j + 1] = 'K';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, false));
					}
				}
			}
			if(j != 0) {
				if( board[i][j - 1] > 90 ) {
					new_state =  this.clone_board();

					new_state[i][j - 1] = 'K';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, false));
				}
			}
			if(j != 7) {
				if( board[i][j + 1] > 90 ) {
					new_state =  this.clone_board();

					new_state[i][j + 1] = 'K';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, false));
				}
			}
		}
		else {
			if(kingside) {
				if(board[0][0] == 'r' && board[0][1] == '_' && board[0][2] == '_' && board[0][3] == '_') {
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 3, false) || is_menaged(0, 2, false)  )  ) {
						new_state =  this.clone_board();
						
						new_state[i][2] = 'k';
						new_state[i][3] = 't';
						new_state[i][j] = '_';
						new_state[0][0] = '_';
							
						new_states.add(new State(new_state, true));
					}
				}
				if(board[0][7] == 'r' && board[0][6] == '_' && board[0][2] == '_' && board[0][5] == '_') {
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 5, false) || is_menaged(0, 6, false)  )  ) {
						new_state =  this.clone_board();

						new_state[i][6] = 'k';
						new_state[i][5] = 't';
						new_state[i][j] = '_';
						new_state[0][7] = '_';

						new_states.add(new State(new_state, true));
					}
				}
			}
			
			if(i != 0) {
				
				if( board[i - 1][j] < 96 ) {
					new_state =  this.clone_board();

					new_state[i - 1][j] = 'k';
					new_state[i][j] = '_';
							
					new_states.add(new State(new_state, true));
				}
				
				if(j != 0) {
					if( board[i - 1][j - 1] < 96 ) {
						new_state =  this.clone_board();

						new_state[i - 1][j - 1] = 'k';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, true));
					}
				}
				
				if(j != 7) {
					if( board[i - 1][j + 1] < 96 ) {
						new_state =  this.clone_board();

						new_state[i - 1][j + 1] = 'k';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, true));
					}
				}
			}
			if(i != 7) {
				if( board[i + 1][j] < 96 ) {
					new_state =  this.clone_board();

					new_state[i + 1][j] = 'k';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, true));
				}
				
				if(j != 0) {
					if( board[i + 1][j - 1] < 96 ) {
						new_state =  this.clone_board();

						new_state[i + 1][j - 1] = 'k';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, true));
					}
				}
				
				if(j != 7) {
					if( board[i + 1][j + 1] < 96 ) {
						new_state =  this.clone_board();

						new_state[i + 1][j + 1] = 'k';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, true));
					}
				}
			}
			if(j != 0) {
				if( board[i][j - 1] < 96 ) {
					new_state =  this.clone_board();

					new_state[i][j - 1] = 'k';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, true));
				}
			}
			if(j != 7) {
				if( board[i][j + 1] < 96 ) {
					new_state =  this.clone_board();

					new_state[i][j + 1] = 'k';
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, true));
				}
			}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;	
	}

	public Vector<State> bishop_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		char[][] new_state; 
		
		if(this.white_moves) {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board[i + k][j + k] == '_') {
					new_state[i + k][j + k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i + k][j + k] > 90) {
					new_state[i + k][j + k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i + k][j - k] == '_') {					
					new_state[i + k][j - k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i + k][j - k] > 90) {
					new_state[i + k][j - k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board[i - k][j + k] == '_') {				
					new_state[i - k][j + k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i - k][j + k] > 90) {
					new_state[i - k][j + k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i - k][j - k] == '_') {
					new_state[i - k][j - k] = 'A';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i - k][j - k] > 90) {
					new_state[i - k][j - k] = 'A';
					new_state[i][j] = '_';

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
				new_state = clone_board();

				if(board[i + k][j + k] == '_') {	
					new_state[i + k][j + k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i + k][j + k] < 96) {
					new_state[i + k][j + k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i + k][j - k] == '_') {
					new_state[i + k][j - k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i + k][j - k] < 96) {
					new_state[i + k][j - k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board[i - k][j + k] == '_') {
					new_state[i - k][j + k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i - k][j + k] < 96) {
					new_state[i - k][j + k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i - k][j - k] == '_') {				
					new_state[i - k][j - k] = 'a';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i - k][j - k] < 96) {
					new_state[i - k][j - k] = 'a';
					new_state[i][j] = '_';

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
		char[][] new_state; 
		
		if(this.white_moves) {
			if(i > 1 && j < 7) 
				if( board[i - 2][j + 1] > 90 ) {
					new_state = clone_board();
	
					new_state[i - 2][j + 1] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] > 90 ) {
					new_state = clone_board();
					
					new_state[i - 1][j + 2] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] > 90 ) {
					new_state = clone_board();
					
					new_state[i + 1][j + 2] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] > 90 ) {
					new_state = clone_board();
					
					new_state[i + 2][j + 1] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] > 90 ) {
					new_state = clone_board();
					
					new_state[i + 2][j - 1] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] > 90 ) {
					new_state = clone_board();
					
					new_state[i + 1][j - 2] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] > 90 ) {
					new_state = clone_board();
					
					new_state[i - 1][j - 2] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] > 90 ) {
					new_state = clone_board();
					
					new_state[i - 2][j - 1] = 'C';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
				}		
		}
		else {
			if(i > 1 && j < 7) 
				if( board[i - 2][j + 1] < 96 ) {
					new_state = clone_board();
	
					new_state[i - 2][j + 1] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] < 96 ) {
					new_state = clone_board();
					
					new_state[i - 1][j + 2] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] < 96 ) {
					new_state = clone_board();
					
					new_state[i + 1][j + 2] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] < 96 ) {
					new_state = clone_board();
					
					new_state[i + 2][j + 1] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] < 96 ) {
					new_state = clone_board();
					
					new_state[i + 2][j - 1] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] < 96 ) {
					new_state = clone_board();
					
					new_state[i + 1][j - 2] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] < 96 ) {
					new_state = clone_board();
					
					new_state[i - 1][j - 2] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] < 96 ) {
					new_state = clone_board();
					
					new_state[i - 2][j - 1] = 'c';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
				}		
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	public Vector<State> rook_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		char[][] new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[k][j] > 90) {
					new_state[k][j] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[k][j] > 90) {
					new_state[k][j] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[i][k] > 90) {
					new_state[i][k] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[i][k] > 90) {
					new_state[i][k] = 'T';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}	
		} 
		else {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[k][j] < 96) {
					new_state[k][j] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[k][j] < 96) {
					new_state[k][j] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[i][k] < 96) {
					new_state[i][k] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 't';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[i][k] < 96) {
					new_state[i][k] = 't';
					new_state[i][j] = '_';
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

	public Vector<State> queen_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		char[][] new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[k][j] > 90) {
					new_state[k][j] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[k][j] > 90) {
					new_state[k][j] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[i][k] > 90) {
					new_state[i][k] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
				}
				else if(board[i][k] > 90) {
					new_state[i][k] = 'Q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}	
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board[i + k][j + k] == '_') {
					new_state[i + k][j + k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i + k][j + k] > 90) {
					new_state[i + k][j + k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i + k][j - k] == '_') {					
					new_state[i + k][j - k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i + k][j - k] > 90) {
					new_state[i + k][j - k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board[i - k][j + k] == '_') {				
					new_state[i - k][j + k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i - k][j + k] > 90) {
					new_state[i - k][j + k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i - k][j - k] == '_') {
					new_state[i - k][j - k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board[i - k][j - k] > 90) {
					new_state[i - k][j - k] = 'Q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}		
		}
		else {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[k][j] < 96) {
					new_state[k][j] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[k][j] == '_') {
					new_state[k][j] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[k][j] < 96) {
					new_state[k][j] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[i][k] < 96) {
					new_state[i][k] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board[i][k] == '_') {
					new_state[i][k] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
				}
				else if(board[i][k] < 96) {
					new_state[i][k] = 'q';
					new_state[i][j] = '_';
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board[i + k][j + k] == '_') {	
					new_state[i + k][j + k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i + k][j + k] < 96) {
					new_state[i + k][j + k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i + k][j - k] == '_') {
					new_state[i + k][j - k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i + k][j - k] < 96) {
					new_state[i + k][j - k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board[i - k][j + k] == '_') {
					new_state[i - k][j + k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i - k][j + k] < 96) {
					new_state[i - k][j + k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board[i - k][j - k] == '_') {				
					new_state[i - k][j - k] = 'q';
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board[i - k][j - k] < 96) {
					new_state[i - k][j - k] = 'q';
					new_state[i][j] = '_';

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
		char[][] new_state; 
		
		if(this.white_moves) {
			
			if(board[i - 1][j] == '_') {
				new_state = clone_board();
				
				if(i == 1) 
					new_state[i - 1][j] = 'Q';
				else 
					new_state[i - 1][j] = 'O';
				
				new_state[i][j] = '_';

				new_states.add(new State(new_state, false));
				
				if(i == 6) 
					if(board[i - 2][j] == '_') {
						new_state = clone_board();

						new_state[i - 2][j] = 'O';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, false));	
					}
			} 
			if(j != 0) 
				if(board[i - 1][j - 1] > 96) {
					new_state = clone_board();
					
					if(i == 1) 
						new_state[i - 1][j - 1] = 'Q';
					else 
						new_state[i - 1][j - 1] = 'O';
					
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, false));	
				}
			if(j != 7) 
				if(board[i - 1][j + 1] > 96) {
					new_state = clone_board();
					
					if(i == 1) 
						new_state[i - 1][j + 1] = 'Q';
					else 
						new_state[i - 1][j + 1] = 'O';
					
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, false));	
				}	
		} 
		else {
			
			if(board[i + 1][j] == '_') {
				new_state = clone_board();
				
				if(i == 6) 
					new_state[i + 1][j] = 'q';
				else 
					new_state[i + 1][j] = 'o';
				
				new_state[i][j] = '_';
				
				new_states.add(new State(new_state, true));
				
				if(i == 1) 
					if(board[i + 2][j] == '_') {
						new_state = clone_board();

						new_state[i + 2][j] = 'o';
						new_state[i][j] = '_';
						
						new_states.add(new State(new_state, true));	
					}
			} 
			if(j != 0) 
				if(board[i + 1][j - 1] < 91) {
					new_state = clone_board();
					
					if(i == 6) 
						new_state[i + 1][j - 1] = 'q';
					else 
						new_state[i + 1][j - 1] = 'o';
					
					new_state[i][j] = '_';

					new_states.add(new State(new_state, true));	
				}
			if(j != 7) 
				if(board[i + 1][j + 1] < 91) {
					new_state = clone_board();
					
					if(i == 6) 
						new_state[i + 1][j + 1] = 'q';
					else 
						new_state[i + 1][j + 1] = 'o';
					
					new_state[i][j] = '_';
					
					new_states.add(new State(new_state, true));	
				}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	
	public boolean is_menaged(int i, int j, boolean is_white_menaged) {
		if(is_white_menaged) {
			if(i > 0) {
				if(j < 6)
					if(board[i - 1][j + 2] == 'c')
						return true;
				if(j > 1) 
					if(board[i - 1][j - 2] == 'c')
						return true;
				
				if(i > 1) {
					if(j < 7)
						if(board[i - 2][j + 1] == 'c')
							return true;
					if(j > 0) 
						if(board[i - 2][j - 1] == 'c')
							return true;
				}
			}
			if(i < 7) {
				if(j < 6)
					if(board[i + 1][j + 2] == 'c')
						return true;
				if(j > 1) 
					if(board[i + 1][j - 2] == 'c')
						return true;
				
				if(i < 6) { 
					if(j < 7)
						if(board[i + 2][j + 1] == 'c')
							return true;
					if(j > 0) 
						if(board[i + 2][j - 1] == 'c')
							return true;
				}
			}
			
			if(i != 0) {
				if(j != 0)
					if(board[i - 1][j - 1] == 'o' || board[i - 1][j - 1] == 'P' || board[i - 1][j - 1] == 'k' || board[i - 1][j - 1] == 'm')
						return true;
				if(j != 7)
					if(board[i - 1][j + 1] == 'o' || board[i - 1][j + 1] == 'P' || board[i - 1][j + 1] == 'k' || board[i - 1][j + 1] == 'm')
						return true;
				if(board[i - 1][j] == 'k' || board[i - 1][j] == 'm')
					return true;
			}
			if(i != 7) {
				if(j != 0)
					if(board[i + 1][j - 1] == 'k' || board[i + 1][j - 1] == 'm')
						return true;
				if(j != 7)
					if(board[i + 1][j + 1] == 'k' || board[i + 1][j + 1] == 'm')
						return true;
				if(board[i + 1][j] == 'k' || board[i + 1][j] == 'm')
					return true;
			}
			if(j != 0)
				if(board[i][j - 1] == 'k' || board[i][j - 1] == 'm')
					return true;
			if(j != 7)
				if(board[i][j + 1] == 'k' || board[i][j + 1] == 'm')
					return true;
			
			{
				int k = 1;
				while( (i + k) <= 7 && (j + k) <= 7) {
	
					char piece = board[i + k][j + k];
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i + k) <= 7 && (j - k) >= 0 ) {

					char piece = board[i + k][j - k];
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j + k) <= 7 ) {
	
					char piece = board[i - k][j + k];
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j - k) >= 0 ) {
	
					char piece = board[i - k][j - k];
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}	
			}

			for(int k = i + 1; k <= 7; k++) {
				char piece = board[k][j];

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				char piece = board[k][j];

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				char piece = board[i][k];

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				char piece = board[i][k];

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}	
		} 
		else {
			if(i > 0) {
				if(j < 6)
					if(board[i - 1][j + 2] == 'C')
						return true;
				if(j > 1) 
					if(board[i - 1][j - 2] == 'C')
						return true;
				
				if(i > 1) {
					if(j < 7)
						if(board[i - 2][j + 1] == 'C')
							return true;
					if(j > 0) 
						if(board[i - 2][j - 1] == 'C')
							return true;
				}
			}
			if(i < 7) {
				if(j < 6)
					if(board[i + 1][j + 2] == 'C')
						return true;
				if(j > 1) 
					if(board[i + 1][j - 2] == 'C')
						return true;
				
				if(i < 6) { 
					if(j < 7)
						if(board[i + 2][j + 1] == 'C')
							return true;
					if(j > 0) 
						if(board[i + 2][j - 1] == 'C')
							return true;
				}
			}
			if(i != 0) {
				if(j != 0)
					if(board[i - 1][j - 1] == 'K' || board[i - 1][j - 1] == 'M')
						return true;
				if(j != 7)
					if(board[i - 1][j + 1] == 'K' || board[i - 1][j + 1] == 'M')
						return true;
				if(board[i - 1][j] == 'K' || board[i - 1][j] == 'M')
					return true;
			}
			if(i != 7) {
				if(j != 0)
					if(board[i + 1][j - 1] == 'O' || board[i + 1][j - 1] == 'P' || board[i + 1][j - 1] == 'K' || board[i + 1][j - 1] == 'M')
						return true;
				if(j != 7)
					if(board[i + 1][j + 1] == 'O' || board[i + 1][j + 1] == 'P' || board[i + 1][j + 1] == 'K' || board[i + 1][j + 1] == 'M')
						return true;
				if(board[i + 1][j] == 'K' || board[i + 1][j] == 'M')
					return true;
			}
			if(j != 0)
				if(board[i][j - 1] == 'K' || board[i][j - 1] == 'M')
					return true;
			if(j != 7)
				if(board[i][j + 1] == 'K' || board[i][j + 1] == 'M')
					return true;
			
			{
				int k = 1;
				while( (i + k) <= 7 && (j + k) <= 7) {
	
					char piece = board[i + k][j + k];
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i + k) <= 7 && (j - k) >= 0 ) {

					char piece = board[i + k][j - k];
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j + k) <= 7 ) {
	
					char piece = board[i - k][j + k];
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j - k) >= 0 ) {
	
					char piece = board[i - k][j - k];
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}	
			}

			for(int k = i + 1; k <= 7; k++) {
				char piece = board[k][j];

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				char piece = board[k][j];

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				char piece = board[i][k];

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				char piece = board[i][k];

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}	
		} 
		return false;
	}
	
	
	public char[][] clone_board() {

		char clone[][] = new char[8][8];
		
		for(int i = 0; i < 8; i++) 
			for(int j = 0; j < 8; j++)
				clone[i][j] = board[i][j];
		
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
		System.out.println("DZTXRYVIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPSIYWOIEXYYRWIV\\nwciurrrrrrrrrrrrrrrpprwrquuuuuuuuuuuuuuuu\\nDZTXRYVIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPSIYWOIEXYYRWIV\\nwciurrrrrrrrrrrrrrrpprwrquuuuuuuuuuuuuuuu\\nDZTXRYVIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPSIYWOIEXYYRWIV\\nwciurrrrrrrrrrrrrrrpprwrquuuuuuuuuuuuuuuu\\nDZTXRYVIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPSIYWOIEXYYRWIV\\nwciurrrrrrrrrrrrrrrpprwrquuuuuuuuuuuuuuuu\\nDZTXRYVIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPSIYWOIEXYYRWIV\\nwciurrrrrrrrrrrrrrrpprwrquuuuuuuuuuuuuuuu\\n");
		return State.void_state();
	}

	public static State void_state() {
		char new_table[][] = new char[8][8];
		
		for(int i = 0; i < 8; i++) 
            for(int j = 0; j < 8; j++)
            	new_table[i][j] = '_';
        
		return new State(new_table, true);
	}
	
	public static char[][] blak_wins() {
		
		char new_table[][] = new char[8][8];
		
		new_table[2][1] = 'b';
		new_table[2][2] = 'l';
		new_table[2][3] = 'a';
		new_table[2][4] = 'c';
		new_table[2][5] = 'k';
		
		new_table[4][2] = 'w';
		new_table[4][3] = 'i';
		new_table[4][4] = 'n';
		new_table[4][5] = 's';

		new_table[0][0] = 'k';
		new_table[0][7] = 'k';
		new_table[7][0] = 'k';
		new_table[7][7] = 'k';

		return new_table;
	}
	
	public static char[][] white_wins() {
		
		char new_table[][] = new char[8][8];
		
		new_table[2][1] = 'W';
		new_table[2][2] = 'H';
		new_table[2][3] = 'I';
		new_table[2][4] = 'T';
		new_table[2][5] = 'E';
		
		new_table[4][2] = 'W';
		new_table[4][3] = 'I';
		new_table[4][4] = 'N';
		new_table[4][5] = 'S';

		new_table[0][0] = 'K';
		new_table[0][7] = 'K';
		new_table[7][0] = 'K';
		new_table[7][7] = 'K';

		return new_table;
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
		char[][] clone = clone_board();
		
		char pieza = clone[i1 - 1][j1 - 1];
		
		if(pieza == 'm')
			pieza = 'k';
		else if(pieza == 'M')
			pieza = 'K';
		else if(pieza == 'r')
			pieza = 't';
		else if(pieza == 'R')
			pieza = 'T';
		/*else if(pieza == 'o' && i1 == 2 && i2 == 4)
			pieza = 'p';
		else if(pieza == 'O' && i1 == 7 && i2 == 5)
			pieza = 'P';*/

		clone[i2 - 1][j2 - 1] = pieza;
		clone[i1 - 1][j1 - 1] = '_';
		
		return new State(clone, !this.white_moves);
	}
	
}
