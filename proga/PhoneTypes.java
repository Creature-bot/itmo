package Enum;

public enum PhoneTypes {
    PHONE("телефон"),
    SMARTPHONE("смартфон");

    private final String whatPhone;

    public String getWhatPhone() {
        return whatPhone;
    }

    PhoneTypes(String whatPhone) {
        this.whatPhone = whatPhone;
    }
}
