package sources;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
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
	private boolean x, blancas, abajo;
	private MiBoolean stop;
	private int a, b;
	
	
	public Tablero(boolean blancas, boolean abajo) {
		char table[][] = {  {'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
							{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'_', '_', '_', '_', '_', '_', '_', '_'},
							{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
							{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		

		Vector<Vector<Character>> vector = new Vector<Vector<Character>>();
		
		for(int i = 0; i < 8; i++) {
			vector.add(new Vector<Character>());
			for(int j = 0; j < 8; j++)
				vector.get(i).add(table[i][j]);
		}
		
		this.blancas = blancas;
		this.abajo = abajo;
		this.state = new State(vector, true);
		this.layout = new GridLayout(8, 8);
		this.frame = new Frame("Board");
		this.botones = new JButton[8][8];
		this.stop = new MiBoolean(false);
		
		crear_ventana();
		
		if(!blancas)
			turno_de_la_IA();
	}
	
	public Tablero(State state, boolean blancas, boolean abajo) {
		this.blancas = blancas;
		this.abajo = abajo;
		this.state = state;
		this.layout = new GridLayout(8, 8);
		this.frame = new Frame("Board");
		this.botones = new JButton[8][8];
		this.stop = new MiBoolean(false);
		
		crear_ventana();
		
		if(!blancas)
			turno_de_la_IA();
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

		Vector<Vector<Character>> table = this.state.getBoard();
		
		if(abajo) {
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++) {				
					String imagename = "images/" + table.get(i).get(j);
					
					if(table.get(i).get(j) <= 90)
						imagename += "2";

					botones[i][j].setIcon(new ImageIcon(imagename + ".png"));
					
					botones[i][j].setBorder(new LineBorder(Color.DARK_GRAY));
					
					if((i + j) % 2 != 0)
						botones[i][j].setBackground(Color.LIGHT_GRAY);
					else
						botones[i][j].setBackground(Color.WHITE);
				}
		}
		else {
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++) {				
					String imagename = "images/" + table.get(i).get(j);
					
					if(table.get(i).get(j) <= 90)
						imagename += "2";

					botones[Math.abs(i - 7)][Math.abs(j - 7)].setIcon(new ImageIcon(imagename + ".png"));
					
					botones[Math.abs(i - 7)][Math.abs(j - 7)].setBorder(new LineBorder(Color.DARK_GRAY));
					
					if((i + j) % 2 != 0)
						botones[i][j].setBackground(Color.LIGHT_GRAY);
					else
						botones[i][j].setBackground(Color.WHITE);
				}
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
	
							turno_de_la_IA();
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
				if(this.state.getBoard().get(i).get(j) == rey1 || this.state.getBoard().get(i).get(j) == rey2) 
					if(this.state.is_menaged(i, j, this.state.get_white_moves()))
						this.botones[i][j].setBackground(Color.RED);
				
	}
	
	
	private void turno_de_la_IA() {
		
		stop.value = false;
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		exec.submit(() -> {
			
			colocar_piezas();					
			desactivar_botones();
			
			try {Thread.sleep(10000);} catch (InterruptedException e1) {e1.printStackTrace();}
			
			stop.value = true;
			
			colocar_piezas();
			activar_botones();
			amenaza_rey();
			
			System.out.println("===========");
		});
		
		calcular_movimiento(4, stop);
		calcular_movimiento(5, stop);

	}

	private void calcular_movimiento(int profundidad, MiBoolean stop) {
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		State s = new State(state.clone_board(), !this.blancas);
		
		exec.submit(() -> {
			long time = System.currentTimeMillis();
											
			State min_max = s.getMinMax(profundidad, stop);
			
			this.state = new State(min_max.getMove().clone_board(), this.blancas);
			
			System.out.println((System.currentTimeMillis() - time));
			
			if(System.currentTimeMillis() - time < 500) {
				min_max = s.getMinMax(profundidad, stop);
				
				this.state = new State(min_max.getMove().clone_board(), this.blancas);
				
				System.out.println((System.currentTimeMillis() - time));
			}
				
		});
	}
	
}