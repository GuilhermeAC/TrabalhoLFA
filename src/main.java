import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String arqentrada = args[0];
		String palavra = args[1];
		String arqsaida = args[2];

		
		System.out.print("Arquivo de Leitura: ");
		System.out.println(arqentrada);
		System.out.print("Arquivo de Saída: ");
		System.out.println(arqsaida);
		
		System.out.print("Palavra de Entrada: ");
		System.out.println(palavra);
		
		
		Scanner scanner = new Scanner(new FileReader(arqentrada)).useDelimiter("\\||\\n");
		// O useDelimiter("\\||\\n") especifica como que será pulado durante a leitura
		while (scanner.hasNext()) {
			String teste = scanner.next();
			System.out.println(teste); // Teste
		}
		
	
	}
	
}
