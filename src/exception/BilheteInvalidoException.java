package exception;

public class BilheteInvalidoException  extends RuntimeException {
    public BilheteInvalidoException(String mensagem) {
        super(mensagem);
    }
}
