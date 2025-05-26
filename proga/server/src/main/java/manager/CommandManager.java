package manager;

import commands.*;
import exceptions.CommandException;
import net.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final CollectionManager collectionManager;
    private final Map<String, Command<?>> commands = new HashMap<>();
    private final boolean isServer;

    public CommandManager(CollectionManager collectionManager, boolean isServer) {
        this.collectionManager = collectionManager;
        this.isServer = isServer;

        registerCommand(new Save(this));
        registerCommand(new Exit(this));
        registerCommand(new Help(this));
        registerCommand(new Add(collectionManager));
        registerCommand(new Info(collectionManager));
        registerCommand(new Clear(collectionManager));
        registerCommand(new AddIfMax(collectionManager));
        registerCommand(new AddIfMin(collectionManager));
        registerCommand(new CountLessThanStatus(collectionManager));
        registerCommand(new FilterLessThanOrganization(collectionManager));
        registerCommand(new PrintFieldDescendingPosition(collectionManager));
        registerCommand(new RemoveById(collectionManager));
        registerCommand(new RemoveLower(collectionManager));
        registerCommand(new Show(collectionManager));
        registerCommand(new Update(collectionManager));
    }

    public boolean isServer() {
        return isServer;
    }

    private void registerCommand(Command<?> command) {
        commands.put(command.getName(), command);
    }

    public Command<?> getCommand(String name) throws CommandException {
        Command<?> command = commands.get(name);
        if (command == null) {
            throw new CommandException("Команда \"" + name + "\" не найдена.");
        }
        return command;
    }

    public Map<String, Command<?>> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public Response executeCommand(String commandName, Object data) {
        Command<?> command = commands.get(commandName);
        if (command == null) {
            return new Response("Команда не найдена: " + commandName, false);
        }
        try {
            @SuppressWarnings("unchecked")
            Response response = ((Command<Object>) command).execute(data);
            return response;
        } catch (Exception e) {
            return new Response("Ошибка выполнения команды: " + e.getMessage(), false);
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
