package Character;

import Enum.*;
import Interface.Afraid;

public class Gunilla extends People implements Afraid {
    public Gunilla() {
        super("Гунилла", 15, Gender.FEMALE, Room.KITCHEN);
    }

    @Override
    public void afraid() {
        System.out.print(getName() + " испугалась. ");
    }
}