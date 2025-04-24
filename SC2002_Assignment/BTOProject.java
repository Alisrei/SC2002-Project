package SC2002_Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a BTO (Build-To-Order) project containing information about the project, its flats,
 * applications, registrations, enquiries, and assigned officers.
 * Provides methods to manage officers, view project details, and check application periods.
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
     * Constructor for initializing a BTO project.
     *
     * @param projectName           The name of the project.
     * @param neighborhood          The neighborhood in which the project is located.
     * @param applicationOpenDate   The date when applications open.
     * @param applicationCloseDate  The date when applications close.
     * @param managerInCharge       The HDB manager in charge of the project.
     * @param flatTypes             A list of the types of flats available in the project.
     * @param twoRoomFlats          The number of two-room flats available.
     * @param threeRoomFlats        The number of three-room flats available.
     * @param twoP                  The number of 2P flats available.
     * @param threeP                The number of 3P flats available.
     */
    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge,
                      List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP) {
        // Constructor logic here
    }

    // Getters and Setters

    /**
     * Gets the project name.
     *
     * @return the name of the project.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the neighborhood.
     *
     * @return the neighborhood of the project.
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Gets the BTO flats details.
     *
     * @return the details of the flats.
     */
    public BTOFlats getFlats() {
        return flats;
    }

    /**
     * Gets the list of available flat types.
     *
     * @return a list of flat types available in the project.
     */
    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    /**
     * Gets the application open date.
     *
     * @return the date when applications open.
     */
    public LocalDate getApplicationOpenDate() {
        return applicationOpenDate;
    }

    /**
     * Gets the application close date.
     *
     * @return the date when applications close.
     */
    public LocalDate getApplicationCloseDate() {
        return applicationCloseDate;
    }

    /**
     * Gets the manager in charge of the project.
     *
     * @return the HDB manager responsible for the project.
     */
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    /**
     * Gets the list of applications made for the project.
     *
     * @return a list of applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Gets the list of registrations related to the project.
     *
     * @return a list of registrations.
     */
    public List<Registration> getRegistrations() {
        return registrations;
    }

    /**
     * Gets the list of enquiries related to the project.
     *
     * @return a list of enquiries.
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Gets the list of officers assigned to the project.
     *
     * @return a list of HDB officers assigned to the project.
     */
    public List<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    /**
     * Gets the visibility status of the project.
     *
     * @return a boolean value representing whether the project is visible or not.
     */
    public Boolean getVisibility() {
        return visibility;
    }

    // Setters

    /**
     * Sets the project name.
     *
     * @param Name the new name of the project.
     */
    public void setProjectName(String Name) {
        this.projectName = Name;
    }

    /**
     * Sets the neighborhood of the project.
     *
     * @param Neighbourhood the new neighborhood.
     */
    public void setNeighborhood(String Neighbourhood) {
        this.neighborhood = Neighbourhood;
    }

    /**
     * Sets the BTO flats details.
     *
     * @param Flats the new flats details.
     */
    public void setFlats(BTOFlats Flats) {
        this.flats = Flats;
    }

    /**
     * Sets the list of flat types available in the project.
     *
     * @param FlatType the new list of flat types.
     */
    public void setFlatTypes(List<FlatType> FlatType) {
        this.flatTypes = FlatType;
    }

    /**
     * Sets the application open date.
     *
     * @param Open the new open date.
     */
    public void setApplicationOpenDate(LocalDate Open) {
        this.applicationOpenDate = Open;
    }

    /**
     * Sets the application close date.
     *
     * @param Close the new close date.
     */
    public void setApplicationCloseDate(LocalDate Close) {
        this.applicationCloseDate = Close;
    }

    /**
     * Sets the manager in charge of the project.
     *
     * @param M the new manager in charge.
     */
    public void setManagerInCharge(HDBManager M) {
        this.managerInCharge = M;
    }

    /**
     * Adds an application to the list of applications.
     *
     * @param A the application to add.
     */
    public void addApplication(Application A) {
        this.applications.add(A);
    }

    /**
     * Adds a registration to the list of registrations.
     *
     * @param R the registration to add.
     */
    public void addRegistration(Registration R) {
        this.registrations.add(R);
    }

    /**
     * Adds an enquiry to the list of enquiries.
     *
     * @param E the enquiry to add.
     */
    public void addEnquiry(Enquiry E) {
        this.enquiries.add(E);
    }

    /**
     * Attempts to add an officer to the list of assigned officers.
     *
     * @param officer the officer to add.
     * @return true if the officer is successfully added, false otherwise.
     */
    public boolean addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() < MAX_OFFICERS) {
            assignedOfficers.add(officer);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an officer from the list of assigned officers.
     *
     * @param officer the officer to remove.
     * @return true if the officer was removed, false otherwise.
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
     * Displays the project details including project name, neighborhood, flat types, application dates,
     * manager, and assigned officers.
     */
    public void displayProjectDetails() {
        // Display logic here
    }

    /**
     * Checks if a given date is within the application period.
     *
     * @param date the date to check.
     * @return true if the date is within the application period, false otherwise.
     */
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
                (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    /**
     * Checks if the project can be deleted (i.e., if there are no applications, registrations,
     * enquiries, or assigned officers).
     *
     * @return true if the project can be deleted, false otherwise.
     */
    public boolean deletable() {
        return this.applications.isEmpty() && this.getRegistrations().isEmpty() && this.enquiries.isEmpty() && this.assignedOfficers.isEmpty();
    }

    /**
     * Removes an officer by their NRIC.
     *
     * @param officerNric the NRIC of the officer to remove.
     * @return true if the officer was removed, false otherwise.
     */
    public boolean removeOfficer(String officerNric) {
        for (HDBOfficer officer : assignedOfficers) {
            if (officer.getNric().equals(officerNric)) {
                assignedOfficers.remove(officer);
                return true;
            }
        }
        return false;
    }
}
