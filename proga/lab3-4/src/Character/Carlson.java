package Character;

import Enum.*;
import Interface.Afraid;

import java.util.concurrent.ThreadLocalRandom;

public class Carlson extends People implements Afraid {
    public Carlson() {
        super("Карлсон", 33, Gender.MALE, Room.HALLWAY);
    }

    public void fly() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
        if (randomNum == 1) System.out.println(getName() + " поднялся в воздух");
            else System.out.println(getName() + " раскрутил пропеллер");
    }

    @Override
    public void afraid() {
        System.out.println(getName() + " ничуть не испугался.");
    }
}