package commands;

import exceptions.CommandException;
import exceptions.ManagerStorageException;
import manager.CollectionManager;
import model.*;
import net.Response;
import java.util.Map;

/**
 * Команда {@code update} обновляет элемент коллекции по заданному {@code id}.
 */
public class Update extends Command<Map<String, Object>> {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update", "Обновить элемент по ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Map<String, Object> data) throws CommandException {
        if (data == null) throw new CommandException("Не переданы аргументы команды update.");
        Object idObj = data.get("id");
        Object workerObj = data.get("worker");

        if (!(idObj instanceof Long id) || !(workerObj instanceof Worker worker)) {
            throw new CommandException("Ожидаются поля: id (Long), worker (Worker).");
        }

        if (!collectionManager.checkId(id)) {
            return new Response("Элемент с таким ID не найден", false);
        }

        try {
            String result = collectionManager.update(id, worker);
            return new Response(result, true);
        } catch (ManagerStorageException e) {
            throw new CommandException("Ошибка обновления: " + e.getMessage());
        }
    }
}
