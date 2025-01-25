package Other;

import Character.*;
import Enum.Room;
import Interface.*;
import Exception.*;
import java.util.List;
import java.util.stream.Collectors;

public class FearManager {
    public void invokeFear(List<People> people) {
        boolean dogPresentInHallway = people.stream()
                .anyMatch(person -> person instanceof Effa && person.getRoom() == Room.HALLWAY);

        List<String> namesInHallway = people.stream()
                .filter(person -> person instanceof Afraid && person.getRoom() == Room.HALLWAY)
                .map(People::getName)
                .collect(Collectors.toList());

        if (dogPresentInHallway) {
            for (People person : people) {
                if (person instanceof Effa && person.getRoom() == Room.HALLWAY) {
                    ((Effa) person).bark();
                    break;
                }
            }
            System.out.println(String.join(", ", namesInHallway)
                    + " чувствовали себя уверено рядом с собакой и не испугались.");
        } else {
            for (People person : people) {
                if (person instanceof Afraid && person.getRoom() == Room.HALLWAY) {
                    ((Afraid) person).afraid();
                }
            }
        }
    }
}