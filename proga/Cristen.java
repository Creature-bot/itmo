package Character;

import Enum.*;
import Interface.Action;
import Interface.Afraid;
import Exception.LocationException;

public class Cristen extends People implements Afraid, Action {
    public Cristen() {
        super("Кристен", 14, Gender.MALE, Room.HALLWAY);
    }

    @Override
    public void afraid() {
        System.out.print(getName() + " испугался. ");
    }

    @Override
    public void action(People toPeople) {
        if (toPeople instanceof Effa effa) {
            try {
                if (effa.getRoom() == Room.HALLWAY) {
                    throw new LocationException(effa.getName() + " находится в " + effa.getRoom().getRoomName() + "!");
                } else {
                    System.out.println(getName() + " запер " + toPeople.getName() + " в " + toPeople.getRoom().getRoomName()
                            + ", чтобы " + toPeople.getName() + " своим лаем не испортила игру в привидения, и "
                            + getName() + " теперь очень пожалел об этом.");
                }
            } catch (LocationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(getName() + " не знает, как взаимодействовать с " + toPeople.getName() + ".");
        }
    }
}
