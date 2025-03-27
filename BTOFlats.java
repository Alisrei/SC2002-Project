import java.util.ArrayList;
import java.util.List;

// BTOFlats class to manage the flat types and their counts
public class BTOFlats {
    private int twoRoomFlats;
    private int threeRoomFlats;
    private List<String> availableFlats; // Stores unit numbers

    public BTOFlats(int twoRoomFlats, int threeRoomFlats) {
        this.twoRoomFlats = twoRoomFlats;
        this.threeRoomFlats = threeRoomFlats;
        this.availableFlats = new ArrayList<>();

        // Automatically generate unit numbers
        generateUnitNumbers();
    }

    private void generateUnitNumbers() {
        for (int i = 1; i <= twoRoomFlats; i++) {
            availableFlats.add("2R-" + i);
        }
        for (int i = 1; i <= threeRoomFlats; i++) {
            availableFlats.add("3R-" + i);
        }
    }

    public int getTwoRoomFlats() {
        return twoRoomFlats;
    }

    public int getThreeRoomFlats() {
        return threeRoomFlats;
    }

    public List<String> getAvailableFlats() {
        return availableFlats;
    }

    public boolean bookFlat(String unitNumber) {
        return availableFlats.remove(unitNumber); // Returns true if successful
    }

    public void displayAvailableFlats() {
        System.out.println("Available Flats: " + availableFlats);
    }
}
