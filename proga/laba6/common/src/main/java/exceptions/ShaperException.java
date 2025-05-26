package exceptions;

public class ShaperException extends Exception {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ShaperException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ShaperException(String message) {
        super(message);
    }
}
