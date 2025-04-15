package SC2002_Assignment;

public enum FlatType {
    TWOROOM(2, "Two Room Flat"),
    THREEROOM(3, "Three Room Flat");

    private final int numberOfRooms;
    private final String description;

    FlatType(int numberOfRooms, String description) {
        this.numberOfRooms = numberOfRooms;
        this.description = description;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getDescription() {
        return description;
    }
}
