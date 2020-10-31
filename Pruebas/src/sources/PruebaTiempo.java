package sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class PruebaTiempo {

	public static void main(String[] args) {

		File partida1 = new File("partida1");
		File partida2 = new File("partida2");
		
		Vector<Integer> vector1 = new Vector<Integer>();
		Vector<Integer> vector2 = new Vector<Integer>();

		try {
			Scanner sc1 = new Scanner(partida1);
			Scanner sc2 = new Scanner(partida2);
			
			String str;
			
			while(sc1.hasNext()) {
				str = sc1.nextLine();
				
				if(str.contains("Milisec")) {
					String array[] = str.split(" ");
					vector1.add(Integer.parseInt(array[1]));
				}
			}
			
			while(sc2.hasNext()) {
				str = sc2.nextLine();
				
				if(str.contains("Milisec")) {
					String array[] = str.split(" ");
					vector2.add(Integer.parseInt(array[1]));
				}
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		for(int i = 0; i < vector2.size(); i++)
			System.out.println( ((float) vector2.get(i) / (float) vector1.get(i)));
		
	}

}
