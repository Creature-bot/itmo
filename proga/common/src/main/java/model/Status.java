package model;

public enum Status {
    FIRED("Уволен"),
    PROBATION("Стажер"),
    HIRED("Наемный рабочий"),
    RECOMMENDED_FOR_PROMOTION("Рекомендован на повышение");
    /**
     * Поле имени (описания).
     */
    private final String name;

    /**
     * Конструктор класса, отвечающий за создание нового экземпляра класса Status.
     *
     * @param name имя (описание)
     */
    Status(String name) {
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