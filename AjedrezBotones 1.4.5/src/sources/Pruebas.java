package sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class Pruebas {

	public static void main(String[] args) {

		String str = "r c a q m a c r";
			  str += "o o o o _ o o o";
			  str += "_ _ _ _ _ _ _ _";
			  str += "_ _ _ _ p _ _ _";
			  str += "_ _ _ _ O _ _ _";
			  str += "_ _ _ _ _ C _ _";
			  str += "O O O O _ O O O";
			  str += "R C A Q M A C R";

	
		str = str.replace(" ", "");
		State state = new State(str , true);
		
		state.print();
		
		/*Vector<State> children = new Vector<State>();
		str = "rcaqmacroooooooo________________________O________OOOOOOORCAQMACR";  
		children.add(new State(str , false));
		str = "rcaqmacroooooooo________________P________________OOOOOOORCAQMACR";
		children.add(new State(str , false));
		state.setChildren(children);
		
		for(State st : state.next_states())
			st.print();*/
		
		File file = new File("chuleta_blancas");
		
		try {
			Scanner sc = new Scanner(file);
			String text = "";
			
			while(sc.hasNext()) 
				text += sc.nextLine() + "\n";
			
			PrintWriter printer = new PrintWriter("chuleta_blancas");
			
			printer.write(text);
			printer.write(str);
			printer.close();

		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		
	}

}
