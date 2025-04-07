package SC2002_Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HDBManager extends User {
    private List<BTOProject> projects;

    public HDBManager(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried);
        this.projects = new ArrayList<>();
    }

    // Create a new BTO project
    public void createProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                              LocalDate applicationCloseDate, List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats) {
        BTOProject newProject = new BTOProject(projectName, neighborhood, applicationOpenDate, applicationCloseDate, this, flatTypes, twoRoomFlats, threeRoomFlats);
        projects.add(newProject);
        System.out.println("Project " + projectName + " created successfully.");
    }

    // Edit an existing BTO project
    public void editProject(String projectName, String newNeighborhood, LocalDate newApplicationOpenDate,
                            LocalDate newApplicationCloseDate, List<FlatType> newFlatTypes, int newTwoRoomFlats, int newThreeRoomFlats) {
        for (BTOProject project : projects) {
            if (project.getProjectName().equals(projectName)) {
                project.setNeighborhood(newNeighborhood);
                project.setApplicationOpenDate(newApplicationOpenDate);
                project.setApplicationCloseDate(newApplicationCloseDate);
                project.setFlatTypes(newFlatTypes);
                project.setFlats(newTwoRoomFlats, newThreeRoomFlats);
                System.out.println("Project " + projectName + " updated successfully.");
                return;
            }
        }
        System.out.println("Project " + projectName + " not found.");
    }

    // Delete an existing BTO project
    public void deleteProject(String projectName) {
        projects.removeIf(project -> project.getProjectName().equals(projectName));
        System.out.println("Project " + projectName + " deleted successfully.");
    }

    // Approve or reject HDB Officer registration
    public void approveOfficerRegistration(HDBOfficer officer, BTOProject project) {
        if (project.addOfficer(officer)) {
            System.out.println("Officer " + officer.getName() + " approved for project " + project.getProjectName() + ".");
        } else {
            System.out.println("Officer " + officer.getName() + " could not be approved for project " + project.getProjectName() + ".");
        }
    }

    // Approve or reject BTO application
    public void approveBTOApplication(Application application, boolean isApproved) {
        if (isApproved) {
            application.setStatus(ApplicationStatus.SUCCESSFUL);
            System.out.println("Application " + application.getApplicationId() + " approved.");
        } else {
            application.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application " + application.getApplicationId() + " rejected.");
        }
    }

    // Generate report of applicants
    public void generateReport() {
        for (BTOProject project : projects) {
            System.out.println("Project: " + project.getProjectName());
            project.displayProjectDetails();
        }
    }
}
