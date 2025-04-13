package lab5.model;

import lab5.exceptions.FieldSetterException;
import java.util.Objects;

/**
 * Класс Coordinates {@link Coordinates}.
 */
public class Coordinates {
    private static final float MAX_Y_VALUE = 456;
    /**
     * Поле не может быть null.
     */
    private Integer xvalue;
    /**
     * Максимальное значение поля: 456. Поле не может быть null.
     */
    private Integer yvalue;
    /**
     * Метод setter, меняющий поле x.
     *
     * @param xvalue новое значение поле
     */
    public void setXvalue(Integer xvalue) {
        this.xvalue = xvalue;
    }
    /**
     * Метод setter, меняющий поле y.
     *
     * @param yvalue новое значение поле
     */
    public void setYvalue(Integer yvalue) throws FieldSetterException {
        if (yvalue > MAX_Y_VALUE) {
            throw new FieldSetterException("Максимальное значение поля: 456");
        }
        this.yvalue = yvalue;
    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "x=" + xvalue
                + ", y=" + yvalue
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
        final Coordinates that = (Coordinates) o;
        return Float.compare(that.yvalue, yvalue) == 0 && Objects.equals(xvalue, that.xvalue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xvalue, yvalue);
    }
}
