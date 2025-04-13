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

                // Assign officers
                if (data.length > 12 && !data[12].isEmpty()) {
                    String[] officerNames = data[12].split(",");
                    for (String name : officerNames) {
                        name = name.trim();
                        for (HDBOfficer officer : officers) {
                            if (officer.getName().equalsIgnoreCase(name)) {
                                project.addOfficer(officer);
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
    public static HashMap<String, String> createCombinedMap(
        List<Applicant> applicants,
        List<HDBOfficer> officers,
        List<HDBManager> managers) {

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

    public static boolean authenticate(String NRIC, String Password, HashMap Database){
        // Check if NRIC exists in the map
        if (!Database.containsKey(NRIC)) {return false;}
        // Compare passwords (use equals() for string comparison)
        return Database.get(NRIC).equals(Password);
    }
    public static void applicantMenu(){
        System.out.println("1. View available projects\n" +
                           "2. Apply for project\n" +
                           "3. View applied project\n" +
                           "4. Request application withdrawal\n" +
                           "5 manage enquiries\n" +
                           "6. Exit");
    }
    public static void officerMenu(){
        System.out.println("1. Register for project team\n" +
                           "2. View current registration status\n" +
                           "3. View current project details\n" +
                           "4. Update number of flats\n" +
                           "5. Retrieve applicant BTO application\n" +  //into update status and profile
                           "6. Generate applicant booking receipt\n" +
                           "7. Manage project enquiries\n" +
                           "8. Exit" );
    }
    public static void managerMenu(){
        System.out.println("1. Manage Projects\n" +
                           "2. Manage Applications\n" +
                           "3. Manage enquiries\n" +
                           "4. Generate Report\n");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadApplicants("/Users/Ruitao/eclipse-workspace/sce.cz2002.tkl.first/src/SC2002_Assignment/ApplicantList.csv");
        loadManagers("/Users/Ruitao/eclipse-workspace/sce.cz2002.tkl.first/src/SC2002_Assignment/ManagerList.csv");
        loadOfficers("/Users/Ruitao/eclipse-workspace/sce.cz2002.tkl.first/src/SC2002_Assignment/OfficerList.csv");
        loadProjects("/Users/Ruitao/eclipse-workspace/sce.cz2002.tkl.first/src/SC2002_Assignment/ProjectList.csv");
        //read csvs
        //create objs and add to lists
        System.out.println("Select user class to login");
        System.out.println("1. Applicant\n2. HDBOfficer\n3. HDBManager");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice){
            case 1:
                HashMap applicantMap = createApplicantMap(applicants);
                System.out.println("enter your Nric");
                String nricA = sc.nextLine();
                System.out.println("enter your Password");
                String PasswordA = sc.nextLine();
                Boolean loggedA = authenticate(nricA,PasswordA,applicantMap);
                while(loggedA){
                    Applicant currentApplicant = getApplicant(applicants,nricA);
                    applicantMenu();
                    int choiceA = sc.nextInt();
                    switch (choiceA){
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        default:
                    }
                }
                break;
            case 2:
                HashMap officerMap = createOfficerMap(officers);
                System.out.println("enter your Nric");
                String nricO = sc.nextLine();
                System.out.println("enter your Password");
                String PasswordO = sc.nextLine();
                Boolean loggedO = authenticate(nricO,PasswordO,officerMap);
                while(loggedO) {
                    HDBOfficer currentOfficer = getOfficer(officers, nricO);
                    officerMenu();
                    int choiceO = sc.nextInt();
                    switch (choiceO){
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        default:
                    }
                }
                break;
            case 3:
                HashMap managerMap = createManagerMap(managers);
                System.out.println("enter your Nric");
                String nricM = sc.nextLine();
                System.out.println("enter your Password");
                String PasswordM= sc.nextLine();
                Boolean loggedM = authenticate(nricM,PasswordM,managerMap);
                while(loggedM) {
                    HDBManager currentManager = getManager(managers, nricM);
                    managerMenu();
                    int choiceM = sc.nextInt();
                    switch (choiceM){
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        default:
                    }
                }
                break;
            default:
                System.out.println("invalid choice please try again");
                break;
        }



    }
}
