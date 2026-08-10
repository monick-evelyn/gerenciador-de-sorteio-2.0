package controller;

import model.Bilhete;
import model.Comprador;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.enums.TipoSorteio;
import model.interfaces.Sorteavel;
import service.SistemaSorteio;

public class ControladorSorteio {
	private SistemaSorteio sistema;

	public ControladorSorteio() {
		sistema = new SistemaSorteio();
	}

	public boolean cadastrarSorteio(int codigoSorteio, TipoSorteio tipo, String premio, double valorBilhete,
			double valorParaArrecadar) {
		return sistema.cadastrarSorteio(codigoSorteio, tipo, premio, valorBilhete, valorParaArrecadar);
	}

	public boolean cadastrarVendedor(String cpf, String nome, String telefone) {
		return sistema.cadastrarVendedor(cpf, nome, telefone);
	}

	public boolean cadastrarComprador(String cpf, String nome, String telefone) {
		return sistema.cadastrarComprador(cpf, nome, telefone);
	}

	public boolean cadastrarBilhete(int codigoSorteio, int numero, Vendedor vendedor, Comprador comprador,
			FormaDePagamento formaPagamento) {
		return sistema.cadastrarBilhete(codigoSorteio, numero, vendedor, comprador, formaPagamento);
	}

	public Vendedor buscarVendedorPorCPF(String cpf) {
		return sistema.buscarVendedorPorCPF(cpf);
	}

	public Comprador buscarCompradorPorCPF(String cpf) {
		return sistema.buscarCompradorPorCPF(cpf);
	}

	public Bilhete buscarBilhetePorCodigo(int codigoSorteio, int numeroBilhete) {
		return sistema.buscarBilhetePorCodigo(codigoSorteio, numeroBilhete);
	}

	public Sorteavel buscarSorteioPorCodigo(int codigoSorteio) {
		return sistema.buscarSorteioPorCodigo(codigoSorteio);
	}

}
