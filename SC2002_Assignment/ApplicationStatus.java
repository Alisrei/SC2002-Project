package SC2002_Assignment;

/**
 * The {@code ApplicationStatus} enum represents the various possible statuses of an application 
 * for a BTO project. This status is used to track the progress of the application from its 
 * submission to its outcome.
 * <p>
 * The possible statuses are:
 * <ul>
 *     <li>{@code PENDING} - The application is still under review and has not been processed.</li>
 *     <li>{@code SUCCESSFUL} - The application has been approved and the applicant is eligible.</li>
 *     <li>{@code UNSUCCESSFUL} - The application has been rejected.</li>
 *     <li>{@code BOOKED} - The applicant has successfully booked a flat unit after a successful application.</li>
 * </ul>
 * </p>
 */
public enum ApplicationStatus {
    
    /**
     * The application is still pending and has not yet been processed or reviewed.
     */
    PENDING,
    
    /**
     * The application has been successful, and the applicant is eligible for further actions.
     */
    SUCCESSFUL,
    
    /**
     * The application was unsuccessful, and the applicant does not meet the eligibility criteria.
     */
    UNSUCCESSFUL,
    
    /**
     * The applicant has successfully booked a flat after the application was successful.
     */
    BOOKED;
}
