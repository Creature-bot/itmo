import utility.UserHandler;

/**
 * Главный класс клиента, запускающий взаимодействие с пользователем и сервером.
 */
public class ClientMain {
    public static void main(String[] args) {
        new UserHandler("localhost", 12345).start();
    }
}