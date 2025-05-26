package model;

import exceptions.FieldSetterException;
import java.io.Serializable;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Поле годового оборота Organization {@link Organization}
     * Значение поля должно быть больше 0
     */
    private int annualTurnover;
    /**
     * Поле количества сотрудников Organization {@link Organization}
     * Значение поля должно быть больше 0
     */
    private int employeesCount;
    /**
     * Поле типа Organization {@link Organization}
     * Поле может быть null
     */
    private OrganizationType type;

    public OrganizationType getType() {
        return type;
    }

    public int getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Метод setter, меняющее поле annualTurnover.
     *
     * @param annualTurnover годовой оборот
     * @throws FieldSetterException ошибка сеттера
     */
    public void setAnnualTurnover(int annualTurnover) throws FieldSetterException {
        if (annualTurnover <= 0) {
            throw new FieldSetterException("Годовой оборот не может быть отрицательным");
        }
        this.annualTurnover = annualTurnover;
    }

    /**
     * Метод setter, меняющее поле employeesCount.
     *
     * @param employeesCount количество сотрудников
     * @throws FieldSetterException ошибка сеттера
     */
    public void setEmployeesCount(int employeesCount) throws FieldSetterException {
        if (employeesCount <= 0) {
            throw new FieldSetterException("Количество сотрудников не может быть отрицательным");
        }
        this.employeesCount = employeesCount;
    }

    /**
     * Метод setter, меняющий значение поля type.
     *
     * @param type новое значение поля type
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Organization{"
                + "annualTurnover='" + annualTurnover + '\''
                + ", employeesCount=" + employeesCount
                + ", type=" + type + '\''
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
        final Organization organization = (Organization) o;
        return type == organization.type
                && Objects.equals(annualTurnover, organization.annualTurnover)
                && Objects.equals(employeesCount, organization.employeesCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annualTurnover, employeesCount, type);
    }
}