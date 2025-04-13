package lab5.exceptions;

public class ParserNoFileException extends ParserFileException {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ParserNoFileException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ParserNoFileException(String message) {
        super(message);
    }
}
