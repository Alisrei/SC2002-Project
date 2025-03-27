import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        HDBManager manager = new HDBManager("Alice Tan");

        BTOProject btoProject = new BTOProject("Sunrise Grove", "Yishun",
                LocalDate.of(2025, 6, 1), LocalDate.of(2025, 8, 1), 
                manager, 50, 30);

        btoProject.displayProjectDetails();

        // Adding officers
        HDBOfficer officer1 = new HDBOfficer("John Lee", "OFC1001");
        HDBOfficer officer2 = new HDBOfficer("Maria Wong", "OFC1002");
        btoProject.addOfficer(officer1);
        btoProject.addOfficer(officer2);

        // Removing an officer
        btoProject.removeOfficer("OFC1001");

        // Booking flats
        btoProject.bookFlat("2R-10");
        btoProject.bookFlat("3R-5");

        System.out.println("\nUpdated Project Details:");
        btoProject.displayProjectDetails();

        LocalDate testDate1 = LocalDate.of(2025, 6, 15); // Within range
        LocalDate testDate2 = LocalDate.of(2025, 8, 2);  // After close date
        LocalDate testDate3 = LocalDate.of(2025, 6, 1);  // Exact open date
        LocalDate testDate4 = LocalDate.of(2025, 8, 1);  // Exact close date

        System.out.println("Test Date 1 (June 15, 2025): " + btoProject.isWithinApplicationPeriod(testDate1));
        System.out.println("Test Date 2 (August 2, 2025): " + btoProject.isWithinApplicationPeriod(testDate2));
        System.out.println("Test Date 3 (June 1, 2025): " + btoProject.isWithinApplicationPeriod(testDate3));
        System.out.println("Test Date 4 (August 1, 2025): " + btoProject.isWithinApplicationPeriod(testDate4));
    }
}
