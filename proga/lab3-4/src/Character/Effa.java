package Character;

import Enum.*;

public class Effa extends People {
    public Effa() {
        super("Еффа", 2, Gender.FEMALE ,Room.KITCHEN);
    }
    public void bark(){
        System.out.println(getName() + " громко загавкала на дверь.");
    }
}