package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Bilhete;
import model.Comprador;
import model.PixPremiado;
import model.Rifa;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.interfaces.Relatoravel;
import model.interfaces.Sorteavel;

import exception.BilheteInvalidoException;
import exception.BilheteNaoEncontradoException;
import exception.CompradorInvalidoException;
import exception.CompradorNaoEncontradoException;
import exception.LimiteInvalidoException;
import exception.SorteioInvalidoException;
import exception.SorteioNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;

public class SistemaSorteio {

	private HashMap<String, Sorteavel> itens;
	private List<Vendedor> vendedores;
	private List<Comprador> compradores;

	public SistemaSorteio() {
		this.itens = new HashMap<>();
		this.vendedores = new ArrayList<>();
		this.compradores = new ArrayList<>();
	}

	public boolean cadastrarRifa(String codigo, String premio, double valorBilhete, double meta) {
		if (codigo == null || codigo.isBlank()) {
			throw new SorteioInvalidoException("Codigo do sorteio invalido.");
		}
		if (premio == null || premio.isBlank()) {
			throw new SorteioInvalidoException("Premio nao pode ser vazio.");
		}
		if (itens.containsKey(codigo)) {
			throw new SorteioInvalidoException("Ja existe um sorteio cadastrado com esse codigo.");
		}
		if (meta <= 0.0) {
			throw new SorteioInvalidoException("Meta insuficiente.");
		}

		Rifa rifa = new Rifa(codigo, premio, valorBilhete, meta);
		itens.put(codigo, rifa);
		return true;
	}

	public boolean cadastrarPixPremiado(String codigo, String premio, double valorBilhete, int limiteBilhetes) {
		if (codigo == null || codigo.isBlank()) {
			throw new SorteioInvalidoException("Codigo do sorteio invalido.");
		}
		if (premio == null || premio.isBlank()) {
			throw new SorteioInvalidoException("Premio nao pode ser vazio.");
		}
		if (itens.containsKey(codigo)) {
			throw new SorteioInvalidoException("Ja existe um sorteio cadastrado com esse codigo.");
		}

		PixPremiado pix = new PixPremiado(codigo, premio, valorBilhete, limiteBilhetes);
		itens.put(codigo, pix);
		return true;
	}

	public boolean cadastrarVendedor(String cpf, String nome, String telefone) {

		if (cpf == null || cpf.isBlank()) {
			throw new VendedorInvalidoException("CPF do vendedor nao pode ser vazio.");
		}

		if (nome == null || nome.isBlank()) {
			throw new VendedorInvalidoException("Nome do vendedor nao pode ser vazio.");
		}

		Vendedor novoVendedor = new Vendedor(cpf, nome, telefone);

		if (!vendedores.add(novoVendedor)) {
			throw new VendedorInvalidoException("Ja existe um vendedor cadastrado.");
		}

		return true;
	}

	public boolean cadastrarComprador(String cpf, String nome, String telefone) {
		if (cpf == null || cpf.isBlank()) {
			throw new CompradorInvalidoException("CPF do comprador nao pode ser vazio.");
		}

		if (nome == null || nome.isBlank()) {
			throw new CompradorInvalidoException("Nome do comprador nao pode ser vazio.");
		}

		Comprador novoComprador = new Comprador(cpf, nome, telefone);

		if (!compradores.add(novoComprador)) {
			throw new CompradorInvalidoException("Ja existe um comprador cadastrado.");
		}

		return true;
	}

