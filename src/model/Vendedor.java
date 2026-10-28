package model;

import model.enums.NivelVendedor;

public class Vendedor extends Pessoa {
	private int quantidadeVendas;
	private NivelVendedor nivel;

	public Vendedor(String cpf, String nome, String telefone) {
		super(cpf, nome, telefone);
		this.quantidadeVendas = 0;
		this.nivel = NivelVendedor.BRONZE;
	}

	@Override
	public void registrarHistorico() {
		quantidadeVendas++;
		alterarNivelVendedor();
	}

	public boolean alterarNivelVendedor() {
		NivelVendedor novoNivel = NivelVendedor.calcularNivel(quantidadeVendas);

		if (novoNivel != nivel) {
			this.nivel = novoNivel;
			return true;
		}

		return false;
	}

	public int getQuantidadeVendas() {
		return quantidadeVendas;
	}

	public void setQuantidadeVendas(int quantidadeVendas) {
		this.quantidadeVendas = quantidadeVendas;
	}

	public NivelVendedor getNivel() {
		return nivel;
	}

	@Override
	public String toString() {
		return super.toString() + "\nNível: " + nivel.name() + "\nQuantidade de vendas: " + quantidadeVendas;
	}
}
