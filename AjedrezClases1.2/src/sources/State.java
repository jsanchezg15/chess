package sources;

import java.util.Vector;

public class State {

	private char[][] board;
	public boolean white_moves, white_king, black_king;
	public double white_pts, black_pts;
	private State min_max;
	private Vector<Movimiento> children;
	
	public State(char[][] board, boolean white_moves) {
		this.board = board;
		this.white_moves = white_moves;
		
		this.black_king = true;
		this.white_king = true;
		
		this.white_pts = 0;
		this.black_pts = 0;

		this.min_max = null;
		this.children = new Vector<Movimiento>();
		
		set_pawns();
	}
	
	public State(String board, boolean white_moves) {
		this.board = parse_board(board);
		this.white_moves = white_moves;
		
		this.black_king = true;
		this.white_king = true;
		
		this.white_pts = 0;
		this.black_pts = 0;

		this.min_max = null;

		this.children = new Vector<Movimiento>();
		
		set_pawns();
	}
	
	
	public void setChildren(Vector<Movimiento> children) { this.children = children; }
		
	public void setWhiteMoves(boolean wm) { this.white_moves = wm; }
		
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
		
		if(!this.white_king) 
			return 0;
		else if(!this.black_king)
			return Double.MAX_VALUE;		
		
		return this.white_pts / this.black_pts;
	}
	
	public double piece_value(byte i, byte j) {
		
		// Utiliza la puntuación de prev_new_calculate_equilibrium()
		
		char piece = this.board[i][j];		
		
		if(piece == '_')
			return 0;
		
		if(piece < 95) {
			if(piece == 'O')  					// Peón 
				return 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
			else if(piece == 'C') 				// Caballo
				return 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
			else if(piece == 'A') 				// Alfil
				return 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
			else if(piece == 'R') 				// Torre
				return + 5;
			else if(piece == 'T') 				// Torre que ya ha hecho un movimiento
				return + 5;
			else if(piece == 'Q')				// Reina
				return + 9;
			else if(piece == 'K')				// Rey que ya ha hecho un movimiento
				return 1;
			else if(piece == 'M') 				// Rey
				return 1;
			else if(piece == 'P') 				// Peón capturable al paso
				return + 1 + ((i == 4 && (j == 3 || j == 4)) ? 0.07 : 0);
		}
		else {
			if(piece == 'o')
				return 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
			else if(piece == 'c')
				return 3 + ((i > 1 && i < 6 && j > 1 && j < 6) ? 0.1 : 0);
			else if(piece == 'a')
				return 3 + ((i == j || (i + j) == 7) ? 0.12 : 0);
			else if(piece == 'r')
				return 5;
			else if(piece == 't')
				return 5;
			else if(piece == 'q')
				return 9;	
			else if(piece == 'k') 
				return 1;
			else if(piece == 'm') 
				return 1;
			else if(piece == 'p')
				return 1 + ((i == 3 && (j == 3 || j == 4)) ? 0.07 : 0);
		}
		
		return 0;
	}
	
	public Vector<Movimiento> next_states() {
			
		Vector<Movimiento> next_states = new Vector<Movimiento>();
		
		if(!this.white_king) {
			State state = new State( State.black_wins(), !this.white_moves );
			//next_states.add(state);
		}
		else if(!this.black_king) {
			State state = new State( State.white_wins(), !this.white_moves );
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
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 3, '_'));
					
				if(board[7][7] == 'R' && board[7][6] == '_' && board[7][5] == '_') {
					if(  !(   is_menaged(7, 4, true) || is_menaged(7, 5, true) || is_menaged(7, 6, true)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 6, '_'));
				}
			}
					
			if(i != 0) {
				if( board[i - 1][j] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) j, board[i - 1][j]));
				
				if(j != 0) 
					if( board[i - 1][j - 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1), board[i - 1][j - 1] ) );
				
				if(j != 7) 
					if( board[i - 1][j + 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1) , board[i - 1][j + 1] ) );

			}
			if(i != 7) {
				if( board[i + 1][j] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) j, board[i + 1][j] ) );
				
				if(j != 0) 
					if( board[i + 1][j - 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1), board[i + 1][j - 1] ) );

				if(j != 7) 
					if( board[i + 1][j + 1] > 90 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1), board[i + 1][j + 1] ) );

			}
			if(j != 0) 
				if( board[i][j - 1] > 90 )
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j - 1), board[i][j - 1] ) );

			if(j != 7) 
				if( board[i][j + 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j + 1), board[i][j + 1] ) );

		}
		else {
			if(kingside) {
				if(board[0][0] == 'r' && board[0][1] == '_' && board[0][2] == '_' && board[0][3] == '_') 
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 3, false) || is_menaged(0, 2, false)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 3, '_'));
					
				if(board[0][7] == 'r' && board[0][6] == '_' && board[0][5] == '_') 
					if(  !(is_menaged(0, 4, false) || is_menaged(0, 5, false) || is_menaged(0, 6, false)  )  ) 
						movimientos.add(new Movimiento(i, j, (byte) i, (byte) 6, '_'));
			}
			
			if(i != 0) {
				if( board[i - 1][j] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) j, board[i - 1][j]));
				
				if(j != 0) 
					if( board[i - 1][j - 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1), board[i - 1][j - 1] ) );
				
				if(j != 7) 
					if( board[i - 1][j + 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1), board[i - 1][j + 1] ) );

			}
			if(i != 7) {
				if( board[i + 1][j] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) j, board[i + 1][j] ) );
				
				if(j != 0) 
					if( board[i + 1][j - 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1), board[i + 1][j - 1] ) );

				if(j != 7) 
					if( board[i + 1][j + 1] < 96 ) 
						movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1), board[i + 1][j + 1] ) );

			}
			if(j != 0) 
				if( board[i][j - 1] < 96 )
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j - 1), board[i][j - 1] ) );

			if(j != 7) 
				if( board[i][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) i, (byte) (j + 1), board[i][j + 1] ) );
		}
		
		return movimientos;	
	}
	
	public Vector<Movimiento> bishop_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			int k = 1;
			while( (i + k) <= 7 && (j + k) <= 7) {

				if(board[i + k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k), '_'));
					k++;
				}
				else if(board[i + k][j + k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k), board[i + k][j + k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {

				if(board[i + k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k), '_'));
					k++;
				}
				else if(board[i + k][j - k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k), board[i + k][j - k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {

				if(board[i - k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k), '_'));
					k++;
				}
				else if(board[i - k][j + k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k), board[i - k][j + k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {

				if(board[i - k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k), '_'));
					k++;
				}
				else if(board[i - k][j - k] > 90) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k), board[i - k][j - k]));
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
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k), '_'));
					k++;
				}
				else if(board[i + k][j + k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j + k), board[i + k][j + k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i + k) <= 7 && (j - k) >= 0 ) {

				if(board[i + k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k), '_'));
					k++;
				}
				else if(board[i + k][j - k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i + k), (byte) (j - k), board[i + k][j - k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j + k) <= 7 ) {

				if(board[i - k][j + k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k), '_'));
					k++;
				}
				else if(board[i - k][j + k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j + k), board[i - k][j + k]));
					break;
				} 
				else 
					break;
			}
			k = 1;
			while( (i - k) >= 0 && (j - k) >= 0 ) {

				if(board[i - k][j - k] == '_') {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k), '_'));
					k++;
				}
				else if(board[i - k][j - k] < 96) {
					movimientos.add(new Movimiento(i, j, (byte) (i - k), (byte) (j - k), board[i - k][j - k]));
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
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j + 1), board[i - 2][j + 1]));
				
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 2), board[i - 1][j + 2]));
				
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 2), board[i + 1][j + 2]));
				
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j + 1), board[i + 2][j + 1]));
				
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j - 1), board[i + 2][j - 1]));
				
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 2), board[i + 1][j - 2]));
				
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 2), board[i - 1][j - 2]));
				
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] > 90 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j - 1), board[i - 2][j - 1]));	
		}
		else  {
			if(i > 1 && j < 7) 
				if( board[i - 2][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j + 1), board[i - 2][j + 1]));
				
			if(i > 0 && j < 6) 
				if( board[i - 1][j + 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 2), board[i - 1][j + 2]));
				
			if(i < 7 && j < 6) 
				if( board[i + 1][j + 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 2), board[i + 1][j + 2]));
				
			if(i < 6 && j < 7) 
				if( board[i + 2][j + 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j + 1), board[i + 2][j + 1]));
				
			if(i < 6 && j > 0) 
				if( board[i + 2][j - 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), (byte) (j - 1), board[i + 2][j - 1]));
				
			if(i < 7 && j > 1) 
				if( board[i + 1][j - 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 2), board[i + 1][j - 2]));
				
			if(i > 0 && j > 1) 
				if( board[i - 1][j - 2] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 2), board[i - 1][j - 2]));
				
			if(i > 1 && j > 0) 
				if( board[i - 2][j - 1] < 96 ) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), (byte) (j - 1), board[i - 2][j - 1]));	
		}
		
		return movimientos;
	}
	
	public Vector<Movimiento> rook_movement(byte i, byte j) {
		Vector<Movimiento> movimientos = new Vector<Movimiento>();
		
		if(this.white_moves) {
			for(byte k = (byte) (i + 1); k <= 7; k++) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j, '_'));
				
				else if(board[k][j] > 90) {
					movimientos.add(new Movimiento(i, j, k, j, board[k][j]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (i - 1); k >= 0; k--) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j, '_'));
				
				else if(board[k][j] > 90) {
					movimientos.add(new Movimiento(i, j, k, j, board[k][j]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j + 1); k <= 7; k++) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k, '_'));
				
				else if(board[i][k] > 90) {
					movimientos.add(new Movimiento(i, j, i, k, board[i][k]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j - 1); k >= 0; k--) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k, '_'));
				
				else if(board[i][k] > 90) {
					movimientos.add(new Movimiento(i, j, i, k, board[i][k]));
					break;
				} 
				else 
					break;
			}	
		} 
		else {
			for(byte k = (byte) (i + 1); k <= 7; k++) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j, '_'));
				
				else if(board[k][j] < 96) {
					movimientos.add(new Movimiento(i, j, k, j, board[k][j]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (i - 1); k >= 0; k--) {

				if(board[k][j] == '_') 
					movimientos.add(new Movimiento(i, j, k, j, '_'));
				
				else if(board[k][j] < 96) {
					movimientos.add(new Movimiento(i, j, k, j, board[k][j]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j + 1); k <= 7; k++) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k, '_'));
				
				else if(board[i][k] < 96) {
					movimientos.add(new Movimiento(i, j, i, k, board[i][k]));
					break;
				} 
				else 
					break;
			}
			for(byte k = (byte) (j - 1); k >= 0; k--) {

				if(board[i][k] == '_') 
					movimientos.add(new Movimiento(i, j, i, k, '_'));
				
				else if(board[i][k] < 96) {
					movimientos.add(new Movimiento(i, j, i, k, board[i][k]));
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
				movimientos.add(new Movimiento(i, j, (byte) (i - 1), j, '_'));
								
				if(i == 6 && board[i - 2][j] == '_') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 2), j, '_'));
			}
			 
			if(j != 0) {
				if(board[i - 1][j - 1] > 96) 					
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1), board[i - 1][j - 1]));
					
				else if(board[i][j - 1] == 'p') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j - 1), board[i - 1][j - 1], true));
			}
			if(j != 7) {
				if(board[i - 1][j + 1] > 96) 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1), board[i - 1][j + 1]));

				else if(board[i][j + 1] == 'p') 
					movimientos.add(new Movimiento(i, j, (byte) (i - 1), (byte) (j + 1), board[i - 1][j + 1], true));
			}
		} 
		else {
			
			if(board[i + 1][j] == '_') {
				movimientos.add(new Movimiento(i, j, (byte) (i + 1), j, '_'));
				
				if(i == 1 && board[i + 2][j] == '_') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 2), j, '_'));
			} 
			if(j != 0) {
				if(board[i + 1][j - 1] < 91) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1), board[i + 1][j - 1]));
	
				else if(board[i][j - 1] == 'P') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j - 1), board[i + 1][j - 1], true));
			}
			if(j != 7) {
				if(board[i + 1][j + 1] < 91) 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1), board[i + 1][j + 1]));

				else if(board[i][j + 1] == 'P') 
					movimientos.add(new Movimiento(i, j, (byte) (i + 1), (byte) (j + 1), board[i + 1][j + 1], true));
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
				Vector<Movimiento> moves = next_states();
				
				if(!moves.isEmpty()) {
					Movimiento move = moves.remove(0);
					
					State max = this.clone();
					max.move(move);
					
					for(Movimiento m : moves) {
						this.move(m);

						if(max.getEquilibium() < this.getEquilibium()) {
							max.unmove(move);
							max.move(m);
							//max.equilibrium = this.equilibrium;
							move = m;
						}
						
						this.unmove(m);
					}
					
					return max;
				}
			} 
			else {
				Vector<Movimiento> moves = next_states();
				
				if(!moves.isEmpty()) {
					Movimiento move = moves.remove(0);
					
					State min = this.clone();
					min.move(move);
					
					for(Movimiento m : moves) {
						this.move(m);
						
						if(min.getEquilibium() > this.getEquilibium()) {
							min.unmove(move);
							min.move(m);
							//min.equilibrium = this.equilibrium;
							move = m;
						}
						
						this.unmove(m);
					}
					
					return min;
				}
			}
		}
		else {
			if(this.white_moves) {
				Vector<Movimiento> next = next_states();

				if(!next.isEmpty()) {
					Movimiento move = next.remove(0);
					
					State max = this.clone();
					max.move(move);
					max = max.getMinMax(iteration - 1, stop);

					for(Movimiento m : next) {
						//this.move(m).getMinMax(iteration - 1, max.getEquilibium(), stop);
						this.move(m).getMinMax(iteration - 1, stop);
						
						if(max.getEquilibium() < this.getEquilibium()) {
							max.unmove(move);
							max.move(m);
							//max.equilibrium = this.equilibrium;
							move = m;
						}
						
						this.unmove(m);
					}
					
					return max;
				}
			}
			else {
				Vector<Movimiento> next = next_states();
				
				if(!next.isEmpty()) {
					Movimiento move = next.remove(0);

					State min = this.clone();
					min.move(move);
					min = min.getMinMax(iteration - 1, stop).clone();
					
					for(Movimiento m : next) {
						//this.move(m).getMinMax(iteration - 1, min.getEquilibium(), stop);
						this.move(m).getMinMax(iteration - 1, stop);

						if(min.getEquilibium() < this.getEquilibium()) {
							min.unmove(move);
							min.move(m);
							//min.equilibrium = this.equilibrium;
							move = m;
						}
						
						this.unmove(m);
					}
					
					return min;
				}
			}
		}	
		return new State(void_board(), true);
	}
	
