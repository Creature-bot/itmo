package lab5.commands;

import lab5.manager.CommandManager;

public class Info extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта Info.
     */
    public Info(CommandManager commandManager) {
        super("info", "Вывести в стандартный поток вывода информацию о коллекции"
                + " (тип, дата инициализации, количество элементов)", commandManager);
    }
    /**
     * Метод исполнения команды.
     */
    @Override
    public String doCommand() {
        return commandManager.getManager().info();
    }
}
