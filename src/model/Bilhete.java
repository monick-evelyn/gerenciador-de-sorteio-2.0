package model;

import model.enums.FormaDePagamento;

public class Bilhete {
	private int numero;
	private Vendedor vendedor;
	private Comprador comprador;
	private FormaDePagamento formaPagamento;

	public Bilhete(int numero, Vendedor vendedor, Comprador comprador, FormaDePagamento formaPagamento) {
		this.numero = numero;
		this.vendedor = vendedor;
		this.comprador = comprador;
		this.formaPagamento = formaPagamento;
	}

	public int getNumero() {
		return numero;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public FormaDePagamento getFormaPagamento() {
		return formaPagamento;
	}

	@Override
	public String toString() {
		return "Numero: " + numero + "\nVendedor: " + vendedor.getCpf() + "\nComprador: " + comprador.getNome()
				+ "\nForma de pagamento: " + formaPagamento.name();
	}

}