	public boolean venderBilhete(String codigoSorteio, int numero, String codigoVendedor, String codigoComprador,
			FormaDePagamento formaPagamento) {

		Vendedor vendedor = buscarVendedorPorCPF(codigoVendedor);
		Comprador comprador = buscarCompradorPorCPF(codigoComprador);
		Sorteavel item = buscarSorteioPorCodigo(codigoSorteio);

		if (numero <= 0) {
			throw new BilheteInvalidoException("O numero do bilhete nao pode ser negativo.");
		}

		if (vendedor == null) {
			throw new BilheteInvalidoException("Esse vendedor nao existe.");
		}

		if (comprador == null) {
			throw new BilheteInvalidoException("Esse comprador nao existe.");
		}

		if (formaPagamento == null) {
			throw new BilheteInvalidoException("Essa forma de pagamento nao existe.");
		}

		if (item == null) {
			throw new BilheteInvalidoException("Item de codigo " + codigoSorteio + " nao encontrado.");
		}

		return item.venderBilhete(numero, vendedor, comprador, formaPagamento);

	}

	public Vendedor buscarVendedorPorCPF(String cpf) {

		for (Vendedor vendedor : vendedores) {

			if (vendedor.getCpf().equalsIgnoreCase(cpf)) {
				return vendedor;
			}
		}
		throw new VendedorNaoEncontradoException("Vendedor com CPF " + cpf + " nao encontrado.");
	}

	public Comprador buscarCompradorPorCPF(String cpf) {

		for (Comprador comprador : compradores) {

			if (comprador.getCpf().equalsIgnoreCase(cpf)) {
				return comprador;
			}
		}
		throw new CompradorNaoEncontradoException("Comprador com CPF " + cpf + " nao encontrado.");
	}

	public Bilhete buscarBilhetePorCodigo(String codigoSorteio, int numeroBilhete) {

		Sorteavel item = itens.get(codigoSorteio);

		if (item == null) {
			throw new BilheteNaoEncontradoException("Item de codigo " + codigoSorteio + " nao encontrado.");
		}

		Bilhete bilhete = item.buscarBilhete(numeroBilhete);

		if (bilhete == null) {
			throw new BilheteNaoEncontradoException(
					"Bilhete " + numeroBilhete + " nao encontrado no item " + codigoSorteio + ".");
		}
		return bilhete;
	}

