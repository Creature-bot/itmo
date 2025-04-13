package lab5.commands;

import lab5.exceptions.CommandException;
import lab5.exceptions.ParserFileException;
import lab5.manager.CommandManager;
import lab5.parser.Parser;

public class ExecuteScript extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта класса ExecuteScript.
     */
    public ExecuteScript(CommandManager commandManager) {
        super("execute_script", "Считать и исполнить скрипт из указанного файла. "
                + "В скрипте содержатся команды в таком же виде, "
                + "в котором их вводит пользователь в интерактивном режиме", commandManager);
    }
    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        if (commandArgs.isEmpty()) {
            throw new CommandException("Не введены аргументы");
        }

        final String path = commandArgs.get(0);
        if (commandManager.getPaths().contains(path)) {
            throw new CommandException("Обнаружена рекурсия: скрипт " + path + " уже выполняется.");
        }

        commandManager.getPaths().add(path);
        try {
            for (String command : Parser.parseFromCommand(path)) {
                commandManager.executeCommand(command);
            }
            return "Скрипт успешно выполнен: " + path;
        } catch (ParserFileException e) {
            throw new CommandException("Ошибка чтения файла скрипта: " + e.getMessage());
        } finally {
            if (!commandManager.getPaths().isEmpty() && path.equals(commandManager.getPaths().peek())) {
                commandManager.getPaths().pop();
            }
        }
    }
}
