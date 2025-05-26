package commands;

import exceptions.CommandException;
import exceptions.ManagerStorageException;
import manager.CollectionManager;
import model.Worker;
import net.Response;

/**
 * Команда для добавления нового элемента, если он меньше минимального.
 */
public class AddIfMin extends Command<Worker> {
    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min", "Добавить элемент, если он меньше минимального в коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Worker worker) throws CommandException {
        if (worker == null) throw new CommandException("Не передан объект Worker.");
        try {
            String result = collectionManager.addIfMin(worker);
            return new Response(result, true);
        } catch (ManagerStorageException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
