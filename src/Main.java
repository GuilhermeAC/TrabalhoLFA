import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
		
	private static String arqentrada;
	private static String palavra;
	private static String arqsaida;
	private static Map<String, List<String>> regrasGLC;
	private static String mat [][];
	
	// Método principal
	
	public static void main (String[] args) throws FileNotFoundException {
		
		arqentrada = args[0]; // Arquivo de Entrada
		palavra = args[1]; // Palavra
		arqsaida = args[2]; // Arquivo de Saída
		regrasGLC = new HashMap<String, List<String>>(); // HashMap de List de String
		
		leitura ();    
		
		
		// Imprimi o HashMap de List 
        for (Map.Entry<String, List<String>> entry : regrasGLC.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.print(key + " = ");
            System.out.println(values);
        }
        
        
        System.out.println();
        cyk ();

	}
	
	
	// Método para a leitura do arquivo
	
	public static void leitura () throws FileNotFoundException {
	
		String esquerda = null;
		String direita;
		String simbolo;
				
		// O useDelimiter(regex) utiliza a expressão regular "[^aA-zZ]+" que desconsidera
		// Todos os caracteres que não são letras do alfabeto na hora da leitura
		
		Scanner leitura = new Scanner(new FileReader(arqentrada)).useDelimiter("[^aA-zZ]+");
		
		while (leitura.hasNext()) { // Caso ainda seja possível ler o arquivo
			
			simbolo = leitura.next(); // Então é lido o próximo símbolo
			
			// Caso o símbolo seja do lado esquerdo
			// Eu apenas armazeno ele para utilizá-lo depois
			
			if (simbolo.matches("[A-Z]")) { 
				
				esquerda = simbolo;
				
			}
			
			else { 
							
				direita = simbolo;
				
				// Caso a símbolo do lado direito ainda não esteja no HashMap,
				// Ele é inserido como chave no HashMap, com uma lista de string nula
				
				if (regrasGLC.get(direita) == null) {
				
					List<String> lista = new ArrayList<String>();	
					regrasGLC.put(direita, lista);
					
				}
				
				// Depois, eu busco o HashMap pelo símbolo do direita
				// E insiro o símbolo da esquerda (que eu armazei antes) na lista
				
				List<String> listaAux = regrasGLC.get(direita);
				listaAux.add(esquerda);
				regrasGLC.put(direita, listaAux);
								
			}
				
		}
				
		leitura.close();	
					
	}
	
	
	// Método que irá realizar os cálculos do CYK
		
	public static void cyk () {
		
		int tam = palavra.length();
		int i,j;
		
		int linha = tam+1;
		int coluna = tam;
				
        mat = new String[linha][coluna]; 
        
		int limite = coluna;
        
        for (j=0; j<coluna; j++) {
        	// Preenche a primeira linha da matriz com a palavra
        	mat[0][j] = palavra.charAt(j) + ""; 
        }
		
        // Percorre e imprimi a matriz "triangular"
        for (i=0; i<linha; i++) {
            for (j=0; j<limite; j++) {
                System.out.print(mat[i][j] + "\t");
            }
            
            // Na segunda linha, o limite não diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

            System.out.println();
        }
        
        
        // CONTINUAR
        // CONTINUAR
        // CONTINUAR
        
        
	}	

}