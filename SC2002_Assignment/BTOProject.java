package SC2002_Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Build-To-Order (BTO) project in the Housing Development Board (HDB) system.
 * The project includes information such as project name, neighborhood, flat types, application period,
 * the manager in charge, and a list of applications, registrations, enquiries, and assigned officers.
 */
public class BTOProject {

    private String projectName;
    private String neighborhood;
    private BTOFlats flats; // Stores types of flats and their count
    private List<FlatType> flatTypes; // List of flat types in this project
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private HDBManager managerInCharge;
    private List<Application> applications;
    private List<Registration> registrations;
    private List<Enquiry> enquiries;
    private List<HDBOfficer> assignedOfficers; // List of HDB Officers
    private static final int MAX_OFFICERS = 10;
    private Boolean visibility;

    /**
     * Constructs a BTOProject with the specified project details.
     * 
     * @param projectName The name of the project.
     * @param neighborhood The neighborhood in which the project is located.
     * @param applicationOpenDate The date when the application period opens.
     * @param applicationCloseDate The date when the application period closes.
     * @param managerInCharge The HDB Manager in charge of this project.
     * @param flatTypes A list of flat types available in this project.
     * @param twoRoomFlats The number of two-room flats available.
     * @param threeRoomFlats The number of three-room flats available.
     * @param twoP The number of two-room flats available for purchase.
     * @param threeP The number of three-room flats available for purchase.
     */
    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge,
                      List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.managerInCharge = managerInCharge;
        this.flatTypes = flatTypes;
        this.flats = new BTOFlats(twoRoomFlats, threeRoomFlats, twoP, threeP);
        this.assignedOfficers = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.registrations = new ArrayList<>();
        this.enquiries = new ArrayList<>();
        LocalDate today = LocalDate.now();
        this.visibility = today.isAfter(applicationOpenDate) && today.isBefore(applicationCloseDate);
    }

    // Getters

    /**
     * Returns the name of the project.
     * 
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the neighborhood in which the project is located.
     * 
     * @return The neighborhood name.
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Returns the flats associated with this project.
     * 
     * @return The BTOFlats object containing flat details.
     */
    public BTOFlats getFlats() {
        return flats;
    }

    /**
     * Returns a list of flat types available in this project.
     * 
     * @return The list of flat types.
     */
    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    /**
     * Returns the opening date of the application period.
     * 
     * @return The application opening date.
     */
    public LocalDate getApplicationOpenDate() {
        return applicationOpenDate;
    }

    /**
     * Returns the closing date of the application period.
     * 
     * @return The application closing date.
     */
    public LocalDate getApplicationCloseDate() {
        return applicationCloseDate;
    }

    /**
     * Returns the HDB Manager in charge of this project.
     * 
     * @return The manager in charge.
     */
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    /**
     * Returns the list of applications for this project.
     * 
     * @return The list of applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Returns the list of registrations for this project.
     * 
     * @return The list of registrations.
     */
    public List<Registration> getRegistrations() {
        return registrations;
    }

    /**
     * Returns the list of enquiries related to this project.
     * 
     * @return The list of enquiries.
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Returns the list of officers assigned to this project.
     * 
     * @return The list of assigned officers.
     */
    public List<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    /**
     * Returns whether the project is visible for new applications.
     * 
     * @return The visibility status.
     */
    public Boolean getVisibility() {
        return visibility;
    }

    // Setters

    /**
     * Sets the name of the project.
     * 
     * @param name The new project name.
     */
    public void setProjectName(String name) {
        this.projectName = name;
    }

    /**
     * Sets the neighborhood of the project.
     * 
     * @param neighborhood The new neighborhood name.
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Sets the flats associated with this project.
     * 
     * @param flats The new BTOFlats object.
     */
    public void setFlats(BTOFlats flats) {
        this.flats = flats;
    }

    /**
     * Sets the list of flat types available in this project.
     * 
     * @param flatTypes The new list of flat types.
     */
    public void setFlatTypes(List<FlatType> flatTypes) {
        this.flatTypes = flatTypes;
    }

    /**
     * Sets the opening date for the application period.
     * 
     * @param open The new opening date.
     */
    public void setApplicationOpenDate(LocalDate open) {
        this.applicationOpenDate = open;
    }

    /**
     * Sets the closing date for the application period.
     * 
     * @param close The new closing date.
     */
    public void setApplicationCloseDate(LocalDate close) {
        this.applicationCloseDate = close;
    }

    /**
     * Sets the HDB Manager in charge of this project.
     * 
     * @param manager The new manager in charge.
     */
    public void setManagerInCharge(HDBManager manager) {
        this.managerInCharge = manager;
    }

    /**
     * Sets the list of officers assigned to this project.
     * 
     * @param officers The new list of officers.
     */
    public void setAssignedOfficers(List<HDBOfficer> officers) {
        this.assignedOfficers = officers;
    }

    /**
     * Sets the visibility of this project.
     * 
     * @param visibility The new visibility status.
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    // Methods for managing applications, registrations, enquiries, and officers

    /**
     * Adds a new application to the project.
     * 
     * @param application The new application to add.
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    /**
     * Adds a new registration to the project.
     * 
     * @param registration The new registration to add.
     */
    public void addRegistration(Registration registration) {
        this.registrations.add(registration);
    }

    /**
     * Adds a new enquiry to the project.
     * 
     * @param enquiry The new enquiry to add.
     */
    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    /**
     * Adds an officer to the project if the maximum number of officers has not been reached.
     * 
     * @param officer The officer to add.
     * @return True if the officer was successfully added, false otherwise.
     */
    public boolean addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() < MAX_OFFICERS) {
            assignedOfficers.add(officer);
            return true;
        }
        return false;
    }

    /**
     * Removes an officer from the project by officer NRIC.
     * 
     * @param officerNric The NRIC of the officer to remove.
     * @return True if the officer was successfully removed, false otherwise.
     */
    public boolean removeOfficer(HDBOfficer officer) {
        if (assignedOfficers.contains(officer)) {
            assignedOfficers.remove(officer);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Displays the details of the project, including project name, neighborhood, flat types,
     * application dates, assigned officers, and available flats.
     */
    public void displayProjectDetails() {
        System.out.println("Project Name: " + this.projectName);
        System.out.println("Neighborhood: " + this.neighborhood);
        System.out.println("Flat Types Available: " + this.flatTypes);
        System.out.println("Application Open Date: " + this.applicationOpenDate);
        System.out.println("Application Close Date: " + this.applicationCloseDate);
        System.out.println("HDB Manager in Charge: " + this.managerInCharge.getName());
        System.out.println("Number of applications: " + this.applications.size());
        System.out.print("Assigned Officers (" + this.assignedOfficers.size() + "/" + MAX_OFFICERS + "): ");
        System.out.println(this.assignedOfficers.stream()
                .map(HDBOfficer::getName)
                .collect(Collectors.joining(", ")));

        // Display available flat information
        if (this.flatTypes.contains(FlatType.TWOROOM)) {
            System.out.println("Total 2-Room Flats: " + flats.getTwoRoomFlats());
            System.out.println("Two room flat price: " + this.flats.getTwoRoomPrice());
        }
        if (this.flatTypes.contains(FlatType.THREEROOM)) {
            System.out.println("Total 3-Room Flats: " + flats.getThreeRoomFlats());
            System.out.println("Three room flat price: " + this.flats.getThreeRoomPrice());
        }

        flats.displayAvailableFlats();
    }

    /**
     * Checks if a given date falls within the application period for the project.
     * 
     * @param date The date to check.
     * @return True if the date is within the application period, false otherwise.
     */
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
                (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    /**
     * Checks if the project can be deleted (only if there are no applications, registrations,
     * enquiries, or assigned officers).
     * 
     * @return True if the project can be deleted, false otherwise.
     */
    public boolean deletable() {
        return this.applications.isEmpty() && this.registrations.isEmpty() && this.enquiries.isEmpty() && this.assignedOfficers.isEmpty();
    }

    /**
     * Removes an officer from the list of assigned officers using their NRIC.
     * This method iterates through the list of assigned officers and removes the officer with the matching NRIC.
     * If the officer is found and removed, a message indicating that the officer was removed is printed.
     * If the officer with the given NRIC is not found, a message is printed to indicate that the officer was not found.
     * 
     * @param officerNric The NRIC of the officer to be removed.
     * @return True if the officer was successfully removed, false if no officer with the given NRIC was found.
     */
    public boolean removeOfficer(String officerNric) {
        for (HDBOfficer officer : assignedOfficers) {
            if (officer.getNric().equals(officerNric)) {
                assignedOfficers.remove(officer);
                System.out.println("Officer " + officer.getName() + " removed.");
                return true;
            }
        }
        System.out.println("Officer Nric " + officerNric + " not found.");
        return false;
    }
}
