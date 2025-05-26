package net;

public class Printer {
    /**
     * Метод, отвечающий за вывод текста в консоль.
     *
     * @param message текст вывода в консоль
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Метод, отвечающий за вывод текста ошибок в консоль.
     *
     * @param message текст ошибки
     */
    public static void printError(String message) {
        System.out.printf("Err: %s\n", message);
    }
}
