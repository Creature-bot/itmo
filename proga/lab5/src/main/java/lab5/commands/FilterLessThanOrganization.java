package lab5.commands;

import lab5.model.Worker;
import lab5.exceptions.CommandException;
import lab5.manager.CommandManager;

/**
 * Класс, реализующий команду filter_less_than_organization.
 */
public class FilterLessThanOrganization extends Command {
    /**
     * Конструктор класса FilterLessThanOrganization.
     */
    public FilterLessThanOrganization(CommandManager commandManager) {
        super("filter_less_than_organization", "Вывести элементы, "
                + "значение поля organization которых меньше заданного", commandManager);
    }

    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        if (commandArgs.isEmpty()) {
            throw new CommandException("Нужно указать годовой оборот для сравнения.");
        }

        try {
            int turnover = Integer.parseInt(commandArgs.get(0));
            return commandManager.getManager().filterLessThanOrganization(turnover);
        } catch (NumberFormatException e) {
            throw new CommandException("Ошибка: аргумент должен быть целым числом.");
        }
    }
}
