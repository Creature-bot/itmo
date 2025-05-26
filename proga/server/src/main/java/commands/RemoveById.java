package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import net.Response;
import exceptions.ManagerException;

public class RemoveById extends Command<Integer> {

    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "Удалить элемент из коллекции по его ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Integer id) throws CommandException {
        if (id == null) {
            throw new CommandException("ID не может быть null.");
        }

        try {
            return new Response(collectionManager.removeById(id), true);
        } catch (ManagerException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
