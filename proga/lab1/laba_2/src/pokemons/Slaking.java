package pokemons;

import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;

public class Slaking extends Vigoroth {
    public Slaking(String name, int level){
        super(name,level);
        setStats(150,160,100,95,65,100);
        setType(Type.NORMAL);
        setMove(new WorkUp(), new ShadowClaw(), new FocusEnergy(), new DoubleTeam());
    }
}