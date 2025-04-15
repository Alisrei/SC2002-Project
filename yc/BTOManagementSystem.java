import java.io.*;
import java.util.*;
import java.util.regex.*;

public class BTOManagementSystem {
    public static void main(String[] arg)
    {
        List<Applicant> applicantList = readApplicants("ApplicantList.csv");
        List<Officer> officerList = readOfficers("OfficerList.csv");
        List<Manager> managerList = readManagers("ManagerList.csv");
        List<Project> projectList = readProjects("ProjectList.csv");

        // Preview of Data Extracted (password not printed)
        System.out.println("\nApplicants:");
        for (Applicant applicant : applicantList) {
            System.out.println(applicant);
        }

        System.out.println("\nOfficers:");
        for (Officer officer : officerList) {
            System.out.println(officer);
        }

        System.out.println("\nManagers:");
        for (Manager manager : managerList) {
            System.out.println(manager);
        }

        System.out.println("\nProjects:");
        for (Project project : projectList) {
            System.out.println(project);
        }
        System.out.println();

        // Start of Application
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        while (running)
        {
            // Display options
            System.out.println("===== BTO Management System =====");
            System.out.println("1. Login as Applicant");
            System.out.println("2. Login as Staff");
            System.out.print("Enter your choice (0 to exit): ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice)
            {
                case 0:
                    running = false;
                    break;
                case 1:
                    String userID = getUserID(sc);

                    break;
                case 2:

                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        sc.close();
        System.out.println("Shutting down!");
    }
    
    public static List<Applicant> readApplicants(String filePath) {
        List<Applicant> applicants = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0].trim();
                String nric = values[1].trim();
                int age = Integer.parseInt(values[2].trim());
                String maritalStatus = values[3].trim();
                String password = values[4].trim();
                
                applicants.add(new Applicant(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicants;
    }
    
    public static List<Officer> readOfficers(String filePath) {
        List<Officer> officers = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0].trim();
                String nric = values[1].trim();
                int age = Integer.parseInt(values[2].trim());
                String maritalStatus = values[3].trim();
                String password = values[4].trim();
                
                officers.add(new Officer(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return officers;
    }
    
    public static List<Manager> readManagers(String filePath) {
        List<Manager> managers = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0].trim();
                String nric = values[1].trim();
                int age = Integer.parseInt(values[2].trim());
                String maritalStatus = values[3].trim();
                String password = values[4].trim();
                
                managers.add(new Manager(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return managers;
    }
    
    public static List<Project> readProjects(String filePath) {
        List<Project> projects = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            
            while ((line = br.readLine()) != null) {
                // Use a more robust CSV parsing approach
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                String projectName = values[0].trim();
                String neighborhood = values[1].trim();
                
                // Create flats
                List<Flat> flats = new ArrayList<>();
                flats.add(new Flat(
                    values[2].trim(), 
                    Integer.parseInt(values[3].trim()), 
                    Double.parseDouble(values[4].trim())
                ));
                
                // Check if there's a second type
                if (values.length > 5 && !values[5].trim().isEmpty()) {
                    flats.add(new Flat(
                        values[5].trim(), 
                        Integer.parseInt(values[6].trim()), 
                        Double.parseDouble(values[7].trim())
                    ));
                }
                
                String openingDate = values[8].trim();
                String closingDate = values[9].trim();
                String manager = values[10].trim();
                
                // Parse officers - handle quoted field with commas
                List<String> officers = new ArrayList<>();
                String officersStr = values[12].trim().replace("\"", "");
                for (String officer : officersStr.split(",")) {
                    officers.add(officer.trim());
                }
                
                projects.add(new Project(
                    projectName, neighborhood, flats, 
                    openingDate, closingDate, manager, officers
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static String getUserID(Scanner sc) {
        boolean validID = false;
        String userID = "";
        Pattern pattern = Pattern.compile("^[STst]\\d{7}[A-Za-z]$");

        while (!validID)
        {
            System.out.print("Enter User ID: ");
            userID = sc.nextLine().trim();
            if (pattern.matcher(userID).matches())
            {
                validID = true;
            }
            else
            {
                System.out.println("Invalid User ID! Format: S or T + 7 digits + 1 letter.");
            }
        }
        return userID;
    }

    

}
