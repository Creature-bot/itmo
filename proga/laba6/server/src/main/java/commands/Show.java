package commands;

import manager.CollectionManager;
import model.Worker;
import net.Response;

import java.util.stream.Collectors;

/**
 * Команда show — выводит все элементы коллекции в строковом представлении.
 */
public class Show extends Command<Void> {
    private final CollectionManager collectionManager;
    /**
     * Конструктор класса Show.
     */
    public Show(CollectionManager collectionManager) {
        super("show", "Вывести все элементы коллекции");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду в режиме клиента/сервера.
     */
    @Override
    public Response execute(Void data) {
        if (collectionManager.getStorage().isEmpty()) {
            return new Response("Коллекция пуста.", true);
        }
        String result = collectionManager.getStorage().stream()
                .sorted()
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));

        return new Response(result, true);
    }

}