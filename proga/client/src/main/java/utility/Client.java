package utility;

import net.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private int failureCount = 0;
    private static final int FAILURE_LIMIT = 3;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendRequest(Request request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            out.flush();

            Response response = (Response) in.readObject();

            failureCount = 0;
            return response;

        } catch (Exception e) {
            failureCount++;
            if (failureCount >= FAILURE_LIMIT) {
                Printer.printError("Сервер недоступен. Повторите попытку позже.");
            } else {
                Printer.printError("Сервер временно недоступен. Попробуйте ещё раз.");
            }
            return null;
        }
    }
}