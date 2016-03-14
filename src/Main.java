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
		
		// Caso não passe os 3 argumentos		
		if (args.length == 3) {
	
			arqentrada = args[0]; // Arquivo de Entrada
			palavra = args[1]; // Palavra
			arqsaida = args[2]; // Arquivo de Saída
			regrasGLC = new HashMap<String, List<String>>(); // HashMap de List de String
			
			// TALVEZ SEJA BOM VERIFICAR SE O ARQUIVO EXISTE
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
		
		else {
			System.out.println("Argumentos Inválidos");
		}
	

	}
	
	
	// Método para a leitura do arquivo
	
	public static void leitura () throws FileNotFoundException {
	
		String esquerda = null;
		String direita;
		String simbolo;
				
		// O useDelimiter(regex) utiliza a expressão regular "[^aA-zZ]+" que desconsidera
		// Todos os caracteres que não são letras do alfabeto na hora da leitura
		
		Scanner leitura = new Scanner(new FileReader(arqentrada)).useDelimiter("[^aA-zZ.]+");
		
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
		
		for (i=0; i<linha; i++) { 
	        for (j=0; j<coluna; j++) {
	        	// Preenche a primeira linha da matriz com a palavra
	        	if (i==0) {
	        		mat[i][j] = palavra.charAt(j) + ""; 
	        	}
	        	// E o resto preenche como tudo vazio ""
	        	else {
	        		mat[i][j] = "";
	        	}
	        	
	        }
		}
		
		/*
        // Percorre e imprimi a matriz "triangular"
        for (i=0; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	if (mat[i][j].equals("")) {
            		System.out.print("-" + "\t");
            	}
            	else {
            		System.out.print(mat[i][j] + "\t");
            	}
            }
            
            // Na segunda linha, o limite não diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

            System.out.println();
        }
        */
                
        limite = coluna;
        
        int k;
        int a;
        int b;
        int x;
        int y;
        
        for (i=1; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	
            	// A linha 1 da matriz é diferente porque apenas ela 
            	// Compara com a linha 0 para ver quem gera os terminais
            	// Para isso vamos criar um if apenas para ela
            	
            	if (i==1) {
            		
            		// Esse for percorre todo o HashMap
            		
            		// Entrada é a combinação de uma chave com a sua respectiva lista
            		// De variaváeis (do lado esquerdo) que a geram
            		
	                for (Map.Entry<String, List<String>> entrada : regrasGLC.entrySet()) {
	                	
	                    String chave = entrada.getKey();

	                   
	                    // Encontro a chave no HashMap que é igual ao terminal daquela posição da matriz
	                    if (mat[0][j].equals(chave)) {
	                    	
	                    	//System.out.println("mat: " + mat[0][j] + " chave: " + chave);
	                    	
		                    List<String> lista = entrada.getValue();
		                    
		                    // Nesse for, eu coloco TODAS as variáveis que geram aquele terminal
		                    // Em uma posição da matriz
		                    
		                    // Caso mais de uma variável gere o terminal, será concatenado
		                    // Tudo em uma string: Exemplo: ABC
		                    
	                    	for(k=0; k<lista.size(); k++) {
	                    		mat[i][j] = mat[i][j] + lista.get(k);
	                    	}
	                    	
	                    }
	                }
            		
            	}
            	
            	// A partir da linha 2, será entrado nesse else para realizar as comparações
            	
            	else {
            		
            	
	            	// Percorre todas as linhas anteriores para fazer as comparações
	            	
        			// Aqui eu percorro uma coluna e uma diagonal ao mesmo tempo
		            // E realizo as comarações
            		
            		for (k=i-1; k>0; k--) {

            			// Para cada posição da matriz, talvez tenha de mais uma variável
            			// Exemplo: AB compara com TX. Daí se faz a permutação
            			// AT, AX, BT e BX. Para isso serve esses 2 for
            			
            			for (a=0; a<mat[k][j].length(); a++) { 
            				for (b=0; b<mat[i-k][k+j].length(); b++) {
            					
            					// Nessa parte eu concateno duas varíaveis
            					// Seguindo o exemplo anterior: AB compara com TX
            					// Nessa parte eu concatenaria A com T para formar AT
            					
            					String variavel = (mat[k][j].charAt(a) + "") + (mat[i-k][k+j].charAt(b) + "");
            					
                        		// Nesse for, eu percorro o HashMap para ver se encontro o AT do exemplo
                        		
            	                for (Map.Entry<String, List<String>> entrada : regrasGLC.entrySet()) {

            	                    String chave = entrada.getKey();
            	                	
            	                    // Caso eu encontre, ele entra nesse if
            	                    if (variavel.equals(chave)) {

            		                    List<String> lista = entrada.getValue();
            		                    
            		                    // Como encontrou o AT em alguma chave do HashMap
            		                    // Então pego a lista do HashMap de AT e insiro todos os elementos
            		                    // Da lista (que no caso são as variáveis que deviram AT) 
            		                    // Na posição da matriz [i][j]
            	                    	for(x=0; x<lista.size(); x++) {
            	                    		
            	                    		int contador = 0;
            	                    		
            	                    		// Porém antes se iserir as variáveis que derivam AT
            	                    		// Exemplo: S -> AT e F -> AT
            	                    		// Mas suponha que F já esteja inserido na posição da matriz
            	                    		// Então antes de inserir o F, verifico se já existe na mat[i][j]
            	                    		
            	                    		for (y=0; y<mat[i][j].length(); y++) {
            	                    			
            	                    			if ((mat[i][j].charAt(y) + "").equals(lista.get(x))) {
            	                    				contador ++;
            	                    			}
            	                    		}
            	                    		
            	                    		// Se não existe a palavra, eu inserio a variável na matriz
            	                    		if (contador == 0) {            	                    		
            	                    			mat[i][j] = mat[i][j] + lista.get(x);
            	                    		}
            	                    		
            	                    	}
            	                    	
            	                    }
            	                    
            	                } // Fim do percurso no HashMap
            						
            				} // Fim do for das permutações de b
            				
            			} // Fim do for das permutações de a

            		} // Fim do for que percorre as linhas anteriores para a comparação
	            	
            	} // Fim do else

            } // Fim do for das colunas
            
        	limite--;
       
        }
        
        limite = coluna;
        
        for (i=0; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	if (mat[i][j].equals("")) {
            		System.out.print("-" + "\t");
            	}
            	else {
            		System.out.print(mat[i][j] + "\t");
            	}
            }
            
            // Na segunda linha, o limite não diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

            System.out.println();
        }

        
	}	// Fim do método cyk

} // Fim da classe Main