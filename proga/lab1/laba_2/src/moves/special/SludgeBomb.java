package moves.special;

import ru.ifmo.se.pokemon.*;

public class SludgeBomb extends SpecialMove {
    public SludgeBomb() {
        super(Type.POISON, 90, 100);
    }
    private boolean flag;
    @Override
    protected void applyOppEffects(Pokemon def){
        if (Math.random() <= 0.3){
            flag = true;
            Effect.poison(def);
        }
    }
    @Override
    protected String describe(){
        if (flag) return "наносит удар и травит жертву";
        else return "наносит удар";
    }
}