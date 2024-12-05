package moves.special;

import ru.ifmo.se.pokemon.*;

public class FocusEnergy extends SpecialMove {
    public FocusEnergy(){
        super(Type.NORMAL, 0, 0);
    }
    protected String describe(){
        return "юзает баф";
    }
}