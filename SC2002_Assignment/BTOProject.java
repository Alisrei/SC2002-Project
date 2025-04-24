/**
 * The BTOProject class represents a Build-To-Order (BTO) project in the Housing Development Board (HDB).
 * It encapsulates information about the project, including the project name, neighborhood, available flat types,
 * application dates, manager in charge, assigned officers, applications, registrations, and inquiries. It also 
 * provides methods to manage the assigned officers, application periods, and display project details.
 * <p>
 * This class is designed to model a BTO project and allows interaction with various aspects of the project, 
 * such as adding and removing officers, checking visibility based on the application period, and viewing details 
 * about the project and its flats.
 * </p>
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
     * Constructs a BTOProject with the specified details.
     *
     * @param projectName         The name of the BTO project.
     * @param neighborhood        The neighborhood where the project is located.
     * @param applicationOpenDate The date when the application for the project opens.
     * @param applicationCloseDate The date when the application for the project closes.
     * @param managerInCharge     The HDB manager responsible for the project.
     * @param flatTypes           List of flat types available in the project.
     * @param twoRoomFlats        The count of two-room flats available in the project.
     * @param threeRoomFlats      The count of three-room flats available in the project.
     * @param twoP                The count of two-room premium flats available in the project.
     * @param threeP              The count of three-room premium flats available in the project.
     */
    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge,
                      List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP) {
        // Constructor implementation...
    }

    /**
     * Gets the name of the BTO project.
     *
     * @return The project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the neighborhood where the project is located.
     *
     * @return The neighborhood.
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Gets the available flats for the project.
     *
     * @return The BTOFlats object containing the flat information.
     */
    public BTOFlats getFlats() {
        return flats;
    }

    /**
     * Gets the list of flat types available in this BTO project.
     *
     * @return The list of flat types.
     */
    public List<FlatType> getFlatTypes() {
        return flatTypes;
    }

    /**
     * Gets the date when the application for the BTO project opens.
     *
     * @return The application open date.
     */
    public LocalDate getApplicationOpenDate() {
        return applicationOpenDate;
    }

    /**
     * Gets the date when the application for the BTO project closes.
     *
     * @return The application close date.
     */
    public LocalDate getApplicationCloseDate() {
        return applicationCloseDate;
    }

    /**
     * Gets the HDB manager in charge of this BTO project.
     *
     * @return The HDB manager in charge.
     */
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    /**
     * Gets the list of applications submitted for the BTO project.
     *
     * @return The list of applications.
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     * Gets the list of registrations related to the BTO project.
     *
     * @return The list of registrations.
     */
    public List<Registration> getRegistrations() {
        return registrations;
    }

    /**
     * Gets the list of inquiries made for the BTO project.
     *
     * @return The list of inquiries.
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * Gets the list of HDB officers assigned to the BTO project.
     *
     * @return The list of HDB officers.
     */
    public List<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    /**
     * Gets the visibility of the project based on the current date and the application period.
     *
     * @return True if the project is visible (i.e., within the application period), otherwise false.
     */
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Adds an application to the project.
     *
     * @param application The application to be added.
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    /**
     * Adds a registration to the project.
     *
     * @param registration The registration to be added.
     */
    public void addRegistration(Registration registration) {
        this.registrations.add(registration);
    }

    /**
     * Adds an enquiry to the project.
     *
     * @param enquiry The enquiry to be added.
     */
    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    /**
     * Adds an officer to the project, if the maximum number of officers has not been reached.
     *
     * @param officer The officer to be added.
     * @return True if the officer was added successfully, otherwise false.
     */
    public boolean addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() < MAX_OFFICERS) {
            assignedOfficers.add(officer);
            return true;
        }
        return false;
    }

    /**
     * Removes an officer from the project by their NRIC number.
     *
     * @param officerNric The NRIC number of the officer to be removed.
     * @return True if the officer was removed successfully, otherwise false.
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

    /**
     * Displays detailed information about the project, including project name, neighborhood,
     * available flat types, application dates, assigned officers, and the number of applications.
     */
    public void displayProjectDetails() {
        // Method implementation...
    }

    /**
     * Checks if a given date is within the application period for the project.
     *
     * @param date The date to check.
     * @return True if the date is within the application period, otherwise false.
     */
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
                (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    /**
     * Checks if the project can be deleted. A project is deletable if it has no applications, 
     * registrations, enquiries, or assigned officers.
     *
     * @return True if the project can be deleted, otherwise false.
     */
    public boolean deletable() {
        return this.applications.isEmpty() && this.getRegistrations().isEmpty() &&
                this.enquiries.isEmpty() && this.assignedOfficers.isEmpty();
    }

    /**
     * Removes an officer by their object reference.
     *
     * @param officer The officer to be removed.
     * @return True if the officer was removed successfully, otherwise false.
     */
    public boolean removeOfficer(HDBOfficer officer) {
        if (assignedOfficers.contains(officer)) {
            assignedOfficers.remove(officer);
            return true;
        }
        return false;
    }
}
