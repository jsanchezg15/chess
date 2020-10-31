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

public class Tablero extends JFrame implements ActionListener {

	private State state;
	private GridLayout layout;
	private Frame frame;
	private JButton[][] botones;
	private boolean x;
	private int a, b;
	
	
	public Tablero(State state) {
		this.state = state;
		this.layout = new GridLayout(8, 8);
		this.frame = new Frame("Board");
		this.botones = new JButton[8][8];
		
		crear_ventana();
	}
	
	
	private void crear_ventana() {
		crear_botones();
		colocar_piezas();
		
		frame.setLayout(layout);
		frame.setSize(500,500);
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
				
				if((i + j) % 2 != 0)
					botones[i][j].setBackground(Color.LIGHT_GRAY);
				else
					botones[i][j].setBackground(Color.WHITE);
				
				botones[i][j].setActionCommand(i + "" + j); 
				botones[i][j].addActionListener(this); 
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
			}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) 
				if(e.getSource() == this.botones[i][j]) {
					if(x) {
						if(state.exist_move(state.move(a+1, b+1, i+1, j+1))) {
							
							state = state.move(a+1, b+1, i+1, j+1);
							colocar_piezas();

							ExecutorService exec = Executors.newSingleThreadExecutor();

							exec.submit(() -> {
								State min_max = state.getMinMax(4);
								state = new State(min_max.getMove().clone_board(), true);
								colocar_piezas();
							});
						}
						else 
							System.out.println("Ese movimiento no es posible");
							
						x = false;
					}
					else {
						a = i;
						b = j;
						x = true;
					}
				}
	}


}