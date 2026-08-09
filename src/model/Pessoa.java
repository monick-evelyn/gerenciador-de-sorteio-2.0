package model;

public abstract class Pessoa {
	private String cpf;
	private String nome;
	private String telefone;
	
	
	public Pessoa(String cpf, String nome, String telefone) {
		this.cpf = validarCPF(cpf);
		this.nome = nome;
		this.telefone = telefone;
	}

	public abstract void registrarHistorico();
	
	
	public String getCpf() {
		return cpf;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	protected String validarCPF(String cpf) {
		if (cpf == null) {
			
		}
		return cpf;
	}

	@Override
	public String toString() {
		return "CPF: " + cpf + 
				"\nNome: " + nome + 
				"\nTelefone: " + telefone;
	}
	
	
}
