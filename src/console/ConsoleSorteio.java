package console;

import java.util.Scanner;

import controller.ControladorSorteio;
import exception.BilheteInvalidoException;
import exception.LimiteInvalidoException;
import exception.SorteioNaoEncontradoException;
import exception.VendedorInvalidoException;
import model.PixPremiado;
import model.Rifa;
import model.enums.TipoSorteio;

public class ConsoleSorteio {

	private Scanner scanner;
	private ControladorSorteio controlador;

	public ConsoleSorteio() {
		scanner = new Scanner(System.in);
		controlador = new ControladorSorteio();
	}

	public void iniciar() {
		int opcao;

		do {
			exibirMenu();
			opcao = lerInteiro("Opcao: ");

			executarOpcao(opcao);

			if (opcao != 0) {
				System.out.println();
			}

		} while (opcao != 0);

		scanner.close();

	}

	private void executarOpcao(int opcao) {
		switch (opcao) {
		case 1:
			cadastrarSorteio();
			break;

		case 2:
			cadastrarVendedor();
			break;

		case 3:
			cadastrarComprador();
			break;

		case 4:
			buscarSorteioPorCodigo();
			break;

		case 5:
			buscarBilhetePorCodigo();
			break;

		case 6:
			buscarVendedorPorCPF();
			break;

		case 7:
			buscarCompradorPorCPF();
			break;

		case 8:
			venderBilhete();
			break;

		case 9:
			removerVenda();
			break;

		case 10:
			atualizarMetaDoSorteio();
			break;

		case 11:
			atualizarNivelDoVendedor();
			break;

		case 12:
			transformarUmaRifaEmPix();
			break;

		case 13:
			listarSorteiosCadastrados();
			break;

		case 14:
			listarRelatorioGeralDoSorteio();
			break;

		case 15:
			listarRankingDeVendedores();
			break;

		case 16:
			listarRankingDeVendedoresPorSorteio();
			break;

		case 17:
			listarVendasPorVendedor();
			break;

		case 18:
			exibirFunilDeCompradores();
			break;

		case 19:
			exibirHistoricoPorComprador();
			break;

		case 20:
			sortearNumero();
			break;

		case 0:
			System.out.println("Programa encerrado.");
			break;

		default:
			System.out.println("Opcao invalida.");
		}

	}

	private void sortearNumero() {
		// TODO Auto-generated method stub

	}

	private void exibirHistoricoPorComprador() {
		// TODO Auto-generated method stub

	}

	private void exibirFunilDeCompradores() {
		// TODO Auto-generated method stub

	}

	private void listarVendasPorVendedor() {
		// TODO Auto-generated method stub

	}

	private void listarRankingDeVendedoresPorSorteio() {
		// TODO Auto-generated method stub

	}

	private void listarRankingDeVendedores() {
		// TODO Auto-generated method stub

	}

	private void listarRelatorioGeralDoSorteio() {
		// TODO Auto-generated method stub

	}

	private void listarSorteiosCadastrados() {
		// TODO Auto-generated method stub

	}

	private void transformarUmaRifaEmPix() {
		// TODO Auto-generated method stub

	}

	private void atualizarNivelDoVendedor() {
		// TODO Auto-generated method stub

	}

	private void atualizarMetaDoSorteio() {
		// TODO Auto-generated method stub

	}

	private void removerVenda() {
		// TODO Auto-generated method stub

	}

	private void venderBilhete() {
		// TODO Auto-generated method stub

	}

	private void buscarVendedorPorCPF() {
		// TODO Auto-generated method stub

	}

	private void buscarCompradorPorCPF() {
		// TODO Auto-generated method stub

	}

	private void buscarBilhetePorCodigo() {
		// TODO Auto-generated method stub

	}

	private void buscarSorteioPorCodigo() {
		try {
			int codigoSorteio=lerInteiro("Codigo do sorteio: ");
			controlador.buscarSorteioPorCodigo(codigoSorteio);
		} catch(SorteioNaoEncontradoException e) {
			System.out.println("Erro ao buscar sorteio: "+ e.getMessage());
		}
	}

	private void cadastrarComprador() {
		try {
			String cpf = lerTexto("CPF: ");
			String nome = lerTexto("Nome: ");
			String telefone = lerTexto("telefone: ");
			controlador.cadastrarComprador(cpf, nome, telefone);
		} catch (VendedorInvalidoException e) {
			System.out.println("Erro ao cadastrar vendedor: " + e.getMessage());
		}

	}

