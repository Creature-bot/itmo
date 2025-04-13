package lab5.parser;

import lab5.Printer;
import lab5.model.*;
import lab5.exceptions.ShaperException;
import lab5.exceptions.ShaperPrimitiveDataException;
import lab5.exceptions.FieldSetterException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Shaper {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Метод для создания и заполнения полей Organization {@link Organization}.
     *
     * @return объект класса Organization
     * @throws ShaperException если произошла ошибка создания
     */
    public static Organization buildOrganization() throws ShaperException {
        final Organization build = new Organization();
        boolean flag = false;

        Printer.printMessage("Введите годовой оборот организации:");
        for (int i = 0; i < 2; i++) {
            try {
                build.setAnnualTurnover(getInt());
                flag = true;
                break;
            } catch (ShaperPrimitiveDataException | FieldSetterException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        flag = false;


        Printer.printMessage("Введите количество сотрудников в организации:");
        for (int i = 0; i < 2; i++) {
            try {
                build.setEmployeesCount(getInt());
                flag = true;
                break;
            } catch (ShaperPrimitiveDataException | FieldSetterException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        flag = false;


        Printer.printMessage("Выберите тип организации (или оставьте пустым):");
        for (OrganizationType type : OrganizationType.values()) {
            Printer.printMessage(type.name() + " - " + type.getName());
        }

        String typeInput = scanner.nextLine().strip();
        if (!typeInput.isEmpty()) {
            try {
                build.setType(OrganizationType.valueOf(typeInput.toUpperCase()));
            } catch (IllegalArgumentException e) {
                Printer.printError("Ошибка: Неверный тип организации. Поле останется null.");
            }
        }

        return build;
    }

    /**
     * Метод, необходимый для создания и заполнения полей класса Worker {@link Worker}.
     *
     * @return объект класса Worker
     * @throws ShaperException если произошла ошибка создания
     */
    public static Worker buildWorker() throws ShaperException {
        final Worker build = new Worker();
        boolean flag = false;

        Printer.printMessage("Введите имя работника:");
        for (int i = 0; i < 2; i++) {
            final String nameBuild = scanner.nextLine().strip();
            try {
                build.setName(nameBuild);
                flag = true;
                break;
            } catch (FieldSetterException e) {
                Printer.printError(String.format("%s\nПовторите ввод:", e.getMessage()));
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }
        flag = false;
        Printer.printMessage("Введите координаты работника:");
        build.setCoordinates(buildCoordinates());

        Printer.printMessage("Введите зарплату работника:");
        for (int i = 0; i < 2; i++) {
            try {
                build.setSalary(Integer.parseInt(scanner.nextLine()));
                flag = true;
                break;
            } catch (NumberFormatException | FieldSetterException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        Printer.printMessage("Введите дату окончания работы (в формате \"ДД-MM-ГГГГ ЧЧ:ММ Z\"):");
        flag = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm z");

        for (int i = 0; i < 2; i++) {
            try {
                String input = scanner.nextLine();
                ZonedDateTime endDate = ZonedDateTime.parse(input, formatter);
                build.setEndDate(endDate);
                flag = true;
                break;
            } catch (DateTimeParseException e) {
                Printer.printError("Ошибка: неверный формат даты. Используйте формат " +
                        "\"ДД-MM-ГГГГ ЧЧ:ММ Z\", например, \"28-03-2025 14:30 UTC\"");
            } catch (FieldSetterException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимое значение endDate");
        }

        Printer.printMessage("Выберите должность работника:");
        for (Position position : Position.values()) {
            Printer.printMessage(position.name() + " - " + position.getName());
        }
        flag = false;
        for (int i = 0; i < 2; i++) {
            try {
                build.setPosition(Position.valueOf(scanner.nextLine().toUpperCase()));
                flag = true;
                break;
            } catch (IllegalArgumentException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        Printer.printMessage("Выберите статус работника:");
        for (Status status : Status.values()) {
            Printer.printMessage(status.name() + " - " + status.getName());
        }
        flag = false;
        for (int i = 0; i < 2; i++) {
            try {
                build.setStatus(Status.valueOf(scanner.nextLine().toUpperCase()));
                flag = true;
                break;
            } catch (IllegalArgumentException e) {
                Printer.printError("Ошибка: некорректный статус. Повторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимое значение статуса");
        }


        Printer.printMessage("Заполним данные организации:");
        build.setOrganization(buildOrganization());

        return build;
    }

    /**
     * Метод для создания и заполнения полей Coordinates {@link Coordinates}.
     *
     * @return объект класса Coordinates
     * @throws ShaperException если произошла ошибка создания
     */
    public static Coordinates buildCoordinates() throws ShaperException {
        final Coordinates build = new Coordinates();
        boolean flag = false;

        // Ввод координаты X
        Printer.printMessage("Введите координату X:");
        for (int i = 0; i < 2; i++) {
            try {
                build.setXvalue(getInt());
                flag = true;
                break;
            } catch (ShaperPrimitiveDataException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        flag = false;

        // Ввод координаты Y
        Printer.printMessage("Введите координату Y (не больше 456):");
        for (int i = 0; i < 2; i++) {
            try {
                build.setYvalue(getInt());
                flag = true;
                break;
            } catch (ShaperPrimitiveDataException | FieldSetterException e) {
                Printer.printError("Ошибка: " + e.getMessage() + "\nПовторите ввод:");
            }
        }

        if (!flag) {
            throw new ShaperException("Недопустимые значения полей");
        }

        return build;
    }

    /**
     * Метод для парсинга ввода из консоли в целочисленное число.
     *
     * @return целочисленное число
     * @throws ShaperPrimitiveDataException ввод с консоли не является числом
     */
    private static int getInt() throws ShaperPrimitiveDataException {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ShaperPrimitiveDataException("Ошибка парсинга: " + e.getMessage());
        }
    }


    /**
     * Метод для парсинга ввода из консоли в число с плавающей запятой.
     *
     * @return число с плавающей запятой число
     * @throws ShaperPrimitiveDataException ввод с консоли не является числом
     */
    private static float getFloat() throws ShaperPrimitiveDataException {
        try {
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ShaperPrimitiveDataException(String.format("Ошибка парсинга:"
                    + " %s\nПовторите ввод:\n", e.getMessage()));
        }
    }

    /**
     * Метод для парсинга ввода из консоли в целочисленное число (long).
     *
     * @return целочисленное число
     * @throws ShaperPrimitiveDataException ввод с консоли не является числом
     */
    private static long getLong() throws ShaperPrimitiveDataException {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ShaperPrimitiveDataException(String.format("Ошибка парсинга: "
                    + "%s\nПовторите ввод:\n", e.getMessage()));
        }
    }
}