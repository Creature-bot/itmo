package manager;

import exceptions.ParserFileException;

public class InitializationManager {

    /**
     * Инициализирует CollectionManager: либо загружает из файла, либо создаёт пустой.
     */
    public static CollectionManager initialize() {
        String path = System.getenv("LABA_FILE");
        if (path == null || path.isBlank()) {
            System.err.println("Ошибка: переменная окружения LABA_FILE не задана.");
            System.exit(1);
        }

        CollectionManager collectionManager;
        try {
            CollectionParser.path = path;
            collectionManager = CollectionParser.parseFromFileManager(path);
            System.out.println("Коллекция успешно загружена из файла: " + path);
        } catch (ParserFileException e) {
            System.err.println("Не удалось загрузить коллекцию: " + e.getMessage());
            System.err.println("Будет использована новая пустая коллекция.");
            collectionManager = new CollectionManager();
        }

        return collectionManager;
    }
}
