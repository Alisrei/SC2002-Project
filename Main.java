import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HDBManager manager = new HDBManager("Alice Tan");

        // Creating a BTO Project with only 2-room flats
        BTOProject project1 = new BTOProject("Sunrise Grove", "Yishun",
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 8, 1), 
                manager, Arrays.asList(FlatType.TWOROOM), 50, 0);

        // Creating a BTO Project with both 2-room and 3-room flats
        BTOProject project2 = new BTOProject("Horizon View", "Boon Lay",
                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 7, 1), 
                manager, Arrays.asList(FlatType.TWOROOM, FlatType.THREEROOM), 30, 40);

        // Display details
        project1.displayProjectDetails();
        project2.displayProjectDetails();

        // Adding officers
        HDBOfficer officer1 = new HDBOfficer("John Lee", "OFC1001");
        HDBOfficer officer2 = new HDBOfficer("Maria Wong", "OFC1002");
        project1.addOfficer(officer1);
        project1.addOfficer(officer2);

        // Removing an officer
        project1.removeOfficer("OFC1001");

        // Booking flats
        project1.bookFlat("2R-10");
        project1.bookFlat("3R-5");

        System.out.println("\nUpdated Project Details:");
        project1.displayProjectDetails();

        LocalDate testDate1 = LocalDate.of(2025, 6, 15); // Within range
        LocalDate testDate2 = LocalDate.of(2025, 8, 2);  // After close date
        LocalDate testDate3 = LocalDate.of(2025, 6, 1);  // Exact open date
        LocalDate testDate4 = LocalDate.of(2025, 8, 1);  // Exact close date

        System.out.println("Test Date 1 (June 15, 2025): " + project1.isWithinApplicationPeriod(testDate1));
        System.out.println("Test Date 2 (August 2, 2025): " + project1.isWithinApplicationPeriod(testDate2));
        System.out.println("Test Date 3 (June 1, 2025): " + project1.isWithinApplicationPeriod(testDate3));
        System.out.println("Test Date 4 (August 1, 2025): " + project1.isWithinApplicationPeriod(testDate4));
    }
}
