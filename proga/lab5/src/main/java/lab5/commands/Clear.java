package lab5.commands;

import lab5.manager.CommandManager;

public class Clear extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта clear.
     */
    public Clear(CommandManager commandManager) {
        super("clear", "Очистить коллекцию", commandManager);
    }

    /**
     * Метод исполнения команды.
     */
    @Override
    public String doCommand() {
        return commandManager.getManager().clear();
    }
}
