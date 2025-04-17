package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Applicant extends User {

    private Application application;
    private List<Enquiry> enquiries;

    public Applicant(String nric, String name, String password, int age, boolean maritalStatus) {
        super(nric, name, password, age, maritalStatus);
        //this.applicationStatus = ApplicationStatus.NOT_APPLIED;
        this.enquiries = new ArrayList<>();
    }

    public Application getApplication(){return this.application;}
    public void setApplication(Application A){this.application = A;}


    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    public void viewProjects(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()){System.out.println("No projects created yet");}
        System.out.println("\n\n******** Eligible BTO Projects ********");
        boolean found = false;
        int i = 1;
        for (BTOProject project : allProjects) {
            if (//project.isWithinApplicationPeriod(java.time.LocalDate.now()) &&
                    //for debug
                    project.getFlatTypes() != null
                    && project.getVisibility()
                    && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                i += 1;
                found = true;
            }
        }
        if(!found){System.out.println("No eligible projects");}
    }
    public BTOProject selectProject(List<BTOProject> allProjects){
        if (allProjects.isEmpty()){
            System.out.println("No projects created yet");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        List<BTOProject> temp = new ArrayList<>();
        System.out.println("Select desired project based on number:");
        int i = 1;
        boolean found = false;
        for (BTOProject project : allProjects) {
            if (//project.isWithinApplicationPeriod(java.time.LocalDate.now()) &&
                //for debug
                    project.getFlatTypes() != null
                            && project.getVisibility()
                            && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                temp.add(project);
                i += 1;
                found = true;
            }
        }
        if(!found){
            System.out.println("No available projects found.");
            return null;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return temp.get(choice-1);
    }

    private boolean isEligibleForProject(BTOProject project) {
        if (getAge() < 21){ return false;}
        if (!isMarried() && getAge() < 35){ return false;}
        if (!isMarried() && getAge() >= 35 && !project.getFlatTypes().contains(FlatType.TWOROOM)) {
            return false;
        }
        return true;
    }

    public boolean applyForProject(BTOProject project) {
        if(project == null){
            System.out.println("Therefore no project to apply for.");
            return false;
        }
        if (!isEligibleForProject(project)) {
            System.out.println("You do not meet the eligibility criteria for this project.");
            return false;
        }
        this.application = new Application(this.getName(), this, project);
        System.out.println("Application submitted for project: " + project.getProjectName());
        return true;
    }

    public boolean withdrawApplication() {
        if (this.application == null) {
            System.out.println("No application to withdraw.");
            return false;
        }
        this.application.setStatus(ApplicationStatus.WITHDRAWAL_REQUESTED);
        System.out.println("Application withdrawal requested successfully.");
        return true;
    }

    public FlatType selectFlatType(){
        Scanner sc = new Scanner(System.in);
        FlatType flatType = null;
        if (this.isMarried()){
            System.out.println("Select room type by entering either 1 0r 2:\n1.Two room\n2.Three room");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    flatType = FlatType.TWOROOM;
                    System.out.println("Two room flat selected.");
                    break;
                case 2:
                    flatType = FlatType.THREEROOM;
                    System.out.println("Three room flat selected.");
                    break;
                default:
                    System.out.println("invalid choice");
            }
        }
        else{flatType = FlatType.TWOROOM;System.out.println("Two room flat assigned.");}
        return flatType;
    }

    public boolean bookFlat() {
        if (this.application == null){
            System.out.println("You have not applied for a project.");
            return false;
        }
        if (this.application.getStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("You are not eligible to book a flat.");
            return false;
        }
        if (this.getApplication().getFlatTypeBooking() != null) {
            System.out.println("You have already booked a flat.");
            return false;
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
            return false;
        }

        this.getApplication().setFlatTypeBooking(flatType);
        System.out.println("Successfully created booking for " + flatType.getDescription());
        return true;
    }

    public void submitEnquiry(List<BTOProject> AP) {
        Scanner sc = new Scanner(System.in);
        BTOProject P = this.selectProject(AP);
        if(P == null){
            return;
        }
        System.out.println("Enter your enquiry:");
        String enquiryText = sc.nextLine();
        Enquiry newEnquiry = new Enquiry(enquiryText, P,this);
        enquiries.add(newEnquiry);
        System.out.println("Enquiry submitted.");
    }

    public int getEnquiryIndex(){
        Scanner sc = new Scanner(System.in);
        int i = 1;
        if(enquiries.isEmpty()){
            System.out.println("print no enquiry found");
            return 0;
        }
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : this.enquiries) {
            System.out.print(i+".");
            enquiry.viewEnq();
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice-1;
    }

    public void viewEnquiries() {
        if(enquiries.isEmpty()) {
            System.out.println("print no enquiry found");
            return;
        }
        for (Enquiry enquiry : this.enquiries) {
            enquiry.viewEnq();
        }
    }

    public void editEnquiry(int index, String newText) {
        if(enquiries.isEmpty()) {
            System.out.println("print no enquiry found");
            return;
        }
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).editEnq(newText);
    }

    public void deleteEnquiry(int index) {
        if(enquiries.isEmpty()) {
            System.out.println("print no enquiry found");
            return;
        }
        if (index < 0 || index >= enquiries.size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        enquiries.get(index).deleteEnq();
        this.enquiries.remove(index);
    }
}
