package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import net.Response;

/**
 * Команда для очистки коллекции.
 */
public class Clear extends Command<Void> {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "Очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Void ignored) throws CommandException {
        String result = collectionManager.clear();
        return new Response(result, true);
    }
}
