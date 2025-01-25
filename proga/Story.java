import Character.*;
import Enum.*;
import Interface.*;
import Other.*;
import java.util.List;

public class Story {
    public static void main(String[] args) {
        Kid kid = new Kid();
        Carlson carlson = new Carlson();
        Cristen cristen = new Cristen();
        Gunilla gunilla = new Gunilla();
        Effa dog = new Effa();
        Burglar bug = new Burglar();

        List<People> people = List.of(kid, carlson, cristen, gunilla, dog, bug);

        Day day = new Day();
        day.initializePeople(people);
        day.setPeople(bug);

        kid.think(Parent.PARENTS);
        bug.hack(Attempt.PULL_THE_DOOR_HANDLE);
        kid.action(bug);
        Phone phone = new Phone(PhoneTypes.PHONE, "newModel");
        bug.phoneCall(phone);
        cristen.action(dog);
        FearManager fearManager = new FearManager();
        fearManager.invokeFear(people);
    }
}
