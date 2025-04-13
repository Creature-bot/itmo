package lab5.commands;

import lab5.manager.CommandManager;

public class Show extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта Show.
     */
    public Show(CommandManager commandManager) {
        super("show", "Вывести в стандартный поток вывода "
                + "все элементы коллекции в строковом представлении", commandManager);
    }
    /**
     * Метод исполнения команды.
     */
    @Override
    public String doCommand() {
        return commandManager.getManager().show();
    }
}
