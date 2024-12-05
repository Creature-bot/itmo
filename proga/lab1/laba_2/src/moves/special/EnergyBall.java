package moves.special;

import ru.ifmo.se.pokemon.*;

public class EnergyBall extends SpecialMove{
    public EnergyBall(){
        super(Type.GRASS, 90, 100);
    }
    private boolean flag;
    @Override
    protected void applyOppEffects(Pokemon p){
        if (Math.random() <= 0.1){
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
            flag = true;
        }
    }
    @Override
    protected String describe(){
        if (flag) return "юзает шаровую молнию и снижает защиту жертвы";
        else return "юзает шаровую молнию";
    }
}