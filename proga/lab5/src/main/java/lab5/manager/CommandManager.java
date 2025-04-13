package lab5.manager;

import lab5.Printer;
import lab5.commands.*;
import lab5.exceptions.CommandException;
import lab5.exceptions.EndException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class CommandManager {
    /**
     * Поле списка допустимых команд, работающих с менеджером {@link CollectionManager}.
     */
    private final HashMap<String, Command> commands = new HashMap<>();
    /**
     * Поле стека, необходимое для корректного исполнения команды execute_script.
     */
    private final Stack<String> paths = new Stack<>();
    /**
     * Поле менеджера {@link CollectionManager}, с которым взаимодействуют команды.
     */
    private final CollectionManager manager;

    /**
     * Конструктор класса, отвечающий создание нового экземпляра класса CommandManager.
     *
     * @param manager экземпляр класса {@link CollectionManager}
     */
    public CommandManager(CollectionManager manager) {
        this.manager = manager;
        this.fill();
    }
    /**
     * Метод getter для получения экземпляра менеджера {@link CollectionManager}.
     *
     * @return экземпляр класса Manager
     */
    public CollectionManager getManager() {
        return manager;
    }
    /**
     * Метод для заполнения списка команд менеджера {@link CollectionManager}.
     */
    private void fill() {
        commands.put("help", new Help(this));
        commands.put("info", new Info(this));
        commands.put("show", new Show(this));
        commands.put("add", new Add(this));
        commands.put("update", new Update(this));
        commands.put("remove_by_id", new RemoveById(this));
        commands.put("clear", new Clear(this));
        commands.put("save", new Save(this));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("exit", new Exit(this));
        commands.put("add_if_max", new AddIfMax(this));
        commands.put("add_if_min", new AddIfMin(this));
        commands.put("remove_lower", new RemoveLower(this));
        commands.put("count_less_than_status", new CountLessThanStatus(this));
        commands.put("filter_less_than_organization", new FilterLessThanOrganization(this));
        commands.put("print_field_descending_position", new PrintFieldDescendingPosition(this));
    }
    /**
     * Метод getter возвращает hashmap команд.
     *
     * @return hashmap команд
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }
    /**
     * Возвращает Stack вывоза команд.
     *
     * @return Stack вызова команд
     */
    public Stack<String> getPaths() {
        return paths;
    }
    /**
     * Метод, отвечающих за обработку, запуск и отлов ошибок команд.
     *
     * @param input входящая строка из терминала
     * @throws CommandException ошибка обработки или исполнения сказать
     */
    public void executeCommand(String input) throws CommandException {
        final ArrayList<String> toRead = new ArrayList<>(Arrays.stream(
                input.strip().split(" ")).toList());
        final String command = toRead.get(0);

        if (!command.isEmpty()) {
            final ArrayList<String> commandArgs = new ArrayList<>(toRead.subList(1, toRead.size()));
            if (commands.containsKey(command)) {
                try {
                    Printer.printMessage(commands.get(command).setCommandArgs(commandArgs).doCommand());
                } catch (EndException e) {
                    System.exit(0);
                }
            } else {
                throw new CommandException(String.format("Введена недопустимая команда: %s\nДля вывода команд напишите help", command));
            }
        }
    }

    /**
     * Метод, реализующий считывание команд с консоли.
     */
    public void loopReadingFromConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    break;
                }
                executeCommand(scanner.nextLine());
            } catch (CommandException e) {
                Printer.printError(e.getMessage());
            }
        }
        scanner.close();
    }
}
