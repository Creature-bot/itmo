package pokemons;

import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;

public class Shuckle extends Pokemon {
    public Shuckle(String name,int level){
        super(name,level);
        setStats(20,10,230,10,230,5);
        setType(Type.BUG, Type.ROCK);
        setMove(new RockSlide(), new SludgeBomb(), new Confide(), new MudSlap());
    }
}