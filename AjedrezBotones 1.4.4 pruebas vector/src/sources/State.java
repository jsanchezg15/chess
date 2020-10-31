package sources;

import java.util.Vector;

public class State {

	private Vector<Vector<Character>> board;
	private double equilibrium;
	private boolean white_moves;
	private State parent;
	private Vector<State> children;
	
	public State(Vector<Vector<Character>> board, boolean white_moves) {
		this.board = board;
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		
		set_pawns();
	}
	
	public State(String board, boolean white_moves) {
		//this.board = parse_board(board);
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<State>();
		
		set_pawns();
	}
	
	
	public void setChildren(Vector<State> children) { this.children = children; }
	
	public State getParent() {return this.parent;}
	
	public void setWhiteMoves(boolean wm) { this.white_moves = wm; }
	
	public void setParent(State parent) { this.parent = parent; }
	
	public Vector<Vector<Character>> getBoard() { return this.board; }
	
	public boolean get_white_moves() { return this.white_moves; }
	
	public void print() {
		
		System.out.println("    +---+---+---+---+---+---+---+---+");
		System.out.println("    | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |");
		System.out.println("+---+---+---+---+---+---+---+---+---+");

		int k = 1;
		for(int i = 0; i < 8; i++) {

			System.out.print("| " + k++ + " " + "|");

			for(int j = 0; j < 8; j++) 
				System.out.print((board.get(i).get(j) == '_' ? "   " : (board.get(i).get(j) < 90 ? ("-" + board.get(i).get(j) + "-") : ("<" + (char)(board.get(i).get(j) - 32) + ">"))) + "|");
			
			System.out.println("\n+---+---+---+---+---+---+---+---+---+");
		}		
		//System.out.println(this.white_moves);
	}

	
	public double getEquilibium() { 
		if(this.equilibrium < 0) 
			this.equilibrium = calculate_equilibrium();
		
		return this.equilibrium; 
	}
	
