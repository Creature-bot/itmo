package commands;

import exceptions.CommandException;
import manager.CollectionManager;
import model.Status;
import net.Response;

/**
 * Команда для подсчёта элементов, у которых статус меньше заданного.
 */
public class CountLessThanStatus extends Command<Status> {

    private final CollectionManager collectionManager;

    public CountLessThanStatus(CollectionManager collectionManager) {
        super("count_less_than_status", "Подсчитать количество элементов, "
                + "у которых статус меньше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Status status) throws CommandException {
        if (status == null) {
            throw new CommandException("Не передан статус.");
        }
        int count = collectionManager.countLessThanStatus(status);
        return new Response("Найдено работников с меньшим статусом: " + count, true);
    }
}
