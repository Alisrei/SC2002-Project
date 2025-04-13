package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;


public class Applicant extends User {

    //private BTOProject appliedProject;
    //private FlatType flatTypeBooked;
    private Application application;
    //private ApplicationStatus applicationStatus;
    private List<Enquiry> enquiries;

    public Applicant(String nric, String name, String password, int age, boolean maritalStatus) {
        super(nric, name, password, age, maritalStatus);
        //this.applicationStatus = ApplicationStatus.NOT_APPLIED;
        this.enquiries = new ArrayList<>();
    }

//    public BTOProject getAppliedProject() {
//        return appliedProject;
//    }
    //public FlatType getFlatTypeBooked(){return this.flatTypeBooked;}
    public Application getApplication(){return this.application;}

//    public ApplicationStatus getApplicationStatus() {
//        return applicationStatus;
//    }

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
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
        project.getVisibility();
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
        if (this.getApplication() != null && this.getApplication().getStatus() != ApplicationStatus.NOT_APPLIED) {
            System.out.println("You have already applied for a project.");
            return false;
        }
        if (!isEligibleForProject(project)) {
            System.out.println("You do not meet the eligibility criteria for this project.");
            return false;
        }
        this.application = new Application(this.getName(), this, project);
        //this.appliedProject = project;
        //this.applicationStatus = ApplicationStatus.PENDING;
        System.out.println("Application submitted for project: " + project.getProjectName());
        return true;
    }

//    public void updateApplicationStatus(ApplicationStatus status) {
//        this.applicationStatus = status;
//        System.out.println("Your application status is now: " + status);
//    }

    public boolean withdrawApplication() {
        if (this.application == null) {
            System.out.println("No application to withdraw.");
            return false;
        }
        this.application.deleteApplication();
        this.application = null;
        //applicationStatus = ApplicationStatus.NOT_APPLIED;
        System.out.println("Application withdrawn successfully.");
        return true;
    }

    public boolean bookFlat(FlatType flatType) {
        if (this.application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You are not eligible to book a flat.");
            return false;
        }
        if (this.getApplication().getFlatTypeBooking() != null) {
            System.out.println("You have already booked a flat.");
            return false;
        }

        boolean available = false;
        if (flatType == FlatType.TWOROOM && this.application.getProject().getFlats().getTwoRoomFlats() > 0) {

            //this.application.getProject().getFlats().decreaseTwoRoomFlatCount();
            available = true;
        } else if (flatType == FlatType.THREEROOM && this.application.getProject().getFlats().getThreeRoomFlats() > 0) {
            //this.application.getProject().getFlats().decreaseThreeRoomFlatCount();
            available = true;
        }

        if (!available) {
            System.out.println("Selected flat type is unavailable.");
            return false;
        }

        this.getApplication().setFlatTypeBooking(flatType);
        System.out.println("Successfully created booking for " + flatType.getDescription());
        return true;
    }

    public void submitEnquiry(String enquiryText) {
        Enquiry newEnquiry = new Enquiry(enquiryText, this.getApplication().getProject());
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
