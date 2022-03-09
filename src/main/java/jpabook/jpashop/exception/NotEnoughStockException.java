package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {


    public NotEnoughStockException() {
        super();
    }

    //메세지 넘겨줌
    public NotEnoughStockException(String message) {
        super(message);
    }

    //Exception trace가 쭉 나오게 함
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    //얘는 없어도 되겠다
    /*
    protected NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }*/
}
