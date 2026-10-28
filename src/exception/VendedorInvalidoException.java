package exception;

public class VendedorInvalidoException extends RuntimeException {
    public VendedorInvalidoException(String mensagem) {
        super(mensagem);
    }

}
