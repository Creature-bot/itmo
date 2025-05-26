package commands;

import exceptions.CommandException;
import manager.CommandManager;
import net.Response;

/**
 * Команда выхода из программы — клиентской или серверной части.
 */
public class Exit extends Command<Void> {
    private final CommandManager commandManager;

    public Exit(CommandManager commandManager) {
        super("exit", "Завершить работу");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Void arg) throws CommandException {
        try {
            commandManager.getCollectionManager().saveCollection();
            return new Response("Коллекция сохранена. Сервер завершает работу.", true, true);
        } catch (Exception e) {
            return new Response("Клиент завершает работу.", true, true);
        }
    }
}