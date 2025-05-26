package utility;

import net.Printer;
import net.Request;
import net.Response;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserHandler {
    private final Scanner scanner;
    private final Client client;
    private final ClientCommandManager commandManager;

    public UserHandler(String host, int port) {
        this.scanner = new Scanner(System.in);
        this.client = new Client(host, port);
        this.commandManager = new ClientCommandManager(
                new ClientSideShaper(scanner),
                this::processCommandLine
        );
    }

    public void start() {
        Printer.printMessage("Клиент запущен. Введите команду:");
        while (true) {
            try {
                if (!scanner.hasNextLine()) {
                    Printer.printMessage("Завершение клиента ...");
                    System.exit(0);
                }

                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                processCommandLine(input);
            } catch (NoSuchElementException e) {
                Printer.printMessage("Завершение клиента...");
                System.exit(0);
            } catch (Exception e) {
                Printer.printError("Ошибка: " + e.getMessage());
            }
        }
    }

    public void processCommandLine(String input) {
        String[] tokens = input.trim().split("\\s+");
        String commandName = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        if (commandName.equalsIgnoreCase("exit")) {
            Printer.printMessage("Завершение клиента...");
            System.exit(0);
        }

        try {
            Request request = commandManager.buildRequest(commandName, args);
            if (request != null) {
                Response response = client.sendRequest(request);
                if (response == null) {
                    Printer.printError("Сервер не ответил.");
                } else if (response.success()) {
                    if (commandName.equalsIgnoreCase("help")) {
                        String msg = response.message();
                        msg += "\nexecute_script: Выполнить команды из скрипта";
                        Printer.printMessage(msg);
                    } else {
                        Printer.printMessage(response.message());
                    }
                    if (response.isExit()) {
                        Printer.printMessage("Завершение клиента по команде сервера.");
                        System.exit(0);
                    }
                } else {
                    Printer.printError(response.message());
                }
            }
        } catch (RuntimeException e) {
            Printer.printError("Ошибка клиента: " + e.getMessage());
        }
    }

}
