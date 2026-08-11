package model;

import java.util.Objects;

import exception.DadosInvalidosException;

public abstract class Pessoa {
	private String cpf;
	private String nome;
	private String telefone;
	
	
	public Pessoa(String cpf, String nome, String telefone) {
		validarCPF(cpf);
		validarTexto(nome);
		validarTelefone(telefone);
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
	}
	
	protected String validarCPF(String cpf) {
	    if (cpf == null || cpf.isBlank()) {
	        throw new DadosInvalidosException("CPF nao pode ser vazio.");
	    }
	    if (!cpf.matches("\\d{11}")) {
	        throw new DadosInvalidosException("CPF deve conter exatamente 11 digitos numericos.");
	    }
	    return cpf;
	}

	protected String validarTelefone(String telefone) {
	    if (telefone == null || telefone.isBlank()) {
	        throw new DadosInvalidosException("Telefone nao pode ser vazio.");
	    }
	    if (!telefone.matches("\\d{10,11}")) {
	        throw new DadosInvalidosException("Telefone deve conter 10 ou 11 digitos numericos.");
	    }
	    return telefone;
	}
	
	protected String validarTexto(String nome) {
	    if (nome == null || nome.isBlank()) {
	        throw new DadosInvalidosException("Nome nao pode ser vazio.");
	    }
	    return nome;
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

	@Override
	public String toString() {
		return "CPF: " + cpf + 
				"\nNome: " + nome + 
				"\nTelefone: " + telefone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
	
	
	
}
