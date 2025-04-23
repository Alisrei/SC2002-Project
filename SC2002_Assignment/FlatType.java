package SC2002_Assignment;

public enum FlatType {
    TWOROOM(2, "Two Room Flat"),
    THREEROOM(3, "Three Room Flat");

    private final int numberOfRooms;
    private final String price;

    FlatType(int numberOfRooms, String description) {
        this.numberOfRooms = numberOfRooms;
        this.price = description;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getDescription() {
        return price;
    }
}