/*	public State getMinMax(int iteration, MiBoolean stop) {
		if(stop.value)
			return null;
		
		if(iteration == 1) {
			if(this.white_moves) {
				Vector<Movimiento> moves = next_states();
				
				if(!moves.isEmpty()) {
					State max = this.clone();
					max.move(moves.get(0));
					moves.remove(0);
					
					for(Movimiento m : moves) {
						this.move(m);

						if(max.getEquilibium() < this.getEquilibium())
							max = this.clone();
						
						this.unmove(m);
					}
					
					return max;
				}
			} 
			else {
				Vector<Movimiento> moves = next_states();
				
				if(!moves.isEmpty()) {
					State min = this.clone();
					min.move(moves.get(0));
					moves.remove(0);
					
					for(Movimiento m : moves) {
						this.move(m);
						
						if(min.getEquilibium() > this.getEquilibium())
							min = this.clone();
						
						this.unmove(m);
					}
					
					return min;
				}
			}
		}
		else {
			if(this.white_moves) {
				Vector<Movimiento> next = next_states();

				if(!next.isEmpty()) {
					Movimiento move = next.remove(0);
					
					State max = this.clone();
					max.move(move);
					max = max.getMinMax(iteration - 1, stop);

					for(Movimiento m : next) {
						this.move(m).getMinMax(iteration - 1, max.getEquilibium(), stop);
						
						if(max.getEquilibium() < this.getEquilibium()) {
							max.unmove(move);
							max.move(m);
						}
						
						this.unmove(m);
					}
					
					return max;
				}
			}
			else {
				Vector<Movimiento> next = next_states();
				
				if(!next.isEmpty()) {
					Movimiento move = next.remove(0);

					State min = this.clone();
					min.move(move);
					min = min.getMinMax(iteration - 1, stop).clone();
					
					for(Movimiento m : next) {
						this.move(m).getMinMax(iteration - 1, min.getEquilibium(), stop);
						
						if(min.getEquilibium() < this.getEquilibium()) {
							min.unmove(move);
							min.move(m);
						}
						
						this.unmove(m);
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
				Vector<Movimiento> next = next_states();
				
				if(!next.isEmpty()) {
					this.move(next.get(0));
					State max = this.clone();
					this.unmove(next.get(0));
					next.remove(0);
					
					if(max.getEquilibium() >= limit) 
						return max;

					for(Movimiento m : next) {
						this.move(m);

						if(this.getEquilibium() >= limit) {
							State st = this.clone();
							this.unmove(m);
							return st;
						}

						if(max.getEquilibium() < this.getEquilibium())
							max = this.clone();

						this.unmove(m);
					}

				return max;
				}
			} 
			else {
				Vector<Movimiento> next = next_states();
				
				if(!next.isEmpty()) {
					this.move(next.get(0));
					State min = this.clone();
					this.unmove(next.get(0));
					next.remove(0);
					
					if(min.getEquilibium() <= limit)
						return min;
					
					for(Movimiento m : next) {
						this.move(m);

						if(this.getEquilibium() <= limit) {
							State st = this.clone();
							this.unmove(m);
							return st;
						}

						if(min.getEquilibium() < this.getEquilibium())
							min = this.clone();

						this.unmove(m);
					}

					
					return min;
				}	
			}
		}
		else {
			if(this.white_moves) {
				Vector<Movimiento> next = next_states();
				
				if(!next.isEmpty()) {
					this.move(next.get(0));
					State max = this.getMinMax(iteration - 1, stop).clone();
					this.unmove(next.get(0));
					next.remove(0);
										
					if(max.getEquilibium() >= limit)
						return max;
					
					for(State state : next) {
						state = state.getMinMax(iteration - 1, max.getEquilibium(), stop);
						
						if(state.getEquilibium() >= limit)
							return state;
						
						if(max.getEquilibium() < state.getEquilibium())
							max = state;
					}
					
					for(Movimiento m : next) {
						this.move(m).getMinMax(iteration - 1, max.getEquilibium(), stop);
						
						if(this.getEquilibium() >= limit)
							return state;
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
	}*/

	
	public State move(Movimiento m) {
				
		byte i1, j1, i2, j2;
		
		i1 = m.getX1();
		j1 = m.getY1();
		i2 = m.getX2();
		j2 = m.getY2();
				
		//System.out.println(i1 + " - " + j1 + " - " + i2 + " - " + j2);
		
		char pieza = board[i1][j1];
		
		double playerA_pts = -piece_value(i1, j1);
		double playerB_pts = -piece_value(i2, j2);		
		
		if(pieza == 'm') {
			pieza = 'k';
			if(j2 == 2 && board[0][0] == 'r') {
				board[0][0] = '_';
				board[0][3] = 't';
			}
			else if(j2 == 6 && board[0][7] == 'r') {
				System.out.println("HEY");
				board[0][7] = '_';
				board[0][5] = 't';
			}
		}
		else if(pieza == 'M') {
			pieza = 'K';
			if(j2 == 2 && board[7][0] == 'R') {
				board[7][0] = '_';
				board[7][3] = 'T';
			}
			else if(j2 == 6 && board[7][7] == 'R') {
				board[7][7] = '_';
				board[7][5] = 'T';
			}
		}
		else if(pieza == 'r')
			pieza = 't';
		else if(pieza == 'R')
			pieza = 'T';
		else if(pieza == 'o' && i1 == 1 && i2 == 3)
			pieza = 'o';
			//pieza = 'p';
		else if(pieza == 'O' && i1 == 6 && i2 == 4)
			pieza = 'O';
			//pieza = 'P';
		else if(pieza == 'o' && i2 == 7)
			pieza = 'q';
		else if(pieza == 'O' && i2 == 0)
			pieza = 'Q';
		else if(pieza == 'p')
			pieza = 'o';
		else if(pieza == 'P')
			pieza = 'O';
		else if(pieza == 'o' && i2 == 5 && (j2 - j1) == 1 && j1 != 0) {
			if(board[4][j1 - 1] == 'P')
				board[4][j1 - 1] = '_';
		}
		else if(pieza == 'o' && i2 == 5 && (j2 - j1) == -1 && j1 > 2) {
			if(board[4][j1 - 3] == 'P')
				board[4][j1 - 3] = '_';
		}
		else if(pieza == 'O' && i2 == 2 && (j2 - j1) == 1 && j1 != 0) {
			if(board[3][j1 - 1] == 'p')
				board[3][j1 - 1] = '_';
		}
		else if(pieza == 'O' && i2 == 2 && (j2 - j1) == -1 && j1 > 2) {
			if(board[3][j1 - 3] == 'p')
				board[3][j1 - 3] = '_';
		}
		
		
		board[i2][j2] = pieza;
		board[i1][j1] = '_';
		
		playerA_pts += piece_value(i2, j2);
		
		if(this.white_moves) {
			this.white_pts += playerA_pts;
			this.black_pts += playerB_pts;
		} 
		else {
			this.black_pts += playerA_pts;
			this.white_pts += playerB_pts;
		}
		
		this.white_moves = !this.white_moves;
		
		return this;
	}

	public State unmove(Movimiento m) {
		this.white_moves = !this.white_moves;
		this.move(new Movimiento(m.getX2(), m.getY2(), m.getX1(), m.getY1(), '_'));
		this.white_moves = !this.white_moves;
		
		board[m.getX2()][m.getY2()] = m.getPiece();

		if(this.white_moves) 
			this.white_pts += piece_value(m.getX2(), m.getY2());
		else 
			this.black_pts += piece_value(m.getX2(), m.getY2());
			
		if(m.getElse()) {
			if(m.getPawnCapture()) {
				if(m.getX1() > m.getX2()) 
					board[3][m.getY2()] = 'p';
				else
					board[4][m.getY2()] = 'P';
			}
		}

		return this;
	}
	
	
	public boolean board_equals(State state) {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) 
				 if(board[i][j] != state.board[i][j])
					 return false;
		
		return true;		 
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
		State st = new State(initial_board(), true);
		st.white_pts = 40;
		st.black_pts = 40;
		return st;
	}

	public State clone() {
		State st = new State(this.clone_board(), this.white_moves);
		st.white_king = this.white_king;
		st.black_king = this.black_king;
		st.white_pts = this.white_pts;
		st.black_pts = this.black_pts;
		return st;
	}
	
	
}