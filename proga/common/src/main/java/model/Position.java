package model;

public enum Position {
    CLEANER("Уборщик"),
    ENGINEER("Инженер"),
    MANAGER("Менеджер");
    /**
     * Поле имени (описания).
     */
    private final String name;

    /**
     * Конструктор класса, отвечающий за создание нового экземпляра класса Position.
     *
     * @param name имя (описание)
     */
    Position(String name) {
        this.name = name;
    }

    /**
     * Метод getter возвращает имя (описание) команды.
     *
     * @return имя (описание) команды
     */
    public String getName() {
        return name;
    }
}