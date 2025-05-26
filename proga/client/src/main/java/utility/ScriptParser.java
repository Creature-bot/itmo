package utility;

import exceptions.ParserFileException;
import exceptions.ParserNoFileException;
import java.io.*;
import java.util.ArrayList;

/**
 * Класс ScriptParser отвечает за чтение скриптов команд из файла на стороне клиента.
 */
public class ScriptParser {

    /**
     * Считывает команды из файла по указанному пути.
     *
     * @param path путь к файлу со скриптом
     * @return список строк-команд
     * @throws ParserFileException если произошла ошибка чтения файла
     */
    public static ArrayList<String> parseFromCommand(String path) throws ParserFileException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path)))) {
            final ArrayList<String> commands = new ArrayList<>();
            while (reader.ready()) {
                commands.add(reader.readLine());
            }
            return commands;
        } catch (FileNotFoundException e) {
            throw new ParserNoFileException("Файл не найден");
        } catch (IOException e) {
            throw new ParserFileException(String.format("Ошибка чтения файла: %s", e.getMessage()));
        }
    }
}