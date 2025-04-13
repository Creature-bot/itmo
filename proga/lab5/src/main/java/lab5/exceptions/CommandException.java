package lab5.exceptions;

public class CommandException extends Exception {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public CommandException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public CommandException(String message) {
        super(message);
    }
}
