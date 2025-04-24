package SC2002_Assignment;

/**
 * The {@code Application} class represents an application submitted by an {@code Applicant} for a specific BTO project.
 * It contains information about the application, such as the application ID, associated applicant, selected project, booking details,
 * application status, and whether a withdrawal has been requested.
 * 
 * <p>An application allows an applicant to apply for a BTO project, choose a flat type for booking, and track the application's status.</p>
 */
public class Application {
    
    private String applicationId;
    private Applicant applicant;
    private BTOProject project;
    private FlatType flatTypeBooking;
    private String bookedUnit;
    private ApplicationStatus status;
    private boolean withdrawalRequested;

    /**
     * Constructs an {@code Application} with the specified application ID, applicant, and BTO project.
     * The application status is initially set to {@code ApplicationStatus.PENDING}.
     * 
     * @param applicationId The unique ID for the application.
     * @param applicant The {@code Applicant} who is submitting the application.
     * @param project The {@code BTOProject} the applicant is applying for.
     */
    public Application(String applicationId, Applicant applicant, BTOProject project) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.project = project;
        this.flatTypeBooking = null;
        this.bookedUnit = null;
        this.status = ApplicationStatus.PENDING;
        project.addApplication(this);
    }

    // Getters

    /**
     * Returns the unique ID of the application.
     * 
     * @return The application ID.
     */
    public String getApplicationId() {
        return this.applicationId;
    }

    /**
     * Returns the applicant who submitted the application.
     * 
     * @return The {@code Applicant} object.
     */
    public Applicant getApplicant() {
        return this.applicant;
    }

    /**
     * Returns the project associated with the application.
     * 
     * @return The {@code BTOProject} object.
     */
    public BTOProject getProject() {
        return this.project;
    }

    /**
     * Returns the flat type selected for booking.
     * 
     * @return The {@code FlatType} selected for booking, or {@code null} if no booking has been made.
     */
    public FlatType getFlatTypeBooking() {
        return this.flatTypeBooking;
    }

    /**
     * Returns the unit booked by the applicant, if applicable.
     * 
     * @return The booked unit, or {@code null} if no unit has been booked.
     */
    public String getBookedUnit() {
        return this.bookedUnit;
    }

    /**
     * Returns the current status of the application.
     * 
     * @return The {@code ApplicationStatus} representing the status of the application.
     */
    public ApplicationStatus getStatus() {
        return this.status;
    }

    /**
     * Returns whether a withdrawal request has been made for the application.
     * 
     * @return {@code true} if a withdrawal has been requested, otherwise {@code false}.
     */
    public boolean getWithdrawalRequested() {
        return this.withdrawalRequested;
    }

    // Setters

    /**
     * Sets the unique application ID.
     * 
     * @param applicationId The application ID to set.
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Sets the applicant associated with the application.
     * 
     * @param applicant The {@code Applicant} to set.
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * Sets the project associated with the application.
     * 
     * @param project The {@code BTOProject} to set.
     */
    public void setProject(BTOProject project) {
        this.project = project;
    }

    /**
     * Sets the flat type selected for booking.
     * 
     * @param flatTypeBooking The {@code FlatType} to set for booking.
     */
    public void setFlatTypeBooking(FlatType flatTypeBooking) {
        this.flatTypeBooking = flatTypeBooking;
    }

    /**
     * Sets the booked unit for the application.
     * 
     * @param bookedUnit The unit booked by the applicant.
     */
    public void setBookedUnit(String bookedUnit) {
        this.bookedUnit = bookedUnit;
    }

    /**
     * Sets the status of the application.
     * 
     * @param status The {@code ApplicationStatus} to set.
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Sets whether a withdrawal request has been made for the application.
     * 
     * @param withdrawalRequested {@code true} if a withdrawal has been requested, otherwise {@code false}.
     */
    public void setWithdrawalRequested(boolean withdrawalRequested) {
        this.withdrawalRequested = withdrawalRequested;
    }

    // Application Methods

    /**
     * Displays the details of the application, including the application ID, associated project,
     * selected flat type, application status, and booked unit.
     */
    public void displayApplication() {
        System.out.println("Application ID: " + this.getApplicationId() + "\n" +
                           "Project of Application: " + this.getProject().getProjectName() + "\n" +
                           "Booking Choice: " + this.getFlatTypeBooking() + "\n" +
                           "Application Status: " + this.getStatus() + "\n" +
                           "Booked Unit: " + this.getBookedUnit());
    }

    /**
     * Deletes the application and resets all application-related details.
     * This includes clearing the project’s booked unit and resetting the application properties.
     */
    public void deleteApplication() {
        this.getProject().getFlats().addRoom(this.getBookedUnit());
        this.setApplicationId(null);
        this.setApplicant(null);
        this.setProject(null);
        this.setFlatTypeBooking(null);
        this.setBookedUnit(null);
        this.setStatus(null);
        // System.out.println("Application deleted successfully.");
    }
}
