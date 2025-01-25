package Exception;

public class LocationException extends Exception {
    public LocationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Ошибка: " + super.getMessage();
    }
}
