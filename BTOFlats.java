package SC2002_Assignment;
import java.util.ArrayList;
import java.util.List;
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
    //getters
    public int getTwoRoomFlats() {return twoRoomFlats;}
    public int getThreeRoomFlats() {return threeRoomFlats;}
    public List<String> getAvailableFlats() {return availableFlats;}
    //setters
    public void setTwoRoomFlats(int twos){this.twoRoomFlats = twos;}
    public void setThreeRoomFlats(int threes){this.threeRoomFlats = threes;}



    public boolean bookFlat(String unitNumber) {
        if(this.availableFlats.contains(unitNumber)){
            if(unitNumber.substring(0,2).equals("2R")){
                this.twoRoomFlats -= 1;
            }
            if(unitNumber.substring(0,2).equals("3R")){
                this.threeRoomFlats -= 1;
            }
            return this.availableFlats.remove(unitNumber);
        }
        else{return false;}
    }

    public void displayAvailableFlats() {
        System.out.println("Available Flats: " + availableFlats);
    }

}
