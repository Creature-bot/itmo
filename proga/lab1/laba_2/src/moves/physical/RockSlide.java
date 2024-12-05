package moves.physical;

import ru.ifmo.se.pokemon.*;

public class RockSlide extends SpecialMove {
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }
    private boolean flag;
    @Override
    protected void applyOppEffects(Pokemon def){
        if (Math.random() <= 0.3){
            flag = true;
            Effect.flinch(def);
        }
    }
    @Override
    protected String describe(){
        if (flag) return "наносит удар и заставляет жертву подергиваться";
        else return "наносит удар";
    }
}