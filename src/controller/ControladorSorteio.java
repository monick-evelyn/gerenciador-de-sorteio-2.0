
package controller;

import model.Bilhete;
import model.Comprador;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.interfaces.Sorteavel;
import service.SistemaSorteio;

public class ControladorSorteio {
	private SistemaSorteio sistema;

	public ControladorSorteio() {
		sistema = new SistemaSorteio();
	}

	public boolean cadastrarRifa(String codigoSorteio, String premio, double valorBilhete, double valorParaArrecadar) {
		return sistema.cadastrarRifa(codigoSorteio, premio, valorBilhete, valorParaArrecadar);
	}

	public boolean cadastrarPixPremiado(String codigo, String premio, double valorBilhete, int limiteBilhetes) {
		return sistema.cadastrarPixPremiado(codigo, premio, valorBilhete, limiteBilhetes);
	}

	public boolean cadastrarVendedor(String cpf, String nome, String telefone) {
		return sistema.cadastrarVendedor(cpf, nome, telefone);
	}

	public boolean cadastrarComprador(String cpf, String nome, String telefone) {
		return sistema.cadastrarComprador(cpf, nome, telefone);
	}

	public boolean venderBilhete(String codigoSorteio, int numero, String codigoVendedor, String codigoComprador,
			FormaDePagamento formaPagamento) {
		return sistema.venderBilhete(codigoSorteio, numero, codigoVendedor, codigoComprador, formaPagamento);
	}

	public Vendedor buscarVendedorPorCPF(String cpf) {
		return sistema.buscarVendedorPorCPF(cpf);
	}

	public Comprador buscarCompradorPorCPF(String cpf) {
		return sistema.buscarCompradorPorCPF(cpf);
	}

	public Bilhete buscarBilhetePorCodigo(String codigoSorteio, int numeroBilhete) {
		return sistema.buscarBilhetePorCodigo(codigoSorteio, numeroBilhete);
	}

	public String consularBilhetePorCodigo(String codigoSorteio, int numeroBilhete) {
		return sistema.consultarBilhetePorCodigo(codigoSorteio, numeroBilhete);
	}

	public Sorteavel buscarSorteioPorCodigo(String codigo) {
		return sistema.buscarSorteioPorCodigo(codigo);
	}

	public String consultarSorteioPorCodigo(String codigo) {
		return sistema.consultarSorteioPorCodgigo(codigo);
	}

	public int contarSorteios() {
		return sistema.contarSorteios();
	}

	public int contarVendedores() {
		return sistema.contarVendedores();
	}

	public int contarCompradores() {
		return sistema.contarCompradores();
	}

	public String exibirTodosOsSorteios() {
		return sistema.exibirTodosOsSorteios();
	}

	public String realizarSorteio(String codigoSorteio) {
		return sistema.realizarSorteio(codigoSorteio);

	}

	public boolean removerVenda(String codigoSorteio, int numeroBilhete) {
		return sistema.removerVenda(codigoSorteio, numeroBilhete);
	}

	public boolean atualizarMetaRifa(String codigo, double novaMeta) {
		return sistema.atualizarMetaRifa(codigo, novaMeta);
	}

	public boolean atualizarMetaPix(String codigo, int novaMeta) {
		return sistema.atualizarMetaPix(codigo, novaMeta);
	}

	public String sortearNumero(String codigoRifa) {
		return sistema.sortearNumero(codigoRifa);
	}

	public boolean atualizarNivelDoVendedor(String cpf) {
		return sistema.atualizarNivelDoVendedor(cpf);
	}

	public boolean transformarRifaemPix(String codigo, int metaBilhetes) {
		return sistema.transformarRifaemPix(codigo, metaBilhetes);
	}

	public String exibirRelatorioGeralDoSorteio(String codigo) {
		return sistema.exibirRelatorioGeralDoSorteio(codigo);
	}

	public String gerarRankingVendedores() {
		return sistema.gerarRankingVendedores();
	}

	public String gerarRankingVendedoresPorSorteio(String codigoSorteio) {
		return sistema.gerarRankingVendedoresPorSorteio(codigoSorteio);
	}

	public String listarVendasPorVendedor(String cpf) {
		return sistema.listarVendasPorVendedor(cpf);
	}

	public String exibirTodosOsCompradores() {
		return sistema.exibirTodosOsCompradores();
	}

	public String exibirHistoricoPorComprador(String cpf) {
		return sistema.exibirHistoricoPorComprador(cpf);
	}

}
