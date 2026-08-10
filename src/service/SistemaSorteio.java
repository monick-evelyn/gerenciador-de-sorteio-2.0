package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import exception.PessoaNaoEncontradaException;
import exception.SorteioInvalidoException;
import exception.SorteioNaoEncontradoException;
import exception.VendedorInvalidoException;
import exception.VendedorNaoEncontradoException;

public class SistemaSorteio {

	private HashMap<String, Sorteavel> itens;
	private List<Vendedor> vendedores;
	private List<Comprador> compradores;

	public SistemaSorteio() {
		this.itens = new HashMap<>();
		this.vendedores = new ArrayList<>();
		this.compradores = new ArrayList<>();
	}
	
	public boolean cadastrarRifa(String codigo, String premio, double valorBilhete, double meta) {
	    if (codigo == null || codigo.isBlank()) {
	        throw new SorteioInvalidoException("Codigo do sorteio invalido.");
	    }
	    if (premio == null || premio.isBlank()) {
	        throw new SorteioInvalidoException("Premio nao pode ser vazio.");
	    }
	    if (itens.containsKey(codigo)) {
	        throw new SorteioInvalidoException("Ja existe um sorteio cadastrado com esse codigo.");
	    }
	    if (meta <= 0.0) {
	    	throw new SorteioInvalidoException("Meta insuficiente.");
	    }

	    Rifa rifa = new Rifa(codigo, premio, valorBilhete, meta);
	    itens.put(codigo, rifa);
	    return true;
	}

	public boolean cadastrarPixPremiado(String codigo, String premio, double valorBilhete, int limiteBilhetes) {
	    if (codigo == null || codigo.isBlank()) {
	        throw new SorteioInvalidoException("Codigo do sorteio invalido.");
	    }
	    if (premio == null || premio.isBlank()) {
	        throw new SorteioInvalidoException("Premio nao pode ser vazio.");
	    }
	    if (itens.containsKey(codigo)) {
	        throw new SorteioInvalidoException("Ja existe um sorteio cadastrado com esse codigo.");
	    }

	    PixPremiado pix = new PixPremiado(codigo, premio, valorBilhete, limiteBilhetes);
	    itens.put(codigo, pix);
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

	public boolean venderBilhete(String codigoSorteio, int numero, String codigoVendedor, String codigoComprador,
			FormaDePagamento formaPagamento) {
		
		Vendedor vendedor = buscarVendedorPorCPF(codigoVendedor);
		Comprador comprador = buscarCompradorPorCPF(codigoComprador);
		Sorteavel item = buscarSorteioPorCodigo(codigoSorteio);
		
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

	public Sorteavel buscarSorteioPorCodigo(String codigo) {
		Sorteavel item = itens.get(codigo);

		if (item == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigo + " nao encontrado.");
		}
		return item;
	}

	public int contarSorteios() {
		return itens.size();
	}

	public int contarVendedores() {
		return vendedores.size();
	}

	public int contarCompradores() {
		return compradores.size();
	}

	public String exibirTodosOsSorteios() {
		if (contarSorteios() == 0) {
			return "Nenhum sorteio encontrado.";
		}
		return itens.toString();
	}

	public String realizarSorteio(String codigoSorteio) {
		Sorteavel sorteio = itens.get(codigoSorteio);
		
		if (sorteio == null) {
			throw new SorteioNaoEncontradoException("Sorteio de codigo: " + codigoSorteio + " nao encontrado.");
		}
		
		return sorteio.realizarSorteio();
	}

}
