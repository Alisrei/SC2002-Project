package SC2002_Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HDBManager extends User {
    private List<BTOProject> projects;
    private Map<String, Boolean> projectVisibility;
    private List<Enquiry> enquiries;

    public static class Enquiry {
        private String enquiryId;
        private String content;
        private String reply;

        public Enquiry(String enquiryId, String content) {
            this.enquiryId = enquiryId;
            this.content = content;
            this.reply = "";
        }

        public String getEnquiryId() {
            return enquiryId;
        }

        public String getContent() {
            return content;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String toString() {
            return "Enquiry ID: " + enquiryId + "\nContent: " + content +
                   "\nReply: " + (reply.isEmpty() ? "No reply yet" : reply);
        }
    }

    public HDBManager(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried);
        this.projects = new ArrayList<>();
        this.projectVisibility = new HashMap<>();
        this.enquiries = new ArrayList<>();
    }

    public void createProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                              LocalDate applicationCloseDate, List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats) {
        BTOProject newProject = new BTOProject(projectName, neighborhood, applicationOpenDate, applicationCloseDate, this, flatTypes, twoRoomFlats, threeRoomFlats);
        projects.add(newProject);
        projectVisibility.put(projectName, true);
        System.out.println("Project " + projectName + " created successfully and is visible to applicants.");
    }

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

    public void deleteProject(String projectName) {
        projects.removeIf(project -> project.getProjectName().equals(projectName));
        projectVisibility.remove(projectName);
        System.out.println("Project " + projectName + " deleted successfully.");
    }

    public void toggleVisibility(String projectName) {
        if (projectVisibility.containsKey(projectName)) {
            boolean currentVisibility = projectVisibility.get(projectName);
            boolean newVisibility = !currentVisibility;
            projectVisibility.put(projectName, newVisibility);
            System.out.println("Project " + projectName + " visibility set to " + (newVisibility ? "ON" : "OFF") + ".");
        } else {
            System.out.println("Project " + projectName + " not found.");
        }
    }
    
    public void approveHDBOfficer(HDBOfficer officer, BTOProject project) {
        if (project.addOfficer(officer)) {
            System.out.println("Officer " + officer.getName() + " approved for project " + project.getProjectName() + ".");
        } else {
            System.out.println("Officer " + officer.getName() + " could not be approved for project " + project.getProjectName() + ".");
        }
    }

    public void approveApplication(Application application, boolean isApproved) {
        if (isApproved) {
            application.setStatus(ApplicationStatus.SUCCESSFUL);
            System.out.println("Application " + application.getApplicationId() + " approved.");
        } else {
            application.setStatus(ApplicationStatus.UNSUCCESSFUL);
            System.out.println("Application " + application.getApplicationId() + " rejected.");
        }
    }

    public void approveWithdrawal(Application application, boolean isApproved) {
        if (isApproved) {
            application.setStatus(ApplicationStatus.WITHDRAWAL_APPROVED);
            System.out.println("Withdrawal request for application " + application.getApplicationId() + " approved.");
        } else {
            application.setStatus(ApplicationStatus.WITHDRAWAL_REJECTED);
            System.out.println("Withdrawal request for application " + application.getApplicationId() + " rejected.");
        }
    }

    public void generateReport() {
        for (BTOProject project : projects) {
            System.out.println("Project: " + project.getProjectName());
            project.displayProjectDetails();
            boolean visible = projectVisibility.getOrDefault(project.getProjectName(), true);
            System.out.println("Visibility: " + (visible ? "ON" : "OFF"));
        }
    }

    public void addEnquiry(String enquiryId, String content) {
        Enquiry newEnquiry = new Enquiry(enquiryId, content);
        enquiries.add(newEnquiry);
        System.out.println("Enquiry " + enquiryId + " added.");
    }
    
    public void viewEnquiries() {
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available.");
        } else {
            System.out.println("Enquiries:");
            for (Enquiry enquiry : enquiries) {
                System.out.println("------------------------");
                System.out.println(enquiry);
            }
            System.out.println("------------------------");
        }
    }

    public void replyToEnquiry(String enquiryId, String reply) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryId().equals(enquiryId)) {
                enquiry.setReply(reply);
                System.out.println("Reply sent for Enquiry " + enquiryId + ".");
                return;
            }
        }
        System.out.println("Enquiry with ID " + enquiryId + " not found.");
    }
}
