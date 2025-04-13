import java.util.ArrayList;
import java.util.List;


public class Applicant extends User {

    private BTOProject appliedProject;
    private FlatType flatTypeBooked;
    private ApplicationStatus applicationStatus;
    private List<Enquiry> enquiries;

    public Applicant(String nric, String name, String password, int age, boolean maritalStatus) {
        super(nric, name, password, age, maritalStatus);
        this.applicationStatus = ApplicationStatus.NOT_APPLIED;
        this.enquiries = new ArrayList<>();
    }

    public BTOProject getAppliedProject() {
        return appliedProject;
    }

    public FlatType getFlatTypeBooked() {
        return flatTypeBooked;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void viewProjects(List<BTOProject> allProjects) {
        System.out.println("******** Eligible BTO Projects ********");
        for (BTOProject project : allProjects) {
            if (project.isWithinApplicationPeriod(java.time.LocalDate.now())
                    && project.getFlatTypes() != null
                    && projectVisibilityOn(project)
                    && isEligibleForProject(project)) {
                System.out.println("-> " + project.getProjectName());
            }
        }
    }

    private boolean projectVisibilityOn(BTOProject project) {
        // need help
    	return true;
    }

    private boolean isEligibleForProject(BTOProject project) {
        if (getAge() < 21) return false;
        if (!isMarried() && getAge() < 35) return false;
        if (!isMarried() && getAge() >= 35 && !project.getFlatTypes().contains(FlatType.TWOROOM)) {
            return false;
        }
        return true;
    }

    public boolean applyForProject(BTOProject project) {
        if (applicationStatus != ApplicationStatus.NOT_APPLIED) {
            System.out.println("You have already applied for a project.");
            return false;
        }
        if (!isEligibleForProject(project)) {
            System.out.println("You do not meet the eligibility criteria for this project.");
            return false;
        }
        this.appliedProject = project;
        this.applicationStatus = ApplicationStatus.PENDING;
        System.out.println("Application submitted for project: " + project.getProjectName());
        return true;
    }

    public void updateApplicationStatus(ApplicationStatus status) {
        this.applicationStatus = status;
        System.out.println("Your application status is now: " + status);
    }

    public boolean withdrawApplication() {
        if (applicationStatus == ApplicationStatus.NOT_APPLIED) {
            System.out.println("No application to withdraw.");
            return false;
        }
        appliedProject = null;
        applicationStatus = ApplicationStatus.NOT_APPLIED;
        flatTypeBooked = null;
        System.out.println("Application withdrawn successfully.");
        return true;
    }

    public boolean bookFlat(FlatType flatType) {
        if (applicationStatus != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You are not eligible to book a flat.");
            return false;
        }
        if (flatTypeBooked != null) {
            System.out.println("You have already booked a flat.");
            return false;
        }

        boolean available = false;
        if (flatType == FlatType.TWOROOM && appliedProject.getFlats().getTwoRoomFlats() > 0) {
            appliedProject.getFlats().decreaseTwoRoomFlatCount();
            available = true;
        } else if (flatType == FlatType.THREEROOM && appliedProject.getFlats().getThreeRoomFlats() > 0) {
            appliedProject.getFlats().decreaseThreeRoomFlatCount();
            available = true;
        }

        if (!available) {
            System.out.println("Selected flat type is unavailable.");
            return false;
        }

        this.flatTypeBooked = flatType;
        this.applicationStatus = ApplicationStatus.BOOKED;
        System.out.println("Successfully booked a " + flatType.getDescription());
        return true;
    }

    public void submitEnquiry(String enquiryText) {
        Enquiry newEnquiry = new Enquiry(enquiryText, appliedProject);
        enquiries.add(newEnquiry);
        System.out.println("Enquiry submitted.");
    }

    public void viewEnquiries() {
        for (Enquiry enquiry : enquiries) {
            enquiry.viewEnq();
        }
    }

    public void editEnquiry(int index, String newText) {
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).editEnq(newText);
    }

    public void deleteEnquiry(int index) {
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).deleteEnq();
    }
}
