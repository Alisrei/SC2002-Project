package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Applicant} class represents a user who is applying for a Build-To-Order (BTO) project.
 * It extends the {@code User} class and implements the interfaces {@code ViewProjects}, {@code EnquiryCreateEditDelete}, and {@code EnquiryView}.
 * This class handles functionalities like viewing projects, applying for projects, booking flats, and managing enquiries.
 * 
 * <p>An applicant can view eligible BTO projects, apply for a project, withdraw an application, book a flat based on application status,
 * and create, edit, or delete enquiries related to the BTO projects.</p>
 */
public class Applicant extends User implements ViewProjects, EnquiryCreateEditDelete, EnquiryView {

    private Application application;
    private List<Enquiry> enquiries;

    /**
     * Constructs a new {@code Applicant} with the specified user details.
     * 
     * @param nric The NRIC of the applicant.
     * @param name The name of the applicant.
     * @param password The password for the applicant.
     * @param age The age of the applicant.
     * @param maritalStatus The marital status of the applicant.
     */
    public Applicant(String nric, String name, String password, int age, boolean maritalStatus) {
        super(nric, name, password, age, maritalStatus);
        this.enquiries = new ArrayList<>();
    }

    // Getters

    /**
     * Returns the application associated with the applicant.
     * 
     * @return The application object.
     */
    public Application getApplication() {
        return this.application;
    }

    /**
     * Returns the list of enquiries created by the applicant.
     * 
     * @return The list of {@code Enquiry} objects.
     */
    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    // Setters

    /**
     * Sets the application for the applicant.
     * 
     * @param A The application to set.
     */
    public void setApplication(Application A) {
        this.application = A;
    }

    // Project-related Methods

    /**
     * Displays all the eligible BTO projects available for the applicant.
     * The eligibility is based on the application period, visibility, and the applicant's criteria.
     * 
     * @param allProjects A list of all BTO projects to check for eligibility.
     */
    public void viewProjects(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects created yet");
            return;
        }
        System.out.println("\n\n******** Eligible BTO Projects ********");
        boolean found = false;
        int i = 1;
        for (BTOProject project : allProjects) {
            if (project.isWithinApplicationPeriod(java.time.LocalDate.now()) &&
                project.getFlatTypes() != null &&
                project.getVisibility() &&
                isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                i += 1;
                found = true;
            }
        }
        if (!found) {
            System.out.println("No eligible projects");
        }
    }

    /**
     * Allows the applicant to select a project from a list of available BTO projects.
     * 
     * @param allProjects A list of all BTO projects.
     * @return The selected {@code BTOProject}, or {@code null} if no projects are available.
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
            if (project.getFlatTypes() != null &&
                project.getVisibility() &&
                isEligibleForProject(project)) {
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
     * Determines if the applicant is eligible for a particular BTO project.
     * 
     * @param project The BTO project to check eligibility against.
     * @return {@code true} if the applicant is eligible for the project, otherwise {@code false}.
     */
    private boolean isEligibleForProject(BTOProject project) {
        if (getAge() < 21) {
            return false;
        }
        if (!isMarried() && getAge() < 35) {
            return false;
        }
        return isMarried() || getAge() < 35 || project.getFlatTypes().contains(FlatType.TWOROOM);
    }

    // Application and Withdrawal Methods

    /**
     * Applies for a selected BTO project if the applicant meets the eligibility criteria.
     * 
     * @param project The BTO project to apply for.
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
     * Withdraws the applicant's current application if it exists.
     */
    public void withdrawApplication() {
        if (this.application == null) {
            System.out.println("No application to withdraw.");
            return;
        }
        this.application.setWithdrawalRequested(true);
        System.out.println("Application withdrawal requested successfully.");
    }

    // Flat Booking Methods

    /**
     * Allows the applicant to book a flat based on their application status.
     * 
     * @throws IllegalStateException if the application status is not successful.
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
     * Allows the applicant to select the type of flat they wish to book.
     * 
     * @return The selected {@code FlatType}.
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
                    System.out.println("Invalid choice");
            }
        } else {
            flatType = FlatType.TWOROOM;
            System.out.println("Two room flat assigned.");
        }
        return flatType;
    }

    // Enquiry Methods

    /**
     * Submits a new enquiry related to a BTO project.
     * 
     * @param AP A list of available BTO projects to choose from.
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
     * Allows the applicant to view all their submitted enquiries.
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
     * Allows the applicant to edit an existing enquiry by specifying the new enquiry text.
     * 
     * @param index The index of the enquiry to edit.
     * @param newText The new text for the enquiry.
     */
    public void editEnquiry(int index, String newText) {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiry found");
            return;
        }
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).editEnq(newText);
    }

    /**
     * Deletes an enquiry from the list of submitted enquiries.
     * 
     * @param index The index of the enquiry to delete.
     */
    public void deleteEnquiry(int index) {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiry found");
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
