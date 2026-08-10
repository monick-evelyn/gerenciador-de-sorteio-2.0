package service;

import java.util.ArrayList;
import java.util.HashMap;

import model.Bilhete;
import model.Comprador;
import model.Pessoa;
import model.PixPremiado;
import model.Rifa;
import model.Vendedor;
import model.enums.FormaDePagamento;
import model.enums.TipoSorteio;
import model.interfaces.Sorteavel;

import exception.BilheteInvalidoException;
import exception.BilheteNaoEncontradoException;
import exception.CompradorInvalidoException;
import exception.CompradorNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;
import exceptions.PessoaNaoEncontradaException;

public class SistemaSorteio {

	private HashMap<Integer, Sorteavel> itens;
	private ArrayList<Vendedor> vendedores;
	private ArrayList<Comprador> compradores;

	public SistemaSorteio() {
		this.itens = new HashMap<>();
		this.vendedores = new ArrayList<>();
		this.compradores = new ArrayList<>();
	}

	public boolean cadastrarSorteio(int codigoSorteio, TipoSorteio tipo, String premio, double valorBilhete,
			double valorParaArrecadar) {
		if (codigoSorteio <= 0) {
			throw new BilheteInvalidoException("Codigo do sorteio invalido.");
		}

		if (premio == null || premio.isBlank()) {
			throw new BilheteInvalidoException("Nome do bilhete nao pode ser vazio.");
		}

		if (tipo == null) {
			throw new BilheteInvalidoException("O tipo do sorteio precisa ser informado.");
		}

		if (premio == null || premio.isBlank()) {
			throw new BilheteInvalidoException("Premio nao pode ser vazio.");
		}

		if (valorParaArrecadar <= 0) {
			throw new BilheteInvalidoException("O valor para arrecadar deve ser maior que zero.");
		}

		if (itens.containsKey(codigoSorteio)) {
			throw new BilheteInvalidoException("Ja existe um sorteio cadastrado.");
		}

		Sorteavel novoSorteio;

		switch (tipo) {
		case RIFA:
			novoSorteio = new Rifa(String.valueOf(codigoSorteio), premio, valorBilhete, valorParaArrecadar);
			break;

		case PIXPREMIADO:
			int metaBilhetes = (int) valorParaArrecadar;
			novoSorteio = new PixPremiado(String.valueOf(codigoSorteio), premio, valorBilhete, metaBilhetes);
			break;
		default:
			throw new BilheteInvalidoException("Tipo de sorteio invalido");

		}
		itens.put(codigoSorteio, novoSorteio);
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

	public boolean cadastrarBilhete(int codigoSorteio, int numero, Vendedor vendedor, Comprador comprador,
			FormaDePagamento formaPagamento) {

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

		Sorteavel item = itens.get(codigoSorteio);

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

	public Bilhete buscarBilhetePorCodigo(int codigoSorteio, int numeroBilhete) {

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

	public Pessoa buscarPessoaPorCPF(String cpf) {
		for (Vendedor vendedor : vendedores) {
			if (vendedor.getCpf().equalsIgnoreCase(cpf)) {
				return vendedor;
			}
		}

		for (Comprador comprador : compradores) {
			if (comprador.getCpf().equalsIgnoreCase(cpf)) {
				return comprador;
			}
		}

		throw new PessoaNaoEncontradaException("Nenhuma pessoa com CPF " + cpf + " encontrada.");
	}
	
	public Sorteavel buscarSorteioPorCodigo(int codigoSorteio) {
		Sorteavel item = itens.get(codigoSorteio);
		
		if(item==null) {
			throw new BilheteInvalidoException("Sorteio de codigo: "+codigoSorteio+" nao encontrado.");
		}
		return item;
	}

}
