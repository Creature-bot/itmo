package moves.physical;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade(){
        super(Type.NORMAL, 70,100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        Status Pokemon_Condition = def.getCondition();
        if (Pokemon_Condition.equals(Status.BURN) ||
                Pokemon_Condition.equals(Status.POISON) ||
                Pokemon_Condition.equals(Status.PARALYZE)) {
            def.setMod(Stat.HP, (int) Math.round(damage) * 2);
        }
        def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected String describe() {
        return "юзает Facade и херачит с двойной силой";
    }
}