	private void cadastrarVendedor() {
		try {
			String cpf = lerTexto("CPF: ");
			String nome = lerTexto("Nome: ");
			String telefone = lerTexto("telefone: ");
			controlador.cadastrarVendedor(cpf, nome, telefone);
		} catch (VendedorInvalidoException e) {
			System.out.println("Erro ao cadastrar vendedor: " + e.getMessage());
		}

	}

	private void cadastrarSorteio() {

		menuExibirTipoSorteio();
		int opcao = lerInteiro("Opcao: ");

		try {
			switch (opcao) {
			case 1:
				TipoSorteio tipo;
				tipo = TipoSorteio.RIFA;

				int codigo = lerInteiro("Codigo do sorteio: ");
				String premio = lerTexto("Premio: ");
				double valorBilhete = lerDouble("Valor do bilhete: ");
				double valorParaArrecadar = lerDouble("Valor para arrecadar: ");

				controlador.cadastrarSorteio(codigo, tipo, premio, valorBilhete, valorParaArrecadar);
				break;

			case 2:
				tipo = TipoSorteio.PIXPREMIADO;

				codigo = lerInteiro("Codigo do sorteio: ");
				premio = lerTexto("Premio: ");
				valorBilhete = lerDouble("Valor do bilhete: ");
				double metaBilhetes = lerInteiro("Meta de bilhetes: ");

				controlador.cadastrarSorteio(codigo, tipo, premio, valorBilhete, metaBilhetes);
				break;

			default:
				System.out.println("Opcao invalida.");
			}
		} catch (BilheteInvalidoException | LimiteInvalidoException e) {
			System.out.println("Erro ao cadastrar sorteio: " + e.getMessage());
		}

	}

	private void menuExibirTipoSorteio() {
		System.out.println("\n╔══════════════════════════════════════════════════╗");
		System.out.println("║           QUAL TIPO DE SORTEIO?                   ║");
		System.out.println("╠══════════════════════════════════════════════════╣");
		System.out.printf("║  %-49s║%n", "1 - Rifa");
		System.out.printf("║  %-49s║%n", "2 - Pix premiado");
		System.out.printf("║  %-49s║%n", "0 - Cancelar");
		System.out.println("╚══════════════════════════════════════════════════╝");
	}

	private void exibirMenu() {
		System.out.println("\n╔══════════════════════════════════════════════════╗");
		System.out.println("║               MENU DE OPÇÕES                     ║");
		System.out.println("╠══════════════════════════════════════════════════╣");
		System.out.printf("║ %-49s║%n", "1  - Cadastrar sorteio");
		System.out.printf("║ %-49s║%n", "2  - Cadastrar vendedor");
		System.out.printf("║ %-49s║%n", "3  - Cadastrar comprador");
		System.out.printf("║ %-49s║%n", "4  - Buscar sorteio por código");
		System.out.printf("║ %-49s║%n", "5  - Buscar bilhete por código");
		System.out.printf("║ %-49s║%n", "6  - Buscar vendedor por CPF");
		System.out.printf("║ %-49s║%n", "7  - Buscar comprador por CPF");
		System.out.printf("║ %-49s║%n", "8  - Vender bilhete");
		System.out.printf("║ %-49s║%n", "9  - Remover venda");
		System.out.printf("║ %-49s║%n", "10 - Atualizar meta do sorteio");
		System.out.printf("║ %-49s║%n", "11 - Atualizar nível do vendedor");
		System.out.printf("║ %-49s║%n", "12 - Transformar uma rifa em pix");
		System.out.printf("║ %-49s║%n", "13 - Listar sorteios cadastrados");
		System.out.printf("║ %-49s║%n", "14 - Listar relatório geral do sorteio");
		System.out.printf("║ %-49s║%n", "15 - Listar ranking de vendedores");
		System.out.printf("║ %-49s║%n", "16 - Listar ranking de vendedores por sorteio");
		System.out.printf("║ %-49s║%n", "17 - Listar vendas por vendedor");
		System.out.printf("║ %-49s║%n", "18 - Exibir funil de compradores");
		System.out.printf("║ %-49s║%n", "19 - Exibir histórico por comprador");
		System.out.printf("║ %-49s║%n", "20 - Sortear número");
		System.out.printf("║ %-49s║%n", "0  - Sair");
		System.out.println("╚══════════════════════════════════════════════════╝");
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

	private double lerDouble(String mensagem) {
		while (true) {
			System.out.print(mensagem);
			String entrada = scanner.nextLine();

			try {
				return Double.parseDouble(entrada);

			} catch (NumberFormatException e) {
				System.out.println("Digite um numero inteiro valido.");
			}
		}
	}

}