	public Sorteavel buscarSorteioPorCodigo(String codigo) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}
		return item;
	}

	public int contarSorteios() {
		return itens.size();
	}

	public int contarVendedores() {
		return vendedores.size();
	}

	public int contarCompradores() {
		return compradores.size();
	}

	public String exibirTodosOsSorteios() {
		if (contarSorteios() == 0) {
			return "Nenhum sorteio encontrado.";
		}
		return itens.toString();
	}

	public String realizarSorteio(String codigoSorteio) {
		Sorteavel sorteio = itens.get(codigoSorteio);

		if (sorteio == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigoSorteio + " nao encontrado.");
		}

		return sorteio.realizarSorteio();
	}

	public boolean removerVenda(String codigoSorteio, int numeroBilhete) {
		Sorteavel item = itens.get(codigoSorteio);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigoSorteio + " nao encontrado.");
		}

		Bilhete bilheteAux = item.buscarBilhete(numeroBilhete);

		if (bilheteAux == null) {
			throw new BilheteNaoEncontradoException(
					"Bilhete " + numeroBilhete + " nao encontrado no item " + codigoSorteio + ".");
		}

		return item.removerBilhete(numeroBilhete);

	}

	public boolean atualizarMetaRifa(String codigo, double novaMeta) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}
		if (!(item instanceof Rifa)) {
			throw new BilheteInvalidoException("O sorteio de codigo " + codigo + " nao e uma Rifa.");
		}

		Rifa rifa = (Rifa) item;
		rifa.atualizarMetaRifa(novaMeta);
		return true;
	}

	public boolean atualizarMetaPix(String codigo, int novaMeta) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}
		if (!(item instanceof Rifa)) {
			throw new BilheteInvalidoException("O sorteio de codigo " + codigo + " nao e uma Rifa.");
		}

		PixPremiado pix = (PixPremiado) item;
		pix.atualizarMeta(novaMeta);
		return true;
	}

	public String sortearNumero(String codigoRifa) {
		Sorteavel item = itens.get(codigoRifa);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigoRifa + " nao encontrado.");
		}

		return item.realizarSorteio();
	}

	public boolean atualizarNivelDoVendedor(String cpf) {
		Vendedor vendedorAux = buscarVendedorPorCPF(cpf);
		return vendedorAux.alterarNivelVendedor();
	}

	public boolean transformarRifaemPix(String codigo, int metaBilhetes) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}
		if (!(item instanceof Rifa)) {
			throw new BilheteInvalidoException("O sorteio de codigo " + codigo + " nao e uma Rifa.");
		}
		Rifa rifa = (Rifa) item;
		if (rifa.isSorteado()) {
			throw new SorteioInvalidoException("Nao e possivel transformar: a rifa ja foi sorteada.");
		}
		if (metaBilhetes < rifa.contarBilhetes()) {
			throw new LimiteInvalidoException(
					"A nova meta de bilheres nao pode ser menor que a quantidade de bilhetes ja vendida.");
		}

		for (Integer numero : rifa.getBilhetes().keySet()) {
			if (numero < 1 || numero > metaBilhetes) {
				throw new LimiteInvalidoException(
						"O bilhete " + numero + " ja vendido fica fora do intervalo da nova meta.");
			}
		}

		PixPremiado pix = new PixPremiado(rifa.getCodigo(), rifa.getPremio(), rifa.getValorBilhete(), metaBilhetes);
		pix.getBilhetes().putAll(rifa.getBilhetes());
		pix.setArrecadacaoAtual(rifa.getArrecadacaoAtual());
		itens.put(codigo, pix);
		return true;
	}

	public String exibirRelatorioGeralDoSorteio(String codigo) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}

		if (item instanceof Relatoravel) {
			Relatoravel sorteio = (Relatoravel) item;
			return sorteio.gerarRelatorio();
		}
		throw new SorteioInvalidoException("Nao e possivel gerar o relatorio");

	}

	private HashMap<Integer, Bilhete> obterBilhetesDoSorteio(Sorteavel item) {
		if (item instanceof Rifa) {
			return ((Rifa) item).getBilhetes();
		} else if (item instanceof PixPremiado) {
			return ((PixPremiado) item).getBilhetes();
		}
		return null;
	}

	public String gerarRankingVendedores() {
		if (vendedores == null || vendedores.isEmpty()) {
			throw new VendedorNaoEncontradoException("Nenhum vendedor encontrado");
		}
		return formatarRankingDeVendedores(this.vendedores, "RANKING GERAL DE VENDEDORES");

	}

	public String gerarRankingVendedoresPorSorteio(String codigoSorteio) {
		Sorteavel item = buscarSorteioPorCodigo(codigoSorteio);
		HashMap<Integer, Bilhete> bilhetes = obterBilhetesDoSorteio(item);
		if (bilhetes == null || bilhetes.isEmpty()) {
			throw new BilheteNaoEncontradoException("Nenhuma venda encontrada");
		}

		List<Vendedor> vendedoresDoSorteio = new ArrayList<>();
		for (Bilhete bilhete : bilhetes.values()) {
			vendedoresDoSorteio.add(bilhete.getVendedor());
		}
		return formatarRankingDeVendedores(vendedoresDoSorteio, "RANKING DO SORTEIO (" + codigoSorteio + ")");
	}

	private String formatarRankingDeVendedores(List<Vendedor> vendedores, String titulo) {
		List<Vendedor> ranking = new ArrayList<>(vendedores);

		for (int i = 0; i < ranking.size() - 1; i++) {
			for (int j = 0; j < ranking.size() - 1 - i; j++) {
				if (ranking.get(j).getQuantidadeVendas() < ranking.get(j + 1).getQuantidadeVendas()) {
					Vendedor aux = ranking.get(j);

					ranking.set(j, ranking.get(j + 1));
					ranking.set(j, aux);
				}
			}
		}
		String resultado = "";

		resultado = "====================" + titulo + "====================";
		for (int i = 0; i < ranking.size(); i++) {
			Vendedor v = ranking.get(i);
			resultado += ((i + 1) + "º Lugar: " + v.getNome() + " (CPF: " + v.getCpf() + ")" + " - Total Vendido: "
					+ v.getQuantidadeVendas() + " bilhetes");
		}
		resultado += "===============================================================";

		return resultado;

	}

	public String listarVendasPorVendedor(String cpf) {
		Vendedor vendedorAux = buscarVendedorPorCPF(cpf);

		if (itens.isEmpty()) {
			throw new BilheteNaoEncontradoException("Nenhuma venda cadastrada no sistema");
		}
		String resultado = "";
		resultado += "\n==================== VENDAS POR VENDEDOR ====================\n";
		resultado += "Vendedor: " + vendedorAux.getNome() + " (CPF: " + vendedorAux.getCpf() + ")\n";
		resultado += "===============================================================";
		int bilhetesEncontrados = 0;
		for (Sorteavel item : itens.values()) {
			HashMap<Integer, Bilhete> bilhetes = obterBilhetesDoSorteio(item);
			if (bilhetes != null) {
				for (Bilhete bilhete : bilhetes.values()) {
					if (bilhete.getVendedor() != null && bilhete.getVendedor().getCpf().equalsIgnoreCase(cpf)) {
						resultado += bilhete.toString() + "\n";
						bilhetesEncontrados++;
					}
				}
			}
		}
		if (bilhetesEncontrados == 0) {
			throw new BilheteNaoEncontradoException(
					"O vendedor " + vendedorAux.getNome() + " não realizou nenhuma venda");
		}

		resultado += "Total de bilhetes vendidos: " + bilhetesEncontrados + "\n";
		resultado += "===============================================================";
		return resultado;

	}

	public String exibirTodosOsCompradores() {
		if (compradores == null || compradores.isEmpty()) {
			throw new CompradorNaoEncontradoException("Nenhum comprador cadastrado.");
		}
		String resultado = "";
		resultado = "==================== COMPRADORES CADASTRADOS ====================\n";
		for (int i = 0; i < compradores.size(); i++) {
			Comprador comprador = compradores.get(i);
			resultado += ((i + 1) + "º - Nome: " + comprador.getNome() + " | CPF: " + comprador.getCpf()
					+ " | Telefone: " + comprador.getTelefone() + "\n");
		}
		resultado += "===============================================================";
		return resultado;
	}

	public String exibirHistoricoPorComprador(String cpf) {
		Comprador compradorAux = buscarCompradorPorCPF(cpf);
		if (itens.isEmpty()) {
			throw new BilheteNaoEncontradoException("Nenhum bilhete encontrado no sistema.");
		}

		String resultado = "";
		resultado += "==================== HISTÓRICO DO COMPRADOR ====================\n";
		resultado += "Comprador: " + compradorAux.getNome() + " (CPF: " + compradorAux.getCpf() + ")\n";
		resultado += "===============================================================";

		int bilhetesComprados = 0;

		for (Sorteavel item : itens.values()) {
			HashMap<Integer, Bilhete> bilhetes = obterBilhetesDoSorteio(item);

			if (bilhetes != null) {
				for (Bilhete bilhete : bilhetes.values()) {
					if (bilhete.getComprador() != null && bilhete.getComprador().getCpf().equalsIgnoreCase(cpf)) {
						String nomeVendedor = bilhete.getVendedor().getNome();

						resultado += " | Bilhete Nº: " + bilhete.getNumero() + " | Vendedor: " + nomeVendedor
								+ " | Pagamento: " + bilhete.getFormaPagamento() + "\n";
						bilhetesComprados++;
					}
				}
			}
		}
		if (bilhetesComprados == 0) {
			throw new BilheteNaoEncontradoException(compradorAux.getNome() + " ainda não comprou nenhum bilhete.");
		}
		resultado += "Total de bilhetes adquiridos: " + bilhetesComprados + "\n";
		resultado += "===========================================================";
		return resultado;
	}
}
