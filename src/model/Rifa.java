package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import exception.BilheteInvalidoException;
import exception.DadosInvalidosException;
import exception.LimiteInvalidoException;
import exception.PessoaNaoEncontradaException;
import exception.SorteioInvalidoException;
import model.enums.FormaDePagamento;
import model.interfaces.Relatoravel;
import model.interfaces.Sorteavel;

public class Rifa implements Sorteavel, Relatoravel {
	private String codigo;
	private String premio;
	private double valorBilhete;
	private double meta;
	private double arrecadacaoAtual;
	private HashMap<Integer, Bilhete> bilhetes;
	private boolean sorteado;

	public Rifa(String codigo, String premio, double valorBilhete, double meta) {
		validarTexto(codigo);
		validarTexto(premio);
		validarValorBilhete(valorBilhete);
		validarMeta(meta);
		
		this.codigo = codigo;
		this.premio = premio;
		this.valorBilhete = valorBilhete;
		this.meta = meta;
		this.arrecadacaoAtual = 0;
		this.bilhetes = new HashMap<>();
		this.sorteado = false;
	}
	
	private void validarMeta(double meta) {
	    if (meta <= 0) {
	        throw new LimiteInvalidoException("A meta deve ser maior que zero.");
	    }
	}

	private void validarValorBilhete(double valorBilhete) {
	    if (valorBilhete <= 0) {
	        throw new LimiteInvalidoException("Valor do bilhete deve ser maior que zero: " + valorBilhete);
	    }
	}
	
	private void validarTexto(String texto) {
		if (texto.isBlank()) {
			throw new DadosInvalidosException(texto);
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}

	public double getValorBilhete() {
		return valorBilhete;
	}

	public void setValorBilhete(double valorBilhete) {
		this.valorBilhete = valorBilhete;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public double getArrecadacaoAtual() {
		return arrecadacaoAtual;
	}

	public void setArrecadacaoAtual(double arrecadacaoAtual) {
		this.arrecadacaoAtual = arrecadacaoAtual;
	}

	public HashMap<Integer, Bilhete> getBilhetes() {
		return bilhetes;
	}

	public boolean isSorteado() {
		return sorteado;
	}

	public void setSorteado(boolean sorteado) {
		this.sorteado = sorteado;
	}

	@Override
	public String gerarRelatorio() {
		String relatorio = "\n============================== RELATÓRIO GERAL =============================="
				+ "\nPROGRESSO: =================================================================="
				+ "\nMeta de arrecadação: R$ %.2f%n" + meta + "\nValor Arrecadado:    R$ %.2f%n" + arrecadacaoAtual
				+ "\nBilhetes vendidos: " + contarBilhetes() + "\nProgresso: %.1f%%%n"
				+ calcularProgressoEmPorcentagem() + "Restante para meta: %.1f%%%n" + calcularRestanteEmPorcentagem()
				+ "\n===========================================================================";
		return relatorio;
	}

	@Override
	public double calcularRestanteEmPorcentagem() {
		return 100 - calcularProgressoEmPorcentagem();
	}

	@Override
	public double calcularProgressoEmPorcentagem() {
		double progressoPorcentagem = (100 * arrecadacaoAtual / meta);
		return progressoPorcentagem;
	}

	@Override
	public boolean venderBilhete(int numero, Vendedor vendedor, Comprador comprador, FormaDePagamento pagamento) {
		if (bilhetes.containsKey(numero)) {
			throw new BilheteInvalidoException("Bilhete " + numero + " não está disponível para venda.");
		}
		if (sorteado) {
			throw new SorteioInvalidoException("A rifa já foi sorteada.");
		}
		
		if (vendedor == null) {
			throw new PessoaNaoEncontradaException("Vendedor não encontrado.");
		}
		
		if (comprador == null) {
			throw new PessoaNaoEncontradaException("Comprador não encontrado.");
		}

		Bilhete bilhete = new Bilhete(numero, vendedor, comprador, pagamento);
		bilhetes.put(numero, bilhete);
		arrecadacaoAtual += valorBilhete;
		vendedor.registrarHistorico();
		comprador.registrarHistorico();
		return true;
	}

	@Override
	public boolean prontoParaSorteio() {
		if (arrecadacaoAtual >= meta) {
			return true;
		}
		return false;
	}

	@Override
	public String realizarSorteio() {
		if (bilhetes.isEmpty()) {
			throw new SorteioInvalidoException("Nenhum bilhete foi vendido ainda, não é possível sortear!");
		}

		if (!prontoParaSorteio()) {
			throw new SorteioInvalidoException("Meta ainda não foi alcançada");
		}

		if (sorteado) {
			throw new SorteioInvalidoException("A rifa já foi sorteada.");
		}

		int posicaoSorteada = new Random().nextInt(contarBilhetes());

		Iterator<Bilhete> iterator = bilhetes.values().iterator();
		Bilhete bilheteGanhador = null;

		for (int i = 0; i <= posicaoSorteada; i++) {
			bilheteGanhador = iterator.next();
		}
		this.sorteado = true;

		String resultado = "\n=================================================\n"
				+ "           NÚMERO SORTEADO COM SUCESSO!           \n"
				+ "=================================================\n" + "Bilhete: " + bilheteGanhador.toString()
				+ "\nCPF do ganhador: " + bilheteGanhador.getComprador().getCpf()
				+ "\n=================================================\n";

		return resultado;
	}

	public int contarBilhetes() {
		return bilhetes.size();
	}

	@Override
	public String toString() {
		return "Código: " + codigo + "\nPrêmio: " + premio + "\nValor por bilhete: " + valorBilhete + "\nMeta: " + meta
				+ "\nArrecadacao atual: " + arrecadacaoAtual + "\nQuantidade de bilhetes vendidos: " + contarBilhetes()
				+ "\nSorteado? " + sorteado;
	}

	@Override
	public Bilhete buscarBilhete(int numero) {
		return bilhetes.get(numero);
	}

	@Override
	public boolean removerBilhete(int numero) {
		if (sorteado) {
			throw new SorteioInvalidoException("Nao e possivel remover bilhete: a rifa ja foi sorteada");
		}

		Bilhete bilheteRemovido = bilhetes.remove(numero);

		if (bilheteRemovido == null) {
			return false;
		}
		arrecadacaoAtual -= valorBilhete;
		return true;
	}

	public void atualizarMetaRifa(double novaMeta) {
		if (novaMeta <= 0) {
			throw new LimiteInvalidoException("Meta deve ser maior que zero.");
		}

		this.meta = novaMeta;
	}

}
