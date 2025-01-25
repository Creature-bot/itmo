package Character;

import Enum.*;
import Interface.*;
import Exception.*;

public class Kid extends People implements Afraid, Think, Action {
    public Kid() {
        super("Малыш", 10, Gender.MALE, Room.HALLWAY);
    }

    @Override
    public void afraid() {
        System.out.println(getName() +" страшно испугался, когда понял, что происходит.");
    }

    @Override
    public void think(Parent who) {
        System.out.println(getName() + " было подумал, что это" + who.getWhichParent()
                + " возвращаются домой. Но вдруг " + getName() + " увидел:");
    }

    @Override
    public void action(People toPeople) {
        try {
            if (this.getRoom() != Room.HALLWAY) {
                throw new LocationException(getName() +
                        " должен находиться в прихожей, чтобы увидеть, что происходит.");
            }

            System.out.println(getName() + " сразу понял, что к ним лезет " + toPeople.getName()
                    + ". ");
            System.out.println(getName() + " вспомнил, что на днях папа читал маме статью из газеты. " +
                    "Там говорилось, что в городе появился квартирный " + toPeople.getName() + ".");
        } catch (LocationException e) {
            System.out.println(e.getMessage());
        }
    }
}