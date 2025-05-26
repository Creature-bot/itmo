package commands;

import manager.CommandManager;
import net.Response;
import java.util.Comparator;

public class Help extends Command<Object> {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "Вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Object argument) {
        boolean calledByClient = "client".equals(argument);

        StringBuilder sb = new StringBuilder("Доступные команды:\n");

        commandManager.getCommands().values().stream()
                .sorted(Comparator.comparing(Command::getName))
                .forEach(cmd -> {
                    String name = cmd.getName();
                    if (calledByClient && name.equals("save")) return;
                    if (commandManager.isServer() && !calledByClient && !(name.equals("save") || name.equals("finish")))
                        return;
                    sb.append(name).append(": ").append(cmd.getDescription()).append("\n");
                });

        return new Response(sb.toString().trim(), true);
    }
}
