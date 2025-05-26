package manager;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import exceptions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * Класс CollectionParser отвечает за сериализацию и десериализацию CollectionManager в JSON.
 */
public class CollectionParser {

    public static String path = "";

    /**
     * Сохраняет объект CollectionManager в файл в формате JSON.
     *
     * @param manager объект менеджера коллекции
     * @throws ParserFileException если произошла ошибка при сохранении
     */
    public static void parseToFileManager(CollectionManager manager)
            throws ParserFileException {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(path, false))) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.findAndRegisterModules();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            final String jsonString = mapper.writeValueAsString(manager);
            writer.write(jsonString);
        } catch (JsonProcessingException e) {
            throw new ParserFileException(String.format("Ошибка сериализации в JSON: %s", e.getMessage()));
        } catch (IOException e) {
            throw new ParserFileException(String.format("Ошибка записи в файл: %s", e.getMessage()));
        }
    }

    /**
     * Загружает объект CollectionManager из файла JSON.
     *
     * @return десериализованный объект CollectionManager
     * @throws ParserFileException если произошла ошибка при чтении или десериализации
     */
    public static CollectionManager parseFromFileManager(String path) throws ParserFileException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.findAndRegisterModules();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

            String json = Files.readString(Path.of(path));
            if (json == null || json.trim().isEmpty()) {
                return new CollectionManager();
            }
            return mapper.readValue(json, CollectionManager.class);
        } catch (NoSuchFileException e) {
            throw new ParserNoFileException("Файл отсутствует");
        } catch (JsonProcessingException e) {
            throw new ParserFileException("Ошибка десериализации JSON: файл неверного формата");
        } catch (IOException e) {
            throw new ParserFileException("Ошибка чтения файла: " + e.getMessage());
        }
    }

}