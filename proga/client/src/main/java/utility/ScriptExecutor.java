package utility;

import net.Printer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Выполняет команды из скрипта. Обрабатывает рекурсивные вызовы.
 */
public class ScriptExecutor {
    private static final Set<String> executingScripts = new HashSet<>();

    /**
     * Выполняет команды из файла-скрипта.
     *
     * @param path путь к скрипту
     * @param commandProcessor обработчик строк команд (например, client::processCommandLine)
     */
    public static void executeScript(String path, Consumer<String> commandProcessor) {
        if (executingScripts.contains(path)) {
            Printer.printMessage("Обнаружена рекурсия: скрипт уже выполняется: " + path);
            return;
        }

        executingScripts.add(path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    commandProcessor.accept(trimmed);
                }
            }
        } catch (IOException e) {
            Printer.printMessage("Ошибка при чтении скрипта: " + e.getMessage());
        } finally {
            executingScripts.remove(path);
        }
    }
}
