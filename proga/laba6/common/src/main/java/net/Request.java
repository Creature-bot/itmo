package net;

import java.io.Serializable;

/**
 * Класс, представляющий запрос клиента к серверу.
 * Содержит имя команды и сериализуемый аргумент команды.
 */
public record Request(String commandName, Object argument) implements Serializable {
}