package exception;

public class BilheteNaoEncontradoException extends RuntimeException {
	public BilheteNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
}

