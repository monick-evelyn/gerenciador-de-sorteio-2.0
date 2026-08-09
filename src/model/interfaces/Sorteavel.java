package model.interfaces;

import model.Bilhete;
import model.Comprador;
import model.Vendedor;
import model.enums.FormaDePagamento;

public interface Sorteavel {
	Bilhete venderBilhete(int numero, Vendedor vendedor, Comprador comprador, FormaDePagamento pagamento);
	boolean prontoParaSorteio();
	String realizarSorteio();
}