package sources;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SquaredButton {

	public static void main(String[] args) {

		char table[][] = {   	{'r', 'c', 'a', 'q', 'm', 'a', 'c', 'r'},
								{'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'_', '_', '_', '_', '_', '_', '_', '_'},
								{'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
								{'R', 'C', 'A', 'Q', 'M', 'A', 'C', 'R'}};		

		GridLayout layout = new GridLayout(8, 8);
		Button button;
		
		Frame frame = new Frame("Board");
		ActionListener a = new MyActionListener(frame, table); 

		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				frame.add(button = new Button(table[i][j] + ""));
				
				if((i + j) % 2 == 0)
					button.setBackground(Color.lightGray);
				
				button.setActionCommand(i + "" + j); 
				button.addActionListener(a); 
			}
			
		
		frame.setLayout(layout);
		frame.setSize(500,500);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}

}
