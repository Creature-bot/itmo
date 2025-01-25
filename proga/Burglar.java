package Character;

import Enum.*;
import Interface.*;
import Other.Phone;

import java.util.concurrent.ThreadLocalRandom;

public class Burglar extends People implements MakesNoise,Hack, PhoneCall, Action {
    public Burglar() {
        super("Взломщик", 40, Gender.MALE, Room.OUTSIDE);
    }

    @Override
    public void makesNoise() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        if (randomNum == 1) System.out.println(getName() +" царапает дверную ручку.");
        if (randomNum == 2) System.out.println(getName() +" пытается взломать замок.");
        else System.out.println(getName() +" шуршит в замочной скважине.");
    }

    @Override
    public void hack(Attempt attempt) {
        System.out.println(getName() + attempt.getWhichAttempt());
    }

    @Override
    public void phoneCall(Phone toPhone) {
        System.out.println(getName() + " сперва звонит на " + toPhone.phoneType().getWhatPhone() + ".");
        action(null);
    }

    @Override
    public void action(People toPeople) {
        if (toPeople == null) {
            System.out.println("Убедившись, что дома никого нет, " + getName() +
                    " взламывает замок и выносит из квартиры все ценное.");
        } else {
            System.out.println("Убедившись, что дома никого нет, " + getName() +
                    " взаимодействует с " + toPeople.getName() + " перед тем, как выносит из квартиры все ценное.");
        }
    }
}