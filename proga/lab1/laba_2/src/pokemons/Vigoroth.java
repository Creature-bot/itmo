package pokemons;

import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;

    public class Vigoroth extends Slakoth{
    public Vigoroth(String name, int level){
        super(name,level);
        setStats(80,80,80,55,55,90);
        setType(Type.NORMAL);
        addMove(new FocusEnergy());
    }
}