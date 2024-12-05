package moves.status;

import ru.ifmo.se.pokemon.*;

public class WorkUp extends StatusMove {
    public WorkUp(){
        super(Type.NORMAL, 0.0, 1.0);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        super.applySelfEffects(p);
        p.addEffect(new Effect()
                        .stat(Stat.SPECIAL_ATTACK, 1)
                        .stat(Stat.ATTACK, 1));
    }
    @Override
    protected String describe() {
        return "юзает баф";
    }
}