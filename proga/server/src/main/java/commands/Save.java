package commands;

import exceptions.CommandException;
import manager.CommandManager;
import net.Response;

/**
 * Команда {@code save} сохраняет коллекцию в файл на серверной стороне.
 */
public class Save extends Command<Void> {
    private final CommandManager commandManager;

    public Save(CommandManager commandManager) {
        super("save", "Сохранить коллекцию в файл");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Void arg) throws CommandException {
        try {
            commandManager.getCollectionManager().saveCollection();
            return new Response("Коллекция успешно сохранена.", true);
        } catch (Exception e) {
            throw new CommandException("Ошибка при сохранении: " + e.getMessage());
        }
    }
}