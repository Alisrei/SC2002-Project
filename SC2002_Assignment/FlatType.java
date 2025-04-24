package SC2002_Assignment;

/**
 * Enum representing different types of flats available in a BTO project.
 * Each flat type contains information about the number of rooms and a description.
 */
public enum FlatType {
    
    /** Represents a two-room flat. */
    TWOROOM(2, "Two Room Flat"),

    /** Represents a three-room flat. */
    THREEROOM(3, "Three Room Flat");

    private final int numberOfRooms;
    private final String description;

    /**
     * Constructor to initialize the flat type with the number of rooms and description.
     * 
     * @param numberOfRooms The number of rooms in the flat type.
     * @param description A short description of the flat type.
     */
    FlatType(int numberOfRooms, String description) {
        this.numberOfRooms = numberOfRooms;
        this.description = description;
    }

    /**
     * Gets the number of rooms in the flat type.
     * 
     * @return The number of rooms in the flat.
     */
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    /**
     * Gets the description of the flat type.
     * 
     * @return The description of the flat type.
     */
    public String getDescription() {
        return description;
    }
}
