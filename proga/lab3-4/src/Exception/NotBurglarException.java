package Exception;

public class NotBurglarException extends Exception {
    private final String personName;

    public NotBurglarException(String personName) {
        super(personName + " не является взломщиком, не может издать шум.");
        this.personName = personName;
    }

    @Override
    public String getMessage() {
        return "Ошибка: " + personName + " не взломщик, а значит, не может шуметь.";
    }
}
