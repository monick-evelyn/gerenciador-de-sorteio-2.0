package model;

public class Comprador extends Pessoa {
	int quantidadeBilhetesComprados;
	
	public Comprador(String cpf, String nome, String telefone) {
		super(cpf, nome, telefone);
		this.quantidadeBilhetesComprados = 0;
	}

	@Override
	public void registrarHistorico() {
		quantidadeBilhetesComprados++;
	}

	public int getQuantidadeBilhetesComprados() {
		return quantidadeBilhetesComprados;
	}

	@Override
	public String toString() {
		return super.toString() + 
				"Quantidade de bilhetes comprados: " + quantidadeBilhetesComprados;
	}
}
