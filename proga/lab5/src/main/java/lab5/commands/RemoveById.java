package lab5.commands;

import lab5.exceptions.CommandException;
import lab5.exceptions.ManagerException;
import lab5.manager.CommandManager;

public class RemoveById extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта RemoveById.
     */
    public RemoveById(CommandManager commandManager) {
        super("remove_by_id", "Удалить элемент из коллекции по его id", commandManager);
    }
    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        try {
            if (commandArgs.isEmpty()) {
                throw new CommandException(String.format("В команду %s не был введен аргумент",
                        command));
            }
            return commandManager.getManager()
                    .removeById(Integer.parseInt(commandArgs.get(0)));
        } catch (NumberFormatException e) {
            throw new CommandException(String.format(
                    "В команду %s введен недопустимый аргумент: значение должно быть числом",
                    command, e.getMessage()));
        } catch (ManagerException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
