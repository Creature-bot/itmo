package lab5.commands;

import lab5.exceptions.EndException;
import lab5.manager.CommandManager;

public class Exit extends Command {
    /**
     * Конструктор класса создание нового объекта класса Exit.
     */
    public Exit(CommandManager commandManager) {
        super("exit", "Завершить программу (без сохранения в файл)",
                commandManager);
    }
    /**
     * Метод исполнения команды.
     *
     * @throws EndException завершение работы программы
     */
    @Override
    public String doCommand() throws EndException {
        throw new EndException();
    }
}
