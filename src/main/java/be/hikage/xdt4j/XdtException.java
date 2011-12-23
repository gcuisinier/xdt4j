package be.hikage.xdt4j;

public class XdtException  extends RuntimeException{

    public XdtException(String message) {
        super(message);
    }

    public XdtException(String message, Throwable cause) {
        super(message, cause);
    }
}
