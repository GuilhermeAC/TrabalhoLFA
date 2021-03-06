import java.io.*;
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
	
	// M�todo principal
	
	public static void main (String[] args) throws FileNotFoundException {
		
		// Caso n�o passe os 3 argumentos		
		if (args.length == 3) {
	
			arqentrada = args[0]; // Arquivo de Entrada
			palavra = args[1]; // Palavra
			arqsaida = args[2]; // Arquivo de Sa�da
			regrasGLC = new HashMap<String, List<String>>(); // HashMap de List de String
			
			leitura ();    			
			
			/*
			
			// Imprimi o HashMap de List 
			
	        for (Map.Entry<String, List<String>> entry : regrasGLC.entrySet()) {
	            String key = entry.getKey();
	            List<String> values = entry.getValue();
	            System.out.print(key + " = ");
	            System.out.println(values);
	        }
	        	        
	        System.out.println();
	        
	        */
			
	        cyk ();
	        padronizar ();
	        imprimir ();
	        writeFile ();
        
		}
		
		else {
			System.out.println("Argumentos Inv�lidos");
		}
	
	}
	
	
	// M�todo para a leitura do arquivo
	
	public static void leitura () throws FileNotFoundException {
	
		String esquerda = null;
		String direita;
		String simbolo;
				
		// O useDelimiter(regex) utiliza a express�o regular "[^aA-zZ]+" que desconsidera
		// Todos os caracteres que n�o s�o letras do alfabeto na hora da leitura
		
		Scanner leitura = new Scanner(new FileReader(arqentrada)).useDelimiter("[^aA-zZ.]+");
		
		while (leitura.hasNext()) { // Caso ainda seja poss�vel ler o arquivo
			
			simbolo = leitura.next(); // Ent�o � lido o pr�ximo s�mbolo
			
			// Caso o s�mbolo seja do lado esquerdo
			// Eu apenas armazeno ele para utiliz�-lo depois
			
			if (simbolo.matches("[A-Z]")) { 
				
				esquerda = simbolo;
				
			}
			
			else { 
							
				direita = simbolo;
				
				// Caso a s�mbolo do lado direito ainda n�o esteja no HashMap,
				// Ele � inserido como chave no HashMap, com uma lista de string nula
				
				if (regrasGLC.get(direita) == null) {
				
					List<String> lista = new ArrayList<String>();	
					regrasGLC.put(direita, lista);
					
				}
				
				// Depois, eu busco o HashMap pelo s�mbolo do direita
				// E insiro o s�mbolo da esquerda (que eu armazei antes) na lista
				
				List<String> listaAux = regrasGLC.get(direita);
				listaAux.add(esquerda);
				regrasGLC.put(direita, listaAux);
								
			}
				
		}
				
		leitura.close();	
					
	}
	
	
	// M�todo que ir� realizar os c�lculos do CYK
		
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
		                
        limite = coluna;
        
        int k;
        int a;
        int b;
        int x;
        int y;
        
        for (i=1; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	
            	// A linha 1 da matriz � diferente porque apenas ela 
            	// Compara com a linha 0 para ver quem gera os terminais
            	// Para isso vamos criar um if apenas para ela
            	
            	if (i==1) {
            		
            		// Esse for percorre todo o HashMap
            		
            		// Entrada � a combina��o de uma chave com a sua respectiva lista
            		// De variav�eis (do lado esquerdo) que a geram
            		
	                for (Map.Entry<String, List<String>> entrada : regrasGLC.entrySet()) {
	                	
	                    String chave = entrada.getKey();

	                   
	                    // Encontro a chave no HashMap que � igual ao terminal daquela posi��o da matriz
	                    if (mat[0][j].equals(chave)) {
	                    	
	                    	//System.out.println("mat: " + mat[0][j] + " chave: " + chave);
	                    	
		                    List<String> lista = entrada.getValue();
		                    
		                    // Nesse for, eu coloco TODAS as vari�veis que geram aquele terminal
		                    // Em uma posi��o da matriz
		                    
		                    // Caso mais de uma vari�vel gere o terminal, ser� concatenado
		                    // Tudo em uma string: Exemplo: ABC
		                    
	                    	for(k=0; k<lista.size(); k++) {
	                    		mat[i][j] = mat[i][j] + lista.get(k);
	                    	}
	                    	
	                    }
	                    
	                }
            		
            	}
            	
            	// A partir da linha 2, ser� entrado nesse else para realizar as compara��es
            	
            	else {
            		            	
	            	// Percorre todas as linhas anteriores para fazer as compara��es
	            	
        			// Aqui eu percorro uma coluna e uma diagonal ao mesmo tempo
		            // E realizo as comara��es
            		
            		for (k=i-1; k>0; k--) {

            			// Para cada posi��o da matriz, talvez tenha de mais uma vari�vel
            			// Exemplo: AB compara com TX. Da� se faz a permuta��o
            			// AT, AX, BT e BX. Para isso serve esses 2 for
            			
            			for (a=0; a<mat[k][j].length(); a++) { 
            				for (b=0; b<mat[i-k][k+j].length(); b++) {
            					
            					// Nessa parte eu concateno duas var�aveis
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
            		                    // Ent�o pego a lista do HashMap de AT e insiro todos os elementos
            		                    // Da lista (que no caso s�o as vari�veis que deviram AT) 
            		                    // Na posi��o da matriz [i][j]
            	                    	for (x=0; x<lista.size(); x++) {
            	                    		
            	                    		int contador = 0;
            	                    		
            	                    		// Por�m antes se iserir as vari�veis que derivam AT
            	                    		// Exemplo: S -> AT e F -> AT
            	                    		// Mas suponha que F j� esteja inserido na posi��o da matriz
            	                    		// Ent�o antes de inserir o F, verifico se j� existe na mat[i][j]
            	                    		
            	                    		for (y=0; y<mat[i][j].length(); y++) {
            	                    			
            	                    			if ((mat[i][j].charAt(y) + "").equals(lista.get(x))) {
            	                    				contador ++;
            	                    			}
            	                    		}
            	                    		
            	                    		// Se n�o existe a palavra, eu inserio a vari�vel na matriz
            	                    		if (contador == 0) {            	                    		
            	                    			mat[i][j] = mat[i][j] + lista.get(x);
            	                    		}
            	                    		
            	                    	}
            	                    	
            	                    }
            	                    
            	                } // Fim do percurso no HashMap
            						
            				} // Fim do for das permuta��es de b
            				
            			} // Fim do for das permuta��es de a

            		} // Fim do for que percorre as linhas anteriores para a compara��o
	            	
            	} // Fim do else

            } // Fim do for das colunas
            
        	limite--;
       
        }
        
        limite = coluna;
        
	}	
	
	// M�todo para padronizar a matriz com as {} e v�rgulas
	
	public static void padronizar () {
	
		int tam = palavra.length();
		int i, j, k;
		
		int linha = tam+1;
		int coluna = tam;
      
		int limite = coluna;
		
        for (i=1; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	
            	// Caso contenha apenas uma ou nenhuma var�avel, colocamos {VARIAVEL} ou {}          	
            	if (mat[i][j].length() <= 1) {
            		mat[i][j] = "{" + mat[i][j] + "}";
            	}
            	
            	// Caso haja mais de uma vari�vel, colocamos as v�rgulas
            	else {
            		
            		String auxiliar = "";
            		
            		for (k=0; k<mat[i][j].length()-1; k++) {
            			auxiliar = auxiliar + mat[i][j].charAt(k) + ",";
            		}

            		// E por fim, padronizamos para {VARIAVEl,VARIAVEL,VARIAVEL}
            		auxiliar = "{" + auxiliar + mat[i][j].charAt(k) + "}";
            		mat[i][j] = auxiliar;
            	}
            	
            }
            
            // Na segunda linha, o limite n�o diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

        }
		
		
	}
	
	public static void imprimir () {
		
		
		int tam = palavra.length();
		int i, j;
		
		int linha = tam+1;
		int coluna = tam;
      
		int limite = coluna;
					
        for (i=0; i<linha; i++) {
            for (j=0; j<limite; j++) {
            	System.out.print(mat[i][j] + "\t");
            }
            // Na segunda linha, o limite n�o diminui 
            // Pois ela tem a mesma quantidade de colunas da primeira linha
            if (i != 0) {
            	limite--;
            }            

            System.out.println();
        }     
	        		
	       
	}
	
	public static void writeFile () {		
		try {
			int tam = palavra.length();
			int i, j;
			
			int linha = tam+1;      
			//limite comeca em 1 pois se deve imprimir apenas 1 elemento no comeco do loop e ir aumentando
			int limite = 1;
			
		    PrintWriter escrita = new PrintWriter (arqsaida);
						
	        for (i=linha-1; i>=0; i--) {
	            for (j=0; j<limite; j++) {
	            	escrita.write(mat[i][j] + "\t");
	            }
	            escrita.write("\n");
	            // Na penultima linha, o limite n�o aumenta
	            // Pois ela tem a mesma quantidade de colunas da primeira linha
	            if (i > 1) {
	            	limite++;
	            }
	        }	        
	        escrita.close();
		}
        catch(FileNotFoundException e) {
            System.err.println("Arquivo de saida nao encontrado.");
        }
	}

} // Fim da classe Main