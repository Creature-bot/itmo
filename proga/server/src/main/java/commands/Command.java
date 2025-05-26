package commands;

import exceptions.CommandException;
import net.Response;

public abstract class Command<T> {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    /**
     * Метод, который должен реализовывать каждая команда.
     * @param arg Аргумент типа T (может быть null)
     * @return Response — результат выполнения команды
     */
    public abstract Response execute(T arg) throws CommandException;}