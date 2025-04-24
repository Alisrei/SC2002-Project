package SC2002_Assignment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HDBManager extends User implements ViewProjects,EnquiryReply, EnquiryView{
    private List<BTOProject> projects;

    //constructor
    public HDBManager(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried);
        this.projects = new ArrayList<>();
    }



    //project management
    public void createProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                              LocalDate applicationCloseDate, List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP) {
        boolean inApplicationPeriod = false;
        for(BTOProject P : this.projects){
            inApplicationPeriod = P.isWithinApplicationPeriod(LocalDate.now());
        }
        if(inApplicationPeriod){
            System.out.println("currently in application period for another project, unable to create new project");
            return;
        }
        BTOProject newProject = new BTOProject(projectName, neighborhood, applicationOpenDate, applicationCloseDate, this, flatTypes, twoRoomFlats, threeRoomFlats, twoP, threeP);
        projects.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }
    public void editProjectname(BTOProject project, String newName) {
        project.setProjectName(newName);
        System.out.println("project name changed successfully");
        }
    public void editProject(BTOProject project, String newNeighborhood) {
        project.setNeighborhood(newNeighborhood);
        System.out.println("project neighbourhood changed successfully");
        }
    public void editProject(BTOProject project, LocalDate newApplicationOpenDate, LocalDate newApplicationCloseDate) {
        project.setApplicationOpenDate(newApplicationOpenDate);
        project.setApplicationCloseDate(newApplicationCloseDate);
        System.out.println("application open and close dates changed successfully");

    }
    public void editProject(BTOProject project, List<FlatType> newFlatTypes, int twoRoomFlats, int threeRoomFlats) {
        project.setFlatTypes(newFlatTypes);
        project.getFlats().setTwoRoomFlats(twoRoomFlats);
        project.getFlats().setThreeRoomFlats(threeRoomFlats);
        System.out.println("Project flat types updated successfully.");
    }//do we need this?
    public void editProject(BTOProject project, int TwoP, int ThreeP){
        project.getFlats().setTwoRoomPrice(TwoP);
        project.getFlats().setThreeRoomFlats(ThreeP);
        System.out.println("Project prices updated successfully.");
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
    }
    public void deleteProject(BTOProject project) {
        if(project.deletable()){
            projects.remove(project);
            project.setVisibility(null);
            project.setFlats(null);
            project.setNeighborhood(null);
            project.setFlatTypes(null);
            project.setApplicationCloseDate(null);
            project.setApplicationOpenDate(null);
            project.setAssignedOfficers(null);
            project.setManagerInCharge(null);
            System.out.println("Project " + project.getProjectName() + " deleted successfully.");
            project.setProjectName(null);
        }

    }
    public void addProject(BTOProject p){
        this.projects.add(p);
    }
    public List<BTOProject> getProjects(){return this.projects;}
    public BTOProject getProject(){
        if(this.projects.isEmpty()){
            System.out.println("No managed projects");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the project based on number:");
        viewPersornalProjects();
        int choice = sc.nextInt();
        sc.nextLine();
        return projects.get(choice-1);
    }

    //view projects
    public void viewProjects(List<BTOProject> projectsMaster){
        if (projectsMaster.isEmpty()){
            System.out.println("no projects yet");
            return;
        }
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




    //applications func
    public void viewApplications(BTOProject P){
        if(P == null){
            return;
        }
        if(P.getApplications().isEmpty()){
            System.out.println("Project has no applications yet.");
            return;
        }
        int i = 1;
        for (Application A: P.getApplications()){
            System.out.print(i+".");
            System.out.println(A.getApplicationId());
            i += 1;
        }
    }
    public List<Application> viewPendingApplications(BTOProject P){
        if(P == null){
            return null;
        }
        if(P.getApplications().isEmpty()){
            System.out.println("Project has no applications yet.");
            return null;
        }
        int i = 1;
        boolean found = false;
        List<Application> PA = new ArrayList<>();
        for (Application A: P.getApplications()){
            if(A.getStatus().equals(ApplicationStatus.PENDING)){
                System.out.print(i+".");
                System.out.println(A.getApplicationId());
                PA.add(A);
                i += 1;
                found = true;
            }
        }
        if (!found){
            System.out.println("1. No pending applications in project");
            return null;
        }
        return PA;
    }
    public Application getApplication(BTOProject P){
        if(P.getApplications().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        List<Application> PA = viewPendingApplications(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return PA.get(choice-1);
    }
    public void approveApplication() {
        Application A = this.getApplication(this.getProject());
        if(A == null){
            System.out.println("no applications for the project available");
            return;
        }
        if (A.getProject().getFlats().getTwoRoomFlats() + A.getProject().getFlats().getThreeRoomFlats() > 0) {
            A.setStatus(ApplicationStatus.SUCCESSFUL);
            System.out.println("Application " + A.getApplicationId() + " approved.");
        }
        else {
            A.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application " + A.getApplicationId() + " rejected.");
        }
    }

    //withdrawals func
    public List<Application> viewPendingWithdrawals(BTOProject P){
        if(P == null){
            return null;
        }
        if(P.getApplications().isEmpty()){
            System.out.println("Project has no applications yet.");
            return null;
        }
        boolean found = false;
        List<Application> PW = new ArrayList<>();
        int i = 1;
        for (Application A: P.getApplications()){
            if(A.getWithdrawalRequested()){
                System.out.print(i+".");
                System.out.println(A.getApplicationId());
                PW.add(A);
                i += 1;
            }
        }
        if (!found){
            System.out.println("1. No pending Withdrawals in project");
            return null;
        }
        return PW;
    }
    public Application getWithdrawals(BTOProject P){
        if(P.getApplications().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        List<Application> PW = viewPendingWithdrawals(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return PW.get(choice-1);
    }
    public void  manageWithdrawals(){
        Scanner sc = new Scanner(System.in);
        Application W = this.getWithdrawals(this.getProject());
        if(W == null){
            System.out.println("No withdrawals for the project available");
            return;
        }
        System.out.println("select choice based on number:");
        System.out.println("1. Approve withdrawal\n2. Reject withdrawal");
        int choice = sc.nextInt();
        switch (choice){
            case 1:
                W.getApplicant().setApplication(null);
                W.deleteApplication();
                System.out.println("Withdrawal approved and deleted");
                break;
            case 2:
                W.setWithdrawalRequested(false);
                System.out.println("Withdrawal denied");
                break;
            default:
                System.out.println("invalid choice");
                break;
        }
    }

    //Registration func
    public void viewRegistrations(BTOProject P){
        if(P == null){
            return;
        }
        if(P.getRegistrations().isEmpty()){
            System.out.println("Project has no Registrations yet.");
            return;
        }
        int i = 1;
        for (Registration R: P.getRegistrations()){
            System.out.print(i+".");
            System.out.println(R.getRegId());
            i += 1;
        }
    }
    public void viewUnacceptedRegistrations(BTOProject P){
        if(P == null){
            return;
        }
        if(P.getRegistrations().isEmpty()){
            System.out.println("Project has no Registrations yet.");
            return;
        }
        boolean found = false;
        List<Registration> UR = new ArrayList<>();
        int i = 1;
        for (Registration R: P.getRegistrations()){
            if(R.getAccepted() == Boolean.FALSE){
                System.out.print(i+".");
                System.out.println(R.getRegId());
                UR.add(R);
                i += 1;
                found = true;
            }
        }
        if (!found){
            System.out.println("1. No pending Withdrawals in project");
        }
    }
    public Registration getRegistrations(BTOProject P){
        if(P.getRegistrations().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the Registration based on number:");
        viewUnacceptedRegistrations(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return P.getRegistrations().get(choice-1);
    }
    public void approveRegistration() {
        Registration R = this.getRegistrations(this.getProject());
        if(R == null){
            System.out.println("No registrations for the project available");
            return;
        }
        if(R.getProject().addOfficer(R.getOfficer())){
            R.setAccepted(Boolean.TRUE);
            System.out.println("Officer " + R.getOfficer().getName() + " approved for project " + R.getProject().getProjectName() + ".");
        }
        else{
            System.out.println("Officer " + R.getOfficer().getName() + " could not be approved for project " + R.getProject().getProjectName() + ".");
        }
    }


    //enq func
    public int getEnquiryIndex(BTOProject P){
        Scanner sc = new Scanner(System.in);
        int i = 1;
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : P.getEnquiries()) {
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
    public void replyEnquiry(int index,BTOProject P) {
        if (index < 0 || index >= P.getEnquiries().size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        if(P.getEnquiries().get(index).getReplied()){
            System.out.println("Enquiry has already been replied to");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your reply to the enquiry:");
        String R = sc.nextLine();
        P.getEnquiries().get(index).replyToEnq(R);
    }

    //report
    public void generateReport() {
        for (BTOProject project : projects) {
            System.out.println("Project: " + project.getProjectName());
            project.displayProjectDetails();
        }
    }


    public void viewMyProjects() {
        System.out.println("=== Projects Created by " + this.getName() + " ===");
        BTOProject[] myProjects = null;
        for (BTOProject p : myProjects) {
            System.out.println("- " + p.getProjectName());
        }
    }

    public List<BTOProject> filterMyProjects(String filterType, String value) {
        List<BTOProject> filteredProjects = new ArrayList<>();

        // Assuming 'myProjects' is a method or a field that retrieves all the projects
        Collection<BTOProject> myProjects =this.projects; // Replace this with your actual source

        if (myProjects == null) {
            return filteredProjects; // Return empty list if source is null
        }

        switch (filterType.toLowerCase()) {
            case "name":
                filteredProjects = myProjects.stream()
                        .filter(p -> p.getProjectName() != null &&
                                p.getProjectName().toLowerCase().contains(value.toLowerCase()))
                        .collect(Collectors.toList());
                break;

            case "neighborhood":
                filteredProjects = myProjects.stream()
                        .filter(p -> p.getNeighborhood() != null &&
                                p.getNeighborhood().toLowerCase().contains(value.toLowerCase()))
                        .collect(Collectors.toList());
                break;

            case "open":
                LocalDate now = LocalDate.now();
                filteredProjects = myProjects.stream()
                        .filter(p -> p.getApplicationOpenDate() != null && p.getApplicationCloseDate() != null &&
                                (now.isEqual(p.getApplicationOpenDate()) || now.isAfter(p.getApplicationOpenDate())) &&
                                (now.isBefore(p.getApplicationCloseDate()) || now.isEqual(p.getApplicationCloseDate())))
                        .collect(Collectors.toList());
                break;

            default:
                filteredProjects = new ArrayList<>(myProjects); // If no valid filter, return all
        }

        return filteredProjects;
    }

    /**
     * Display filtered projects with a menu interface
     */
    public void displayFilteredProjects() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n=== Filter My Projects ===");
        System.out.println("1. Filter by project name");
        System.out.println("2. Filter by neighborhood");
        System.out.println("3. Show only currently open projects");
        System.out.println("4. Show all my projects");
        System.out.println("0. Back to main menu");
        
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        List<BTOProject> filtered = new ArrayList<>();
        
        switch (choice) {
            case 1:
                System.out.print("Enter project name to search: ");
                String name = sc.nextLine();
                filtered = filterMyProjects("name", name);
                break;
            case 2:
                System.out.print("Enter neighborhood: ");
                String neighborhood = sc.nextLine();
                filtered = filterMyProjects("neighborhood", neighborhood);
                break;
            case 3:
                filtered = filterMyProjects("open", null);
                break;
            case 4:
                filtered = filterMyProjects(null, null);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        displayProjectList(filtered);
    }
    
    /**
     * Helper method to display a list of projects
     * @param projectList List of projects to display
     */
    private void displayProjectList(List<BTOProject> projectList) {
        if (projectList.isEmpty()) {
            System.out.println("No projects found matching your criteria.");
            return;
        }
        
        System.out.println("\n=== Filtered Projects ===");
        int i = 1;
        for (BTOProject project : projectList) {
            System.out.println(i + ". " + project.getProjectName() + " (" + project.getNeighborhood() + ")");
            i++;
        }
    }
    
    /**
     * Generate a detailed report of applicants for a specific project
     * @param project The project to generate report for
     */
    public void generateDetailedReport(BTOProject project) {
        if (project == null) {
            System.out.println("No project selected.");
            return;
        }
        
        List<Application> applications = project.getApplications();
        
        if (applications.isEmpty()) {
            System.out.println("No applications for this project yet.");
            return;
        }
        
        System.out.println("\n=== Detailed Applicant Report for " + project.getProjectName() + " ===");
        System.out.println("Total applications: " + applications.size());
        
        // Print header
        System.out.printf("%-20s | %-15s | %-15s | %-10s | %-15s\n", 
                "Applicant Name", "Flat Type", "Project", "Age", "Marital Status");
        System.out.println("--------------------------------------------------------------------");
        
        // Print each application
        for (Application app : applications) {
            Applicant applicant = app.getApplicant();
            String flatTypeStr = app.getFlatTypeBooking() != null ? app.getFlatTypeBooking().toString() : "Not Selected";
            
            System.out.printf("%-20s | %-15s | %-15s | %-10d | %-15s\n", 
                    applicant.getName(), 
                    flatTypeStr, 
                    project.getProjectName(),
                    applicant.getAge(),
                    applicant.isMarried() ? "Married" : "Single");
        }
    }

    public void generateFilteredReport() {
        Scanner sc = new Scanner(System.in);
        
        // Select a project first
        System.out.println("\n=== Generate Applicant Report ===");
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }
        
        System.out.println("Select a project:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i+1) + ". " + projects.get(i).getProjectName());
        }
        
        System.out.print("Enter project number (0 to cancel): ");
        int projectChoice = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        if (projectChoice <= 0 || projectChoice > projects.size()) {
            System.out.println("Operation cancelled or invalid selection.");
            return;
        }
        
        BTOProject selectedProject = projects.get(projectChoice - 1);
        List<Application> allApplications = selectedProject.getApplications();
        
        if (allApplications.isEmpty()) {
            System.out.println("No applications for this project yet.");
            return;
        }
        
        // Filter options
        System.out.println("\nFilter options:");
        System.out.println("1. By flat type");
        System.out.println("2. By marital status");
        System.out.println("3. By age range");
        System.out.println("4. Show all applicants");
        System.out.print("Enter your choice: ");
        
        int filterChoice = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        List<Application> filteredApplications = new ArrayList<>();
        
        switch (filterChoice) {
            case 1:
                System.out.print("Enter flat type (TWO_ROOM/THREE_ROOM/etc.): ");
                String flatTypeStr = sc.nextLine();
                FlatType flatType = FlatType.valueOf(flatTypeStr.toUpperCase());
                
                filteredApplications = allApplications.stream()
                    .filter(app -> app.getFlatTypeBooking() == flatType)
                    .collect(Collectors.toList());
                break;
            case 2:
                System.out.print("Show married applicants? (true/false): ");
                boolean isMarried = sc.nextBoolean();
                
                filteredApplications = allApplications.stream()
                    .filter(app -> app.getApplicant().isMarried() == isMarried)
                    .collect(Collectors.toList());
                break;
            case 3:
                System.out.print("Enter minimum age: ");
                int minAge = sc.nextInt();
                System.out.print("Enter maximum age: ");
                int maxAge = sc.nextInt();
                sc.nextLine(); // Clear buffer
                
                filteredApplications = allApplications.stream()
                    .filter(app -> app.getApplicant().getAge() >= minAge && app.getApplicant().getAge() <= maxAge)
                    .collect(Collectors.toList());
                break;
            case 4:
                filteredApplications = allApplications;
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
    
        displayFilteredReport(selectedProject, filteredApplications);
        
        // Option to export
        System.out.print("\nExport report to file? (y/n): ");
        String exportChoice = sc.nextLine();
        
        if (exportChoice.equalsIgnoreCase("y")) {
            exportReportToFile(selectedProject, filteredApplications);
        }
    }
    
    /**
     * Display a filtered report of applications
     * @param project The selected project
     * @param applications Filtered list of applications
     */
    private void displayFilteredReport(BTOProject project, List<Application> applications) {
        if (applications.isEmpty()) {
            System.out.println("No applications match the filter criteria.");
            return;
        }
        
        System.out.println("\n=== Filtered Applicant Report for " + project.getProjectName() + " ===");
        System.out.println("Total matches: " + applications.size());
        
        // Print header
        System.out.printf("%-20s | %-15s | %-15s | %-10s | %-15s\n", 
                "Applicant Name", "Flat Type", "Project", "Age", "Marital Status");
        System.out.println("--------------------------------------------------------------------");
        
        // Print each application
        for (Application app : applications) {
            Applicant applicant = app.getApplicant();
            String flatTypeStr = app.getFlatTypeBooking() != null ? app.getFlatTypeBooking().toString() : "Not Selected";
            
            System.out.printf("%-20s | %-15s | %-15s | %-10d | %-15s\n", 
                    applicant.getName(), 
                    flatTypeStr, 
                    project.getProjectName(),
                    applicant.getAge(),
                    applicant.isMarried() ? "Married" : "Single");
        }
    }
    
    /**
     * Export a report to a file (implementation would depend on your file I/O handling)
     * @param project The selected project
     * @param applications Filtered list of applications
     */
    private void exportReportToFile(BTOProject project, List<Application> applications) {
        System.out.println("Exporting report to file 'report_" + project.getProjectName() + ".csv'");
        // Implementation would depend on your file I/O handling
        // This is a placeholder for the actual implementation
        
        System.out.println("Report exported successfully.");
    }

}