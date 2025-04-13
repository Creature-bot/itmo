package lab5.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab5.exceptions.CommandException;
import lab5.exceptions.ParserFileException;
import lab5.manager.CommandManager;
import lab5.parser.Parser;


public class Save extends Command {
    /**
     * Конструктор класса, отвечающий за создание нового объекта Save.
     */
    public Save(CommandManager commandManager) {
        super("save", "Сохранить коллекцию в файл", commandManager);
    }

    /**
     * Метод исполнения команды.
     *
     * @throws CommandException ошибка исполнения команды
     */
    @Override
    public String doCommand() throws CommandException {
        try {
            Parser.parseToFileManager(commandManager.getManager());
            return String.format("Память была сохранена в файле %s!", Parser.path);
        } catch (JsonProcessingException | ParserFileException e) {
            throw new CommandException(String.format("Недопустимый файл для сохранения: %s",
                    e.getMessage()));
        }
    }
}
