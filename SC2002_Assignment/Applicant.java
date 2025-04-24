package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents an applicant in the HDB BTO system. The applicant can view available projects, 
 * apply for projects, withdraw applications, book flats, and create, edit, view, and delete enquiries.
 * The applicant inherits from the {@link User} class and implements several interfaces such as {@link ViewProjects},
 * {@link EnquiryCreateEditDelete}, and {@link EnquiryView}.
 */
public class Applicant extends User implements ViewProjects, EnquiryCreateEditDelete, EnquiryView {

    private Application application;
    private List<Enquiry> enquiries;

    /**
     * Constructs an applicant with the specified details.
     *
     * @param nric The NRIC of the applicant.
     * @param name The name of the applicant.
     * @param password The password for the applicant's account.
     * @param age The age of the applicant.
     * @param maritalStatus The marital status of the applicant.
     */
    public Applicant(String nric, String name, String password, int age, boolean maritalStatus) {
        super(nric, name, password, age, maritalStatus);
        this.enquiries = new ArrayList<>();
    }

    // Getters and Setters

    /**
     * Gets the application of the applicant.
     *
     * @return The application associated with the applicant.
     */
    public Application getApplication() { return this.application; }

    /**
     * Gets the list of enquiries made by the applicant.
     *
     * @return The list of enquiries.
     */
    public List<Enquiry> getEnquiries() { return this.enquiries; }

    /**
     * Sets the application for the applicant.
     *
     * @param A The application to set.
     */
    public void setApplication(Application A) { this.application = A; }

    // Applicant project methods

    /**
     * Views eligible BTO projects that the applicant can apply for.
     *
     * @param allProjects A list of all available BTO projects.
     */
    public void viewProjects(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects created yet");
        }
        System.out.println("\n\n******** Eligible BTO Projects ********");
        boolean found = false;
        int i = 1;
        for (BTOProject project : allProjects) {
            if (project.isWithinApplicationPeriod(java.time.LocalDate.now()) &&
                    project.getFlatTypes() != null && project.getVisibility() && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                i += 1;
                found = true;
            }
        }
        if (!found) { System.out.println("No eligible projects"); }
    }

    /**
     * Selects a project from the list of available projects.
     *
     * @param allProjects A list of available BTO projects.
     * @return The selected BTO project.
     */
    public BTOProject selectProject(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects created yet");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        List<BTOProject> temp = new ArrayList<>();
        System.out.println("Select desired project based on number:");
        int i = 1;
        boolean found = false;
        for (BTOProject project : allProjects) {
            if (project.getFlatTypes() != null && project.getVisibility() && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                temp.add(project);
                i += 1;
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available projects found.");
            return null;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return temp.get(choice - 1);
    }

    /**
     * Checks if the applicant is eligible for a specific project based on age and marital status.
     *
     * @param project The BTO project to check eligibility for.
     * @return True if the applicant is eligible for the project; false otherwise.
     */
    private boolean isEligibleForProject(BTOProject project) {
        if (getAge() < 21) { return false; }
        if (!isMarried() && getAge() < 35) { return false; }
        return isMarried() || getAge() < 35 || project.getFlatTypes().contains(FlatType.TWOROOM);
    }

    // Project application & withdrawal

    /**
     * Applies for a BTO project if the applicant is eligible and has not already applied.
     *
     * @param project The BTO project the applicant wants to apply for.
     */
    public void applyForProject(BTOProject project) {
        if (project == null) {
            System.out.println("Therefore no project to apply for.");
            return;
        }
        if (!isEligibleForProject(project)) {
            System.out.println("You do not meet the eligibility criteria for this project.");
            return;
        }
        this.application = new Application(this.getName(), this, project);
        System.out.println("Application submitted for project: " + project.getProjectName());
    }

    /**
     * Withdraws the applicant's current application if one exists.
     */
    public void withdrawApplication() {
        if (this.application == null) {
            System.out.println("No application to withdraw.");
            return;
        }
        this.application.setWithdrawalRequested(true);
        System.out.println("Application withdrawal requested successfully.");
    }

    // Flat booking methods

    /**
     * Books a flat for the applicant if they have successfully applied for a project.
     */
    public void bookFlat() {
        if (this.application == null) {
            System.out.println("You have not applied for a project.");
            return;
        }
        if (this.application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You are not eligible to book a flat.");
            return;
        }
        if (this.getApplication().getFlatTypeBooking() != null) {
            System.out.println("You have already booked a flat.");
            return;
        }

        boolean available = false;
        FlatType flatType = selectFlatType();
        if (flatType == FlatType.TWOROOM && this.application.getProject().getFlats().getTwoRoomFlats() > 0) {
            available = true;
        } else if (flatType == FlatType.THREEROOM && this.application.getProject().getFlats().getThreeRoomFlats() > 0) {
            available = true;
        }

        if (!available) {
            System.out.println("Selected flat type is unavailable.");
            return;
        }

        this.getApplication().setFlatTypeBooking(flatType);
        System.out.println("Successfully created booking for " + flatType.getDescription());
    }

    /**
     * Selects a flat type based on the applicant's marital status.
     *
     * @return The selected flat type.
     */
    public FlatType selectFlatType() {
        Scanner sc = new Scanner(System.in);
        FlatType flatType = null;
        if (this.isMarried()) {
            System.out.println("Select room type by entering either 1 or 2:\n1.Two room\n2.Three room");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    flatType = FlatType.TWOROOM;
                    System.out.println("Two room flat selected.");
                    break;
                case 2:
                    flatType = FlatType.THREEROOM;
                    System.out.println("Three room flat selected.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            flatType = FlatType.TWOROOM;
            System.out.println("Two room flat assigned.");
        }
        return flatType;
    }

    // Enquiry methods

    /**
     * Submits a new enquiry for a selected BTO project.
     *
     * @param AP The list of available BTO projects for the enquiry.
     */
    public void submitEnquiry(List<BTOProject> AP) {
        Scanner sc = new Scanner(System.in);
        BTOProject P = this.selectProject(AP);
        if (P == null) {
            return;
        }
        System.out.println("Enter your enquiry:");
        String enquiryText = sc.nextLine();
        Enquiry newEnquiry = new Enquiry(enquiryText, P, this);
        enquiries.add(newEnquiry);
        System.out.println("Enquiry submitted.");
    }

    /**
     * Displays the list of enquiries made by the applicant.
     *
     * @return The index of the selected enquiry.
     */
    public int getEnquiryIndex() {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return -1;
        }
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : this.enquiries) {
            System.out.print(i + ".");
            enquiry.viewEnq();
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice - 1;
    }

    /**
     * Views all enquiries made by the applicant.
     */
    public void viewEnquiries() {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiry found");
            return;
        }
        for (Enquiry enquiry : this.enquiries) {
            enquiry.viewEnq();
        }
    }

    /**
     * Edits an enquiry at a specific index with the new provided text.
     *
     * @param index The index of the enquiry to edit.
     * @param newText The new text to update the enquiry.
     */
    public void editEnquiry(int index, String newText) {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).editEnq(newText);
    }

    /**
     * Deletes an enquiry at the specified index.
     *
     * @param index The index of the enquiry to delete.
     */
    public void deleteEnquiry(int index) {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        if (enquiries.get(index).deleteEnq()) {
            this.enquiries.remove(index);
        }
    }
}