	public double calculate_equilibrium_king_menace() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		double white_points = Math.random() / 100;
		double black_points = Math.random() / 100;
		//double white_points = 0.5;
		//double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board.get(i).get(j);		
		
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
					if( is_menaged(i, j, true) ) {
						if(this.white_moves) 
							black_points += 0.2; 
						else 
							white_king = false;
					}
				}
				else if(piece == 'K')	{			// Rey que ya ha hecho un movimiento
					white_king = true;
					if( is_menaged(i, j, true) ) {
						if(this.white_moves) 
							black_points += 0.2; 
						else 
							white_king = false;
					}
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
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.2; 
					}
				}
				else if(piece == 'k') {
					black_king = true;
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.2; 
					}
				}
			}
		
		//System.out.println(white_points);
		//System.out.println(black_points);
		
		if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}

	public double old_calculate_equilibrium() {
		//double white_points = Math.random() / 10;
		//double black_points = Math.random() / 10;
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board.get(i).get(j);		
		
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

	public double calculate_equilibrium() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		double white_points = 0.5 + Math.random() / 100;
		double black_points = 0.5 + Math.random() / 100;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board.get(i).get(j);		
		
				if(piece == 'O')  					// Peón 
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.1 : 0);
				else if(piece == 'P') 				// Peón capturable al paso
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.1 : 0);
				else if(piece == 'R') 				// Torre
					white_points = white_points + 5;
				else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
					white_points = white_points + 5;
				else if(piece == 'C') 				// Caballo
					white_points = white_points + 3 + knight_movement(i, j).size() / 30;
				else if(piece == 'A') 				// Alfil
					white_points = white_points + 3 + bishop_movement(i, j).size() / 30;
				else if(piece == 'Q')				// Reina
					white_points = white_points + 9;
				else if(piece == 'M') {				// Rey
					white_king = true;
					if( is_menaged(i, j, true) ) {
						if(this.white_moves) 
							black_points += 0.2; 
						else 
							white_king = false;
					}
				}
				else if(piece == 'K')	{			// Rey que ya ha hecho un movimiento
					white_king = true;
					if( is_menaged(i, j, true) ) {
						if(this.white_moves) 
							black_points += 0.2; 
						else 
							white_king = false;
					}
				}
				else if(piece == 'o')
					black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'p')
					black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'r')
					black_points = black_points + 5;
				else if(piece == 't')
					black_points = black_points + 5;
				else if(piece == 'c')
					black_points = black_points + 3 + knight_movement(i, j).size() / 30;
				else if(piece == 'a')
					black_points = black_points + 3 + bishop_movement(i, j).size() / 30;
				else if(piece == 'q')
					black_points = black_points + 9;
				else if(piece == 'm') {
					black_king = true;
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.2; 
					}
				}
				else if(piece == 'k') {
					black_king = true;
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.2; 
					}
				}
			}
		
		if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}
	
	public Vector<State> next_states() {
			
		if(!this.children.isEmpty())
			return this.children;
		
		Vector<State> next_states = new Vector<State>();
		
		if(this.getEquilibium() == 0) {
			State state = new State( State.black_wins(), !this.white_moves );
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
						
						char piece = this.board.get(i).get(j);
												
						if(piece == 'O' || piece == 'P')
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
						
						char piece = this.board.get(i).get(j);
						
						if(piece == 'o' || piece == 'p')
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
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			if(kingside) {
				if(board.get(7).get(0) == 'R' && board.get(7).get(1) == '_' && board.get(7).get(2) == '_' && board.get(7).get(3) == '_') {
					if(  !(is_menaged(7, 4, true) || is_menaged(7, 3, true) || is_menaged(7, 2, true)  )  ) {
						new_state =  this.clone_board();
						
						new_state.get(i).setElementAt('K', 2);
						new_state.get(i).setElementAt('T', 3);
						new_state.get(i).setElementAt('_', j);
						new_state.get(7).setElementAt('_', 0);
						
						new_states.add(new State(new_state, false));
					}
				}
				if(board.get(7).get(7) == 'R' && board.get(7).get(6) == '_' && board.get(7).get(5) == '_') {
					if(  !(   is_menaged(7, 4, true) || is_menaged(7, 5, true) || is_menaged(7, 6, true)  )  ) {
						new_state =  this.clone_board();
						
						new_state.get(i).setElementAt('K', 6);
						new_state.get(i).setElementAt('T', 5);
						new_state.get(i).setElementAt('_', j);
						new_state.get(7).setElementAt('_', 7);
						
						new_states.add(new State(new_state, false));
					}
				}
			}
					
			if(i != 0) {
				
				if( board.get(i - 1).get(j) > 90 ) {
					new_state =  this.clone_board();

					new_state.get(i - 1).setElementAt('K', j);
					new_state.get(i).setElementAt('_', j);
							
					new_states.add(new State(new_state, false));
				}
				
				if(j != 0) {
					if( board.get(i - 1).get(j - 1) > 90 ) {
						new_state =  this.clone_board();

						new_state.get(i - 1).setElementAt('K', j - 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, false));
					}
				}
				
				if(j != 7) {
					if( board.get(i - 1).get(j + 1) > 90 ) {
						new_state =  this.clone_board();

						new_state.get(i - 1).setElementAt('K', j + 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, false));
					}
				}
			}
			if(i != 7) {
				if( board.get(i + 1).get(j) > 90 ) {
					new_state =  this.clone_board();

					new_state.get(i + 1).setElementAt('K', j);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
				}
				
				if(j != 0) {
					if( board.get(i + 1).get(j - 1) > 90 ) {
						new_state =  this.clone_board();

						new_state.get(i + 1).setElementAt('K', j - 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, false));
					}
				}
				
				if(j != 7) {
					if( board.get(i + 1).get(j + 1) > 90 ) {
						new_state =  this.clone_board();

						new_state.get(i + 1).setElementAt('K', j + 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, false));
					}
				}
			}
			if(j != 0) {
				if( board.get(i).get(j - 1) > 90 ) {
					new_state =  this.clone_board();

					new_state.get(i).setElementAt('K', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
				}
			}
			if(j != 7) {
				if( board.get(i).get(j + 1) > 90 ) {
					new_state =  this.clone_board();

					new_state.get(i).setElementAt('K', j + 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
				}
			}
		}
		else {
			if(kingside) {
				if(board.get(0).get(0) == 'r' && board.get(0).get(1) == '_' && board.get(0).get(2) == '_' && board.get(0).get(3) == '_') {
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 3, false) || is_menaged(0, 2, false)  )  ) {
						new_state =  this.clone_board();
						
						new_state.get(i).setElementAt('k', 2);
						new_state.get(i).setElementAt('t', 3);
						new_state.get(i).setElementAt('_', j);
						new_state.get(0).setElementAt('_', 0);
							
						new_states.add(new State(new_state, true));
					}
				}
				if(board.get(0).get(7) == 'r' && board.get(0).get(6) == '_' && board.get(0).get(5) == '_') {
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 5, false) || is_menaged(0, 6, false)  )  ) {
						new_state =  this.clone_board();

						new_state.get(i).setElementAt('k', 6);
						new_state.get(i).setElementAt('t', 5);
						new_state.get(i).setElementAt('_', j);
						new_state.get(0).setElementAt('_', 7);

						new_states.add(new State(new_state, true));
					}
				}
			}
			
			if(i != 0) {
				
				if( board.get(i - 1).get(j) < 96 ) {
					new_state =  this.clone_board();

					new_state.get(i - 1).setElementAt('k', j);
					new_state.get(i).setElementAt('_', j);
							
					new_states.add(new State(new_state, true));
				}
				
				if(j != 0) {
					if( board.get(i - 1).get(j - 1) < 96 ) {
						new_state =  this.clone_board();

						new_state.get(i - 1).setElementAt('k', j - 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, true));
					}
				}
				
				if(j != 7) {
					if( board.get(i - 1).get(j + 1) < 96 ) {
						new_state =  this.clone_board();

						new_state.get(i - 1).setElementAt('k', j + 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, true));
					}
				}
			}
			if(i != 7) {
				if( board.get(i + 1).get(j) < 96 ) {
					new_state =  this.clone_board();

					new_state.get(i + 1).setElementAt('k', j);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, true));
				}
				
				if(j != 0) {
					if( board.get(i + 1).get(j - 1) < 96 ) {
						new_state =  this.clone_board();

						new_state.get(i + 1).setElementAt('k', j - 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, true));
					}
				}
				
				if(j != 7) {
					if( board.get(i + 1).get(j + 1) < 96 ) {
						new_state =  this.clone_board();

						new_state.get(i + 1).setElementAt('k', j + 1);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, true));
					}
				}
			}
			if(j != 0) {
				if( board.get(i).get(j - 1) < 96 ) {
					new_state =  this.clone_board();

					new_state.get(i).setElementAt('k', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, true));
				}
			}
			if(j != 7) {
				if( board.get(i).get(j + 1) < 96 ) {
					new_state =  this.clone_board();

					new_state.get(i).setElementAt('k', j + 1);
					new_state.get(i).setElementAt('_', j);
					
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
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board.get(i + k).get(j + k) == '_') {
					new_state.get(i + k).setElementAt('A', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i + k).get(j + k) > 90) {
					new_state.get(i + k).setElementAt('A', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i + k).get(j - k) == '_') {					
					new_state.get(i + k).setElementAt('A', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i + k).get(j - k) > 90) {
					new_state.get(i + k).setElementAt('A', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j + k) == '_') {				
					new_state.get(i - k).setElementAt('A', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i - k).get(j + k) > 90) {
					new_state.get(i - k).setElementAt('A', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j - k) == '_') {
					new_state.get(i - k).setElementAt('A', j - k);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i - k).get(j - k) > 90) {
					new_state.get(i - k).setElementAt('A', j - k);
					new_state.get(i).setElementAt('_', j);

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

				if(board.get(i + k).get(j + k) == '_') {	
					new_state.get(i + k).setElementAt('a', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i + k).get(j + k) < 96) {
					new_state.get(i + k).setElementAt('a', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i + k).get(j - k) == '_') {
					new_state.get(i + k).setElementAt('a', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i + k).get(j - k) < 96) {
					new_state.get(i + k).setElementAt('a', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j + k) == '_') {
					new_state.get(i - k).setElementAt('a', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i - k).get(j + k) < 96) {
					new_state.get(i - k).setElementAt('a', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j - k) == '_') {				
					new_state.get(i - k).setElementAt('a', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i - k).get(j - k) < 96) {
					new_state.get(i - k).setElementAt('a', j - k);
					new_state.get(i).setElementAt('_', j);

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
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			if(i > 1 && j < 7) 
				if( board.get(i - 2).get(j + 1) > 90 ) {
					new_state = clone_board();
	
					new_state.get(i - 2).setElementAt('C', j + 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j < 6) 
				if( board.get(i - 1).get(j + 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('C', j + 2);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j < 6) 
				if( board.get(i + 1).get(j + 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('C', j + 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j < 7) 
				if( board.get(i + 2).get(j + 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 2).setElementAt('C', j + 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}
			if(i < 6 && j > 0) 
				if( board.get(i + 2).get(j - 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 2).setElementAt('C', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));
				}
			if(i < 7 && j > 1) 
				if( board.get(i + 1).get(j - 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('C', j - 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}
			if(i > 0 && j > 1) 
				if( board.get(i - 1).get(j - 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('C', j - 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}
			if(i > 1 && j > 0) 
				if( board.get(i - 2).get(j - 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 2).setElementAt('C', j - 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
				}	
		}
		else {
			if(i > 1 && j < 7) 
				if( board.get(i - 2).get(j + 1) > 90 ) {
					new_state = clone_board();
	
					new_state.get(i - 2).setElementAt('c', j + 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
			if(i > 0 && j < 6) 
				if( board.get(i - 1).get(j + 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('c', j + 2);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, true));
				}
			if(i < 7 && j < 6) 
				if( board.get(i + 1).get(j + 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('c', j + 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
			if(i < 6 && j < 7) 
				if( board.get(i + 2).get(j + 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 2).setElementAt('c', j + 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
			if(i < 6 && j > 0) 
				if( board.get(i + 2).get(j - 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 2).setElementAt('c', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, true));
				}
			if(i < 7 && j > 1) 
				if( board.get(i + 1).get(j - 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('c', j - 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
			if(i > 0 && j > 1) 
				if( board.get(i - 1).get(j - 2) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('c', j - 2);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
			if(i > 1 && j > 0) 
				if( board.get(i - 2).get(j - 1) > 90 ) {
					new_state = clone_board();
					
					new_state.get(i - 2).setElementAt('c', j - 1);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
				}
		}
		
		for(State state : new_states) 
			state.setParent(this);
		
		return new_states;
	}

	public Vector<State> rook_movement(int i, int j) {
		Vector<State> new_states = new Vector<State>();
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('T', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(k).get(j) > 90) {
					new_state.get(k).setElementAt('T', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('T', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(k).get(j) > 90) {
					new_state.get(k).setElementAt('T', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('T', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(i).get(k) > 90) {
					new_state.get(i).setElementAt('T', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('T', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(i).get(k) > 90) {
					new_state.get(i).setElementAt('T', k);
					new_state.get(i).setElementAt('_', j);
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

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('t', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(k).get(j) < 96) {
					new_state.get(k).setElementAt('t', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('t', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(k).get(j) < 96) {
					new_state.get(k).setElementAt('t', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('t', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(i).get(k) < 96) {
					new_state.get(i).setElementAt('t', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('t', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(i).get(k) < 96) {
					new_state.get(i).setElementAt('t', k);
					new_state.get(i).setElementAt('_', j);
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
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			for(int k = i + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('Q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(k).get(j) > 90) {
					new_state.get(k).setElementAt('Q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('Q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(k).get(j) > 90) {
					new_state.get(k).setElementAt('Q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('Q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(i).get(k) > 90) {
					new_state.get(i).setElementAt('Q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('Q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
				}
				else if(board.get(i).get(k) > 90) {
					new_state.get(i).setElementAt('Q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}	
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board.get(i + k).get(j + k) == '_') {
					new_state.get(i + k).setElementAt('Q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i + k).get(j + k) > 90) {
					new_state.get(i + k).setElementAt('Q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i + k).get(j - k) == '_') {					
					new_state.get(i + k).setElementAt('Q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i + k).get(j - k) > 90) {
					new_state.get(i + k).setElementAt('Q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j + k) == '_') {				
					new_state.get(i - k).setElementAt('Q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i - k).get(j + k) > 90) {
					new_state.get(i - k).setElementAt('Q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j - k) == '_') {
					new_state.get(i - k).setElementAt('Q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, false));
					k++;
				}
				else if(board.get(i - k).get(j - k) > 90) {
					new_state.get(i - k).setElementAt('Q', j - k);
					new_state.get(i).setElementAt('_', j);

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

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(k).get(j) < 96) {
					new_state.get(k).setElementAt('q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(k).get(j) == '_') {
					new_state.get(k).setElementAt('q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(k).get(j) < 96) {
					new_state.get(k).setElementAt('q', j);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(i).get(k) < 96) {
					new_state.get(i).setElementAt('q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				new_state = clone_board();

				if(board.get(i).get(k) == '_') {
					new_state.get(i).setElementAt('q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
				}
				else if(board.get(i).get(k) < 96) {
					new_state.get(i).setElementAt('q', k);
					new_state.get(i).setElementAt('_', j);
					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {
				new_state = clone_board();

				if(board.get(i + k).get(j + k) == '_') {	
					new_state.get(i + k).setElementAt('q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i + k).get(j + k) < 96) {
					new_state.get(i + k).setElementAt('q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i + k).get(j - k) == '_') {
					new_state.get(i + k).setElementAt('q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i + k).get(j - k) < 96) {
					new_state.get(i + k).setElementAt('q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j + k) == '_') {
					new_state.get(i - k).setElementAt('q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i - k).get(j + k) < 96) {
					new_state.get(i - k).setElementAt('q', j + k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {
				new_state = clone_board();

				if(board.get(i - k).get(j - k) == '_') {				
					new_state.get(i - k).setElementAt('q', j - k);
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));
					k++;
				}
				else if(board.get(i - k).get(j - k) < 96) {
					new_state.get(i - k).setElementAt('q', j - k);
					new_state.get(i).setElementAt('_', j);

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
		Vector<Vector<Character>> new_state; 
		
		if(this.white_moves) {
			
			if(board.get(i - 1).get(j) == '_') {
				new_state = clone_board();
				
				if(i == 1) 
					new_state.get(i - 1).setElementAt('Q', j);
				else 
					new_state.get(i - 1).setElementAt('O', j);
				
				new_state.get(i).setElementAt('_', j);

				new_states.add(new State(new_state, false));
				
				if(i == 6) 
					if(board.get(i - 2).get(j) == '_') {
						new_state = clone_board();

						new_state.get(i - 2).setElementAt('P', j);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, false));	
					}
			} 
			if(j != 0) {
				if(board.get(i - 1).get(j - 1) > 96) {
					new_state = clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt('Q', j - 1);
					else 
						new_state.get(i - 1).setElementAt('O', j - 1);
					
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}
				else if(board.get(i).get(j - 1) == 'p') {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('O', j - 1);
					new_state.get(i).setElementAt('_', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}
			}
			if(j != 7) {
				if(board.get(i - 1).get(j + 1) > 96) {
					new_state = clone_board();
					
					if(i == 1) 
						new_state.get(i - 1).setElementAt('Q', j + 1);
					else 
						new_state.get(i - 1).setElementAt('O', j + 1);
					
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}	
				else if(board.get(i).get(j + 1) == 'p') {
					new_state = clone_board();
					
					new_state.get(i - 1).setElementAt('O', j + 1);
					new_state.get(i).setElementAt('_', j + 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}
			}
		} 
		else {
			
			if(board.get(i + 1).get(j) == '_') {
				new_state = clone_board();
				
				if(i == 6) 
					new_state.get(i + 1).setElementAt('q', j);
				else 
					new_state.get(i + 1).setElementAt('o', j);
				
				new_state.get(i).setElementAt('_', j);
				
				new_states.add(new State(new_state, true));
				
				if(i == 1) 
					if(board.get(i + 2).get(j) == '_') {
						new_state = clone_board();

						new_state.get(i + 2).setElementAt('p', j);
						new_state.get(i).setElementAt('_', j);
						
						new_states.add(new State(new_state, true));	
					}
			} 
			if(j != 0) {
				if(board.get(i + 1).get(j - 1) < 91) {
					new_state = clone_board();
					
					if(i == 6) 
						new_state.get(i + 1).setElementAt('q', j - 1);
					else 
						new_state.get(i + 1).setElementAt('o', j - 1);
					
					new_state.get(i).setElementAt('_', j);

					new_states.add(new State(new_state, true));	
				}
				else if(board.get(i).get(j - 1) == 'P') {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('o', j - 1);
					new_state.get(i).setElementAt('_', j - 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}
			}
			if(j != 7) {
				if(board.get(i + 1).get(j + 1) < 91) {
					new_state = clone_board();
					
					if(i == 6) 
						new_state.get(i + 1).setElementAt('q', j + 1);
					else 
						new_state.get(i + 1).setElementAt('o', j + 1);
					
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, true));	
				}
				else if(board.get(i).get(j + 1) == 'P') {
					new_state = clone_board();
					
					new_state.get(i + 1).setElementAt('o', j + 1);
					new_state.get(i).setElementAt('_', j + 1);
					new_state.get(i).setElementAt('_', j);
					
					new_states.add(new State(new_state, false));	
				}
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
					if(board.get(i - 1).get(j + 2) == 'c')
						return true;
				if(j > 1) 
					if(board.get(i - 1).get(j - 2) == 'c')
						return true;
				
				if(i > 1) {
					if(j < 7)
						if(board.get(i - 2).get(j + 1) == 'c')
							return true;
					if(j > 0) 
						if(board.get(i - 2).get(j - 1) == 'c')
							return true;
				}
			}
			if(i < 7) {
				if(j < 6)
					if(board.get(i + 1).get(j + 2) == 'c')
						return true;
				if(j > 1) 
					if(board.get(i + 1).get(j - 2) == 'c')
						return true;
				
				if(i < 6) { 
					if(j < 7)
						if(board.get(i + 2).get(j + 1) == 'c')
							return true;
					if(j > 0) 
						if(board.get(i + 2).get(j - 1) == 'c')
							return true;
				}
			}
			
			if(i != 0) {
				if(j != 0)
					if(board.get(i - 1).get(j - 1) == 'o' || board.get(i - 1).get(j - 1) == 'P' || board.get(i - 1).get(j - 1) == 'k' || board.get(i - 1).get(j - 1) == 'm')
						return true;
				if(j != 7)
					if(board.get(i - 1).get(j + 1) == 'o' || board.get(i - 1).get(j + 1) == 'P' || board.get(i - 1).get(j + 1) == 'k' || board.get(i - 1).get(j + 1) == 'm')
						return true;
				if(board.get(i - 1).get(j) == 'k' || board.get(i - 1).get(j) == 'm')
					return true;
			}
			if(i != 7) {
				if(j != 0)
					if(board.get(i + 1).get(j - 1) == 'k' || board.get(i + 1).get(j - 1) == 'm')
						return true;
				if(j != 7)
					if(board.get(i + 1).get(j + 1) == 'k' || board.get(i + 1).get(j + 1) == 'm')
						return true;
				if(board.get(i + 1).get(j) == 'k' ||board.get(i + 1).get(j) == 'm')
					return true;
			}
			if(j != 0)
				if(board.get(i).get(j - 1) == 'k' || board.get(i).get(j - 1) == 'm')
					return true;
			if(j != 7)
				if(board.get(i).get(j + 1) == 'k' || board.get(i).get(j + 1) == 'm')
					return true;
			
			{
				int k = 1;
				while( (i + k) <= 7 && (j + k) <= 7) {
	
					char piece = board.get(i + k).get(j + k);
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i + k) <= 7 && (j - k) >= 0 ) {

					char piece = board.get(i + k).get(j - k);
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j + k) <= 7 ) {
	
					char piece = board.get(i - k).get(j + k);
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j - k) >= 0 ) {
	
					char piece = board.get(i - k).get(j - k);
					
					if(piece == 'a' || piece == 'q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}	
			}

			for(int k = i + 1; k <= 7; k++) {
				char piece = board.get(k).get(j);

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				char piece = board.get(k).get(j);

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				char piece = board.get(i).get(k);

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				char piece = board.get(i).get(k);

				if(piece == 't' || piece == 'r' || piece == 'q') 
					return true;

				if( !(piece == '_') )
					break;
			}	
		} 
		else {
			if(i > 0) {
				if(j < 6)
					if(board.get(i - 1).get(j + 2) == 'C')
						return true;
				if(j > 1) 
					if(board.get(i - 1).get(j - 2) == 'C')
						return true;
				
				if(i > 1) {
					if(j < 7)
						if(board.get(i - 2).get(j + 1) == 'C')
							return true;
					if(j > 0) 
						if(board.get(i - 2).get(j - 1) == 'C')
							return true;
				}
			}
			if(i < 7) {
				if(j < 6)
					if(board.get(i + 1).get(j + 2) == 'C')
						return true;
				if(j > 1) 
					if(board.get(i + 1).get(j - 2) == 'C')
						return true;
				
				if(i < 6) { 
					if(j < 7)
						if(board.get(i + 2).get(j + 1) == 'C')
							return true;
					if(j > 0) 
						if(board.get(i + 2).get(j - 1) == 'C')
							return true;
				}
			}
			if(i != 0) {
				if(j != 0)
					if(board.get(i - 1).get(j - 1) == 'K' || board.get(i - 1).get(j - 1) == 'M')
						return true;
				if(j != 7)
					if(board.get(i - 1).get(j + 1) == 'K' || board.get(i - 1).get(j + 1) == 'M')
						return true;
				if(board.get(i - 1).get(j) == 'K' || board.get(i - 1).get(j) == 'M')
					return true;
			}
			if(i != 7) {
				if(j != 0)
					if(board.get(i + 1).get(j - 1) == 'O' || board.get(i + 1).get(j - 1) == 'P' || board.get(i + 1).get(j - 1) == 'K' || board.get(i + 1).get(j - 1) == 'M')
						return true;
				if(j != 7)
					if(board.get(i + 1).get(j + 1) == 'O' || board.get(i + 1).get(j + 1) == 'P' || board.get(i + 1).get(j + 1) == 'K' || board.get(i + 1).get(j + 1) == 'M')
						return true;
				if(board.get(i + 1).get(j) == 'K' || board.get(i + 1).get(j) == 'M')
					return true;
			}
			if(j != 0)
				if(board.get(i).get(j - 1) == 'K' || board.get(i).get(j - 1) == 'M')
					return true;
			if(j != 7)
				if(board.get(i).get(j + 1) == 'K' || board.get(i).get(j + 1) == 'M')
					return true;
			
			{
				int k = 1;
				while( (i + k) <= 7 && (j + k) <= 7) {
	
					char piece = board.get(i + k).get(j + k);
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i + k) <= 7 && (j - k) >= 0 ) {

					char piece = board.get(i + k).get(j - k);
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j + k) <= 7 ) {
	
					char piece = board.get(i - k).get(j + k);
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}
				k = 1;
				while( (i - k) >= 0 && (j - k) >= 0 ) {
	
					char piece = board.get(i - k).get(j - k);
					
					if(piece == 'A' || piece == 'Q') 
						return true;
					
					if( !(piece == '_') )
						break;
					
					k++;
				}	
			}

			for(int k = i + 1; k <= 7; k++) {
				char piece = board.get(k).get(j);

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = i - 1; k >= 0; k--) {
				char piece = board.get(k).get(j);

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j + 1; k <= 7; k++) {
				char piece = board.get(i).get(k);

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}
			for(int k = j - 1; k >= 0; k--) {
				char piece = board.get(i).get(k);

				if(piece == 'T' || piece == 'R' || piece == 'Q') 
					return true;

				if( !(piece == '_') )
					break;
			}	
		} 
		return false;
	}
	
	
	public Vector<Vector<Character>> clone_board() {

		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();
		
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add(this.board.get(i).get(j));
		}
		
		return vector;		
	}

	public State getMinMax(int iteration, MiBoolean stop) {
		if(stop.value)
			return null;
		
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
					State max = next.remove(0).getMinMax(iteration - 1, stop);
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, max.getEquilibium(), stop);
						if(max.getEquilibium() < state.getEquilibium())
							max = state;
					}
					
					return max;
				}
			}
			else {
				
				Vector<State> next = next_states();
				
				if(!next.isEmpty()) {
					State min = next.remove(0).getMinMax(iteration - 1, stop);
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, min.getEquilibium(), stop);
						if(min.getEquilibium() > state.getEquilibium())
							min = state;
					}
					
					return min;
				}
			}
		}	
		return State.void_state();
	}

	private State getMinMax(int iteration, double limit, MiBoolean stop) {
		if(stop.value)
			return null;
		
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
					State max = next.remove(0).getMinMax(iteration - 1, stop);
					
					if(max.getEquilibium() >= limit)
						return max;
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, max.getEquilibium(), stop);
						
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
					State min = next.remove(0).getMinMax(iteration - 1, stop);
					
					if(min.getEquilibium() <= limit)
						return min;
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, min.getEquilibium(), stop);
						
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

		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();
		
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add('_');
		}
        
		return new State(vector, true);
	}
	
	public static Vector<Vector<Character>> black_wins() {
		
		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();
		
		char new_table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
								{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'}};		

		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add(new_table[i][j]);
		}
				
		return vector;
	}
	
	public static Vector<Vector<Character>> white_wins() {
		
		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();

		char new_table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
								{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		

		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add(new_table[i][j]);
		}
		
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
		Vector<Vector<Character>> clone = clone_board();
		
		char pieza = clone.get(i1 - 1).get(j1 - 1);
		
		System.out.println(i1 + " " + j1 + " - " + i2 + " " + j2);
		
		
		if(pieza == 'm') {
			pieza = 'k';
			if(j2 == 3 && clone.get(0).get(0) == 'r') {
				clone.get(0).setElementAt('_', 0);
				clone.get(0).setElementAt('t', 3);
			}
			else if(j2 == 7 && clone.get(0).get(7) == 'r') {
				System.out.println("HEY");
				clone.get(0).setElementAt('_', 7);
				clone.get(0).setElementAt('t', 5);
			}
		}
		else if(pieza == 'M') {
			pieza = 'K';
			if(j2 == 3 && clone.get(7).get(0) == 'R') {
				clone.get(7).setElementAt('_', 0);
				clone.get(7).setElementAt('T', 3);
			}
			else if(j2 == 7 && clone.get(7).get(7) == 'R') {
				clone.get(7).setElementAt('_', 7);
				clone.get(7).setElementAt('T', 5);
			}
		}
		else if(pieza == 'r')
			pieza = 't';
		else if(pieza == 'R')
			pieza = 'T';
		else if(pieza == 'o' && i1 == 2 && i2 == 4)
			pieza = 'p';
		else if(pieza == 'O' && i1 == 7 && i2 == 5)
			pieza = 'P';
		else if(pieza == 'o' && i2 == 8)
			pieza = 'q';
		else if(pieza == 'O' && i2 == 1)
			pieza = 'Q';
		else if(pieza == 'p')
			pieza = 'o';
		else if(pieza == 'P')
			pieza = 'O';
		else if(pieza == 'o' && i2 == 6 && (j2 - j1) == 1) {
			if(board.get(4).get(j1) == 'P')
				clone.get(4).setElementAt('_', j1);
		}
		else if(pieza == 'o' && i2 == 6 && (j2 - j1) == -1) {
			if(board.get(4).get(j1 - 2) == 'P')
				clone.get(4).setElementAt('_', j1 - 2);
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == 1) {
			if(board.get(3).get(j1) == 'p')
				clone.get(3).setElementAt('_', j1);
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == -1) {
			if(board.get(3).get(j1 - 2) == 'p')
				clone.get(3).setElementAt('_', j1 - 2);
		}
		
		
		clone.get(i2 - 1).setElementAt(pieza, j2 - 1);
		clone.get(i1 - 1).setElementAt('_', j1 - 1);
		
		State move = new State(clone, !this.white_moves);
		
		move.print();
		
		return move;
	}
	
	public boolean board_equals(State state) {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) 
				 if(this.board.get(i).get(j) != state.board.get(i).get(j))
					 return false;
		
		return true;		 
	}
	
	public boolean exist_move(State state) {
		for(State st : this.next_states()) 
			if(st.board_equals(state))
				return true;
		
		System.out.println("FALSE");
		return false;
	}
	
	private void set_pawns() {
		if(this.white_moves) {
			for(int j = 0; j < 8; j++) {
				if(this.board.get(4).get(j) == 'P') {
					this.board.get(4).setElementAt('O', j);
					return;
				}
			}
		}
		else {
			for(int j = 0; j < 8; j++) {
				if(this.board.get(3).get(j) == 'p') {
					this.board.get(3).setElementAt('O', j);
					return;
				}
			}
		}
	}
	
	
	public static char[][] parse_board(String str) {
		char[][] new_board = new char[8][8];
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				new_board[i][j] = str.charAt(i * 8 + j);
		
		return new_board;
 	}
		
	public String board_to_string() {
		String str = "";
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				str += this.board.get(i).get(j);
		
		return str;
	}
	
}