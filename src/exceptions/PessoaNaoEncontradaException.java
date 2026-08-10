package exceptions;

public class PessoaNaoEncontradaException extends RuntimeException {
	public PessoaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
