package utility;

import manager.CommandManager;
import net.Request;
import net.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс {@code Server} реализует TCP-сервер для обработки клиентских запросов,
 * а также поддерживает ручной ввод команд в консоли сервера.
 */
public class Server {
    private final int port;
    private final CommandManager commandManager;
    private volatile boolean isRunning = true;
    private ServerSocket serverSocket;

    public Server(int port, CommandManager commandManager) {
        this.port = port;
        this.commandManager = commandManager;
    }

    /**
     * Запускает сервер и отдельный поток для ввода с консоли.
     */
    public void start() {
        Thread consoleThread = new Thread(this::consoleInputLoop);
        consoleThread.start();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен на порту " + port);

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Новое подключение: " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                } catch (IOException e) {
                    if (isRunning) {
                        System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    /**
     * Обрабатывает одного клиента.
     */
    private void handleClient(Socket clientSocket) throws IOException, ClassNotFoundException {
        try (clientSocket;
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Request request = (Request) in.readObject();
            Response response = RequestHandler.handle(request, commandManager);

            out.writeObject(response);
            out.flush();
        }
    }

    /**
     * Обрабатывает команды из консоли сервера.
     */
    private void consoleInputLoop() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (isRunning) {
                input = reader.readLine();
                if (input == null) {
                    System.out.println("Завершение работы сервера.");
                    isRunning = false;
                    closeServerSocket();
                    break;
                }

                input = input.trim().toLowerCase();
                switch (input) {
                    case "finish" -> {
                        try {
                            var saveCommand = commandManager.getCommand("save");
                            if (saveCommand != null) {
                                saveCommand.execute(null);
                                System.out.println("Коллекция сохранена.");
                            }
                        } catch (Exception e) {
                            System.err.println("Ошибка при сохранении коллекции: " + e.getMessage());
                        }
                        isRunning = false;
                        closeServerSocket();
                    }
                    case "save" -> {
                        try {
                            var saveCommand = commandManager.getCommand("save");
                            if (saveCommand != null) {
                                var response = saveCommand.execute(null);
                                System.out.println(response.message());
                            }
                        } catch (Exception e) {
                            System.err.println("Ошибка при сохранении: " + e.getMessage());
                        }
                    }
                    case "help" -> printHelp();
                    default -> System.out.println("Команда \"" + input +
                            "\" не найдена. Введите 'help' для списка доступных команд.");
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении с консоли: " + e.getMessage());
        }
    }

    /**
     * Выводит доступные серверные команды.
     */
    private void printHelp() {
        System.out.println("save: Сохранить коллекцию в файл");
        System.out.println("finish: Сохранить коллекцию и завершить сервер");
    }

    /**
     * Завершает работу сервера и сохраняет коллекцию.
     */
    private void shutdown() {
        System.out.println("Завершение работы сервера. Сохраняем коллекцию...");
        try {
            commandManager.getCollectionManager().saveCollection();
            System.out.println("Коллекция успешно сохранена.");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }

    /**
     * Закрывает серверный сокет, если он открыт.
     */
    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }
}