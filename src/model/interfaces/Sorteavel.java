package model.interfaces;

import java.util.HashMap;

import model.Bilhete;
import model.Comprador;
import model.Vendedor;
import model.enums.FormaDePagamento;

import exception.BilheteInvalidoException;
import model.PixPremiado;

public interface Sorteavel {
	boolean venderBilhete(int numero, Vendedor vendedor, Comprador comprador, FormaDePagamento pagamento);

	boolean prontoParaSorteio();

	String realizarSorteio();

	Bilhete buscarBilhete(int numero);

	boolean removerBilhete(int numero);

	HashMap<Integer, Bilhete> getBilhetes();

	String gerarRelatorio();

	double calcularProgressoEmPorcentagem();

	double calcularRestanteEmPorcentagem();

	default void atualizarMetaRifa(double novaMeta) {
		throw new BilheteInvalidoException("Este sorteio nao e uma Rifa.");
	}

	default void atualizarMetaPix(int novaMeta) {
		throw new BilheteInvalidoException("Este sorteio nao e um Pix Premiado.");
	}

	default PixPremiado transformarEmPix(int metaBilhetes) {
		throw new BilheteInvalidoException("Este sorteio nao e uma Rifa.");
	}
}