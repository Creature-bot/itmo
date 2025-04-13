package lab5.exceptions;

public class FieldSetterException extends Exception {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public FieldSetterException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public FieldSetterException(String message) {
        super(message);
    }
}
