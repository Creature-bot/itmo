package lab5;

import lab5.exceptions.ParserFileException;
import lab5.exceptions.ParserNoFileException;
import lab5.manager.CommandManager;
import lab5.manager.CollectionManager;
import lab5.parser.Parser;

public class Main {
    /**
     * Поле CommandManager {@link CommandManager}.
     */
    public static CommandManager commandManager;

    /**
     * main метод (точка входа в программу).
     *
     * @param args аргументы main-метода
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new ParserNoFileException("Отсутствует аргумент");
            }
            Parser.path = args[0];
            commandManager = new CommandManager(Parser.parseFromFileManager());
            commandManager.loopReadingFromConsole();
        } catch (ParserFileException e) {
            Printer.printError(e.getMessage());
            commandManager = new CommandManager(new CollectionManager());
            commandManager.loopReadingFromConsole();
        }
    }
}
