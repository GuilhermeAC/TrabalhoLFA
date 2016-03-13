import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {

<<<<<<< HEAD:src/main.java
	public static void main(String[] args) throws FileNotFoundException {
		
		String arqentrada = args[0];
		String palavra = args[1];
		String arqsaida = args[2];

		
		//System.out.print("Arquivo de Leitura: ");
		//System.out.println(arqentrada);
		//System.out.print("Arquivo de Saída: ");
		//System.out.println(arqsaida);
		
		//System.out.print("Palavra de Entrada: ");
		//System.out.println(palavra);
		
		Map<String, List<String>> regrasGLC = new HashMap<String, List<String>>();
		
		String esquerda = null;
		String direita;
		String simbolo;
				
		// O useDelimiter("[^aA-zZ]+") especifica como que será pulado durante a leitura
		Scanner leitura = new Scanner(new FileReader(arqentrada)).useDelimiter("[^aA-zZ]+");
		
		while (leitura.hasNext()) {
			
			simbolo = leitura.next();
			
			if (simbolo.matches("[A-Z]")) {
				
				esquerda = simbolo;
				
			}
			
			else {
				
				direita = simbolo;
				
				if (regrasGLC.get(direita) == null) {
				
					List<String> lista = new ArrayList<String>();	
					regrasGLC.put(direita, lista);
					
				}
				
				List<String> listaAux = regrasGLC.get(direita);
				listaAux.add(esquerda);
				regrasGLC.put(direita, listaAux);
								
			}
				
		}
				
		leitura.close();	
		
		
        for (Map.Entry<String, List<String>> entry : regrasGLC.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.print(key + " = ");
            System.out.println(values);
        }
		
=======
	public static void main(String[] args) {
		
		System.out.print("Arquivo de Leitura: ");
		System.out.println(args[0]);
		System.out.print("Palavra de Entrada: ");
		System.out.println(args[1]);
		System.out.print("Arquivo de Saida: ");
		System.out.println(args[2]);
>>>>>>> origin/master:src/Main.java

	}

}
