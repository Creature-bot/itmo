package lab5.commands;

import lab5.manager.CommandManager;

public class Help extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта Help.
     */
    public Help(CommandManager commandManager) {
        super("help", "Вывести справку по доступным командам", commandManager);
    }

    /**
     * Метод исполнения команды.
     */
    @Override
    public String doCommand() {
        final StringBuilder result = new StringBuilder();
        commandManager.getCommands().values().forEach(command ->
                result.append(String.format("%s: %s\n", command.getCommand(),
                        command.getDescription())));
        return result.substring(0, result.toString().length() - 1);
    }
}
