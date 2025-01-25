package Enum;

public enum Gender {
    MALE("мужчина"),
    FEMALE("женщина");

    private final String genderName;

    Gender(String roomName) {
        this.genderName = roomName;
    }
    public String getGenderName() {
        return genderName;
    }
    public String toString(){
        return genderName;
    }
}
