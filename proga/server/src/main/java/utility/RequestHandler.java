package utility;

import commands.Command;
import exceptions.CommandException;
import manager.CommandManager;
import net.Request;
import net.Response;

import java.util.Map;

/**
 * Класс RequestHandler обрабатывает команды, поступающие от клиента.
 */
public class RequestHandler {

    /**
     * Обрабатывает запрос клиента, вызывая соответствующую команду из CommandManager.
     *
     * @param request         объект запроса
     * @param commandManager  менеджер команд
     * @return объект ответа с результатом выполнения команды
     */
    public static Response handle(Request request, CommandManager commandManager) {
        try {
            Command<?> command = commandManager.getCommand(request.commandName());
            Object argument = request.argument();

            if ("update".equals(request.commandName()) && argument instanceof Map<?, ?> map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> typedMap = (Map<String, Object>) map;
                @SuppressWarnings("unchecked")
                Command<Map<String, Object>> updateCommand = (Command<Map<String, Object>>) command;
                return updateCommand.execute(typedMap);
            }

            @SuppressWarnings("unchecked")
            Command<Object> genericCommand = (Command<Object>) command;
            return genericCommand.execute(argument);

        } catch (CommandException e) {
            return new Response("Ошибка выполнения команды: " + e.getMessage(), false);
        } catch (Exception e) {
            return new Response("Внутренняя ошибка сервера: " + e.getMessage(), false);
        }
    }
}
