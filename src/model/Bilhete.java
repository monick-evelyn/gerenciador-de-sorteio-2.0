package model;

public class Bilhete {
	private int numero;
	private Vendedor vendedor;
	private String formaPagamento;
	
	public int getNumero() {
		return numero;
	}
	
	public Vendedor getVendedor() {
		return vendedor;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}
