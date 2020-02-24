package exception;

public class MonetaryException extends RuntimeException {

    public MonetaryException(){
        super();
    }

    public MonetaryException(String msg){
        super(msg);
    }

}
