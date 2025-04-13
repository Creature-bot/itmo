package lab5.commands;

import lab5.exceptions.CommandException;
import lab5.exceptions.ManagerStorageException;
import lab5.manager.CommandManager;

public class RemoveLower extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта RemoveLower.
     */
    public RemoveLower(CommandManager commandManager) {
        super("remove_lower", "Удалить из коллекции все элементы, меньшие, чем заданный",
                commandManager);
    }
    /**
     * Метод исполнения команд.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        try {
            return commandManager.getManager().removeLower();
        } catch (ManagerStorageException e) {
            throw new CommandException(String.format("Ошибка исполнения команды %s: %s",
                    command, e.getMessage()));
        }
    }
}
