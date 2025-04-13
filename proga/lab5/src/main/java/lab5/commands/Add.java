package lab5.commands;

import lab5.exceptions.ShaperException;
import lab5.exceptions.CommandException;
import lab5.manager.CommandManager;
import lab5.parser.Shaper;

public class Add extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта add.
     */
    public Add(CommandManager commandManager) {
        super("add", "Добавить новый элемент в коллекцию", commandManager);
    }

    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        try {
            return commandManager.getManager().add(Shaper.buildWorker());
        } catch (ShaperException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
