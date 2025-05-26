import manager.*;
import utility.Server;

public class ServerMain {
    public static void main(String[] args) {
        CollectionManager collectionManager = InitializationManager.initialize();
        CommandManager commandManager = new CommandManager(collectionManager, true);
        new Server(12345, commandManager).start();
    }
}