package sources;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyActionListener implements ActionListener { 

	int a = 0;
	int b = 0;
	int c = 0;
	int d = 0;
	boolean e = false;
	Frame frame;
	char[][] table;
	
	
	public MyActionListener(Frame frame, char[][] table) {
		this.table = table;
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand(); 
		
		System.out.println("i -> " + s.charAt(0));
		System.out.println("j -> " + s.charAt(1));
		
		if(e) {
			c = Integer.parseInt(s.charAt(0) + "");
			d = Integer.parseInt(s.charAt(1) + "");

			this.table[c][d] = this.table[a][b];
			this.table[a][b] = '_';
			
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
		else {
			a = Integer.parseInt(s.charAt(0) + "");
			b = Integer.parseInt(s.charAt(1) + "");
			e = true;
		}
	}
		
}  