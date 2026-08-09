package service;

import java.util.ArrayList;
import java.util.HashMap;

import exception.CompradorInvalidoException;
import exception.CompradorNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;

import model.Comprador;
import model.Vendedor;
import model.interfaces.Sorteavel;

public class SistemaSorteio {
	
	private HashMap<String, Sorteavel> itens;
	private ArrayList<Vendedor> vendedores;
	private ArrayList<Comprador> compradores;
	
	
	public boolean cadastrarVendedor(String cpf, String nome, String telefone) {
		
		if(cpf==null || cpf.isBlank()) {
	        throw new VendedorInvalidoException("CPF do vendedor nao pode ser vazio.");
		}
		
		if(nome==null || nome.isBlank()) {
			throw new VendedorInvalidoException("Nome do vendedor nao pode ser vazio.");
		}
		
		Vendedor novoVendedor = new Vendedor(cpf, nome, telefone);
		
		if(!vendedores.add(novoVendedor)) {
			throw new VendedorInvalidoException("Ja existe um vendedor cadastrado.");
		}
		
		return true;
	}
	
	public boolean cadastrarComprador(String cpf, String nome, String telefone) {
		if(cpf==null || cpf.isBlank()) {
	        throw new CompradorInvalidoException("CPF do comprador nao pode ser vazio.");
		}
		
		if(nome==null || nome.isBlank()) {
			throw new CompradorInvalidoException("Nome do comprador nao pode ser vazio.");
		}
		
		Comprador novoComprador = new Comprador(cpf, nome, telefone);
		
		if(!compradores.add(novoComprador)) {
			throw new CompradorInvalidoException("Ja existe um comprador cadastrado.");
		}
		
		return true;
	}

	public Vendedor buscarVendedorPorCPF(String cpf) {
		
		for(Vendedor vendedor : vendedores) {
			
			if(vendedor.getCpf().equalsIgnoreCase(cpf)) {
				return vendedor;
			}
		}
		throw new VendedorNaoEncontradoException("Vendedor com CPF "+cpf+" nao encontrado.");
	}
	
	public Comprador buscarCompradorPorCPF(String cpf) {
		
		for(Comprador comprador : compradores) {
			
			if(comprador.getCpf().equalsIgnoreCase(cpf)) {
				return comprador;
			}
		}
		throw new CompradorNaoEncontradoException("Comprador com CPF "+cpf+" nao encontrado.");
	}
	
	
	
}
