package commands;

import manager.CollectionManager;
import exceptions.CommandException;
import net.Response;

public class PrintFieldDescendingPosition extends Command<Void> {

    private final CollectionManager collectionManager;

    public PrintFieldDescendingPosition(CollectionManager collectionManager) {
        super("print_field_descending_position", "Вывести значения поля position всех " +
                "элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Void ignored) throws CommandException {
        return new Response(collectionManager.printFieldDescendingPosition(), true);
    }
}
