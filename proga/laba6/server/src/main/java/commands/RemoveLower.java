package commands;

import exceptions.CommandException;
import net.Response;
import exceptions.ManagerStorageException;
import manager.CollectionManager;

public class RemoveLower extends Command<Void> {
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower", "Удалить из коллекции все элементы, меньшие заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Void data) throws CommandException {
        try {
            return new Response(collectionManager.removeLower(), true);
        } catch (ManagerStorageException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
