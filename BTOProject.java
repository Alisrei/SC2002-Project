import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BTOProject {
    private String projectName;
    private String neighborhood;
    private BTOFlats flats; // Stores types of flats and their count
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private HDBManager managerInCharge;
    private List<HDBOfficer> assignedOfficers; // List of HDB Officers
    private static final int MAX_OFFICERS = 10;

    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge, 
                      int twoRoomFlats, int threeRoomFlats)
    {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.managerInCharge = managerInCharge;
        this.flats = new BTOFlats(twoRoomFlats, threeRoomFlats);
        this.assignedOfficers = new ArrayList<>();
    }

    public void displayProjectDetails() {
        System.out.println("Project Name: " + projectName);
        System.out.println("Neighborhood: " + neighborhood);
        System.out.println("Application Open Date: " + applicationOpenDate);
        System.out.println("Application Close Date: " + applicationCloseDate);
        System.out.println("HDB Manager in Charge: " + managerInCharge.getName());
        System.out.println("Assigned Officers (" + assignedOfficers.size() + "/" + MAX_OFFICERS + "): " + assignedOfficers);
        System.out.println("Total 2-Room Flats: " + flats.getTwoRoomFlats());
        System.out.println("Total 3-Room Flats: " + flats.getThreeRoomFlats());
        flats.displayAvailableFlats();
    }

    public boolean bookFlat(String unitNumber) {
        if (flats.bookFlat(unitNumber)) {
            System.out.println("Flat " + unitNumber + " successfully booked.");
            return true;
        } else {
            System.out.println("Flat " + unitNumber + " is unavailable or does not exist.");
            return false;
        }
    }

    // Checks if within application period
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
               (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    // Adds an officer if the max limit is not exceeded
    public boolean addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() < MAX_OFFICERS) {
            assignedOfficers.add(officer);
            System.out.println("Officer " + officer.getName() + " added.");
            return true;
        } else {
            System.out.println("Cannot add officer " + officer.getName() + ". Max officer limit reached.");
            return false;
        }
    }

    // Removes an officer by ID
    public boolean removeOfficer(String officerId) {
        for (HDBOfficer officer : assignedOfficers) {
            if (officer.getOfficerId().equals(officerId)) {
                assignedOfficers.remove(officer);
                System.out.println("Officer " + officer.getName() + " removed.");
                return true;
            }
        }
        System.out.println("Officer ID " + officerId + " not found.");
        return false;
    }
}
