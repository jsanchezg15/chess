package sources;

import java.util.Vector;

public class State {

	private char[][] board;
	private double equilibrium;
	public boolean white_moves;
	private State parent;
	private Vector<Movimiento> children;
	
	public State(char[][] board, boolean white_moves) {
		this.board = board;
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<Movimiento>();
		
		set_pawns();
	}
	
	public State(String board, boolean white_moves) {
		this.board = parse_board(board);
		this.equilibrium = -1;
		this.white_moves = white_moves;
		this.parent = null;
		this.children = new Vector<Movimiento>();
		
		set_pawns();
	}
	
	
	public void setChildren(Vector<Movimiento> children) { this.children = children; }
	
	public State getParent() {return this.parent;}
	
	public void setWhiteMoves(boolean wm) { this.white_moves = wm; }
	
	public void setParent(State parent) { this.parent = parent; }
	
	public char[][] getBoard() { return this.board; }
	
	public boolean get_white_moves() { return this.white_moves; }
	
	public void print() {
		
		System.out.println("    +---+---+---+---+---+---+---+---+\n    | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |\n+---+---+---+---+---+---+---+---+---+");

		for(int i = 0; i < 8; i++) {

			System.out.print("| " + (i + 1) + " " + "|");

			for(int j = 0; j < 8; j++) 
				System.out.print((board[i][j] == '_' ? "   " : (board[i][j] < 90 ? ("-" + board[i][j] + "-") : ("<" + (char)(board[i][j] - 32) + ">"))) + "|");
			
			System.out.println("\n+---+---+---+---+---+---+---+---+---+");
		}		
	}

	
	public double getEquilibium() { 
		if(this.equilibrium < 0) 
			this.equilibrium = prev_new_calculate_equilibrium();
		
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

	public double calculate_equilibrium() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board[i][j];		
		
				if(piece == 'O')  					// Peón 
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'P') 				// Peón capturable al paso
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'R') 				// Torre
					white_points = white_points + 5;
				else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
					white_points = white_points + 5;
				else if(piece == 'C') 				// Caballo
					white_points = white_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
				else if(piece == 'A') 				// Alfil
					white_points = white_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
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
					black_points = black_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
				else if(piece == 'a')
					black_points = black_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
				else if(piece == 'q')
					black_points = black_points + 9;
				else if(piece == 'm') {
					black_king = true;
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.1; 
					}
				}
				else if(piece == 'k') {
					black_king = true;
					if( is_menaged(i, j, false) ) {
						if(this.white_moves) 
							black_king = false;
						else 
							white_points += 0.1; 
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

	public double old_new_calculate_equilibrium() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		//return Math.random() * Math.random();
		
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board[i][j];		
		
				if(piece == '_')
					continue;
				
				if(piece == 'O')  					// Peón 
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'o')
					black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);

				else if(piece == 'C') 				// Caballo
					white_points = white_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
				else if(piece == 'A') 				// Alfil
					white_points = white_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
				else if(piece == 'c')
					black_points = black_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
				else if(piece == 'a')
					black_points = black_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
				
				else if(piece == 'R') 				// Torre
					white_points = white_points + 5;
				else if(piece == 'r')
					black_points = black_points + 5;
				else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
					white_points = white_points + 5;
				else if(piece == 't')
					black_points = black_points + 5;
				
				else if(piece == 'K')				// Rey que ya ha hecho un movimiento
					white_king = true;
				else if(piece == 'k') 
					black_king = true;
				
				else if(piece == 'Q')				// Reina
					white_points = white_points + 9;
				else if(piece == 'q')
					black_points = black_points + 9;	
				
				else if(piece == 'M') 				// Rey
					white_king = true;
				else if(piece == 'm') 
					black_king = true;
				
				else if(piece == 'P') 				// Peón capturable al paso
					white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
				else if(piece == 'p')
					black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
			}
		
		if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}
	
	public double prev_new_calculate_equilibrium() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		//return Math.random() * Math.random();
		
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				char piece = board[i][j];		
		
				if(piece == '_')
					continue;
				
				if(piece < 95) {
					if(piece == 'O')  					// Peón 
						white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
					else if(piece == 'C') 				// Caballo
						white_points = white_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
					else if(piece == 'A') 				// Alfil
						white_points = white_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
					else if(piece == 'R') 				// Torre
						white_points = white_points + 5;
					else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
						white_points = white_points + 5;
					else if(piece == 'Q')				// Reina
						white_points = white_points + 9;
					else if(piece == 'K')				// Rey que ya ha hecho un movimiento
						white_king = true;
					else if(piece == 'M') 				// Rey
						white_king = true;
					else if(piece == 'P') 				// Peón capturable al paso
						white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
				}
				else {
					if(piece == 'o')
						black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
					else if(piece == 'c')
						black_points = black_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
					else if(piece == 'a')
						black_points = black_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
					else if(piece == 'r')
						black_points = black_points + 5;
					else if(piece == 't')
						black_points = black_points + 5;
					else if(piece == 'q')
						black_points = black_points + 9;	
					else if(piece == 'k') 
						black_king = true;
					else if(piece == 'm') 
						black_king = true;
					else if(piece == 'p')
						black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
				}

			}
		
		if(!white_king)
			return 0;
		else if(!black_king)
			return Double.MAX_VALUE;
		else
			return white_points / black_points;
	}
	
	public double new_calculate_equilibrium() {
		// Puntúa +0.2 al contrario por si el rey está amenazado y te toca mover
		// Si el rey está amenazado y no te toca mover puntúa infinito o 0
		
		//return Math.random() * Math.random();
		
		double white_points = 0.5;
		double black_points = 0.5;
		boolean white_king = false;
		boolean black_king = false;
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				
				char piece = board[i][j];		
		
				if(piece == '_')
					continue;
				
				if(piece < 95) {
					if(piece < 79) {
						if(piece == 'C') 				// Caballo
							white_points = white_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
						else if(piece == 'A') 				// Alfil
							white_points = white_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
						else if(piece == 'K')				// Rey que ya ha hecho un movimiento
							white_king = true;
						else if(piece == 'M') 				// Rey
							white_king = true;
					}
					else {
						if(piece < 81) 
							white_points = white_points + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
						
						else {
							if(piece == 'T') 				// Torre que ya ha hecho un movimiento
								white_points = white_points + 5;		
							else if(piece == 'Q')			// Reina
								white_points = white_points + 9;
							else if(piece == 'R') 			// Torre
								white_points = white_points + 5;
						}
					}
				}
				else {
					if(piece < 111) {
						if(piece == 'c')
							black_points = black_points + 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
						else if(piece == 'a')
							black_points = black_points + 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
						else if(piece == 'k') 
							black_king = true;
						else if(piece == 'm') 
							black_king = true;
					}
					else {
						if(piece < 113)
							black_points = black_points + 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);

						if(piece == 't')
							black_points = black_points + 5;
						else if(piece == 'q')
							black_points = black_points + 9;	
						else if(piece == 'r')
							black_points = black_points + 5;
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
	
	public Vector<Movimiento> next_states() {
			
		//if(!this.children.isEmpty())
			//return this.children;
		
		Vector<Movimiento> next_states = new Vector<Movimiento>();
		
		if(this.getEquilibium() == 0) {
			State state = new State( State.black_wins(), !this.white_moves );
			state.setParent(this);
			//next_states.add(state);
		}
		else if(this.getEquilibium() == Double.MAX_VALUE) {
			State state = new State( State.white_wins(), !this.white_moves );
			state.setParent(this);
			//next_states.add(state);
		}
		
		else {
			if(this.white_moves) {
				
				for(byte i = 0; i < 8; i++) {
					for(byte j = 0; j < 8; j++) {
						
						char piece = board[i][j];
									
						if(piece < 95) {
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
			}
			else {
				for(byte i = 0; i < 8; i++) {
					for(byte j = 0; j < 8; j++) {
						
						char piece = board[i][j];
						
						if(piece > 95) {
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
		}
		next_states.trimToSize();
		
		this.children = next_states;
		
		return next_states;
	}
	
	
	public Vector<Movimiento> king_movement(byte i, byte j, boolean kingside) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		kingside = false;
		
		if(this.white_moves) {
			if(kingside) {
				if(board[7][0] == 'R' && board[7][1] == '_' && board[7][2] == '_' && board[7][3] == '_') 
					if(  !(is_menaged(7, 4, true) || is_menaged(7, 3, true) || is_menaged(7, 2, true)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 3));
					
				if(board[7][7] == 'R' && board[7][6] == '_' && board[7][5] == '_') {
					if(  !(   is_menaged(7, 4, true) || is_menaged(7, 5, true) || is_menaged(7, 6, true)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 6));
				}
			}
					
			if(i != 0) {
				if( board[i - 1][j] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) j));
				
				if(j != 0) 
					if( board[i - 1][j - 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1) ) );
				
				if(j != 7) 
					if( board[i - 1][j + 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1) ) );

			}
			if(i != 7) {
				if( board[i + 1][j] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) j ) );
				
				if(j != 0) 
					if( board[i + 1][j - 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1) ) );

				if(j != 7) 
					if( board[i + 1][j + 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1) ) );

			}
			if(j != 0) 
				if( board[i][j - 1] > 90 )
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j - 1) ) );

			if(j != 7) 
				if( board[i][j + 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j + 1) ) );

		}
		else {
			if(kingside) {
				if(board[0][0] == 'r' && board[0][1] == '_' && board[0][2] == '_' && board[0][3] == '_') 
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 3, false) || is_menaged(0, 2, false)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 3));
					
				if(board[0][7] == 'r' && board[0][6] == '_' && board[0][5] == '_') 
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 5, false) || is_menaged(0, 6, false)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 6));
			}
			
			if(i != 0) {
				if( board[i - 1][j] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) j));
				
				if(j != 0) 
					if( board[i - 1][j - 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1) ) );
				
				if(j != 7) 
					if( board[i - 1][j + 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1) ) );

			}
			if(i != 7) {
				if( board[i + 1][j] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) j ) );
				
				if(j != 0) 
					if( board[i + 1][j - 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1) ) );

				if(j != 7) 
					if( board[i + 1][j + 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1) ) );

			}
			if(j != 0) 
				if( board[i][j - 1] < 96 )
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j - 1) ) );

			if(j != 7) 
				if( board[i][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j + 1) ) );
		}
		
		//for(State state : movimientos) 
			//state.setParent(this);
		
		return movimientos;	
	}
	
	public Vector<Movimiento> bishop_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {

				if(board[i + k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k)));
					k++;
				}
				else if(board[i + k][j + k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {

				if(board[i + k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k)));
					k++;
				}
				else if(board[i + k][j - k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {

				if(board[i - k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k)));
					k++;
				}
				else if(board[i - k][j + k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {

				if(board[i - k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k)));
					k++;
				}
				else if(board[i - k][j - k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k)));
					break;
				} 
				else 
					break;
			}		
		}
		else {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {

				if(board[i + k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k)));
					k++;
				}
				else if(board[i + k][j + k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {

				if(board[i + k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k)));
					k++;
				}
				else if(board[i + k][j - k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {

				if(board[i - k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k)));
					k++;
				}
				else if(board[i - k][j + k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k)));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {

				if(board[i - k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k)));
					k++;
				}
				else if(board[i - k][j - k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k)));
					break;
				} 
				else 
					break;
			}		
		}
		
		return movimientos;
	}
	
	public Vector<Movimiento> knight_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			if(i > 1 && j < 7) 
				if( board[i - 2][j + 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j + 1)));
				
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 2)));
				
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 2)));
				
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j + 1)));
				
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j - 1)));
				
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 2)));
				
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 2)));
				
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j - 1)));	
		}
		else  {
			if(i > 1 && j < 7) 
				if( board[i - 2][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j + 1)));
				
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 2)));
				
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 2)));
				
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j + 1)));
				
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j - 1)));
				
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 2)));
				
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 2)));
				
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j - 1)));	
		}
		
		return movimientos;
	}
	
	public Vector<Movimiento> rook_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			for(byte k = (byte) (i + 1); k <= 7; k++) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j));
				
				else if(board[k][j] > 90) {
					movimientos.add(new Movimiento(i, j, k, j));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (i - 1); k >= 0; k--) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j));
				
				else if(board[k][j] > 90) {
					movimientos.add(new Movimiento(i, j, k, j));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j + 1); k <= 7; k++) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k));
				
				else if(board[i][k] > 90) {
					movimientos.add(new Movimiento(i, j, i, k));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j - 1); k >= 0; k--) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k));
				
				else if(board[i][k] > 90) {
					movimientos.add(new Movimiento(i, j, i, k));
					break;
				} 
				else 
					break;
			}	
		} 
		else {
			for(byte k = (byte) (i + 1); k <= 7; k++) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j));
				
				else if(board[k][j] < 96) {
					movimientos.add(new Movimiento(i, j, k, j));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (i - 1); k >= 0; k--) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j));
				
				else if(board[k][j] < 96) {
					movimientos.add(new Movimiento(i, j, k, j));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j + 1); k <= 7; k++) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k));
				
				else if(board[i][k] < 96) {
					movimientos.add(new Movimiento(i, j, i, k));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j - 1); k >= 0; k--) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k));
				
				else if(board[i][k] < 96) {
					movimientos.add(new Movimiento(i, j, i, k));
					break;
				} 
				else 
					break;
			}	
		} 
		
		return movimientos;
	}
	
	public Vector<Movimiento> queen_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		movimientos.addAll(  bishop_movement(i, j)  );
		movimientos.addAll(  rook_movement(i, j)  );
		
		return movimientos;
	}
	
	public Vector<Movimiento> pawn_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			
			if(board[i - 1][j] == '_') {
				movimientos.add(new Movimiento(i, j, (byte) (i - 1), j));
								
				if(i == 6 && board[i - 2][j] == '_') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), j));
			}
			 
			if(j != 0) {
				if(board[i - 1][j - 1] > 96) 					
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1)));
					
				else if(board[i][j - 1] == 'p') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1)));
			}
			if(j != 7) {
				if(board[i - 1][j + 1] > 96) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1)));

				else if(board[i][j + 1] == 'p') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1)));
			}
		} 
		else {
			
			if(board[i + 1][j] == '_') {
				movimientos.add(new Movimiento(i, j, (byte) (i + 1), j));
				
				if(i == 1 && board[i + 2][j] == '_') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), j));
			} 
			if(j != 0) {
				if(board[i + 1][j - 1] < 91) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1)));
	
				else if(board[i][j - 1] == 'P') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1)));
			}
			if(j != 7) {
				if(board[i + 1][j + 1] < 91) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1)));

				else if(board[i][j + 1] == 'P') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1)));
			}
		}
		
		return movimientos;
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
	

	public State getMinMax(int iteration) {
		return getMinMax(iteration, new MiBoolean(false));
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
		return new State(void_board(), true);
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
		System.out.println("VACIO\nVACIO\nVACIO\nVACIO\nVACIO\nVACIO\nVACIO\nVACIO");
		return new State(void_board(), true);
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
		
		System.out.println(i1 + " " + j1 + " - " + i2 + " " + j2);
		
		
		if(pieza == 'm') {
			pieza = 'k';
			if(j2 == 3 && clone[0][0] == 'r') {
				clone[0][0] = '_';
				clone[0][3] = 't';
			}
			else if(j2 == 7 && clone[0][7] == 'r') {
				System.out.println("HEY");
				clone[0][7] = '_';
				clone[0][5] = 't';
			}
		}
		else if(pieza == 'M') {
			pieza = 'K';
			if(j2 == 3 && clone[7][0] == 'R') {
				clone[7][0] = '_';
				clone[7][3] = 'T';
			}
			else if(j2 == 7 && clone[7][7] == 'R') {
				clone[7][7] = '_';
				clone[7][5] = 'T';
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
			if(board[4][j1] == 'P')
				clone[4][j1] = '_';
		}
		else if(pieza == 'o' && i2 == 6 && (j2 - j1) == -1) {
			if(board[4][j1 - 2] == 'P')
				clone[4][j1 - 2] = '_';
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == 1) {
			if(board[3][j1] == 'p')
				clone[3][j1] = '_';
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == -1) {
			if(board[3][j1 - 2] == 'p')
				clone[3][j1 - 2] = '_';
		}
		
		
		clone[i2 - 1][j2 - 1] = pieza;
		clone[i1 - 1][j1 - 1] = '_';
		
		State move = new State(clone, !this.white_moves);
		
		//move.print();
		
		return move;
	}

	public State move(Movimiento m) {
		int i1, j1, i2, j2;
		
		i1 = m.getX1() + 1;
		j1 = m.getY1() + 1;
		i2 = m.getX2() + 1;
		j2 = m.getY2() + 1;
				
		char pieza = board[i1 - 1][j1 - 1];
		
		//System.out.println(i1 + " " + j1 + " - " + i2 + " " + j2);
		
		
		if(pieza == 'm') {
			pieza = 'k';
			if(j2 == 3 && board[0][0] == 'r') {
				board[0][0] = '_';
				board[0][3] = 't';
			}
			else if(j2 == 7 && board[0][7] == 'r') {
				System.out.println("HEY");
				board[0][7] = '_';
				board[0][5] = 't';
			}
		}
		else if(pieza == 'M') {
			pieza = 'K';
			if(j2 == 3 && board[7][0] == 'R') {
				board[7][0] = '_';
				board[7][3] = 'T';
			}
			else if(j2 == 7 && board[7][7] == 'R') {
				board[7][7] = '_';
				board[7][5] = 'T';
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
			if(board[4][j1] == 'P')
				board[4][j1] = '_';
		}
		else if(pieza == 'o' && i2 == 6 && (j2 - j1) == -1) {
			if(board[4][j1 - 2] == 'P')
				board[4][j1 - 2] = '_';
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == 1) {
			if(board[3][j1] == 'p')
				board[3][j1] = '_';
		}
		else if(pieza == 'O' && i2 == 3 && (j2 - j1) == -1) {
			if(board[3][j1 - 2] == 'p')
				board[3][j1 - 2] = '_';
		}
		
		
		board[i2 - 1][j2 - 1] = pieza;
		board[i1 - 1][j1 - 1] = '_';
		
		this.white_moves = !this.white_moves;
		
		return this;
	}

	public State unmove(Movimiento m) {
		return this.move(new Movimiento(m.getX2(), m.getY2(), m.getX1(), m.getY1()));
	}
	
	public State unmove(Movimiento m, char a) {
		this.move(new Movimiento(m.getX2(), m.getY2(), m.getX1(), m.getY1()));
		this.board[m.getX2()][m.getY2()] = a;
		return this;
	}
	
	
	public boolean board_equals(State state) {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) 
				 if(board[i][j] != state.board[i][j])
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
				if(this.board[4][j] == 'P') {
					this.board[4][j] = 'O';
					return;
				}
			}
		}
		else {
			for(int j = 0; j < 8; j++) {
				if(this.board[3][j] == 'p') {
					this.board[3][j] = 'o';
					return;
				}
			}
		}
	}
		
	
	public String board_to_string() {
		String str = "";
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				str += this.board[i][j];
		
		return str;
	}
	
	public char[][] clone_board() {
		char clone[][] = new char[8][8];
		
		for(int i = 0; i < 8; i++) 
			for(int j = 0; j < 8; j++)
				clone[i][j] = this.board[i][j];
		
		return clone;		
	}
	
	public static char[][] void_board() {
		char new_board[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'}};	
        
		return new_board;
	}
	
	public static char[][] black_wins() {
		
		char new_table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
								{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'}};		

		return new_table;
	}
		
	public static char[][] white_wins() {
		
		char new_table[][] = {  {'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
								{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		

		return new_table;
	}
	
	public static char[][] initial_board() {
		
		char new_table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
								{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
								{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};

		return new_table;
	}
	
	public static char[][] parse_board(String str) {
		char[][] new_board = new char[8][8];
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				new_board[i][j] = str.charAt(i * 8 + j);
		
		return new_board;
 	}
	
	public static State INITIAL_STATE() {
		return new State(initial_board(), true);
	}

	public State clone() {
		State st = new State(this.clone_board(), this.white_moves);
		return st;
	}
	
	
}