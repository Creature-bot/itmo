package Enum;

public enum Room {
    HALLWAY(" прихожая"),
    KITCHEN(" кухня"),
    BEDROOM(" спальня"),
    OUTSIDE(" снаружи");

    private final String roomName;

    Room(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return roomName;
    }
    public String toString(){
        return roomName;
    }
}
