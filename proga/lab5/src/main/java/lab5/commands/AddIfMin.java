package lab5.commands;

import lab5.exceptions.ShaperException;
import lab5.exceptions.CommandException;
import lab5.exceptions.ManagerStorageException;
import lab5.manager.CommandManager;
import lab5.parser.Shaper;

public class AddIfMin extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта AddIfMin.
     */
    public AddIfMin(CommandManager commandManager) {
        super("add_if_min", "Добавить новый элемент в коллекцию,"
                + " если его значение меньше, чем у наименьшего элемента этой коллекции",
                commandManager);
    }

    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        try {
            return commandManager.getManager().addIfMin(Shaper.buildWorker());
        } catch (ShaperException e) {
            throw new CommandException(e.getMessage());
        } catch (ManagerStorageException e) {
            throw new CommandException(String.format("ошибка добавления: %s", e.getMessage()));
        }
    }
}
