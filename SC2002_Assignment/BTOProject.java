package SC2002_Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Build-to-Order (BTO) project with details such as the project name, neighborhood, 
 * application dates, types of flats, applications, registrations, and assigned officers.
 * It also contains methods for adding and removing officers, displaying project details, and checking if 
 * the project is within the application period.
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
     * Constructs a new BTO project.
     * 
     * @param projectName The name of the BTO project.
     * @param neighborhood The neighborhood where the BTO project is located.
     * @param applicationOpenDate The date when the application period opens.
     * @param applicationCloseDate The date when the application period closes.
     * @param managerInCharge The HDB manager in charge of the project.
     * @param flatTypes List of available flat types in the project.
     * @param twoRoomFlats The number of 2-room flats available in the project.
     * @param threeRoomFlats The number of 3-room flats available in the project.
     * @param twoP The number of 2-room premium flats available in the project.
     * @param threeP The number of 3-room premium flats available in the project.
     */
    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge,
                      List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP)
    {
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
        if (today.isAfter(applicationOpenDate) && today.isBefore(applicationCloseDate)){
            this.visibility = true;
        }
        else {
            this.visibility = false;
        }
    }

    // Getters and Setters

    /**
     * Gets the name of the BTO project.
     * 
     * @return The project name.
     */
    public String getProjectName(){ return projectName; }

    /**
     * Gets the neighborhood where the BTO project is located.
     * 
     * @return The neighborhood.
     */
    public String getNeighborhood(){ return neighborhood; }

    /**
     * Gets the flat details for the BTO project.
     * 
     * @return The flats information.
     */
    public BTOFlats getFlats(){ return flats; }

    /**
     * Gets the list of flat types available in the BTO project.
     * 
     * @return The list of flat types.
     */
    public List<FlatType> getFlatTypes(){ return flatTypes; }

    /**
     * Gets the application open date for the BTO project.
     * 
     * @return The application open date.
     */
    public LocalDate getApplicationOpenDate(){ return applicationOpenDate; }

    /**
     * Gets the application close date for the BTO project.
     * 
     * @return The application close date.
     */
    public LocalDate getApplicationCloseDate(){ return applicationCloseDate; }

    /**
     * Gets the manager in charge of the BTO project.
     * 
     * @return The manager in charge.
     */
    public HDBManager getManagerInCharge(){ return managerInCharge; }

    /**
     * Gets the list of applications made for the BTO project.
     * 
     * @return The list of applications.
     */
    public List<Application> getApplications(){ return applications; }

    /**
     * Gets the list of registrations for the BTO project.
     * 
     * @return The list of registrations.
     */
    public List<Registration> getRegistrations(){ return registrations; }

    /**
     * Gets the list of enquiries related to the BTO project.
     * 
     * @return The list of enquiries.
     */
    public List<Enquiry> getEnquiries(){ return enquiries; }

    /**
     * Gets the list of HDB officers assigned to the BTO project.
     * 
     * @return The list of assigned officers.
     */
    public List<HDBOfficer> getAssignedOfficers(){ return assignedOfficers; }

    /**
     * Gets the visibility status of the BTO project.
     * 
     * @return The visibility status.
     */
    public Boolean getVisibility(){ return visibility; }

    // Setters

    /**
     * Sets the name of the BTO project.
     * 
     * @param Name The name of the project.
     */
    public void setProjectName(String Name){ this.projectName = Name; }

    /**
     * Sets the neighborhood where the BTO project is located.
     * 
     * @param Neighbourhood The neighborhood name.
     */
    public void setNeighborhood(String Neighbourhood){ this.neighborhood = Neighbourhood; }

    /**
     * Sets the flat details for the BTO project.
     * 
     * @param Flats The flat details.
     */
    public void setFlats(BTOFlats Flats){ this.flats = Flats; }

    /**
     * Sets the list of flat types available in the BTO project.
     * 
     * @param FlatType List of flat types.
     */
    public void setFlatTypes(List<FlatType> FlatType){ this.flatTypes = FlatType; }

    /**
     * Sets the application open date for the BTO project.
     * 
     * @param Open The application open date.
     */
    public void setApplicationOpenDate(LocalDate Open){ this.applicationOpenDate = Open; }

    /**
     * Sets the application close date for the BTO project.
     * 
     * @param Close The application close date.
     */
    public void setApplicationCloseDate(LocalDate Close){ this.applicationCloseDate = Close; }

    /**
     * Sets the manager in charge of the BTO project.
     * 
     * @param M The manager in charge.
     */
    public void setManagerInCharge(HDBManager M){ this.managerInCharge = M; }

    /**
     * Sets the visibility status of the BTO project.
     * 
     * @param vis The visibility status.
     */
    public void setVisibility(Boolean vis){ this.visibility = vis; }

    /**
     * Adds an application to the BTO project.
     * 
     * @param A The application to be added.
     */
    public void addApplication(Application A){ this.applications.add(A); }

    /**
     * Adds a registration to the BTO project.
     * 
     * @param R The registration to be added.
     */
    public void addRegistration(Registration R){ this.registrations.add(R); }

    /**
     * Adds an enquiry to the BTO project.
     * 
     * @param E The enquiry to be added.
     */
    public void addEnquiry(Enquiry E){ this.enquiries.add(E); }

    /**
     * Adds an officer to the list of assigned officers.
     * 
     * @param officer The HDB officer to be added.
     * @return True if the officer is added successfully, otherwise false.
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
     * @param officer The HDB officer to be removed.
     * @return True if the officer is removed successfully, otherwise false.
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
     * Displays the details of the BTO project including project name, neighborhood, flat types, 
     * application dates, number of applications, assigned officers, and available flats.
     */
    public void displayProjectDetails() {
        System.out.println("Project Name: " + this.projectName);
        System.out.println("Neighborhood: " + this.neighborhood);
        System.out.println("Flat Types Available: " + this.flatTypes);
        System.out.println("Application Open Date: " + this.applicationOpenDate);
        System.out.println("Application Close Date: " + this.applicationCloseDate);
        System.out.println("HDB Manager in Charge: " + this.managerInCharge.getName());
        System.out.println("Number of applications:" + this.applications.size());
        System.out.print("Assigned Officers (" + this.assignedOfficers.size() + "/" + MAX_OFFICERS + "): ");
        System.out.println(
                this.assignedOfficers.stream()
                        .map(HDBOfficer::getName)
                        .collect(Collectors.joining(", "))
        );

        // Display total flats and price based on available types
        if (this.flatTypes.contains(FlatType.TWOROOM)) {
            System.out.println("Total 2-Room Flats: " + flats.getTwoRoomFlats());
            System.out.println("Two room flat price:" + this.flats.getTwoRoomPrice());
        }
        if (this.flatTypes.contains(FlatType.THREEROOM)) {
            System.out.println("Total 3-Room Flats: " + flats.getThreeRoomFlats());
            System.out.println("Three room flat price:" + this.flats.getThreeRoomPrice());
        }

        flats.displayAvailableFlats();
    }

    /**
     * Checks if the specified date is within the application period for the BTO project.
     * 
     * @param date The date to be checked.
     * @return True if the date is within the application period, otherwise false.
     */
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
                (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    /**
     * Checks if the BTO project can be deleted.
     * A project can be deleted if there are no applications, registrations, enquiries, or assigned officers.
     * 
     * @return True if the project can be deleted, otherwise false.
     */
    public boolean deletable(){
        return this.applications.isEmpty() && this.getRegistrations().isEmpty() && this.enquiries.isEmpty() && this.assignedOfficers.isEmpty();
    }

    /**
     * Removes an officer from the project by their NRIC number.
     * 
     * @param officerNric The NRIC of the officer to be removed.
     * @return True if the officer is removed successfully, otherwise false.
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
