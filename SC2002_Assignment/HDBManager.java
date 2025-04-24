package SC2002_Assignment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents a HDBManager responsible for managing BTO (Build-To-Order) projects, applications, withdrawals, registrations, and project-related functionalities. 
 * It extends the User class and implements the ViewProjects, EnquiryReply, and EnquiryView interfaces.
 */
public class HDBManager extends User implements ViewProjects,EnquiryReply, EnquiryView{
    private List<BTOProject> projects;

    /**
     * Constructor to create a new HDBManager.
     * 
     * @param nric The NRIC of the HDBManager.
     * @param name The name of the HDBManager.
     * @param password The password for the HDBManager account.
     * @param age The age of the HDBManager.
     * @param isMarried Whether the HDBManager is married.
     */
    public HDBManager(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried);
        this.projects = new ArrayList<>();
    }

    // Project Management Section

    /**
     * Creates a new BTO project and adds it to the list of projects managed by the HDBManager.
     * 
     * @param projectName The name of the BTO project.
     * @param neighborhood The neighborhood of the BTO project.
     * @param applicationOpenDate The opening date for the application.
     * @param applicationCloseDate The closing date for the application.
     * @param flatTypes List of available flat types in the BTO project.
     * @param twoRoomFlats The number of two-room flats available in the project.
     * @param threeRoomFlats The number of three-room flats available in the project.
     * @param twoP The price for two-room flats.
     * @param threeP The price for three-room flats.
     */
    public void createProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                              LocalDate applicationCloseDate, List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats, int twoP, int threeP) {
        BTOProject newProject = new BTOProject(projectName, neighborhood, applicationOpenDate, applicationCloseDate, this, flatTypes, twoRoomFlats, threeRoomFlats, twoP, threeP);
        projects.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }

    /**
     * Edits the name of a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param newName The new name for the project.
     */
    public void editProjectname(BTOProject project, String newName) {
        project.setProjectName(newName);
        System.out.println("project name changed successfully");
        }

    /**
     * Edits the neighborhood of a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param newNeighborhood The new neighborhood for the project.
     */
    public void editProject(BTOProject project, String newNeighborhood) {
        project.setNeighborhood(newNeighborhood);
        System.out.println("project neighbourhood changed successfully");
        }
    
    /**
     * Edits the application dates of a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param newApplicationOpenDate The new application open date.
     * @param newApplicationCloseDate The new application close date.
     */
    public void editProject(BTOProject project, LocalDate newApplicationOpenDate, LocalDate newApplicationCloseDate) {
        project.setApplicationOpenDate(newApplicationOpenDate);
        project.setApplicationCloseDate(newApplicationCloseDate);
        System.out.println("application open and close dates changed successfully");
    }

    /**
     * Edits the flat types and available flat counts of a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param newFlatTypes The new list of flat types.
     * @param twoRoomFlats The new number of two-room flats available.
     * @param threeRoomFlats The new number of three-room flats available.
     */
    public void editProject(BTOProject project, List<FlatType> newFlatTypes, int twoRoomFlats, int threeRoomFlats) {
        project.setFlatTypes(newFlatTypes);
        project.getFlats().setTwoRoomFlats(twoRoomFlats);
        project.getFlats().setThreeRoomFlats(threeRoomFlats);
        System.out.println("Project flat types updated successfully.");
    }

    /**
     * Edits the prices of flats in a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param TwoP The new price for two-room flats.
     * @param ThreeP The new price for three-room flats.
     */
    public void editProject(BTOProject project, int TwoP, int ThreeP){
        project.getFlats().setTwoRoomPrice(TwoP);
        project.getFlats().setThreeRoomFlats(ThreeP);
        System.out.println("Project prices updated successfully.");
    }

    /**
     * Edits the visibility of a BTO project.
     * 
     * @param project The BTO project to be edited.
     * @param v The visibility status, either true (visible) or false (invisible).
     */
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

    /**
     * Deletes a BTO project from the list of managed projects.
     * 
     * @param project The BTO project to be deleted.
     */
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

    /**
     * Adds a BTO project to the list of managed projects.
     * 
     * @param p The BTO project to be added.
     */
    public void addProject(BTOProject p){
        this.projects.add(p);
    }

    /**
     * Retrieves the list of all BTO projects managed by the HDBManager.
     * 
     * @return A list of BTO projects managed by the HDBManager.
     */
    public List<BTOProject> getProjects() {
        return this.projects;
    }

    /**
     * Retrieves a specific project managed by the HDBManager.
     * 
     * @return A BTO project managed by the HDBManager, or null if no project is available.
     */
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

    // View Projects Section

    /**
     * Displays the list of personal projects managed by the HDBManager.
     */
    public void viewPersornalProjects(){
        int i = 1;
        for (BTOProject P: this.projects){
            System.out.print(i+".");
            System.out.println(P.getProjectName());
            i += 1;
        }
    }

    // Applications Section

    /**
     * Displays the list of applications for a specific BTO project.
     * 
     * @param P The BTO project whose applications will be displayed.
     */
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

    /**
     * Displays a list of pending applications for a specific BTO project.
     * 
     * @param P The BTO project whose pending applications will be displayed.
     * @return A list of pending applications for the specified BTO project.
     */
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

    /**
     * Retrieves a specific pending application for a BTO project.
     * 
     * @param P The BTO project whose pending application will be retrieved.
     * @return A pending application for the specified BTO project.
     */
    public Application getApplication(BTOProject P){
        if(P.getApplications().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        List<Application> PA = viewPendingApplications(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return PA.get(choice-1);
    }

    /**
     * Approves a pending application for a BTO project if flats are available.
     */
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

    /**
     * Views the pending withdrawals for a specific BTO project.
     * Displays the applications that have requested withdrawal.
     * 
     * @param P The BTO project to check for pending withdrawals.
     * @return A list of applications with pending withdrawals, or null if none are found.
     */
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
    
    /**
     * Prompts the user to select an application with a pending withdrawal.
     * 
     * @param P The BTO project to get the withdrawal applications from.
     * @return The selected application with a pending withdrawal.
     */
    public Application getWithdrawals(BTOProject P){
        if(P.getApplications().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the application based on number:");
        List<Application> PW = viewPendingWithdrawals(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return PW.get(choice-1);
    }
    
    /**
     * Manages the withdrawal process for an application.
     * Allows the withdrawal to be approved or rejected.
     */
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
    
    /**
     * Displays all the registrations for a specific BTO project.
     * 
     * @param P The BTO project to view registrations for.
     */
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
    
    /**
     * Displays all unaccepted registrations for a specific BTO project.
     * 
     * @param P The BTO project to view unaccepted registrations for.
     */
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
    
    /**
     * Prompts the user to select an unaccepted registration for a BTO project.
     * 
     * @param P The BTO project to get the unaccepted registrations from.
     * @return The selected unaccepted registration.
     */
    public Registration getRegistrations(BTOProject P){
        if(P.getRegistrations().isEmpty()){return null;}
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the Registration based on number:");
        viewUnacceptedRegistrations(P);
        int choice = sc.nextInt();
        sc.nextLine();
        return P.getRegistrations().get(choice-1);
    }
    
    /**
     * Approves a registration for an officer to be added to a BTO project.
     * Removes the registration from the project's list once approved.
     */
    public void approveRegistration() {
        Registration R = this.getRegistrations(this.getProject());
        if(R == null){
            System.out.println("No registrations for the project available");
            return;
        }
        HDBOfficer officer = R.getOfficer();
        if(R.getProject().addOfficer(officer)){
            R.setAccepted(Boolean.TRUE);
            System.out.println("Officer " + R.getOfficer().getName() + " approved for project " + R.getProject().getProjectName() + ".");
            R.getProject().getRegistrations().remove(R);
        }
        else{
            System.out.println("Officer " + R.getOfficer().getName() + " could not be approved for project " + R.getProject().getProjectName() + ".");
        }
    }

    /**
     * Gets the index of an enquiry from a specific BTO project.
     * Displays all the enquiries in the project and prompts the user to select one.
     * 
     * @param P The BTO project from which to select an enquiry.
     * @return The index of the selected enquiry.
     */
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
    
    /**
     * Displays all the enquiries for a list of BTO projects.
     * 
     * @param AllEnquiries List of enquiries to display.
     */
    public void viewAllEnquiries(List<Enquiry> AllEnquiries){
        for (Enquiry enquiry : AllEnquiries) {
            enquiry.viewEnq();
        }
    }
    
    /**
     * Displays all the enquiries for the current BTO project.
     */
    public void viewEnquiries() {
        for (Enquiry enquiry : this.getProject().getEnquiries()) {
            enquiry.viewEnq();
        }
    }
    
    /**
     * Replies to a specific enquiry from the project.
     * Ensures the enquiry hasn't been replied to yet before allowing a response.
     * 
     * @param index The index of the enquiry to reply to.
     * @param P The BTO project where the enquiry exists.
     */
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

    /**
     * Filters the BTO projects based on a given filter type and value.
     * Supports filtering by project name, neighborhood, and open status.
     * 
     * @param MPL List of all BTO projects to filter.
     * @param filterType The type of filter (e.g., "name", "neighborhood", "open").
     * @param value The value to match the filter against.
     * @return A list of filtered BTO projects.
     */
    public List<BTOProject> filterMyProjects(List<BTOProject> MPL,String filterType, String value) {
        List<BTOProject> filteredProjects = new ArrayList<>();

        // Assuming 'myProjects' is a method or a field that retrieves all the projects
        Collection<BTOProject> myProjects = MPL; // Replace this with your actual source

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
            case null:
                filteredProjects = new ArrayList<>(myProjects); // If no valid filter, return all
                break;
            default:
                filteredProjects = new ArrayList<>(myProjects); // If no valid filter, return all
                break;
        }

        return filteredProjects;
    }

    /**
     * Displays a menu to filter and view the user's projects based on different criteria.
     * Allows filtering by project name, neighborhood, or open status.
     * 
     * @param MPL List of BTO projects to be filtered and displayed.
     */
    public void viewProjects(List<BTOProject> MPL) {
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
                filtered = filterMyProjects(MPL,"name", name);
                break;
            case 2:
                System.out.print("Enter neighborhood: ");
                String neighborhood = sc.nextLine();
                filtered = filterMyProjects(MPL,"neighborhood", neighborhood);
                break;
            case 3:
                filtered = filterMyProjects(MPL,"open", null);
                break;
            case 4:
                filtered = filterMyProjects(MPL,null, null);
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
     * Helper method to display a list of filtered projects.
     * 
     * @param projectList List of projects to display.
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
     * Generates a detailed applicant report for a specific project.
     * Displays applicant names, flat type, age, and marital status.
     * 
     * @param project The project to generate the report for.
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

    /**
     * Generates a filtered report based on the selected criteria (flat type, marital status, or age range).
     * 
     * @param P The BTO project to generate the filtered report for.
     */
    public void generateFilteredReport(BTOProject P) {
        Scanner sc = new Scanner(System.in);
        BTOProject selectedProject = P;
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
                FlatType flatType = null;

                while (flatType == null) {
                    System.out.print("Enter flat type (TWO_ROOM/THREE_ROOM): ");
                    String flatTypeStr = sc.nextLine();
                    try {
                        flatType = FlatType.valueOf(flatTypeStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid flat type. Try again.");
                    }
                }

                FlatType finalFlatType = flatType;
                filteredApplications = allApplications.stream()
                        .filter(app -> app.getFlatTypeBooking() == finalFlatType)
                        .collect(Collectors.toList());

                System.out.println("Filtered " + filteredApplications.size() + " applications.");

                break;
            case 2:
                boolean isMarried = false;
                boolean validInput = false;
                while (!validInput) {
                    try {
                        System.out.print("Show married applicants? (true/false): ");
                        isMarried = Boolean.parseBoolean(sc.nextLine().trim().toLowerCase());
                        validInput = true;
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter 'true' or 'false'.");
                    }
                }

                boolean finalIsMarried = isMarried;
                filteredApplications = allApplications.stream()
                        .filter(app -> app.getApplicant().isMarried() == finalIsMarried)
                        .collect(Collectors.toList());
                break;
            case 3:
                int minAge = 0, maxAge = 0;
                while (true) {
                    try {
                        System.out.print("Enter minimum age: ");
                        minAge = Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter maximum age: ");
                        maxAge = Integer.parseInt(sc.nextLine().trim());

                        if (minAge > maxAge) {
                            System.out.println("Minimum age can't be greater than maximum age. Try again.");
                            continue;
                        }
                        break; // Valid input
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter valid integers for age.");
                    }
                }

                int finalMinAge = minAge;
                int finalMaxAge = maxAge;
                filteredApplications = allApplications.stream()
                        .filter(app -> {
                            int age = app.getApplicant().getAge();
                            return age >= finalMinAge && age <= finalMaxAge;
                        })
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
     * Displays the filtered report of applicants for a project.
     * 
     * @param project The selected project.
     * @param applications The filtered list of applications.
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
     * Exports the filtered report of applications to a CSV file.
     * 
     * @param project The selected project.
     * @param applications The filtered list of applications to export.
     */
    private void exportReportToFile(BTOProject project, List<Application> applications) {
        System.out.println("Exporting report to file 'report_" + project.getProjectName() + ".csv'");
        // Implementation would depend on your file I/O handling
        // This is a placeholder for the actual implementation
        
        System.out.println("Report exported successfully.");
    }

}
