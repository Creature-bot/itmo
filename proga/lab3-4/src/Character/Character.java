package Character;

import Enum.*;

public abstract class Character {
    private String name;
    private int age;
    private Gender gender;
    private Room room;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public Room getRoom() {
        return room;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean equals(Object o) {
        return ((Character) o).getGender().equals(getGender()) &&
                ((Character) o).getName().equals(getName()) &&
                ((Character) o).getAge() == getAge() &&
                ((Character) o).getRoom() == getRoom() &&
                getClass().equals(o.getClass());
    }

    @Override
    public int hashCode() {
        int result = 17; // Начальное значение
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + getClass().hashCode(); // Для учета класса
        return result;
    }

    @Override
    public String toString() {
        return "Это человек по имени: " + getName() + ", пола: " + getGender() + ", возраста: " + getAge() +
                ", ушедший когда-то в" + getRoom().getRoomName();
    }

    public abstract void Breathe();
}
