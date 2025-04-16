package SC2002_Assignment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HDBManager extends User{
    private List<BTOProject> projects;

    public HDBManager(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried);
        this.projects = new ArrayList<>();
    }

    public void createProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                              LocalDate applicationCloseDate, List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, Boolean V) {
        BTOProject newProject = new BTOProject(projectName, neighborhood, applicationOpenDate, applicationCloseDate, this, flatTypes, twoRoomFlats, threeRoomFlats, V);
        projects.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }
    public void editProjectname(BTOProject project, String newName) {
        project.setProjectName(newName);
        System.out.println("project name changed successfully");
        return;
        }
    public void editProject(BTOProject project, String newNeighborhood) {
        project.setNeighborhood(newNeighborhood);
        System.out.println("project neighbourhood changed successfully");
        return;
        }
    public void editProject(BTOProject project, LocalDate newApplicationOpenDate, LocalDate newApplicationCloseDate) {
        project.setApplicationOpenDate(newApplicationOpenDate);
        project.setApplicationCloseDate(newApplicationCloseDate);
        System.out.println("application open and close dates changed successfully");
        return;
    }

    //do we need this?
    public void editProject(BTOProject project, List<FlatType> newFlatTypes, int twoRoomFlats, int threeRoomFlats) {
        project.setFlatTypes(newFlatTypes);
        project.getFlats().setTwoRoomFlats(twoRoomFlats);
        project.getFlats().setThreeRoomFlats(threeRoomFlats);
        System.out.println("Project flat types updated successfully.");
        return;
    }
    public void editProject(BTOProject project, boolean v) {
        project.setVisibility(v);
        System.out.print("Project visibility set to:");
        if(v){
            System.out.println("On");
        }
        else {
            System.out.println("Off");
        }
        return;
    }

    public void deleteProject(BTOProject project) {
        projects.remove(project);
        project = null;
        System.out.println("Project " + project.getProjectName() + " deleted successfully.");
    }//incomplete

    public void addProject(BTOProject p){
        this.projects.add(p);
    }

    public void viewAllProjects(List<BTOProject> projectsMaster){
        int i = 1;
        for (BTOProject P: projectsMaster){
            System.out.print(i+".");
            System.out.println(P.getProjectName());
            i += 1;
        }
    }
    public void viewPersornalProjects(){
        int i = 1;
        for (BTOProject P: this.projects){
            System.out.print(i+".");
            System.out.println(P.getProjectName());
            i += 1;
        }
    }
    public BTOProject getProject(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the project based on number:");
        viewPersornalProjects();
        int choice = sc.nextInt();
        sc.nextLine();
        return projects.get(choice-1);
    }

    public void viewApplications(BTOProject P){
        int i = 1;
        for (Application A: P.getApplications()){
            System.out.print(i+".");
            System.out.println(A.getApplicationId());
            i += 1;
        }
    }
    public void viewPendingApplications(BTOProject P){
        int i = 1;
        for (Application A: P.getApplications()){
            if(A.getStatus().equals(ApplicationStatus.PENDING)){
                System.out.print(i+".");
                System.out.println(A.getApplicationId());
                i += 1;
            }
        }
    }

    public Application getApplication(BTOProject P){
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        viewPendingApplications(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return P.getApplications().get(choice-1);
    }

    public void viewPendingWithdrawals(BTOProject P){
        int i = 1;
        for (Application A: P.getApplications()){
            if(A.getStatus().equals(ApplicationStatus.WITHDRAWAL_REQUESTED)){
                System.out.print(i+".");
                System.out.println(A.getApplicationId());
                i += 1;
            }
        }
    }
    public Application getWithdrawals(BTOProject P){
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        viewPendingWithdrawals(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return P.getApplications().get(choice-1);
    }
    public void  manageWithdrawals(){
        Scanner sc = new Scanner(System.in);
        Application W = this.getApplication(this.getProject());
        System.out.println("select choice based on number:");
        System.out.println("1. Approve withdrawal\n2. Reject withdrawal");
        int choice = sc.nextInt();
        switch (choice){
            case 1:
                W.setStatus(ApplicationStatus.WITHDRAWAL_APPROVED);
                W.getApplicant().setApplication(null);
                W.deleteApplication();
                System.out.println("Withdrawal approved and deleted");
                break;
            case 2:
                W.setStatus(ApplicationStatus.WITHDRAWAL_REJECTED);
                System.out.println("Withdrawal denied");
                break;
            default:
                System.out.println("invalid choice");
                break;
        }
    }

    public void viewRegistrations(BTOProject P){
        int i = 1;
        for (Registration R: P.getRegistrations()){
            System.out.print(i+".");
            System.out.println(R.getRegId());
            i += 1;
        }
    }
    public void viewUnacceptedRegistrations(BTOProject P){
        int i = 1;
        for (Registration R: P.getRegistrations()){
            if(R.getAccepted() == Boolean.FALSE){
                System.out.print(i+".");
                System.out.println(R.getRegId());
                i += 1;
            }
        }
    }
    public Registration getRegistrations(BTOProject P){
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the Registration based on number:");
        viewUnacceptedRegistrations(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return P.getRegistrations().get(choice-1);
    }

    public void approveRegistration() {
        Registration R = this.getRegistrations(this.getProject());
        if(R.getProject().addOfficer(R.getOfficer())){
            R.setAccepted(Boolean.TRUE);
            System.out.println("Officer " + R.getOfficer().getName() + " approved for project " + R.getProject().getProjectName() + ".");
        }
        else{
            System.out.println("Officer " + R.getOfficer().getName() + " could not be approved for project " + R.getProject().getProjectName() + ".");
        }
    }

    public void approveApplication() {
        Application A = this.getApplication(this.getProject());
        if (A.getProject().getFlats().getTwoRoomFlats() + A.getProject().getFlats().getThreeRoomFlats() > 0) {
            A.setStatus(ApplicationStatus.SUCCESSFUL);
            System.out.println("Application " + A.getApplicationId() + " approved.");
        } else {
            A.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application " + A.getApplicationId() + " rejected.");
        }
    }

    public int getEnquiryIndex(){
        Scanner sc = new Scanner(System.in);
        int i = 1;
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : this.getProject().getEnquiries()) {
            System.out.print(i+".");
            enquiry.viewEnq();
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice-1;
    }

    public void viewAllEnquiries(List<Enquiry> AllEnquiries){
        for (Enquiry enquiry : AllEnquiries) {
            enquiry.viewEnq();
        }
    }

    public void viewEnquiries() {
        for (Enquiry enquiry : this.getProject().getEnquiries()) {
            enquiry.viewEnq();
        }
    }

    public void replyEnquiry(int index, String newText) {
        if (index < 0 || index >= this.getProject().getEnquiries().size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        this.getProject().getEnquiries().get(index).replyToEnq(newText);
    }

    public void generateReport() {
        for (BTOProject project : projects) {
            System.out.println("Project: " + project.getProjectName());
            project.displayProjectDetails();
        }
    }
}
