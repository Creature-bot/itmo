package lab5.model;

public enum OrganizationType {
    COMMERCIAL("Коммерческий"),
    PUBLIC("Общественный"),
    TRUST("Доверенный");
    /**
     * Поле имени (описания).
     */
    private final String name;

    /**
     * Конструктор класс, отвечающий за создание экземпляра класса Semester.
     *
     * @param name имя (описание)
     */
    OrganizationType(String name) {
        this.name = name;
    }

    /**
     * Метод getter, отвечающий за возвращающий значение поля имени (описания).
     *
     * @return значение поля имени (описания)
     */
    public String getName() {
        return name;
    }
}
