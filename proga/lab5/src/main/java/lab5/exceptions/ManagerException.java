package lab5.exceptions;

public class ManagerException extends Exception {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ManagerException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ManagerException(String message) {
        super(message);
    }
}
