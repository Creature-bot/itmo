package lab5.commands;

import lab5.exceptions.CommandException;
import lab5.manager.CommandManager;
import java.util.ArrayList;

public abstract class Command {
    /**
     * Поле имени команды.
     */
    protected String command;
    /**
     * Поле описания команды.
     */
    protected String description;
    /**
     * Поле списка аргументов.
     */
    protected ArrayList<String> commandArgs = new ArrayList<>();

    protected final CommandManager commandManager;
    /**
     * Конструктор, отвечающий за создание нового объекта.
     *
     * @param command имя команды
     * @param description описание команды
     */
    public Command(String command, String description, CommandManager commandManager) {
        this.command = command;
        this.description = description;
        this.commandManager = commandManager;
    }
    /**
     * Метод Shaper возвращает объект Command.
     *
     * @param commandArgs аргументы команды
     * @return команду с соответствующим полем
     */
    public Command setCommandArgs(ArrayList<String> commandArgs) {
        this.commandArgs = commandArgs;
        return this;
    }
    /**
     * Метод getter возвращает имя команды.
     *
     * @return имя команды
     */
    public String getCommand() {
        return command;
    }
    /**
     * Метод getter возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }
    /**
     * Абстрактный метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    public abstract String doCommand() throws CommandException;
}
