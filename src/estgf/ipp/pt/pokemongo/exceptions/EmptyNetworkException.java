package estgf.ipp.pt.pokemongo.exceptions;

public class EmptyNetworkException extends RuntimeException {

    public EmptyNetworkException() {
    }

    public EmptyNetworkException(String message) {
        super(message);
    }
}
