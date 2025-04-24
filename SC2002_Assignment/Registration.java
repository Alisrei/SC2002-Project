package SC2002_Assignment;

/**
 * Represents a registration for a BTO project handled by an HDB officer.
 * This class stores information about the registration ID, the officer responsible, the project associated with the registration,
 * and whether the registration has been accepted.
 */
public class Registration {
    
    private String regId;
    private HDBOfficer officer;
    private BTOProject project;
    private Boolean accepted;

    /**
     * Constructs a new Registration with the specified registration ID, HDB officer, and BTO project.
     * The registration is initially not accepted.
     *
     * @param RID The registration ID.
     * @param O The HDB officer responsible for the registration.
     * @param P The BTO project associated with the registration.
     */
    public Registration(String RID, HDBOfficer O, BTOProject P) {
        this.regId = RID;
        this.officer = O;
        this.project = P;
        this.accepted = false;
    }

    // Getters

    /**
     * Gets the registration ID.
     * 
     * @return The registration ID.
     */
    public String getRegId() {
        return this.regId;
    }

    /**
     * Gets the HDB officer responsible for the registration.
     * 
     * @return The HDB officer.
     */
    public HDBOfficer getOfficer() {
        return this.officer;
    }

    /**
     * Gets the BTO project associated with the registration.
     * 
     * @return The BTO project.
     */
    public BTOProject getProject() {
        return this.project;
    }

    /**
     * Gets the status of the registration (whether it has been accepted).
     * 
     * @return {@code true} if the registration has been accepted, {@code false} otherwise.
     */
    public Boolean getAccepted() {
        return this.accepted;
    }

    // Setters

    /**
     * Sets the acceptance status of the registration.
     * 
     * @param T The new acceptance status, where {@code true} means accepted and {@code false} means not accepted.
     */
    public void setAccepted(Boolean T) {
        this.accepted = T;
    }

    // Display Method

    /**
     * Displays the details of the registration, including the registration ID, the project, and the acceptance status.
     */
    public void displayRegistration() {
        System.out.println("Registration ID: " + this.getRegId() + "\n" +
                           "Project of Registration: " + this.getProject() + "\n" +
                           "Registration status: " + this.getAccepted());
    }
}
