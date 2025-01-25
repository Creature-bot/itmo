package Other;
import Enum.PhoneTypes;

public record Phone(PhoneTypes phoneType, String phoneModel) {

    @Override
    public boolean equals(Object o) {
        return getClass().equals(o.getClass()) &&
                phoneModel().equals(((Phone) o).phoneModel()) &&
                phoneType().equals(((Phone) o).phoneType());
    }
}
