package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import exception.BilheteInvalidoException;
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
		this.codigo = codigo;
		this.premio = premio;
		this.valorBilhete = valorBilhete;
		this.meta = meta;
		this.arrecadacaoAtual = 0;
		this.bilhetes = new HashMap<>();
		this.sorteado = false;
	}

	@Override
	public String gerarRelatorio() {
		String relatorio = "\n============================== RELATÓRIO GERAL ==============================" + 
							"\nPROGRESSO: ==================================================================" + 
							"\nMeta de arrecadação: R$ %.2f%n" + meta + 
							"\nValor Arrecadado:    R$ %.2f%n" + arrecadacaoAtual +
							"\nBilhetes vendidos: " + bilhetes.size() +
							"\nProgresso: %.1f%%%n" + calcularProgressoEmPorcentagem() + 
							"Restante para meta: %.1f%%%n" + calcularRestanteEmPorcentagem() + 
							"\n===========================================================================";
		return relatorio;
	}

	@Override
	public double calcularRestanteEmPorcentagem() {
		if (calcularProgressoEmPorcentagem() >= 100) {
			return 0.0;
		}
		double valorRestante = meta - arrecadacaoAtual;
		double progressoPorcentagem = (100 * valorRestante)/arrecadacaoAtual;
		return progressoPorcentagem;
	}

	@Override
	public double calcularProgressoEmPorcentagem() {
		double progressoPorcentagem = (100 * arrecadacaoAtual/meta);
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
			return "Nenhum bilhete foi vendido ainda, não é possível sortear!";
		}

		if (!prontoParaSorteio()) {
			return "Meta ainda não foi alcançada";
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

		String resultado = 
				"\n=================================================\n"
				+ "           NÚMERO SORTEADO COM SUCESSO!           \n"
				+ "=================================================\n" 
				+ "Bilhete: " + bilheteGanhador.toString()
				+ "\nCPF do ganhador: " + bilheteGanhador.getComprador().getCpf()
				+ "\n=================================================\n";

		return resultado;
	}
	
	public int contarBilhetes() {
		return bilhetes.size();
	}

	@Override
	public String toString() {
		return "Código: " + codigo + 
				"\nPrêmio: " + premio + 
				"\nValor por bilhete: " + valorBilhete + 
				"\nMeta: " + meta +
				"\nArrecadacao atual: " + arrecadacaoAtual + 
				"\nQuantidade de bilhetes vendidos: " + contarBilhetes() + 
				"\nSorteado? " + sorteado;
	}
	
	
}
