package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BTOFlats} class represents the flats available in a BTO project.
 * It maintains details about the available flats, such as the number of two-room and three-room flats,
 * their respective prices, and the list of unit numbers for the available flats.
 * <p>
 * This class also provides methods to book flats, add new rooms, and view the available flats.
 * </p>
 */
public class BTOFlats {
    
    private int twoRoomFlats;
    private int threeRoomFlats;
    private int twoRoomPrice;
    private int threeRoomPrice;
    private List<String> availableFlats; // Stores unit numbers of available flats

    /**
     * Constructs a {@code BTOFlats} object with the specified number of two-room flats, three-room flats,
     * and their respective prices.
     * <p>
     * The constructor also generates unit numbers for each available flat (e.g., "2R-1", "3R-1").
     * </p>
     * 
     * @param twoRoomFlats The number of available two-room flats.
     * @param threeRoomFlats The number of available three-room flats.
     * @param TwoP The price for a two-room flat.
     * @param ThreeP The price for a three-room flat.
     */
    public BTOFlats(int twoRoomFlats, int threeRoomFlats, int TwoP, int ThreeP) {
        this.twoRoomFlats = twoRoomFlats;
        this.threeRoomFlats = threeRoomFlats;
        this.twoRoomPrice = TwoP;
        this.threeRoomPrice = ThreeP;
        this.availableFlats = new ArrayList<>();

        // Automatically generate unit numbers
        generateUnitNumbers();
    }

    /**
     * Generates unit numbers for the available flats. 
     * For two-room flats, unit numbers are of the format "2R-<number>",
     * and for three-room flats, unit numbers are of the format "3R-<number>".
     */
    private void generateUnitNumbers() {
        for (int i = 1; i <= twoRoomFlats; i++) {
            availableFlats.add("2R-" + i);
        }
        for (int i = 1; i <= threeRoomFlats; i++) {
            availableFlats.add("3R-" + i);
        }
    }

    // Getters

    /**
     * Returns the number of available two-room flats.
     * 
     * @return The number of two-room flats.
     */
    public int getTwoRoomFlats() {
        return twoRoomFlats;
    }

    /**
     * Returns the number of available three-room flats.
     * 
     * @return The number of three-room flats.
     */
    public int getThreeRoomFlats() {
        return threeRoomFlats;
    }

    /**
     * Returns the price for a two-room flat.
     * 
     * @return The price of a two-room flat.
     */
    public int getTwoRoomPrice() {
        return twoRoomPrice;
    }

    /**
     * Returns the price for a three-room flat.
     * 
     * @return The price of a three-room flat.
     */
    public int getThreeRoomPrice() {
        return threeRoomPrice;
    }

    /**
     * Returns a list of available flats by their unit numbers.
     * 
     * @return A list of strings representing the unit numbers of available flats.
     */
    public List<String> getAvailableFlats() {
        return availableFlats;
    }

    // Setters

    /**
     * Sets the number of available two-room flats.
     * 
     * @param twos The number of two-room flats to set.
     */
    public void setTwoRoomFlats(int twos) {
        this.twoRoomFlats = twos;
    }

    /**
     * Sets the number of available three-room flats.
     * 
     * @param threes The number of three-room flats to set.
     */
    public void setThreeRoomFlats(int threes) {
        this.threeRoomFlats = threes;
    }

    /**
     * Sets the price for a two-room flat.
     * 
     * @param twoRoomPrice The price of a two-room flat to set.
     */
    public void setTwoRoomPrice(int twoRoomPrice) {
        this.twoRoomPrice = twoRoomPrice;
    }

    /**
     * Sets the price for a three-room flat.
     * 
     * @param threeRoomPrice The price of a three-room flat to set.
     */
    public void setThreeRoomPrice(int threeRoomPrice) {
        this.threeRoomPrice = threeRoomPrice;
    }

    // Flat Booking

    /**
     * Books a flat by the specified unit number. 
     * If the flat is available, it reduces the number of available flats and removes the unit number from the list.
     * If the flat is unavailable or does not exist, an error message is displayed.
     * 
     * @param unitNumber The unit number of the flat to book.
     * @return {@code true} if the flat was successfully booked, {@code false} otherwise.
     */
    public boolean bookFlat(String unitNumber) {
        if (this.availableFlats.contains(unitNumber)) {
            if (unitNumber.substring(0, 2).equals("2R")) {
                this.twoRoomFlats -= 1;
            }
            if (unitNumber.substring(0, 2).equals("3R")) {
                this.threeRoomFlats -= 1;
            }
            if (this.availableFlats.remove(unitNumber)) {
                System.out.println("Flat " + unitNumber + " successfully booked.");
                return true;
            }
        }
        System.out.println("Flat " + unitNumber + " is unavailable or does not exist.");
        return false;
    }

    /**
     * Adds a room (flat) to the list of available flats.
     * 
     * @param flat The unit number of the flat to add to the available flats.
     */
    public void addRoom(String flat) {
        this.availableFlats.add(flat);
    }

    // View Available Flats

    /**
     * Displays a list of available flats by their unit numbers.
     */
    public void displayAvailableFlats() {
        System.out.println("Available Flats: " + availableFlats);
    }
}
