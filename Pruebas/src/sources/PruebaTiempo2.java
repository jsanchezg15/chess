package sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class PruebaTiempo2 {

	public static void main(String[] args) {

		File data = new File("data");
		
		Vector<Float> vector = new Vector<Float>();

		try {
			Scanner sc = new Scanner(data);
			
			String str;
			
			while(sc.hasNext()) {
				str = sc.nextLine();
				
				if(!str.equals(""))
					vector.add(Float.parseFloat(str));
			}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		for(int i = 0; i < vector.size(); i = i + 3)
			System.out.println(  String.valueOf(  (  (vector.get(i) * vector.get(i) * vector.get(i) * vector.get(i + 1) * vector.get(i + 1)) / vector.get(i + 2)  )  ).replace('.', ',')  );
		
	}

}
