package SC2002_Assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class testMain {
    public static List<Applicant> applicants = new ArrayList<>();
    public static List<HDBManager> managers = new ArrayList<>();
    public static List<HDBOfficer> officers = new ArrayList<>();
    public static List<BTOProject> projects = new ArrayList<>();
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
                BTOProject project = new BTOProject(data[0], data[1], openDate, closeDate, manager, flatTypes, twoRoomFlats, threeRoomFlats, true);
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
//    private static void loadEnquiries(String filename){to be implemented}

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

    // Combined HashMap of all user types
    public static HashMap<String, String> createCombinedMap(List<Applicant> applicants, List<HDBOfficer> officers, List<HDBManager> managers) {

    HashMap<String, String> combinedMap = new HashMap<>();

    // Add all applicants
    for (Applicant applicant : applicants) {
        combinedMap.put(applicant.getNric(), applicant.getPassword());
    }

    // Add all officers
    for (HDBOfficer officer : officers) {
        combinedMap.put(officer.getNric(), officer.getPassword());
    }

    // Add all managers
    for (HDBManager manager : managers) {
        combinedMap.put(manager.getNric(), manager.getPassword());
    }

    return combinedMap;
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
        System.out.println("1. View available projects\n" +
                           "2. Apply for project\n" +
                           "3. View applied project\n" +
                           "4. Request application withdrawal\n" +
                           "5. Book flats\n" +
                           "6. manage enquiries\n" +
                           "7. Logout");
    }
    public static void applicantEnquiryMenu(){
        System.out.println("1. Create enquiry\n" +
                           "2. View enquiries\n" +
                           "3. Edit enquiry\n" +
                           "4. Delete enquiry\n" +
                           "5. Exit");
    }
    public static void officerMenu(){
        System.out.println("1. Register for project team\n" +
                           "2. View current registration status\n" +
                           "3. View current project details\n" +
                           "4. View Booking Applications\n" +
                           "5. Book flat from Application\n" +  //into update status and profile
                           "6. Generate applicant booking receipt\n" +
                           "7. Manage project enquiries\n" +
                           "8. Logout" );
    }
    public static void officerEnquiryMenu(){
        System.out.println("1. View enquiries\n" +
                           "2. reply to enquiry\n" +
                           "3. Exit");
    }
    public static void managerMenu(){
        System.out.println("1. Manage projects\n" +
                           "2. Manage applications\n" +
                           "3. Manage registrations\n" +
                           "4. Manage enquiries\n" +
                           "5. Generate report\n" +
                           "6. Logout");
    }
    public static void managerEnquiryMenu() {
        System.out.println("1. View all enquiries\n" +
                          "2. View enquiries within managed projects" +
                         "3. reply to enquiry within managed projects\n" +
                         "4. Exit");
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadApplicants("ApplicantList.csv");
        loadManagers("ManagerList.csv");
        loadOfficers("OfficerList.csv");
        loadProjects("ProjectList.csv");

        boolean ProgramOn = true;
        while (ProgramOn) {
            System.out.println("Select user class to login");
            System.out.println("1. Applicant\n2. HDBOfficer\n3. HDBManager\n4. Exit program");
            int choice = sc.nextInt();
            sc.nextLine();
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
                        while (loggedA || loggedOA) {
                            applicantMenu();
                            int choiceA = sc.nextInt();
                            sc.nextLine();
                            switch (choiceA) {
                                case 1:
                                    currentApplicant.viewProjects(projects);
                                    break;
                                case 2:
                                    if (loggedA){
                                        currentApplicant.applyForProject(currentApplicant.selectProject(projects));
                                        break;
                                    }
                                    else{

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
                                    currentApplicant.withdrawApplication();
                                    break;
                                case 5:
                                    currentApplicant.bookFlat();
                                    break;
                                case 6:
                                    System.out.println("Select choice by number:");
                                    applicantEnquiryMenu();
                                    int C = sc.nextInt();
                                    sc.nextLine();
                                    switch (C) {
                                        case 1:
                                            System.out.println("Enter your enquiry:");
                                            String E = sc.nextLine();
                                            currentApplicant.submitEnquiry(E, projects);
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
                                            if(i == 0){
                                                break;
                                            }
                                            System.out.println("Enter your edited enquiry:");
                                            String EE = sc.nextLine();
                                            currentApplicant.editEnquiry(i, EE);
                                            break;
                                        case 4:
                                            int Index = currentApplicant.getEnquiryIndex();
                                            if (Index == 0){
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
                                case 7:
                                    loggedA = false;
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
                    while (loggedO) {
                        HDBOfficer currentOfficer = getOfficer(officers, nricO);
                        officerMenu();
                        int choiceO = sc.nextInt();
                        sc.nextLine();
                        switch (choiceO) {
                            case 1:
                                currentOfficer.registerAsOfficer(currentOfficer.selectProjectforRegistration(projects));
                                break;
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
                                        System.out.println("Enter your reply to the enquiry:");
                                        String R = sc.nextLine();
                                        currentOfficer.replyEnquiry(i, R);
                                        break;
                                    case 3:
                                        System.out.println("Exit successful");
                                        break;
                                    default:
                                }
                                break;

                            case 8:
                                loggedO = false;
                                System.out.println("Successfully logged out.");
                                break;
                            default:
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
                    while (loggedM) {
                        HDBManager currentManager = getManager(managers, nricM);
                        managerMenu();
                        int choiceM = sc.nextInt();
                        sc.nextLine();
                        switch (choiceM) {
                            case 1:
                                System.out.println("Select choice by number:");
                                System.out.println("1. Create project\n2. Edit project\n3. Delete project\n4. Exit");
                                int CP = sc.nextInt();
                                sc.nextLine();
                                switch (CP) {
                                    case 1:
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
                                        System.out.println("Select visibility:\n1. On\n2. Off");
                                            int V = sc.nextInt();
                                            sc.nextLine();
                                            boolean Vis = true;
                                            boolean x = true;
                                            while (x){
                                                switch (V){
                                                    case 1:
                                                        Vis = true;
                                                        x = false;
                                                        break;
                                                    case 2:
                                                        Vis = false;
                                                        x = false;
                                                        break;
                                                    default:
                                                        System.out.println("invalid choice please try again");
                                                        break;
                                                }
                                            }
                                        currentManager.createProject(PN,N,Sdate,Edate,FT,TwoR,ThreeR,Vis);
                                        break;
                                    case 2:
                                        BTOProject P = currentManager.getProject();
                                        System.out.println("Enter choice:");
                                        System.out.println("1. Edit project name\n2. Edit neighbourhood\n3. Edit start and end dates\n4. Edit room types\n5. Set visibility\n6. Exit");
                                        int EC = sc.nextInt();
                                        sc.nextLine();
                                        boolean editing = true;
                                        while(editing){
                                            switch (EC){
                                                case 1:
                                                    System.out.println("Enter new project name:");
                                                    String newName = sc.nextLine();
                                                    currentManager.editProjectname(P,newName);
                                                case 2:
                                                    System.out.println("Enter new neighbourhood:");
                                                    String newNeighbourhood = sc.nextLine();
                                                    currentManager.editProject(P,newNeighbourhood);
                                                case 3:
                                                    System.out.print("Enter application start date (YYYY-MM-DD): ");
                                                    String NSD = sc.nextLine();
                                                    LocalDate NSdate = LocalDate.parse(NSD); // Parses ISO format
                                                    System.out.print("Enter application end date (YYYY-MM-DD): ");
                                                    String NED = sc.nextLine();
                                                    LocalDate NEdate = LocalDate.parse(NED); // Parses ISO format
                                                    currentManager.editProject(P,NSdate,NEdate);
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
                                                case 6:
                                                    editing = false;
                                                    System.out.println("Exit successful");
                                                    break;
                                                default:
                                            }
                                        }
                                        break;
                                    case 3:
                                        currentManager.deleteProject(currentManager.getProject());
                                        //abit sus ngl
                                    case 4:
                                        System.out.println("Exit successful.");
                                        break;
                                    default:
                                }
                                break;
                                //project creation, editing, deletion
                            case 2:
                                System.out.println("Select choice by number:");
                                System.out.println("1. View pending applications\n2. Approve pending applications\n3. Manage application withdrawals\n4. Exit");
                                int CA = sc.nextInt();
                                sc.nextLine();
                                switch (CA) {
                                    case 1:
                                        currentManager.viewPendingApplications(currentManager.getProject());
                                        break;
                                    case 2:
                                        currentManager.approveApplication();
                                        break;
                                    case 3:
                                        currentManager.manageWithdrawals();
                                        break;
                                    case 4:
                                        System.out.println("Exit successful.");
                                        break;
                                    default:
                                }
                                break;
                            case 3:
                                System.out.println("Select choice by number:");
                                System.out.println("1. View unaccepted registrations\n2. Approve unaccepted registrations\n3. Exit");
                                int CR = sc.nextInt();
                                sc.nextLine();
                                switch (CR) {
                                    case 1:
                                        currentManager.viewUnacceptedRegistrations(currentManager.getProject());
                                        break;
                                    case 2:
                                        currentManager.approveRegistration();
                                        break;
                                    case 3:
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
                                        int i = currentManager.getEnquiryIndex();
                                        System.out.println("Enter your reply to the enquiry:");
                                        String R = sc.nextLine();
                                        currentManager.replyEnquiry(i, R);
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
                                loggedM = false;
                                System.out.println("Successfully logged out.");
                                break;
                            default:
                        }
                    }
                    break;
                case 4:
                    ProgramOn = false;
                    System.out.println("Exiting program, Thank you for your Usage:)");
                    break;
                default:
                    System.out.println("invalid choice please try again");
                    break;
            }


        }
    }
}
