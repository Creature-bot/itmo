package lab5.exceptions;

public class ManagerStorageException extends ManagerException {
    /**
     * Конструктор класса, отвечающий за создание объекта исключения без сообщения.
     */
    public ManagerStorageException() {
        super();
    }
    /**
     * Конструктор класса, отвечающий за создание объекта исключения с сообщением.
     *
     * @param message сообщение, которое связано с исключением
     */
    public ManagerStorageException(String message) {
        super(message);
    }
}
