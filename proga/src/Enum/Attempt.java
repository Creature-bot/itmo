package Enum;

public enum Attempt {
    STEEL_WIRE(" просовывает стальную проволоку в щель ящика для писем."),
    KICKING_THE_DOOR(" бьет ногами в дверь, пытаясь ее выбить снаружи."),
    PULL_THE_DOOR_HANDLE(" нажимал на дверную ручку снаружи и она дергалась внутри.");

    private final String whichAttempt;

    public String getWhichAttempt() {
        return whichAttempt;
    }

    Attempt(String whichAttempt) {
        this.whichAttempt = whichAttempt;
    }
}
