package manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import exceptions.ParserFileException;
import model.*;
import exceptions.ManagerStorageException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Менеджер коллекции объектов {@link Worker}.
 * Управляет операциями добавления, обновления, удаления и фильтрации.
 */
public class CollectionManager {
    /**
     * Поле даты создания менеджера {@link CollectionManager}.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timeCreated;
    /**
     * Поле коллекции.
     */
    private final LinkedHashSet<Worker> storage;

    /**
     * Конструктор отвечает за создание нового объекта класса Manager.
     */
    public CollectionManager() {
        this.storage = new LinkedHashSet<>();
        this.timeCreated = LocalDateTime.now();
    }

    /**
     * Метод, реализующий вывод информации о менеджере {@link CollectionManager}, т.е. команду info.
     *
     * @return строка результата (информация о коллекции)
     */
    public String info() {
        return String.format("Тип коллекции: %s%nВремя создания: %s%nКоличество элементов: %d",
                storage.getClass().getName(), timeCreated, storage.size());
    }

    /**
     * Метод, реализующий вывод всех работников, т.е. команду show.
     *
     * @return строка результата (список всех работников)
     */
    public String show() {
        if (storage.isEmpty()) {
            return "Коллекция пуста.";
        }
        return storage.stream()
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Метод, реализующий добавление работника, т.е. команду add.
     *
     * @return строка результата
     */
    public String add(Worker worker) {
        storage.add(worker);
        return "Работник успешно добавлен.";
    }

    /**
     * Метод, реализующий добавление работника в случае, если он максимален.
     *
     * @return строка результата
     * @throws ManagerStorageException данный работник не максимальный
     */
    public String addIfMax(Worker worker) throws ManagerStorageException {
        if (storage.isEmpty() || worker.compareTo(Collections.max(storage)) > 0) {
            storage.add(worker);
            return "Работник добавлен как максимальный.";
        }
        throw new ManagerStorageException("Работник не добавлен: он не является максимальным.");
    }

    /**
     * Метод, реализующий добавление работника в случае, если он минимален.
     *
     * @return строка результата
     * @throws ManagerStorageException данный работник не минимальный
     */
    public String addIfMin(Worker worker) throws ManagerStorageException {
        if (storage.isEmpty() || worker.compareTo(Collections.min(storage)) < 0) {
            storage.add(worker);
            return "Работник добавлен как минимальный.";
        }
        throw new ManagerStorageException("Работник не добавлен: он не является минимальным.");
    }

    /**
     * Метод, реализующий удаление работника по id, добавление нового, те команду update.
     *
     * @param id уникальный id работника
     * @return строка результата
     * @throws ManagerStorageException ошибка удаление работника (данный id отсутствует)
     */
    public String update(long id, Worker worker) throws ManagerStorageException {
        Worker old = findById(id);
        storage.remove(old);
        storage.add(worker);
        return "Работник обновлён.";
    }

    /**
     * Метод, реализующих удаление работника по id, т.е. команду remove_by_id.
     *
     * @param id уникальный id работника, которую нужно удалить
     * @return строка результата
     * @throws ManagerStorageException ошибка удаления работника
     */
    public String removeById(int id) throws ManagerStorageException {
        Worker found = findById(id);
        storage.remove(found);
        return "Работник успешно удалён.";
    }

    /**
     * Метод, реализующий очистку коллекции, т.е. команду clear.
     *
     * @return строка результата
     */
    public String clear() {
        storage.clear();
        return "Коллекция очищена.";
    }

    /**
     * Метод, реализующий удаление минимального, т.е. команду remove_lower.
     *
     * @return строка результата
     */
    public String removeLower() throws ManagerStorageException {
        if (storage.isEmpty()) {
            throw new ManagerStorageException("Удаление невозможно: коллекция пуста.");
        }
        Worker min = Collections.min(storage);
        storage.remove(min);
        return "Минимальный элемент удалён.";
    }

    /**
     * Метод, считающий работников, у которых status меньше заданного.
     *
     * @return количество таких работников
     */
    public int countLessThanStatus(Status status) {
        return (int) storage.stream()
                .filter(w -> w.getStatus() != null && w.getStatus().compareTo(status) < 0)
                .count();
    }

    /**
     * Метод, фильтрующий элементы с полем organization, меньшим заданного.
     *
     * @param type Значение организации для сравнения
     * @return строка с отфильтрованными работниками
     */
    public String filterLessThanOrganization(OrganizationType type) {
        List<Worker> filtered = storage.stream()
                .filter(w -> w.getOrganization() != null
                        && w.getOrganization().getType() != null
                        && w.getOrganization().getType().ordinal() < type.ordinal())
                .toList();

        if (filtered.isEmpty()) {
            return "Работников с меньшим типом организации не найдено.";
        }
        return filtered.stream()
                .map(w -> w.getName() + " — " + w.getOrganization().getType())
                .collect(Collectors.joining("\n"));
    }


    /**
     * Метод, реализующий команду print_field_descending_position.
     *
     * @return строку значений полей position объектов коллекции
     */
    public String printFieldDescendingPosition() {
        return storage.stream()
                .filter(w -> w.getPosition() != null)
                .sorted(Comparator.comparing(Worker::getPosition).reversed())
                .map(w -> w.getName() + ": " + w.getPosition())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Метод поиска работника {@link Worker}.
     *
     * @param id уникальный id работника
     * @return учебную группу
     * @throws ManagerStorageException если произошла ошибка поиска работника
     */
    private Worker findById(long id) throws ManagerStorageException {
        return storage.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ManagerStorageException("Работник с таким ID не найден."));
    }

    /**
     * Метод проверки id работника {@link Worker}.
     *
     * @param id уникальный id работника
     * @return наличие работника с id
     */
    public boolean checkId(long id) {
        return storage.stream().anyMatch(w -> w.getId() == id);
    }

    public void saveCollection() {
        try {
            CollectionParser.parseToFileManager(this);
        } catch (ParserFileException e) {
            throw new RuntimeException("Ошибка при сохранении коллекции: " + e.getMessage(), e);
        }
    }

    /**
     * @return текущая коллекция (только для сохранения или сериализации).
     */
    public LinkedHashSet<Worker> getStorage() {
        return storage;
    }
}
