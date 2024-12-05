package moves.special;

import ru.ifmo.se.pokemon.*;

public class MudSlap extends SpecialMove {
    public MudSlap() {
        super(Type.GROUND, 20, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon att){
        att.setMod(Stat.ACCURACY, -1);
    }
    @Override
    protected String describe(){
        return "наносит удар и минусует прицел";
    }
}