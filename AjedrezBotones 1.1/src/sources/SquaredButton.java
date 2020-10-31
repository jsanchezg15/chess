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

import javax.swing.ImageIcon;
import javax.swing.JButton;

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
		JButton button;
		
		Frame frame = new Frame("Board");
		ActionListener a = new MyActionListener(frame, table); 

		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++) {
				String imagename = "";
				
				if(table[i][j] <= 90)
					imagename = (table[i][j]) + "2.png";
				else
					imagename = table[i][j] + ".png";
				
				frame.add(button = new JButton(new ImageIcon(imagename)));
				
				if((i + j) % 2 != 0)
					button.setBackground(Color.LIGHT_GRAY);
				else
					button.setBackground(Color.WHITE);
				
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
