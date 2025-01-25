package Character;

import Enum.*;

public class People extends Character{
    protected People(String name, int age, Gender gender, Room room) {
        setName(name);
        setAge(age);
        setGender(gender);
        setRoom(room);
    }

    @Override
    public void Breathe() {
    }
}
