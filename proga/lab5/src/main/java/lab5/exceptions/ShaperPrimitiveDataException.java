package lab5.exceptions;

public class ShaperPrimitiveDataException extends ShaperException {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ShaperPrimitiveDataException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ShaperPrimitiveDataException(String message) {
        super(message);
    }
}
