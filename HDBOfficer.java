package SC2002_Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HDBOfficer extends Applicant{
    private BTOProject assignedProject;
    private Registration registration;
    private boolean registrationApproved;


    public HDBOfficer(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried); // calls the user constructor
    }

    //getters
    public BTOProject getAssignedProject(){return this.assignedProject;}
    public Registration getRegistration(){return this.registration;}
    //setters
    public void setAssignedProject(BTOProject assignedProject) {this.assignedProject = assignedProject;}

    public boolean registerAsOfficer(BTOProject project) {
        if (this.assignedProject != null) {
            System.out.println("You are already assigned to a project.");
            return false;
        }
        else if(this.getApplication().getProject().equals(project)){
            System.out.println("You are already applying for this project as an applicant.");
            return false;
        }
        else {
            this.registration = new Registration(this.getName(),this,project);
            project.addRegistration(this.registration);
            this.setAssignedProject(project);
            System.out.println("Registration request created");
            return true;
        }
    }
    public BTOProject selectProjectforRegistration(List<BTOProject> allprojects){
        Scanner sc = new Scanner(System.in);
        int i = 1;
        for (BTOProject P :allprojects){
            System.out.print(i + ".");
            System.out.println(P.getProjectName());
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return allprojects.get(choice - 1);
    }

    public void viewAssignedProjectDetails() {
        if (assignedProject != null) {
            assignedProject.displayProjectDetails();
        } else {
            System.out.println("No assigned project.");
        }
    }
    public void viewAssignedProjectApplicationsForBooking(){
        if (assignedProject != null) {
            int i = 1;
            for(Application A : assignedProject.getApplications()){
                if(A.getStatus() == ApplicationStatus.SUCCESSFUL){
                    System.out.print(i+".");
                    A.displayApplication();
                    i += 1;
                }
            }
        }
        else {System.out.println("No assigned project.");}
    }
    public Application selectApplicationforBooking(){
        if(assignedProject != null){
            Scanner sc = new Scanner(System.in);
            System.out.println("Pick application based on number:");
            this.viewAssignedProjectApplicationsForBooking();
            int choice = sc.nextInt();
            List<Application> selection = new ArrayList<>();
            for(Application A : assignedProject.getApplications()){
                if(A.getStatus() == ApplicationStatus.SUCCESSFUL){
                    selection.add(A);
                }
            }
            return selection.get(choice-1);
        }
        else {
            System.out.println("No assigned project");
            return null;
        }

    }
    public String selectunit(Application A){
        if(assignedProject != null){
            Scanner sc = new Scanner(System.in);
            if(A.getApplicant().isMarried()){
                int i = 1;
                System.out.println("Select room based on number:");
                for(String N : this.assignedProject.getFlats().getAvailableFlats()){
                    System.out.print(i+".");
                    System.out.println(N);
                    i += 1;
                }
                int choice = sc.nextInt();
                return this.assignedProject.getFlats().getAvailableFlats().get(choice-1);
            }
            else {
                List<String> tworooms = new ArrayList<>();
                int i = 1;
                System.out.println("Select room based on number:");
                for(String N : this.assignedProject.getFlats().getAvailableFlats()) {
                    if(N.substring(0,2).equals("2R")){
                        System.out.print(i + ".");
                        System.out.println(N);
                        tworooms.add(N);
                        i += 1;
                    }
                }
                int choice = sc.nextInt();
                return tworooms.get(choice-1);
            }

        }
        else{
            System.out.println("No assigned project");
            return null;
        }


    }

    public void handleFlatBooking() {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
        Application A = this.selectApplicationforBooking();
        String unitNumber = this.selectunit(A);
        boolean success = assignedProject.bookFlat(unitNumber);
        if (!success) {
            System.out.println("Booking failed. Please check the unit number.");
        }
        else{
            A.setStatus(ApplicationStatus.BOOKED);
        }

    }

    public int getEnquiryIndex(){
        Scanner sc = new Scanner(System.in);
        int i = 1;
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : this.getAssignedProject().getEnquiries()) {
            System.out.print(i+".");
            enquiry.viewEnq();
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice-1;
    }

    public void viewEnquiries() {
        for (Enquiry enquiry : this.getAssignedProject().getEnquiries()) {
            enquiry.viewEnq();
        }
    }

    public void replyEnquiry(int index, String newText) {
        if (index < 0 || index >= this.getAssignedProject().getEnquiries().size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        this.getAssignedProject().getEnquiries().get(index).replyToEnq(newText);
    }
    public boolean isOfficerOfProject(BTOProject project) {
        return assignedProject != null && assignedProject.equals(project);
    }



    //@Override
    //public String toString() {
    //    return getName() + " (" + getNRIC() + ")";
    //}

}
