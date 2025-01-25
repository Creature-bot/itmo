package Enum;

public enum Parent {
    PARENTS(" родители"),
    MOTHER(" мама"),
    FATHER(" папа");

    private final String whichParent;

    public String getWhichParent() {
        return whichParent;
    }

    Parent(String whichParent) {
        this.whichParent = whichParent;
    }
}
