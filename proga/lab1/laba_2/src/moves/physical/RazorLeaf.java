package moves.physical;

import ru.ifmo.se.pokemon.*;

public class RazorLeaf extends PhysicalMove {
    public RazorLeaf() {
        super(Type.GRASS, 55, 95);
    }
    @Override
    protected String describe(){
        return "наносит удар";
    }
}