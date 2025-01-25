package Other;

import Character.*;
import Enum.Room;
import Exception.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day {
    private boolean isFinish; {
        isFinish = false;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish() {
        if (!isFinish()) {
            isFinish = true;
            System.out.print("На все эти приготовления ушло немало времени. ");
        } else {
            System.out.print("Приготовления не заняли много времени. ");
        }
    }

    private List<People> whoInRoom = new ArrayList<>();

    public void initializePeople(List<People> people) {
        whoInRoom = people.stream()
                .filter(person -> person.getRoom() == Room.HALLWAY)
                .collect(Collectors.toList());
    }

    public void setPeople(People person) {
        try {
            if (whoInRoom.isEmpty()) {
                System.out.println("Никто еще не был готов, поэтому в коридоре было пусто. "
                        + "Но даже так дети что-то услышали.");
            } else {
                String names = whoInRoom.stream()
                        .map(People::getName)
                        .collect(Collectors.joining(", "));
                System.out.println("Когда " + names +
                        " уже стояли перед входной дверью и собирались выйти на лестничную площадку," +
                        "\nчтобы пугать прохожих, " + names + " что-то услышали.");
            }

            if (person instanceof Burglar burglar) {
                if (burglar.getRoom() != Room.OUTSIDE) {
                    throw new LocationException(burglar.getName() +
                            " должен быть снаружи, но находится в " + burglar.getRoom().getRoomName());
                }
                burglar.makesNoise();
            } else {
                throw new NotBurglarException(person.getName());
            }
        } catch (LocationException | NotBurglarException e) {
            System.out.println(e.getMessage());
        }
    }
}

