package console;

import java.util.Scanner;

public class ConsoleSorteio {
private Scanner scanner;
	
	
	public ConsoleSorteio() {
		scanner = new Scanner(System.in);
	}

	public void iniciar() {
		int opcao;
		
		do {
			exibirMenu();
			opcao = lerInteiro("Opcao: ");
			//Opções funcionais
		}while(opcao != 0 );
		
		scanner.close();
		
	}
	
	private void exibirMenu() {
		System.out.println("As opções ficam aqui");
	}
	
	private int lerInteiro(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String entrada = scanner.nextLine();

			try {
				return Integer.parseInt(entrada);

			} catch (NumberFormatException e) {
				System.out.println("Digite um numero inteiro valido.");
			}
		}
	}

	private String lerTexto(String mensagem) {
		System.out.print(mensagem);
		return scanner.nextLine();
	}

}
