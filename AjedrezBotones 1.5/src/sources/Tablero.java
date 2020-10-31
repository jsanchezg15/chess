package sources;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

public class Tablero extends JFrame implements ActionListener {

	private State state;
	private GridLayout layout;
	private Frame frame;
	private JButton[][] botones;
	private boolean x;
	private int a, b;
	
	
	public Tablero() {
		char table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		

		this.state = new State(table, true);
		this.layout = new GridLayout(8, 8);
		this.frame = new Frame("Board");
		this.botones = new JButton[8][8];
		
		crear_ventana();
	}
	
	public Tablero(State state) {
		this.state = state;
		this.layout = new GridLayout(8, 8);
		this.frame = new Frame("Board");
		this.botones = new JButton[8][8];
		
		crear_ventana();
	}
	
	
	private void crear_ventana() {
		crear_botones();
		activar_botones();
		colocar_piezas();
		
		frame.setLayout(layout);
		frame.setBounds(500, 80, 900, 900);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void crear_botones() {
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				botones[i][j] = new JButton();
				
				this.frame.add(botones[i][j]);
				
				botones[i][j].setActionCommand(i + "" + j); 
			}
		
	}
	
	private void colocar_piezas() {
		char[][] table = this.state.getBoard();
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {				
				String imagename = "images/" + table[i][j];
				
				if(table[i][j] <= 90)
					imagename += "2";

				botones[i][j].setIcon(new ImageIcon(imagename + ".png"));
				
				botones[i][j].setBorder(new LineBorder(Color.DARK_GRAY));
				
				if((i + j) % 2 != 0)
					botones[i][j].setBackground(Color.LIGHT_GRAY);
				else
					botones[i][j].setBackground(Color.WHITE);
			}
	}

	
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) 
				if(e.getSource() == this.botones[i][j]) {
					if(x) {
						this.botones[a][b].setBorder(new LineBorder(Color.DARK_GRAY, 1));
						if(state.exist_move(state.move(a+1, b+1, i+1, j+1))) {
							
							state = state.move(a+1, b+1, i+1, j+1);
							
							colocar_piezas();
														
							desactivar_botones();
							
							ExecutorService exec = Executors.newSingleThreadExecutor();

							new MiThread(new State(this.state.clone_board(), false), 4);
							
							exec.submit(() -> {
								long time = System.currentTimeMillis();
																
								State min_max = state.getMinMax(profundidad_adecuada());
								
								state = new State(min_max.getMove().clone_board(), true);
								
								colocar_piezas();
								
								activar_botones();
								
								amenaza_rey();
								
								System.out.println((System.currentTimeMillis()- time) + "\n");
							});
						}
							
						x = false;
					}
					else {
						this.botones[i][j].setBorder(new LineBorder(Color.BLACK, 3));
						
						a = i;
						b = j;
						x = true;
					}
				}
	}
	
	
	private int profundidad_adecuada() {
		
		float branch1 = state.next_states().size();
		float branch2 = 0;
		
		for(State st : state.next_states()) 
			if(st.next_states().size() > branch2)
				branch2 = st.next_states().size();
							
		System.out.println(branch1);
		System.out.println(branch2);
		
		if((branch1 * branch1 * branch1 * branch2 * branch2 * branch2) < 30000000) {
			System.out.println("6");
			return 6;
		}
		else if((branch1 * branch1 * branch1 * branch2 * branch2) < 30000000) {
			System.out.println("5");
			return 5;
		}
		else {
			System.out.println("4");
			return 4;
		}
	}

	private void activar_botones() {
		for(int k1 = 0; k1 < 8; k1++)
			for(int k2 = 0; k2 < 8; k2++)
				this.botones[k1][k2].addActionListener(this);
	}
	
	private void desactivar_botones() {
		for(int k1 = 0; k1 < 8; k1++)
			for(int k2 = 0; k2 < 8; k2++)
				this.botones[k1][k2].removeActionListener(this);		
	}

	private void amenaza_rey() {
		char rey1 = 'K';
		char rey2 = 'm';
				
		if(this.state.get_white_moves()) 
			rey2 = 'M';
		else
			rey1 = 'k';
		
		for(int i = 0; i < 8; i++) 
			for(int j = 0; j < 8; j++) 
				if(this.state.getBoard()[i][j] == rey1 || this.state.getBoard()[i][j] == rey2) 
					if(this.state.is_menaged(i, j, this.state.get_white_moves()))
						this.botones[i][j].setBackground(Color.RED);
				
	}
	
	
}