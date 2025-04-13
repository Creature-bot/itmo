package lab5.commands;

import lab5.exceptions.CommandException;
import lab5.manager.CommandManager;
import lab5.model.Status;

import java.util.Arrays;

/**
 * Класс, реализующий команду count_less_than_status.
 */
public class CountLessThanStatus extends Command {
    /**
     * Конструктор класса CountLessThanStatus.
     */
    public CountLessThanStatus(CommandManager commandManager) {
        super("count_less_than_status", "Вывести количество элементов, "
                + "значение поля status которых меньше заданного", commandManager);
    }

    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        if (commandArgs.isEmpty()) {
            throw new CommandException(String.format("В команду %s не был введен аргумент", command));
        }
        Status status = parseStatusEnum(commandArgs.get(0).trim());
        return String.valueOf(commandManager.getManager().countLessThanStatus(status));
    }

    /**
     * Метод для парсинга строки в Enum Status, с обработкой ошибок.
     *
     * @param input аргумент команды
     * @return соответствующее значение Enum Status
     * @throws CommandException если аргумент некорректен
     */
    private Status parseStatusEnum(String input) throws CommandException {
        try {
            return Status.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CommandException("Ошибка: Некорректный статус.\nДоступные статусы: " +
                    Arrays.toString(Status.values()));
        }
    }
}
