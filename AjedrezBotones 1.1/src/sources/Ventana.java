package sources;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ventana extends JFrame {

	JPanel contentpane;
	JTextField texto1, texto2, texto3;
	JButton boton1, boton2, boton3, boton4;
	
	public Ventana() {
		
		// x, y, width, height
		setBounds(400, 400, 500, 250);
		setVisible(true);
		contentpane = new JPanel();
		contentpane.setBackground(Color.CYAN);
		setContentPane(contentpane);
		contentpane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//
		texto1 = new JTextField();
		texto2 = new JTextField();
		texto3 = new JTextField();
		
		boton1 = new JButton(new ImageIcon("white_king.png"));
		boton2 = new JButton();
		boton3 = new JButton();
		boton4 = new JButton();
		
		contentpane.add(texto1);
		contentpane.add(texto2);
		contentpane.add(texto3);
		
		texto1.setBounds(25, 25, 130, 30);
		texto2.setBounds(25, 70, 130, 30);
		texto3.setBounds(180, 25, 285, 75);
		
		contentpane.add(boton1);
		contentpane.add(boton2);
		contentpane.add(boton3);
		contentpane.add(boton4);
		
		boton1.setBounds(25, 125, 130, 75);
		boton2.setBounds(180, 125, 130, 30);
		boton3.setBounds(180, 170, 130, 30);
		boton4.setBounds(335, 125, 130, 75);
		boton1.setText("Boton 1");

	}
	
	public static void main(String args[]) {
		
		Ventana v = new Ventana();
		
	}
	
}
