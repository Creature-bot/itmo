package moves.physical;

import ru.ifmo.se.pokemon.*;

public class ShadowClaw extends PhysicalMove{
    public ShadowClaw(){
        super(Type.GHOST, 70, 100);
    }
    @Override
    protected String describe(){
        return "наносит удар";
    }
}