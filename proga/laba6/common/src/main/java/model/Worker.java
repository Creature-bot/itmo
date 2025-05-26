package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import exceptions.FieldSetterException;


public class Worker implements Comparable<Worker>, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Значение поля должно быть больше 0.
     * Значение этого поля должно быть уникальным.
     * Значение этого поля должно генерироваться автоматически.
     */
    private final long id;
    /**
     * Поле не может быть null, Строка не может быть пустой.
     */
    private String name;
    /**
     * Поле не может быть null.
     */
    private Coordinates coordinates;
    /**
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически.
     */
    private final ZonedDateTime creationDate;
    /**
     * Значение поля должно быть больше 0.
     */
    private long salary;
    /**
     * Поле не может быть null.
     */
    private ZonedDateTime endDate;
    /**
     * Поле не может быть null.
     */
    private Position position;
    /**
     * Поле не может быть null.
     */
    private Status status;
    /**
     * Поле не может быть null.
     */
    private Organization organization;

    /**
     * Конструктор класса, отвечающий за создание экземпляра класса Worker.
     */
    public Worker() {
        this.id = Math.abs(UUID.randomUUID().getMostSignificantBits());
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Метод getter, возвращающий значение поля id.
     *
     * @return Возвращает значение поля id
     */
    public long getId() {
        return id;
    }

    /**
     * Метод getter, возвращающий значение поля name.
     *
     * @return Возвращает значение поля name
     */
    public String getName() {
        return name;
    }

    /**
     * Метод getter, возвращающий значение поля endDate.
     *
     * @return значение поля endDate
     */
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    /**
     * Метод getter, возвращающий значение поля position.
     *
     * @return значение поля position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Метод getter, возвращающий значение поля organization.
     *
     * @return значение поля organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Метод getter, возвращающий значение поля status.
     *
     * @return значение поля status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Метод setter, меняющий значение поля name.
     *
     * @param name новое значение поля name
     * @throws FieldSetterException ошибка поля
     */
    public void setName(String name) throws FieldSetterException {
        if (name.isEmpty()) {
            throw new FieldSetterException("Имя не может быть пустым");
        }
        this.name = name;
    }

    /**
     * Метод setter, меняющий значение поля Coordinates {@link Coordinates}.
     *
     * @param coordinates новое значение поля name
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Метод setter, меняющий значение поля salary.
     *
     * @param salary новое значение поля salary
     * @throws FieldSetterException ошибка поля
     */
    public void setSalary(long salary) throws FieldSetterException {
        if (salary < 0) {
            throw new FieldSetterException("Значение должно быть больше нуля");
        }
        this.salary = salary;
    }

    /**
     * Метод setter, меняющий значение поля endDate.
     *
     * @param endDate новое значение поля endDate
     */
    public void setEndDate(ZonedDateTime endDate) throws FieldSetterException {
        if (endDate.isBefore(creationDate)) {
            throw new FieldSetterException("Значение не может быть меньше creationDate");
        }
        this.endDate = endDate;
    }

    /**
     * Метод setter, меняющий значение поля position.
     *
     * @param position новое значение поля position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Метод setter, меняющий значение поля status.
     *
     * @param status новое значение поля status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Метод setter, меняющий значение поля organization.
     *
     * @param organization новое значение поля organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int compareTo(Worker worker) {
        return Long.compare(this.salary, worker.salary);
    }

    @Override
    public String toString() {
        return "Worker{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", salary=" + salary
                + ", endDate=" + endDate
                + ", position=" + position
                + ", status=" + status
                + ", organization=" + organization
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Worker that = (Worker) o;
        return salary == that.salary
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(coordinates, that.coordinates)
                && Objects.equals(creationDate, that.creationDate)
                && Objects.equals(endDate, that.endDate)
                && position == that.position && status == that.status
                && Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary,
                endDate, position, status, organization);
    }
}
