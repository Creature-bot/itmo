package lab5.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lab5.exceptions.ParserFileException;
import lab5.exceptions.ParserNoFileException;
import lab5.manager.CollectionManager;
import java.io.*;
import java.util.ArrayList;

public class Parser {

    public static String path = "";

    public static ArrayList<String> parseFromCommand(String path) throws ParserFileException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path)))) {
            final ArrayList<String> commands = new ArrayList<>();
            while (reader.ready()) {
                commands.add(reader.readLine());
            }
            return commands;
        } catch (FileNotFoundException e) {
            throw new ParserNoFileException("файл не найден");
        } catch (IOException e) {
            throw new ParserFileException(String.format("ошибка чтения файла: %s", e.getMessage()));
        }
    }

    /**
     * Сериализует объект класса Manager.
     *
     * @param manager экземпляр класса Manager, который нужно сохранить в файл.
     * @throws ParserFileException если произошла ошибка записи в файл.
     * @throws JsonProcessingException если произошла ошибка сериализации.
     */
    public static void parseToFileManager(CollectionManager manager)
            throws ParserFileException, JsonProcessingException {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(path, false))) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.findAndRegisterModules();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            final String jsonString = mapper.writeValueAsString(manager);
            writer.write(jsonString);
        } catch (JsonProcessingException e) {
            throw new ParserFileException(String.format("ошибка сериализации в JSON: файл неверного формата: %s",
                    e.getMessage()));
        } catch (IOException e) {
            throw new ParserFileException(
                    String.format("ошибка записи в файл: %s", e.getMessage()));
        }
    }

    /**
     * Десериализует объект класса Manager.
     *
     * @return объект класса Manager.
     * @throws ParserFileException если произошла ошибка десериализации или чтения файла.
     */
    public static CollectionManager parseFromFileManager() throws ParserFileException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path)))) {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.findAndRegisterModules();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            final StringBuilder result = new StringBuilder();
            while (reader.ready()) {
                result.append(reader.readLine());
            }
            return mapper.readValue(result.toString(), CollectionManager.class);
        } catch (FileNotFoundException e) {
            throw new ParserNoFileException("файл отсутствует");
        } catch (JsonProcessingException e) {
            throw new ParserFileException("Ошибка десериализации JSON: файл неверного формата");
        } catch (IOException e) {
            throw new ParserFileException(String.format("Ошибка чтения файла: %s", e.getMessage()));
        }
    }
}
