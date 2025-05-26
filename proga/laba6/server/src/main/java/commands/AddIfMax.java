package commands;

import exceptions.CommandException;
import exceptions.ManagerStorageException;
import manager.CollectionManager;
import model.Worker;
import net.Response;

/**
 * Команда для добавления нового элемента, если он больше максимального.
 */
public class AddIfMax extends Command<Worker> {
    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max", "Добавить элемент, если он больше максимального в коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Worker worker) throws CommandException {
        if (worker == null) throw new CommandException("Не передан объект Worker.");
        try {
            String result = collectionManager.addIfMax(worker);
            return new Response(result, true);
        } catch (ManagerStorageException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
