package pokemons;

import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;

public class Slakoth extends Pokemon {
    public Slakoth(String name, int level){
        super(name,level);
        setStats(60,60,60,35,35,30);
        setType(Type.NORMAL);
        setMove(new WorkUp(), new ShadowClaw());
    }
}