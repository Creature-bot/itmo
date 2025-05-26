package utility;

import exceptions.ShaperException;
import model.*;
import net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Менеджер клиентских команд. Отвечает за построение Request-объектов на основе ввода.
 */
public class ClientCommandManager {
    private final ClientSideShaper shaper;
    private final Consumer<String> commandProcessor;
    private final Map<String, Function<String[], Request>> commandMap = new HashMap<>();

    public ClientCommandManager(ClientSideShaper shaper, Consumer<String> commandProcessor) {
        this.shaper = shaper;
        this.commandProcessor = commandProcessor;
        initCommands();
    }

    private void initCommands() {
        commandMap.put("add", args -> {
            try {
                Worker w = shaper.buildWorker();
                return new Request("add", w);
            } catch (ShaperException e) {
                throw new RuntimeException(e);
            }
        });

        commandMap.put("add_if_max", args -> {
            try {
                Worker w = shaper.buildWorker();
                return new Request("add_if_max", w);
            } catch (ShaperException e) {
                throw new RuntimeException(e);
            }
        });

        commandMap.put("add_if_min", args -> {
            try {
                Worker w = shaper.buildWorker();
                return new Request("add_if_min", w);
            } catch (ShaperException e) {
                throw new RuntimeException(e);
            }
        });

        commandMap.put("update", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Не указан ID");
            try {
                long id = Long.parseLong(args[0]);
                Worker updated = shaper.buildWorker();
                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                data.put("worker", updated);
                return new Request("update", data);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID должен быть числом (long)");
            } catch (ShaperException e) {
                throw new RuntimeException(e);
            }
        });


        commandMap.put("help", args -> new Request("help", "client"));
        commandMap.put("exit", args -> new Request("exit", null));
        commandMap.put("clear", args -> new Request("clear", null));
        commandMap.put("info", args -> new Request("info", null));
        commandMap.put("show", args -> new Request("show", null));
        commandMap.put("remove_lower", args -> new Request("remove_lower", null));
        commandMap.put("print_field_descending_position", args -> new Request("print_field_descending_position", null));

        commandMap.put("remove_by_id", args -> new Request("remove_by_id", args));
        commandMap.put("count_less_than_status", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Не указан статус");
            try {
                Status status = Status.valueOf(args[0].toUpperCase());
                return new Request("count_less_than_status", status);
            } catch (IllegalArgumentException e) {
                Printer.printError("Некорректный статус. Доступные значения: FIRED, PROBATION, HIRED, RECOMMENDED_FOR_PROMOTION");
                return null;
            }
        });
        commandMap.put("filter_less_than_organization", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Не указан тип организации");
            return new Request("filter_less_than_organization", args[0]);
        });

        commandMap.put("execute_script", args -> {
            if (args.length == 0) {
                Printer.printMessage("Укажите путь к скрипту.");
                return null;
            }
            ScriptExecutor.executeScript(args[0], commandProcessor);
            return null;
        });
    }

    public Request buildRequest(String command, String[] args) {
        Function<String[], Request> builder = commandMap.get(command);
        if (builder == null) {
            throw new IllegalArgumentException("Неизвестная команда: " + command);
        }
        return builder.apply(args);
    }

    public boolean hasCommand(String command) {
        return commandMap.containsKey(command);
    }
}
