package lab5.commands;

import lab5.manager.CommandManager;

public class PrintFieldDescendingPosition extends Command {
    /**
     * Конструктор класса отвечает за создание нового объекта PrintFieldDescendingPosition.
     */
    public PrintFieldDescendingPosition(CommandManager commandManager) {
        super("print_field_descending_position", "Вывести значения поля "
                + "position всех элементов в порядке убывания", commandManager);
    }

    /**
     * Метод исполнения команды.
     */
    @Override
    public String doCommand() {
        return commandManager.getManager().printFieldDescendingPosition();
    }
}
