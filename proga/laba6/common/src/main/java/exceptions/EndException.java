package exceptions;

public class EndException extends CommandException {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public EndException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public EndException(String message) {
        super(message);
    }
}
