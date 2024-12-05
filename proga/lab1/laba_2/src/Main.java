import pokemons.*;
import moves.physical.*;
import moves.special.*;
import moves.status.*;
import ru.ifmo.se.pokemon.*;


public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Shuckle("Шука", 1);
        Pokemon p2 = new Sunkern("Саня", 1);
        Pokemon p3 = new Sunflora("Саша", 1);
        Pokemon p4 = new Slakoth("Лакоста", 1);
        Pokemon p5 = new Vigoroth("Витя", 1);
        Pokemon p6 = new Slaking("Слаки", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}