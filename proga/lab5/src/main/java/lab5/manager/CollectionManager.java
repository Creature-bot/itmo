package lab5.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import lab5.model.Status;
import lab5.model.Worker;
import lab5.exceptions.ManagerStorageException;
import lab5.parser.Parser;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;


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
        return String.format("Место сохранения - %s\nВремя создания - %s\nКоличество работников - %s",
                Parser.path, timeCreated, storage.size());
    }

    /**
     * Метод, реализующий вывод всех работников, т.е. команду show.
     *
     * @return строка результата (список всех работников)
     */
    public String show() {
        final StringBuilder result = new StringBuilder();
        if (!storage.isEmpty()) {
            for (Worker studyGroup : storage) {
                result.append(studyGroup.toString()).append("\n");
            }
        } else {
            result.append("Память пуста!\n");
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * Метод, реализующий добавление работника, т.е. команду add.
     *
     * @return строка результата
     */
    public String add(Worker worker) {
        storage.add(worker);
        return String.format("Работник %s был добавлен!", worker.getName());
    }

    /**
     * Метод, реализующий добавление работника в случае, если он максимален.
     *
     * @return строка результата
     * @throws ManagerStorageException данный работник не максимальный
     */
    public String addIfMax(Worker worker) throws ManagerStorageException {
        if (storage.isEmpty() || worker.compareTo(this.storage.stream().max(Worker::compareTo).get()) > 0) {
            this.storage.add(worker);
            return String.format("Работник %s добавлен!", worker.getName());
        } else {
            throw new ManagerStorageException(String.format("Работник %s не был добавлен: " +
                    "работник не максимален", worker.getName()));
        }
    }

    /**
     * Метод, реализующий добавление работника в случае, если он минимален.
     *
     * @return строка результата
     * @throws ManagerStorageException данный работник не минимальный
     */
    public String addIfMin(Worker worker) throws ManagerStorageException {
        if (storage.isEmpty() || worker.compareTo(this.storage.stream().min(Worker::compareTo).get()) < 0) {
            this.storage.add(worker);
            return String.format("Работник %s добавлен!", worker.getName());
        } else {
            throw new ManagerStorageException(String.format("Работник %s не был добавлен: " +
                    "работник не минимален", worker.getName()));
        }
    }

    /**
     * Метод, реализующий удаление работника по id, добавление нового, те команду update.
     *
     * @param id уникальный id работника
     * @return строка результата
     * @throws ManagerStorageException ошибка удаление работника (данный id отсутствует)
     */
    public String update(int id, Worker worker) throws ManagerStorageException {
        if (!checkId(id)) {
            throw new ManagerStorageException("Работник с таким id не найден");
        }
        removeById(id);
        storage.add(worker);
        return String.format("Работник с id %s удален, %s добавлен!", id, worker.getName());
    }

    /**
     * Метод, реализующих удаление работника по id, т.е. команду remove_by_id.
     *
     * @param id уникальный id работника, которую нужно удалить
     * @return строка результата
     * @throws ManagerStorageException ошибка удаления работника
     */
    public String removeById(int id) throws ManagerStorageException {
        storage.remove(findId(id));
        return String.format("Работник с id %s удален!", id);
    }

    /**
     * Метод, реализующий очистку коллекции, т.е. команду clear.
     *
     * @return строка результата
     */
    public String clear() {
        storage.clear();
        return "Память очищена!";
    }

    /**
     * Метод, реализующий удаление минимального, т.е. команду remove_lower.
     *
     * @return строка результата
     */
    public String removeLower() throws ManagerStorageException {
        if (!storage.isEmpty()) {
            storage.remove(storage.stream().min(Worker::compareTo).get());
            return "Минимальный элемент был удален!";
        } else {
            throw new ManagerStorageException("Ошибка удаления: память пуста");
        }
    }

    /**
     * Метод, считающий работников, у которых status меньше заданного.
     *
     * @return количество таких работников
     */
    public int countLessThanStatus(Status givenStatus) {
        return (int) storage.stream()
                .filter(worker -> worker.getStatus().compareTo(givenStatus) < 0)
                .count();
    }

    /**
     * Метод, фильтрующий элементы с полем organization, меньшим заданного.
     *
     * @param turnover Значение организации для сравнения
     * @return строка с отфильтрованными работниками
     */
    public String filterLessThanOrganization(int turnover) {
        List<Worker> filtered = storage.stream()
                .filter(worker -> worker.getOrganization() != null)
                .filter(worker -> worker.getOrganization().getAnnualTurnover() < turnover)
                .toList();
        if (filtered.isEmpty()) {
            return "Нет работников с организацией, чей годовой оборот меньше " + turnover + ".";
        }
        return filtered.stream()
                .map(worker -> worker.getName() + ": " + worker.getOrganization().getAnnualTurnover())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Метод, реализующий команду print_field_descending_position.
     *
     * @return строку значений полей position объектов коллекции
     */
    public String printFieldDescendingPosition() {
        return storage.stream()
                .filter(worker -> worker.getPosition() != null)
                .sorted(Comparator.comparing(Worker::getPosition).reversed())
                .map(worker -> String.format("%s: %s", worker.getName(), worker.getPosition().name()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Метод поиска работника {@link Worker}.
     *
     * @param id уникальный id работника
     * @return учебную группу
     * @throws ManagerStorageException если произошла ошибка поиска работника
     */
    private Worker findId(int id) throws ManagerStorageException {
        for (Worker worker : storage) {
            if (worker.getId() == id) {
                return worker;
            }
        }
        throw new ManagerStorageException("в коллекции не существует данный <id>");
    }

    /**
     * Метод проверки id работника {@link Worker}.
     *
     * @param id уникальный id работника
     * @return наличие работника с id
     */
    public boolean checkId(int id) {
        for (Worker worker : storage) {
            if (worker.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
