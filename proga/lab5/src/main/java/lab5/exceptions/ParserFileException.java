package lab5.exceptions;

public class ParserFileException extends Exception {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ParserFileException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ParserFileException(String message) {
        super(message);
    }
}
