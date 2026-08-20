package console;

import java.util.Scanner;

import controller.ControladorSorteio;
import exception.BilheteInvalidoException;
import exception.BilheteNaoEncontradoException;
import exception.CompradorInvalidoException;
import exception.CompradorNaoEncontradoException;
import exception.LimiteInvalidoException;
import exception.PessoaNaoEncontradaException;
import exception.SorteioInvalidoException;
import exception.SorteioNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;
import model.Bilhete;
import model.Comprador;
import model.Pessoa;
import model.Vendedor;
import model.enums.FormaDePagamento;

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
			try {
				cadastrarSorteio();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			atualizarMetaRifa();
			break;

		case 11:
			atualizarMetaPix();
			break;

		case 12:
			mostrarNivelDoVendedor();
			break;

		case 13:
			transformarUmaRifaEmPix();
			break;

		case 14:
			listarSorteiosCadastrados();
			break;

		case 15:
			listarRelatorioGeralDoSorteio();
			break;

		case 16:
			listarRankingDeVendedores();
			break;

		case 17:
			listarRankingDeVendedoresPorSorteio();
			break;

		case 18:
			listarVendasPorVendedor();
			break;

		case 19:
			exibirFunilDeCompradores();
			break;

		case 20:
			exibirHistoricoPorComprador();
			break;

		case 21:
			sortearNumero();
			break;

		case 22:
			buscarPessoaPorCPF();
			break;
		case 0:
			System.out.println("Programa encerrado.");
			break;

		default:
			System.out.println("Opcao invalida.");
		}

	}

	private void buscarPessoaPorCPF() {
		try {
			String cpf = lerTexto("CPF: ");
			Pessoa pessoa = controlador.buscarPessoaPorCPF(cpf);

			if (pessoa == null) {
				throw new PessoaNaoEncontradaException("Nenhuma pessoa encontrada com o CPF(" + cpf + ").");
			}
			System.out.println(pessoa.toString());
		} catch (Exception e) {
			System.out.println("Erro ao buscar pessoa: " + e.getMessage());
		}

	}

	private void sortearNumero() {
		try {
			String codigo = lerTexto("Codigo do sorteio: ");
			String resultado = controlador.sortearNumero(codigo);
			System.out.println(resultado);
		} catch (SorteioNaoEncontradoException | SorteioInvalidoException e) {
			System.out.println("Erro ao sortear: " + e.getMessage());
		}

	}

	private void exibirHistoricoPorComprador() {
		try {
			String cpf = lerTexto("CPF: ");
			String historico = controlador.exibirHistoricoPorComprador(cpf);
			System.out.println(historico);
		} catch (CompradorNaoEncontradoException | BilheteNaoEncontradoException e) {
			System.out.println("Erro ao sortear: " + e.getMessage());
		}

	}

	private void exibirFunilDeCompradores() {
		try {
			String compradores = controlador.exibirTodosOsCompradores();
			System.out.println(compradores);
		} catch (CompradorNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

	private void listarVendasPorVendedor() {
		try {
			String cpf = lerTexto("CPF: ");
			String vendas = controlador.listarVendasPorVendedor(cpf);
			System.out.println(vendas);
		} catch (VendedorNaoEncontradoException | BilheteNaoEncontradoException | VendedorInvalidoException e) {
			System.out.println("Erro: " + e.getMessage());

		}

	}

	private void listarRankingDeVendedoresPorSorteio() {
		try {
			String codigoSorteio = lerTexto("Código sorteio: ");
			String ranking = controlador.gerarRankingVendedoresPorSorteio(codigoSorteio);
			System.out.println(ranking);
		} catch (VendedorNaoEncontradoException | BilheteNaoEncontradoException | SorteioNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());

		}

	}

	private void listarRankingDeVendedores() {
		try {
			String ranking = controlador.gerarRankingVendedores();
			System.out.println(ranking);
		} catch (VendedorNaoEncontradoException | BilheteNaoEncontradoException e) {
			System.out.println("Erro: " + e.getMessage());

		}

	}

	private void listarRelatorioGeralDoSorteio() {
		try {
			String codigoSorteio = lerTexto("Código sorteio: ");
			String relatorio = controlador.exibirRelatorioGeralDoSorteio(codigoSorteio);
			System.out.println(relatorio);

		} catch (SorteioNaoEncontradoException | SorteioInvalidoException e) {
			System.out.println("Erro :" + e.getMessage());

		}

	}

	private void listarSorteiosCadastrados() {
		System.out.println(controlador.exibirTodosOsSorteios());

	}

	private void transformarUmaRifaEmPix() {
		try {
			String codigoRifa = lerTexto("Código da rifa: ");
			int metaBilhetes = lerInteiro("Meta bilhetes: ");
			controlador.transformarRifaemPix(codigoRifa, metaBilhetes);
			System.out.println("A rifa foi transformada em pix premiado!");

		} catch (SorteioNaoEncontradoException | SorteioInvalidoException | LimiteInvalidoException e) {
			System.out.println("Erro ao transformar rifa em pix: " + e.getMessage());
		}
	}

	private void mostrarNivelDoVendedor() {
		try {
			String cpf = lerTexto("CPF: ");
			String resultado = controlador.mostrarNivelDoVendedor(cpf);
			System.out.println(resultado);

		} catch (VendedorNaoEncontradoException e) {
			System.out.println("Erro ao mostrar o nível: " + e.getMessage());
		}

	}

	private void atualizarMetaRifa() {
		try {
			String codigoSorteio = lerTexto("Código sorteio: ");
			double novaMeta = lerDouble("Nova meta: ");

			if (controlador.atualizarMetaRifa(codigoSorteio, novaMeta)) {
				System.out.println("Meta atualizada com sucesso!");
			}

		} catch (BilheteInvalidoException | LimiteInvalidoException e) {
			System.out.println("Erro ao atualizar meta: " + e.getMessage());
		}

	}

	private void atualizarMetaPix() {
		try {
			String codigoSorteio = lerTexto("Código sorteio: ");
			int novaMeta = lerInteiro("Nova meta: ");

			if (controlador.atualizarMetaPix(codigoSorteio, novaMeta)) {

				System.out.println("Meta atualizada com sucesso!");
			}
		} catch (BilheteInvalidoException | LimiteInvalidoException e) {
			System.out.println("Erro ao atualizar meta: " + e.getMessage());
		}

	}

	private void removerVenda() {
		try {
			String codigoSorteio = lerTexto("Código sorteio: ");
			int numeroBilhete = lerInteiro("Número do bilhete: ");

			if (controlador.removerVenda(codigoSorteio, numeroBilhete)) {
				System.out.println("Venda removida com sucesso!");
			}

		} catch (BilheteNaoEncontradoException e) {
			System.out.println("Erro ao remover bilhete: " + e.getMessage());
		}

	}

	private void venderBilhete() {
		String codigoSorteio = lerTexto("Código sorteio: ");
		int numeroBilhete = lerInteiro("Número do bilhete: ");
		String codigoVendedor = lerTexto("Código vendedor: ");
		String codigoComprador = lerTexto("Código comprador: ");

		FormaDePagamento formaPagamento = null;
		menuExibirFormaDePagamento();
		int opcao = lerInteiro("Opcao: ");

		switch (opcao) {
		case 1:
			formaPagamento = FormaDePagamento.PIX;
			break;
		case 2:
			formaPagamento = FormaDePagamento.DINHEIRO;
			break;
		case 3:
			formaPagamento = FormaDePagamento.CARTAO;
			break;
		case 0:
			return;
		default:
			System.out.println("Opcao invalida.");
		}

		try {
			if (controlador.venderBilhete(codigoSorteio, numeroBilhete, codigoVendedor, codigoComprador,
					formaPagamento)) {
				System.out.println("Bilhete vendido!");

				if (controlador.atualizarNivelDoVendedor(codigoVendedor)) {
					Vendedor vendedor = controlador.buscarVendedorPorCPF(codigoVendedor);
					System.out.println("Parabéns! O vendedor subiu para o nível " + vendedor.getNivel() + "!");
				}
			}

		} catch (Exception e) {
			System.out.println("Erro ao vender bilhete: " + e.getMessage());
		}

	}

	private void buscarVendedorPorCPF() {

		try {
			String cpf = lerTexto("CPF: ");
			Vendedor vendedor = controlador.buscarVendedorPorCPF(cpf);
			if (vendedor == null) {
				throw new VendedorNaoEncontradoException("O CPF (" + cpf + ") nao foi encontrado");
			}
			System.out.println("Vendedor encontrado: \n" + vendedor.toString());
		} catch (VendedorNaoEncontradoException e) {
			System.out.println("Erro ao buscar vendedor: " + e.getMessage());
			return;
		}

	}

	private void buscarCompradorPorCPF() {

		try {
			String cpf = lerTexto("CPF: ");
			Comprador comprador = controlador.buscarCompradorPorCPF(cpf);
			if (comprador == null) {
				throw new CompradorNaoEncontradoException("O CPF (" + cpf + ") nao foi encontrado");
			}
			System.out.println("Comprador encontrado: \n" + comprador.toString());
		} catch (CompradorNaoEncontradoException e) {
			System.out.println("Erro ao buscar comprador: " + e.getMessage());
		}

	}

	private void buscarBilhetePorCodigo() {
		try {
			String codigoSorteio = lerTexto("Codigo do sorteio: ");
			int numeroBilhete = lerInteiro("Numero do bilhete: ");

			System.out.println(controlador.consularBilhetePorCodigo(codigoSorteio, numeroBilhete));

		} catch (BilheteNaoEncontradoException e) {
			System.out.println("Erro ao buscar bilhete: " + e.getMessage());
		}
	}

	private void buscarSorteioPorCodigo() {
		try {
			String codigoSorteio = lerTexto("Codigo do sorteio: ");
			System.out.println(controlador.consultarSorteioPorCodigo(codigoSorteio));

		} catch (SorteioNaoEncontradoException e) {
			System.out.println("Erro ao buscar sorteio: " + e.getMessage());
		}
	}

	private void cadastrarComprador() {
		try {
			String cpf = lerTexto("CPF: ");
			String nome = lerTexto("Nome: ");
			String telefone = lerTexto("telefone: ");

			if (!controlador.cadastrarComprador(cpf, nome, telefone)) {
				throw new CompradorInvalidoException(
						"esse CPF já foi cadastrado ou os dados inseridos estão inválidos");
			}
			System.out.println("Comprador cadastrado com sucesso!");
		} catch (CompradorInvalidoException e) {
			System.out.println("Erro ao cadastrar comprador: " + e.getMessage());
		}

	}

	private void cadastrarVendedor() {
		try {
			String cpf = lerTexto("CPF: ");
			String nome = lerTexto("Nome: ");
			String telefone = lerTexto("telefone: ");

			if (!controlador.cadastrarVendedor(cpf, nome, telefone)) {
				throw new VendedorInvalidoException("esse CPF já foi cadastrado ou os dados inseridos estão inválidos");
			}
			System.out.println("Vendedor cadastrado com sucesso!");

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

				String codigo = lerTexto("Codigo do sorteio: ");
				String premio = lerTexto("Premio: ");
				double valorBilhete = lerDouble("Valor do bilhete: ");
				double meta = lerDouble("Valor para arrecadar: ");

				if (controlador.cadastrarRifa(codigo, premio, valorBilhete, meta)) {
					System.out.println("Rifa cadastrada com sucesso!");
				}

				break;

			case 2:

				codigo = lerTexto("Codigo do sorteio: ");
				premio = lerTexto("Premio: ");
				valorBilhete = lerDouble("Valor do bilhete: ");
				int limiteBilhetes = lerInteiro("Limite de bilhetes: ");

				if (controlador.cadastrarPixPremiado(codigo, premio, valorBilhete, limiteBilhetes)) {
					System.out.println("Pix premiado cadastrada com sucesso!");
				}
				break;

			case 0:
				return;

			default:
				System.out.println("Opcao invalida.");
			}
		} catch (BilheteInvalidoException | LimiteInvalidoException e) {
			System.out.println("Erro ao cadastrar sorteio: " + e.getMessage());
		}

	}

	private void menuExibirTipoSorteio() {
		System.out.println("\n╔══════════════════════════════════════════════════╗");
		System.out.println("║          QUAL TIPO DE SORTEIO?                   ║");
		System.out.println("╠══════════════════════════════════════════════════╣");
		System.out.printf("║  %-48s║%n", "1 - Rifa");
		System.out.printf("║  %-48s║%n", "2 - Pix premiado");
		System.out.printf("║  %-48s║%n", "0 - Cancelar");
		System.out.println("╚══════════════════════════════════════════════════╝");
	}

	private void menuExibirFormaDePagamento() {
		System.out.println("\n╔══════════════════════════════════════════════════╗");
		System.out.printf("║ %-49s║%n", "QUAL A FORMA DE PAGAMENTO?");
		System.out.println("╠══════════════════════════════════════════════════╣");
		System.out.printf("║  %-48s║%n", "1 - Pix");
		System.out.printf("║  %-48s║%n", "2 - Dinheiro");
		System.out.printf("║  %-48s║%n", "3 - Cartão");
		System.out.printf("║  %-48s║%n", "0 - Cancelar");
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
		System.out.printf("║ %-49s║%n", "10 - Atualizar meta de uma rifa");
		System.out.printf("║ %-49s║%n", "11 - Atualizar meta de um pix");
		System.out.printf("║ %-49s║%n", "12 - Mostrar nível do vendedor");
		System.out.printf("║ %-49s║%n", "13 - Transformar uma rifa em pix");
		System.out.printf("║ %-49s║%n", "14 - Listar sorteios cadastrados");
		System.out.printf("║ %-49s║%n", "15 - Listar relatório geral do sorteio");
		System.out.printf("║ %-49s║%n", "16 - Listar ranking de vendedores");
		System.out.printf("║ %-49s║%n", "17 - Listar ranking de vendedores por sorteio");
		System.out.printf("║ %-49s║%n", "18 - Listar vendas por vendedor");
		System.out.printf("║ %-49s║%n", "19 - Exibir funil de compradores");
		System.out.printf("║ %-49s║%n", "20 - Exibir histórico por comprador");
		System.out.printf("║ %-49s║%n", "21 - Sortear número");
		System.out.printf("║ %-49s║%n", "22 - Buscar pessoa por cpf");
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
				System.out.println("Digite um numero decimal valido.");
			}
		}
	}

}
