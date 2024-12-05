package pokemons;

import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;

public class Sunkern extends Pokemon {
    public Sunkern(String name, int level){
        super(name,level);
        setStats(30,30,30,30,30,30);
        setType(Type.GRASS);
        setMove(new EnergyBall(), new Facade(), new RazorLeaf());
    }
}