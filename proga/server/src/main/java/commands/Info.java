package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import net.Response;

/**
 * Команда вывода информации о коллекции.
 */
public class Info extends Command<Void> {

    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "Вывести информацию о коллекции (тип, дата инициализации, количество элементов)");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Void ignored) throws CommandException {
        return new Response(collectionManager.info(), true);
    }
}
