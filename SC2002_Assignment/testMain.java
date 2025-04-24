package SC2002_Assignment;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class testMain {
    public static List<Applicant> applicants = new ArrayList<>();
    public static List<HDBManager> managers = new ArrayList<>();
    public static List<HDBOfficer> officers = new ArrayList<>();
    public static List<BTOProject> projects = new ArrayList<>();
    public static List<Application> applications = new ArrayList<>();
    public static List<Registration> registrations = new ArrayList<>();
    public static List<Enquiry> enquiries = new ArrayList<>();
    //load csvs into lists
    private static void loadApplicants(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                String[] data = line.split(",");
                Applicant applicant = new Applicant(
                        data[1], // NRIC
                        data[0], // Name
                        data[4], // Password
                        Integer.parseInt(data[2]), // Age
                        data[3].equalsIgnoreCase("Married") // Marital Status
                );
                applicants.add(applicant);
            }
        } catch (IOException e) {
            System.err.println("Error reading applicants file: " + e.getMessage());
        }
    }
    private static void loadManagers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                String[] data = line.split(",");
                HDBManager manager = new HDBManager(
                        data[1], // NRIC
                        data[0], // Name
                        data[4], // Password
                        Integer.parseInt(data[2]), // Age
                        data[3].equalsIgnoreCase("Married") // Marital Status
                );
                managers.add(manager);
            }
        } catch (IOException e) {
            System.err.println("Error reading managers file: " + e.getMessage());
        }
    }
    private static void loadOfficers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                String[] data = line.split(",");
                HDBOfficer officer = new HDBOfficer(
                        data[1], // NRIC
                        data[0], // Name
                        data[4], // Password
                        Integer.parseInt(data[2]), // Age
                        data[3].equalsIgnoreCase("Married") // Marital Status
                );
                officers.add(officer);
            }
        } catch (IOException e) {
            System.err.println("Error reading officers file: " + e.getMessage());
        }
    }
    private static void loadProjects(String filename) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yy");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                // Handle quoted fields (like officer names)
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Trim all values
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim().replace("\"", "");
                }
                // Parse flat types and counts
                List<FlatType> flatTypes = new ArrayList<>();
                int twoRoomFlats = 0;
                int threeRoomFlats = 0;

                if (data[2].equalsIgnoreCase("2-Room")) {
                    flatTypes.add(FlatType.TWOROOM);
                    twoRoomFlats = Integer.parseInt(data[3]);
                }

                if (data[5].equalsIgnoreCase("3-Room")) {
                    flatTypes.add(FlatType.THREEROOM);
                    threeRoomFlats = Integer.parseInt(data[6]);
                }

                // Parse dates
                LocalDate openDate = LocalDate.parse(data[8], dateFormatter);
                LocalDate closeDate = LocalDate.parse(data[9], dateFormatter);

                // Find manager by name
                HDBManager manager = null;
                for (HDBManager m : managers) {
                    if (m.getName().equalsIgnoreCase(data[10])) {
                        manager = m;
                        break;
                    }
                }

                if (manager == null) {
                    System.err.println("Manager not found: " + data[10]);
                    continue;
                }

                // Create project
                BTOProject project = new BTOProject(data[0], data[1], openDate, closeDate, manager, flatTypes, twoRoomFlats, threeRoomFlats);
                manager.addProject(project);
                // Assign officers
                if (data.length > 12 && !data[12].isEmpty()) {
                    String[] officerNames = data[12].split(",");
                    for (String name : officerNames) {
                        name = name.trim();
                        for (HDBOfficer officer : officers) {
                            if (officer.getName().equalsIgnoreCase(name)) {
                                project.addOfficer(officer);
                                officer.setAssignedProject(project);
                                break;
                            }
                        }
                    }
                }

                projects.add(project);
            }
        } catch (IOException e) {
            System.err.println("Error reading projects file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing project data: " + e.getMessage());
        }
    }
    private static void loadApplications(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                String[] data = line.split(",");

                // Find applicant by NRIC
                Applicant applicant = null;
                for (Applicant a : applicants) {
                    if (a.getNric().equals(data[1])) {
                        applicant = a;
                        break;
                    }
                }

                // Find project by name
                BTOProject project = null;
                for (BTOProject p : projects) {
                    if (p.getProjectName().equals(data[2])) {
                        project = p;
                        break;
                    }
                }

                if (applicant == null || project == null) {
                    System.err.println("Applicant or project not found for application: " + data[0]);
                    continue;
                }

                // Create application
                Application application = new Application(data[0], applicant, project);

                // Set additional fields if available
                if (data.length > 3) {
                    application.setStatus(ApplicationStatus.valueOf(data[3]));

                    if (data.length > 4 && !data[4].isEmpty()) {
                        application.setFlatTypeBooking(FlatType.valueOf(data[4]));
                    }

                    if (data.length > 5 && !data[5].isEmpty()) {
                        application.setBookedUnit(data[5]);
                    }

                    if (data.length > 6 && !data[6].isEmpty()) {
                        application.setWithdrawalRequested(Boolean.parseBoolean(data[6]));
                    }
                }

                // Link to applicant and project
                applicant.setApplication(application);
                applications.add(application);
            }
        } catch (IOException e) {
            System.err.println("Error reading applications file: " + e.getMessage());
        }}
    private static void loadRegistrations(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue; // Skip header
            }

            String[] data = line.split(",");

            // Find officer by NRIC
            HDBOfficer officer = null;
            for (HDBOfficer o : officers) {
                if (o.getNric().equals(data[1])) {
                    officer = o;
                    break;
                }
            }

            // Find project by name
            BTOProject project = null;
            for (BTOProject p : projects) {
                if (p.getProjectName().equals(data[2])) {
                    project = p;
                    break;
                }
            }

            if (officer == null || project == null) {
                System.err.println("Officer or project not found for registration: " + data[0]);
                continue;
            }

            // Create registration
            Registration registration = new Registration(data[0], officer, project);

            // Set acceptance status if available
            if (data.length > 3) {
                registration.setAccepted(Boolean.parseBoolean(data[3]));

                // If accepted, link officer to project
                if (registration.getAccepted()) {
                    officer.setAssignedProject(project);
                    officer.setRegistration(registration);
                }
            }

            registrations.add(registration);
        }
    } catch (IOException e) {
        System.err.println("Error reading registrations file: " + e.getMessage());
    }
    }
    private static void loadEnquiries(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Handle quoted text

                // Find applicant by NRIC
                Applicant applicant = getApplicant(applicants, data[1]);
                // Find project by name
                BTOProject project = projects.stream()
                        .filter(p -> p.getProjectName().equals(data[2]))
                        .findFirst()
                        .orElse(null);

                if (applicant == null || project == null) {
                    System.err.println("Skipping enquiry - applicant or project not found");
                    continue;
                }

                // Create enquiry (trim quotes if present)
                String enquiryText = data[3].replace("\"", "");
                Enquiry enquiry = new Enquiry(enquiryText, project, applicant);

                // Handle reply (column 4)
                if (data.length > 4) {
                    String reply = data[4].replace("\"", "");
                    if (!reply.equalsIgnoreCase("no reply yet")) {
                        enquiry.setReply(reply);
                        enquiry.setReplied(true);
                    }
                    // For "no reply yet", we keep the default values
                }

                // Link to applicant and project
                applicant.getEnquiries().add(enquiry);
                enquiries.add(enquiry);
            }
        } catch (IOException e) {
            System.err.println("Error reading enquiries file: " + e.getMessage());
        }
    }

    //save lists to csvs
    private static void saveApplicants(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write each applicant
            for (Applicant applicant : applicants) {
                writer.println(String.format("%s,%s,%d,%s,%s",
                        applicant.getName(),
                        applicant.getNric(),
                        applicant.getAge(),
                        applicant.isMarried() ? "Married" : "Single",
                        applicant.getPassword()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving applicants to CSV: " + e.getMessage());
        }
    }
    private static void saveOfficers(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write each officer
            for (HDBOfficer officer : officers) {
                writer.println(String.format("%s,%s,%d,%s,%s",
                        officer.getName(),
                        officer.getNric(),
                        officer.getAge(),
                        officer.isMarried() ? "Married" : "Single",
                        officer.getPassword()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving officers to CSV: " + e.getMessage());
        }
    }
    private static void saveManagers(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write each manager
            for (HDBManager manager : managers) {
                writer.println(String.format("%s,%s,%d,%s,%s",
                        manager.getName(),
                        manager.getNric(),
                        manager.getAge(),
                        manager.isMarried() ? "Married" : "Single",
                        manager.getPassword()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving managers to CSV: " + e.getMessage());
        }
    }
    private static void saveProjects(String filename) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yy");

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header matching your ProjectList.csv
            writer.println("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1," +
                    "Type 2,Number of units for Type 2,Selling price for Type 2," +
                    "Application opening date,Application closing date,Manager,Officer Slot,Officer");

            // Write each project
            for (BTOProject project : projects) {
                // Prepare flat type information
                String type1 = "";
                String count1 = "";
                String price1 = "";
                String type2 = "";
                String count2 = "";
                String price2 = "";

                if (project.getFlatTypes().contains(FlatType.TWOROOM)) {
                    type1 = "2-Room";
                    count1 = String.valueOf(project.getFlats().getTwoRoomFlats());
                    price1 = ""; // Empty since price isn't stored in BTOProject
                }

                if (project.getFlatTypes().contains(FlatType.THREEROOM)) {
                    type2 = "3-Room";
                    count2 = String.valueOf(project.getFlats().getThreeRoomFlats());
                    price2 = ""; // Empty since price isn't stored in BTOProject
                }

                // Prepare officer names
                String officerNames = project.getAssignedOfficers().stream()
                        .map(HDBOfficer::getName)
                        .collect(Collectors.joining(","));

                // Calculate officer slot (current officers/max officers)
                int officerSlot = project.getAssignedOfficers().size();

                writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,\"%s\"",
                        project.getProjectName(),
                        project.getNeighborhood(),
                        type1,
                        count1,
                        price1,
                        type2,
                        count2,
                        price2,
                        project.getApplicationOpenDate().format(dateFormatter),
                        project.getApplicationCloseDate().format(dateFormatter),
                        project.getManagerInCharge().getName(),
                        officerSlot,
                        officerNames
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving projects to CSV: " + e.getMessage());
        }
    }
    private static void saveApplications(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("applicationId,applicantNric,projectName,status,flatType,bookedUnit,withdrawalRequested");

            // Write each application
            for (Application app : applications) {
                writer.println(String.format("%s,%s,%s,%s,%s,%s,%s",
                        app.getApplicationId(),
                        app.getApplicant().getNric(),
                        app.getProject().getProjectName(),
                        app.getStatus(),
                        app.getFlatTypeBooking() != null ? app.getFlatTypeBooking().name() : "",
                        app.getBookedUnit() != null ? app.getBookedUnit() : "",
                        app.getWithdrawalRequested()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving applications to CSV: " + e.getMessage());
        }
    }
    private static void saveRegistrations(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("regId,officerNric,projectName,accepted");

            // Write each registration
            for (Registration reg : registrations) {
                writer.println(String.format("%s,%s,%s,%s",
                        reg.getRegId(),
                        reg.getOfficer().getNric(),
                        reg.getProject().getProjectName(),
                        reg.getAccepted()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving registrations to CSV: " + e.getMessage());
        }
    }
    private static void saveEnquiries(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("enquiryId,applicantNric,projectName,enquiryText,reply");

            // Write each enquiry (with proper quoting for text fields)
            for (Enquiry enq : enquiries) {
                writer.println(String.format("%s,%s,%s,\"%s\",\"%s\"",
                        "ENQ" + enquiries.indexOf(enq), // Or use enq.getId() if available
                        enq.getApplicant().getNric(),
                        enq.getProject().getProjectName(),
                        enq.getMainEnq().replace("\"", "\"\""), // Escape quotes
                        enq.getReply().replace("\"", "\"\"")    // Escape quotes
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving enquiries to CSV: " + e.getMessage());
        }
    }

    // Generate HashMap for Applicants
    public static HashMap<String, String> createApplicantMap(List<Applicant> applicants) {
        HashMap<String, String> map = new HashMap<>();
        for (Applicant applicant : applicants) {
            map.put(applicant.getNric(), applicant.getPassword());
        }
        return map;
    }

    // Generate HashMap for Officers
    public static HashMap<String, String> createOfficerMap(List<HDBOfficer> officers) {
        HashMap<String, String> map = new HashMap<>();
        for (HDBOfficer officer : officers) {
            map.put(officer.getNric(), officer.getPassword());
        }
        return map;
    }

    // Generate HashMap for Managers
    public static HashMap<String, String> createManagerMap(List<HDBManager> managers) {
        HashMap<String, String> map = new HashMap<>();
        for (HDBManager manager : managers) {
            map.put(manager.getNric(), manager.getPassword());
        }
        return map;
    }

    //get applicant from list
    public static Applicant getApplicant(List<Applicant> applicants, String NRIC){
        Applicant X = null;
        for(Applicant applicant : applicants){
            if(NRIC.equals(applicant.getNric())){
                X = applicant;
            }
        }
        return X;
    }
    //get officer from list
    public static HDBOfficer getOfficer(List<HDBOfficer> officers, String NRIC){
        HDBOfficer X = null;
        for(HDBOfficer officer : officers){
            if(NRIC.equals(officer.getNric())){
                X = officer;
            }
        }
        return X;
    }
    //get manager from list
    public static HDBManager getManager(List<HDBManager> managers, String NRIC){
        HDBManager X = null;
        for(HDBManager manager : managers){
            if(NRIC.equals(manager.getNric())){
                X = manager;
            }
        }
        return X;
    }

    public static Boolean authenticate(String NRIC, String Password, HashMap Database){
        // Check if NRIC exists in the map
        if (!Database.containsKey(NRIC)) {
            return null;
        }
        // Compare passwords (use equals() for string comparison)
        if(Database.get(NRIC).equals(Password)) {
            System.out.println("Login successful.");
            return true;
        }
        else {
            System.out.println("Wrong password, please try again");
            return false;
        }
    }
    public static void applicantMenu(){
        System.out.println("""
                
                
                1. View available projects
                2. Apply for project
                3. View applied project
                4. View application
                5. Request application withdrawal
                6. Book flats
                7. manage enquiries
                8. Change password
                9. Logout""");
    }
    public static void applicantEnquiryMenu(){
        System.out.println("""
                
                
                1. Create enquiry
                2. View enquiries
                3. Edit enquiry
                4. Delete enquiry
                5. Exit""");
    }
    public static void officerMenu(){
        System.out.println("\n\n1. Register for project team\n" +
                           "2. View current registration status\n" +
                           "3. View current project details\n" +
                           "4. View Booking Applications\n" +
                           "5. Book flat from Application\n" +  //into update status and profile
                           "6. Generate applicant booking receipt\n" +
                           "7. Manage project enquiries\n" +
                           "8. Change password\n" +
                           "9. Logout" );
    }
    public static void officerEnquiryMenu(){
        System.out.println("""
                
                
                1. View enquiries
                2. reply to enquiry
                3. Exit""");
    }
    public static void managerMenu(){
        System.out.println("""
                
                
                1. Manage projects
                2. Manage applications
                3. Manage registrations
                4. Manage enquiries
                5. Generate report
                6. Change password
                7. Logout""");
    }
    public static void managerEnquiryMenu() {
        System.out.println("""
                
                
                1. View all enquiries
                2. View enquiries within managed projects
                3. reply to enquiry within managed projects
                4. Exit""");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadApplicants("ApplicantList.csv");
        loadManagers("ManagerList.csv");
        loadOfficers("OfficerList.csv");
        loadProjects("ProjectList.csv");
        loadApplications("ApplicationList.csv");
        loadRegistrations("RegistrationList.csv");
        loadEnquiries("EnquiryList.csv");

        boolean ProgramOn = true;
        while (ProgramOn) {
            System.out.println("Select user class to login");
            System.out.println("\n1. Applicant\n2. HDBOfficer\n3. HDBManager\n4. Exit program");
            int choice = -1;
            boolean validInput = false;
            while (!validInput) {
                try{
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice >= 1 && choice <= 4) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid choice. Please select a valid option (1-4).");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    HashMap applicantMap = createApplicantMap(applicants);
                    HashMap OM = createOfficerMap(officers);
                    System.out.println("enter your Nric");
                    String nricA = sc.nextLine();
                    System.out.println("enter your Password");
                    String PasswordA = sc.nextLine();
                    Boolean loggedA = authenticate(nricA, PasswordA, applicantMap);
                    Boolean loggedOA = authenticate(nricA, PasswordA, OM);
                    if (loggedA == null && loggedOA == null) System.out.println("Invalid NRIC, please try again.");
                    else {
                        if (loggedA == null) {loggedA = false;}
                        if (loggedOA == null) {loggedOA = false;}
                        Applicant currentApplicant = getApplicant(applicants, nricA);
                        if(loggedOA){currentApplicant = getOfficer(officers, nricA);}
                        //if(PasswordA.equals("password")){currentApplicant.changePassword();}
                        while (loggedA || loggedOA) {
                            applicantMenu();
                            int choiceA = sc.nextInt();
                            sc.nextLine();
                            switch (choiceA) {
                                case 1:
                                    currentApplicant.viewProjects(projects);
                                    break;
                                case 2:
                                    if (currentApplicant.getApplication() == null) {
                                        currentApplicant.applyForProject(currentApplicant.selectProject(projects));
                                        break;
                                    }
                                    else {
                                        System.out.println("you have already applied for a project");
                                        break;
                                    }
                                case 3:
                                    if(currentApplicant.getApplication() != null){
                                        currentApplicant.getApplication().getProject().displayProjectDetails();
                                    }
                                    else{
                                        System.out.println("No project assigned yet");
                                    }
                                    break;
                                case 4:
                                    if (currentApplicant.getApplication() == null){
                                        System.out.println("no application submitted yet");
                                        break;
                                    }
                                    else {
                                        currentApplicant.getApplication().displayApplication();
                                        break;
                                    }
                                case 5:
                                    currentApplicant.withdrawApplication();
                                    break;
                                case 6:
                                    currentApplicant.bookFlat();
                                    break;
                                case 7:
                                    System.out.println("Select choice by number:");
                                    applicantEnquiryMenu();
                                    int C = sc.nextInt();
                                    sc.nextLine();
                                    switch (C) {
                                        case 1:

                                            currentApplicant.submitEnquiry(projects);
                                            for (Enquiry Enq : currentApplicant.getEnquiries()){
                                                if (!enquiries.contains(Enq)){
                                                    enquiries.add(Enq);
                                                }
                                            }//updates enquiries
                                            break;
                                        case 2:
                                            currentApplicant.viewEnquiries();
                                            break;
                                        case 3:
                                            int i = currentApplicant.getEnquiryIndex();
                                            if(i == -1){
                                                break;
                                            }
                                            System.out.println("Enter your edited enquiry:");
                                            String EE = sc.nextLine();
                                            currentApplicant.editEnquiry(i, EE);
                                            break;
                                        case 4:
                                            int Index = currentApplicant.getEnquiryIndex();
                                            if (Index == -1){
                                                break;
                                            }
                                            Enquiry ER = currentApplicant.getEnquiries().get(Index);
                                            enquiries.remove(ER);
                                            currentApplicant.deleteEnquiry(Index);
                                            break;
                                        case 5:
                                            System.out.println("Exit successful");
                                            break;
                                        default:
                                    }
                                    break;
                                case 8 :
                                    currentApplicant.changePassword();
                                    break;
                                case 9:
                                    loggedA = false;
                                    loggedOA = false;
                                    System.out.println("Successfully logged out.");
                                    break;
                                default:
                            }
                        }
                    }
                    break;
                case 2:
                    HashMap officerMap = createOfficerMap(officers);
                    System.out.println("enter your Nric");
                    String nricO = sc.nextLine();
                    System.out.println("enter your Password");
                    String PasswordO = sc.nextLine();
                    Boolean loggedO = authenticate(nricO, PasswordO, officerMap);
                    if (loggedO == null) System.out.println("Invalid NRIC, please try again.");
                    else{
                        HDBOfficer currentOfficer = getOfficer(officers, nricO);
                        //if(PasswordO.equals("password")){currentOfficer.changePassword();}
                        while (loggedO) {
                            officerMenu();
                            int choiceO = sc.nextInt();
                            sc.nextLine();
                            switch (choiceO) {
                                case 1:
                                    if (currentOfficer.getRegistration() == null) {
                                        currentOfficer.registerAsOfficer(currentOfficer.selectProjectforRegistration(projects));
                                        break;
                                    }
                                    else {
                                        System.out.println("you are already assigned to a project");
                                        break;
                                    }
                                case 2:
                                    if(currentOfficer.getRegistration() != null){
                                        if(currentOfficer.getRegistration().getAccepted()){System.out.println("Registration accepted.");}
                                        else{System.out.println("registration not accepted yet.");}
                                    }
                                    else{
                                        System.out.println("No registration submitted yet.");
                                    }
                                    break;
                                case 3:
                                    currentOfficer.viewAssignedProjectDetails();
                                    break;
                                case 4:
                                    currentOfficer.viewAssignedProjectApplicationsForBooking();
                                    break;
                                case 5:
                                    currentOfficer.handleFlatBooking();
                                    break;
                                case 6:
                                    //booking receipt
                                case 7:
                                    System.out.println("Select choice by number:");
                                    officerEnquiryMenu();
                                    int C = sc.nextInt();
                                    sc.nextLine();
                                    switch (C) {
                                        case 1:
                                            currentOfficer.viewEnquiries();
                                            break;
                                        case 2:
                                            int i = currentOfficer.getEnquiryIndex();
                                            BTOProject P = currentOfficer.getAssignedProject();
                                            currentOfficer.replyEnquiry(i,P);
                                            break;
                                        case 3:
                                            System.out.println("Exit successful");
                                            break;
                                        default:
                                    }
                                    break;
                                case 8:
                                    currentOfficer.changePassword();
                                    break;
                                case 9:
                                    loggedO = false;
                                    System.out.println("Successfully logged out.");
                                    break;
                                default:
                            }
                        }
                    }
                    break;
                case 3:
                    HashMap managerMap = createManagerMap(managers);
                    System.out.println("enter your Nric");
                    String nricM = sc.nextLine();
                    System.out.println("enter your Password");
                    String PasswordM = sc.nextLine();
                    Boolean loggedM = authenticate(nricM, PasswordM, managerMap);
                    if (loggedM == null) System.out.println("Invalid NRIC, please try again.");
                    else{
                        HDBManager currentManager = getManager(managers, nricM);
                        //if (PasswordM.equals("password")){currentManager.changePassword();}
                        while (loggedM) {
                            managerMenu();
                            int choiceM = sc.nextInt();
                            sc.nextLine();
                            switch (choiceM) {
                                case 1:
                                    System.out.println("Select choice by number:");
                                    System.out.println("1. View all projects\n2. Create project\n3. Edit project\n4. Delete project\n5. Exit");
                                    int CP = sc.nextInt();
                                    sc.nextLine();
                                    switch (CP) {
                                        case 1:
                                            currentManager.viewProjects(projects);
                                            break;
                                        case 2:
                                            System.out.println("Enter project Name:");
                                            String PN = sc.nextLine();
                                            System.out.println("Enter project neighbourhood:");
                                            String N = sc.nextLine();
                                            System.out.print("Enter application start date (YYYY-MM-DD): ");
                                            String SD = sc.nextLine();
                                            LocalDate Sdate = LocalDate.parse(SD);
                                            System.out.print("Enter application end date (YYYY-MM-DD): ");
                                            String ED = sc.nextLine();
                                            LocalDate Edate = LocalDate.parse(ED);
                                            System.out.println("Select flat types availble:\n1. Two room only\n2. Three room only\n3. Both two and three Rooms");
                                            int FC = sc.nextInt();
                                            sc.nextLine();
                                            List<FlatType> FT = new ArrayList<>();
                                            boolean typed = true;
                                            while(typed){
                                                switch (FC){
                                                    case 1:
                                                        FT.add(FlatType.TWOROOM);
                                                        typed = false;
                                                        break;
                                                    case 2:
                                                        FT.add(FlatType.THREEROOM);
                                                        typed = false;
                                                        break;
                                                    case 3:
                                                        FT.add(FlatType.TWOROOM);
                                                        FT.add(FlatType.THREEROOM);
                                                        typed = false;
                                                        break;
                                                    default:
                                                        System.out.println("invalid choice, try again");
                                                        break;
                                                }
                                            }
                                            int TwoR = 0;
                                            int ThreeR = 0;
                                                if(FT.contains(FlatType.TWOROOM)){
                                                    System.out.println("Enter number of two room flats:");
                                                    TwoR = sc.nextInt();
                                                    sc.nextLine();
                                                }
                                                if(FT.contains(FlatType.THREEROOM)){
                                                    System.out.println("Enter number of three room flats:");
                                                    ThreeR = sc.nextInt();
                                                    sc.nextLine();
                                                }
                                            currentManager.createProject(PN,N,Sdate,Edate,FT,TwoR,ThreeR);
                                            projects.add(currentManager.getProjects().get(currentManager.getProjects().size()-1));
                                            break;
                                        case 3:
                                            BTOProject P = currentManager.getProject();
                                            boolean editing = true;
                                            while(editing){
                                                System.out.println("Enter choice:");
                                                System.out.println("1. Edit project name\n2. Edit neighbourhood\n3. Edit start and end dates\n4. Edit room types\n5. Set visibility\n6. Exit");
                                                int EC = sc.nextInt();
                                                sc.nextLine();
                                                switch (EC){
                                                    case 1:
                                                        System.out.println("Enter new project name:");
                                                        String newName = sc.nextLine();
                                                        currentManager.editProjectname(P,newName);
                                                        break;
                                                    case 2:
                                                        System.out.println("Enter new neighbourhood:");
                                                        String newNeighbourhood = sc.nextLine();
                                                        currentManager.editProject(P,newNeighbourhood);
                                                        break;
                                                    case 3:
                                                        System.out.print("Enter application start date (YYYY-MM-DD): ");
                                                        String NSD = sc.nextLine();
                                                        LocalDate NSdate = LocalDate.parse(NSD); // Parses ISO format
                                                        System.out.print("Enter application end date (YYYY-MM-DD): ");
                                                        String NED = sc.nextLine();
                                                        LocalDate NEdate = LocalDate.parse(NED); // Parses ISO format
                                                        currentManager.editProject(P,NSdate,NEdate);
                                                        break;
                                                    case 4:
                                                        System.out.println("Select flat types availble:\n1. Two room only\n2. Three room only\n3. Both two and three Rooms");
                                                        int NFC = sc.nextInt();
                                                        sc.nextLine();
                                                        List<FlatType> NFT = new ArrayList<>();
                                                        boolean Ntyped = true;
                                                        while(Ntyped){
                                                            switch (NFC){
                                                                case 1:
                                                                    NFT.add(FlatType.TWOROOM);
                                                                    Ntyped = false;
                                                                    break;
                                                                case 2:
                                                                    NFT.add(FlatType.THREEROOM);
                                                                    Ntyped = false;
                                                                    break;
                                                                case 3:
                                                                    NFT.add(FlatType.TWOROOM);
                                                                    NFT.add(FlatType.THREEROOM);
                                                                    Ntyped = false;
                                                                default:
                                                                    System.out.println("invalid choice, try again");
                                                                    break;
                                                            }
                                                        }
                                                        int NTwoR = 0;
                                                        int NThreeR = 0;
                                                        if(NFT.contains(FlatType.TWOROOM)){
                                                            System.out.println("Enter number of two room flats:");
                                                            NTwoR = sc.nextInt();
                                                            sc.nextLine();
                                                        }
                                                        if(NFT.contains(FlatType.THREEROOM)){
                                                            System.out.println("Enter number of three room flats:");
                                                            NThreeR = sc.nextInt();
                                                            sc.nextLine();
                                                        }
                                                        currentManager.editProject(P,NFT,NTwoR,NThreeR);
                                                        break;
                                                        //might not want to edit this
                                                    case 5:
                                                        System.out.println("Select visibility option:\n1. On\n2. Off");
                                                        int VC = sc.nextInt();
                                                        sc.nextLine();
                                                        boolean toggling = true;
                                                        boolean v = true;
                                                        while (toggling){
                                                            switch (VC){
                                                                case 1:
                                                                    v = true;
                                                                    toggling = false;
                                                                    break;
                                                                case 2:
                                                                    v = false;
                                                                    toggling = false;
                                                                    break;
                                                                default:
                                                                    System.out.println("invalid choice please try again");
                                                                    break;
                                                            }
                                                        }
                                                        currentManager.editProject(P,v);
                                                        break;
                                                    case 6:
                                                        editing = false;
                                                        System.out.println("Exit successful");
                                                        break;
                                                    default:
                                                }
                                            }
                                            break;
                                        case 4:
                                            BTOProject p = currentManager.getProject();
                                            if (p.deletable()){
                                                System.out.println("Please confirm to delete this project\n1. Confirm\n2. Reject");
                                                int DC = sc.nextInt();
                                                switch (DC){
                                                    case 1:
                                                        projects.remove(p);
                                                        currentManager.deleteProject(p);
                                                        break;
                                                    case 2:
                                                        System.out.println("Deletion cancelled");
                                                        break;
                                                    default:
                                                        System.out.println("invalid choice");
                                                        break;
                                                }
                                                break;
                                            }
                                            else {
                                                System.out.println("cannot delete project due to dependencies");
                                                break;
                                            }

                                        case 5:
                                            System.out.println("Exit successful.");
                                            break;
                                        default:
                                    }
                                    break;
                                    //project creation, editing, deletion
                                case 2:
                                    System.out.println("Select choice by number:");
                                    System.out.println("1. View all applications for a project\n2. View pending applications\n3. Approve pending applications\n4. Manage application withdrawals\n5. Exit");
                                    int CA = sc.nextInt();
                                    sc.nextLine();
                                    switch (CA) {
                                        case 1:
                                            currentManager.viewApplications(currentManager.getProject());
                                        case 2:
                                            currentManager.viewPendingApplications(currentManager.getProject());
                                            break;
                                        case 3:
                                            currentManager.approveApplication();
                                            break;
                                        case 4:
                                            currentManager.manageWithdrawals();
                                            break;
                                        case 5:
                                            System.out.println("Exit successful.");
                                            break;
                                        default:
                                    }
                                    break;
                                case 3:
                                    System.out.println("Select choice by number:");
                                    System.out.println("1.View all registrations for a project\n2. View unaccepted registrations\n3. Approve unaccepted registrations\n4. Exit");
                                    int CR = sc.nextInt();
                                    sc.nextLine();
                                    switch (CR) {
                                        case 1:
                                            currentManager.viewRegistrations((currentManager.getProject()));
                                        case 2:
                                            currentManager.viewUnacceptedRegistrations(currentManager.getProject());
                                            break;
                                        case 3:
                                            currentManager.approveRegistration();
                                            break;
                                        case 4:
                                            break;
                                        default:
                                    }
                                    break;
                                case 4:
                                    System.out.println("Select choice by number:");
                                    managerEnquiryMenu();
                                    int C = sc.nextInt();
                                    sc.nextLine();
                                    switch (C) {
                                        case 1:
                                            currentManager.viewAllEnquiries(enquiries);
                                            break;
                                        case 2:
                                            currentManager.viewEnquiries();
                                            break;
                                        case 3:
                                            BTOProject P = currentManager.getProject();
                                            int i = currentManager.getEnquiryIndex(P);
                                            currentManager.replyEnquiry(i, P);
                                            break;
                                        case 4:
                                            System.out.println("Exit successful");
                                            break;
                                        default:
                                    }
                                    break;
                                case 5:
                                    break;
                                    //generate report
                                case 6:
                                    currentManager.changePassword();
                                    break;
                                case 7:
                                    loggedM = false;
                                    System.out.println("Successfully logged out.");
                                    break;
                                default:
                            }
                        }
                    }
                    break;
                case 4:
                    ProgramOn = false;
                    saveApplicants("ApplicantList.csv");
                    saveOfficers("OfficerList.csv");
                    saveManagers("ManagerList.csv");
                    saveProjects("ProjectList.csv");
                    saveApplications("ApplicationList.csv");
                    saveRegistrations("RegistrationList.csv");
                    saveEnquiries("EnquiryList.csv");
                    System.out.println("All data saved successfully");
                    System.out.println("Exiting program, Thank you for your Usage:)");
                    break;
                default:
                    System.out.println("invalid choice please try again");
                    break;
            }


        }
    }
}
