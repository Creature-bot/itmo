package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import model.Worker;
import net.Response;

/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class Add extends Command<Worker> {

    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "Добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Worker worker) throws CommandException {
        if (worker == null) {
            throw new CommandException("Не передан объект Worker.");
        }
        String result = collectionManager.add(worker);
        return new Response(result, true);
    }
}
