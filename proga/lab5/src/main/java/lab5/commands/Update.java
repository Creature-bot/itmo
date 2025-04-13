package lab5.commands;

import lab5.exceptions.ShaperException;
import lab5.exceptions.CommandException;
import lab5.exceptions.ManagerException;
import lab5.manager.CommandManager;
import lab5.parser.Shaper;

public class Update extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта Update.
     */
    public Update(CommandManager commandManager) {
        super("update", "Обновить значение элемента коллекции, id которого равен заданному",
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
            if (commandArgs.isEmpty()) {
                throw new CommandException(String.format("в команду %s не был введен "
                        + "аргумент", command));
            }
            final int id = Integer.parseInt(commandArgs.get(0));
            if (!commandManager.getManager().checkId(id)) {
                throw new CommandException("данный id отсутствует");
            }
            return commandManager.getManager()
                    .update(id, Shaper.buildWorker());
        } catch (NumberFormatException e) {
            throw new CommandException(String.format("в команду %s введен недопустимый аргумент: "
                    + "значение должно быть число", command));
        } catch (ManagerException | ShaperException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
