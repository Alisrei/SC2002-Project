package SC2002_Assignment;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents an HDB Officer responsible for handling BTO (Build-To-Order) project applications, managing registrations,
 * viewing and selecting projects, handling flat bookings, responding to enquiries, and generating receipts.
 * The officer can register as part of a project, view and book flats, and interact with applicants.
 */
public class HDBOfficer extends Applicant implements ViewProjects, EnquiryReply, EnquiryView {

    private BTOProject assignedProject;
    private Registration registration;

    /**
     * Constructs an HDB Officer with the specified personal details.
     * @param nric The NRIC of the officer.
     * @param name The name of the officer.
     * @param password The password for the officer's account.
     * @param age The age of the officer.
     * @param isMarried The marital status of the officer.
     */
    public HDBOfficer(String nric, String name, String password, int age, boolean isMarried) {
        super(nric, name, password, age, isMarried); // Calls the parent (Applicant) constructor
    }

    // Getters and Setters

    public BTOProject getAssignedProject() {
        return this.assignedProject;
    }

    public Registration getRegistration() {
        return this.registration;
    }

    public void setAssignedProject(BTOProject assignedProject) {
        this.assignedProject = assignedProject;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    // Officer Project Duties

    /**
     * Registers the officer as part of the specified BTO project if the officer isn't already an applicant.
     * @param project The BTO project to register for.
     */
    public void registerAsOfficer(BTOProject project) {
        if (this.getApplication() != null && this.getApplication().getProject().equals(project)) {
            System.out.println("You are already applying for this project as an applicant.");
        } else {
            this.registration = new Registration(this.getName(), this, project);
            project.addRegistration(this.registration);
            this.setAssignedProject(project);
            System.out.println("Registration request created");
        }
    }

    /**
     * Allows the officer to select a BTO project from a list of available projects for registration.
     * @param allProjects The list of available BTO projects.
     * @return The selected BTO project.
     */
    public BTOProject selectProjectforRegistration(List<BTOProject> allProjects) {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        for (BTOProject project : allProjects) {
            System.out.print(i + ".");
            System.out.println(project.getProjectName());
            i += 1;
        }

        BTOProject selectedProject = null;
        while (selectedProject == null) {
            System.out.print("Enter your choice (1 to " + allProjects.size() + "): ");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > allProjects.size()) {
                    System.out.println("Invalid choice. Please select a number between 1 and " + allProjects.size() + ".");
                } else {
                    selectedProject = allProjects.get(choice - 1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
        }
        return selectedProject;
    }

    /**
     * Displays the details of the assigned BTO project.
     */
    public void viewAssignedProjectDetails() {
        if (assignedProject != null) {
            assignedProject.displayProjectDetails();
        } else {
            System.out.println("No assigned project.");
        }
    }

    // Officer Application Handling

    /**
     * Displays the list of successful applications for booking in the assigned project.
     */
    public void viewAssignedProjectApplicationsForBooking() {
        if (assignedProject != null) {
            if (assignedProject.getApplications().isEmpty()) {
                System.out.println("No applications");
                return;
            }
            int i = 1;
            for (Application application : assignedProject.getApplications()) {
                if (application.getStatus() == ApplicationStatus.SUCCESSFUL) {
                    System.out.print(i + ".");
                    application.displayApplication();
                    i += 1;
                }
            }
        } else {
            System.out.println("No assigned project.");
        }
    }

    /**
     * Allows the officer to select a successful application for booking.
     * @return The selected application.
     */
    public Application selectApplicationforBooking() {
        if (assignedProject != null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Pick application based on number:");
            this.viewAssignedProjectApplicationsForBooking();
            int choice = sc.nextInt();
            List<Application> selection = new ArrayList<>();
            for (Application application : assignedProject.getApplications()) {
                if (application.getStatus() == ApplicationStatus.SUCCESSFUL) {
                    selection.add(application);
                }
            }
            return selection.get(choice - 1);
        } else {
            System.out.println("No assigned project");
            return null;
        }
    }

    /**
     * Allows the officer to select a flat unit for the selected application.
     * @param application The application for which a flat is being selected.
     * @return The selected unit number.
     */
    public String selectunit(Application application) {
        if (assignedProject != null) {
            Scanner sc = new Scanner(System.in);
            if (application.getApplicant().isMarried()) {
                int i = 1;
                System.out.println("Select room based on number:");
                for (String unit : this.assignedProject.getFlats().getAvailableFlats()) {
                    System.out.print(i + ".");
                    System.out.println(unit);
                    i += 1;
                }
                int choice = sc.nextInt();
                return this.assignedProject.getFlats().getAvailableFlats().get(choice - 1);
            } else {
                List<String> twoRooms = new ArrayList<>();
                int i = 1;
                System.out.println("Select room based on number:");
                for (String unit : this.assignedProject.getFlats().getAvailableFlats()) {
                    if (unit.substring(0, 2).equals("2R")) {
                        System.out.print(i + ".");
                        System.out.println(unit);
                        twoRooms.add(unit);
                        i += 1;
                    }
                }
                int choice = sc.nextInt();
                return twoRooms.get(choice - 1);
            }
        } else {
            System.out.println("No assigned project");
            return null;
        }
    }

    /**
     * Handles the booking of a flat for a selected application.
     */
    public void handleFlatBooking() {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
        Application application = this.selectApplicationforBooking();
        String unitNumber = this.selectunit(application);
        boolean success = assignedProject.getFlats().bookFlat(unitNumber);
        if (!success) {
            System.out.println("Booking failed. Please check the unit number.");
        } else {
            application.setBookedUnit(unitNumber);
            application.setStatus(ApplicationStatus.BOOKED);
        }
    }

    // Officer Project Viewing Methods

    /**
     * Displays a list of eligible projects for the officer to view and apply.
     * @param allProjects The list of all available BTO projects.
     */
    public void viewProjects(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects created yet");
        }
        System.out.println("\n\n******** Eligible BTO Projects ********");
        boolean found = false;
        int i = 1;
        for (BTOProject project : allProjects) {
            if (project.getFlatTypes() != null && project.getVisibility() && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                i += 1;
                found = true;
            }
        }
        if (!found) {
            System.out.println("No eligible projects");
        }
    }

    /**
     * Allows the officer to select a project from a list of available BTO projects.
     * @param allProjects The list of available projects.
     * @return The selected BTO project.
     */
    public BTOProject selectProject(List<BTOProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects created yet");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        List<BTOProject> temp = new ArrayList<>();
        System.out.println("Select desired project based on number:");
        int i = 1;
        boolean found = false;
        for (BTOProject project : allProjects) {
            if (project.getFlatTypes() != null && project.getVisibility() && isEligibleForProject(project)) {
                System.out.println(i + "." + project.getProjectName());
                temp.add(project);
                i += 1;
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available projects found");
            return null;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return temp.get(choice - 1);
    }

    /**
     * Allows the officer to apply for a selected project.
     * @param project The BTO project the officer is applying for.
     */
    public void applyForProject(BTOProject project) {
        if (this.getApplication() != null) {
            System.out.println("You have already applied for a project.");
            return;
        }
        if (project == null) {
            System.out.println("Therefore no project to apply for.");
            return;
        }
        if (!isEligibleForProject(project)) {
            System.out.println("You do not meet the eligibility criteria for this project.");
            return;
        }
        this.setApplication(new Application(this.getName(), this, project));
        System.out.println("Application submitted for project: " + project.getProjectName());
    }

    private boolean isEligibleForProject(BTOProject project) {
        if (getAge() < 21) {
            return false;
        }
        if (!isMarried() && getAge() < 35) {
            return false;
        }
        if (!isMarried() && getAge() >= 35 && !project.getFlatTypes().contains(FlatType.TWOROOM)) {
            return false;
        }
        return !project.equals(this.assignedProject);
    }

    // Officer Enquiry Methods

    /**
     * Allows the officer to select an enquiry to respond to based on index.
     * @return The index of the selected enquiry.
     */
    public int getEnquiryIndex() {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        System.out.println("Select enquiry based on number:");
        for (Enquiry enquiry : this.getAssignedProject().getEnquiries()) {
            System.out.print(i + ".");
            enquiry.viewEnq();
            i += 1;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        return choice - 1;
    }

    /**
     * Displays all enquiries related to the assigned project.
     */
    public void viewEnquiries() {
        for (Enquiry enquiry : this.getAssignedProject().getEnquiries()) {
            enquiry.viewEnq();
        }
    }

    /**
     * Allows the officer to reply to a specific enquiry in the assigned project.
     * @param index The index of the enquiry to reply to.
     * @param project The BTO project related to the enquiry.
     */
    public void replyEnquiry(int index, BTOProject project) {
        if (index < 0 || index >= project.getEnquiries().size()) {
            System.out.println("Invalid enquiry index.");
            return;
        }
        if (project.getEnquiries().get(index).getReplied()) {
            System.out.println("Enquiry has already been replied to");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your reply to the enquiry:");
        String reply = sc.nextLine();
        project.getEnquiries().get(index).replyToEnq(reply);
    }

    /**
     * Checks whether the officer is assigned to the given project.
     * @param project The BTO project to check.
     * @return True if the officer is assigned to the project, otherwise false.
     */
    public boolean isOfficerOfProject(BTOProject project) {
        return assignedProject != null && assignedProject.equals(project);
    }

    // Officer Receipt Generation

    /**
     * Allows the officer to generate a booking receipt for a booked application.
     */
    public void generateReceipt() {
        Scanner sc = new Scanner(System.in);
        List<Application> bookedApplications = new ArrayList<>();
        int i = 1;
        if (this.assignedProject.getApplications().isEmpty()) {
            System.out.println("No Applications available");
            return;
        }
        System.out.println("Select booked application to generate receipt");
        boolean found = false;
        for (Application application : this.assignedProject.getApplications()) {
            if (application.getStatus() == ApplicationStatus.BOOKED) {
                System.out.print(i + ":");
                application.displayApplication();
                bookedApplications.add(application);
                i += 1;
                found = true;
            }
        }
        if (!found) {
            System.out.println("No booked applications available");
            return;
        }
        int choice = sc.nextInt();
        sc.nextLine();
        Application selectedApplication = bookedApplications.get(choice - 1);
        System.out.println("----- Booking Receipt -----");
        System.out.println("Applicant Name: " + selectedApplication.getApplicant().getName());
        System.out.println("NRIC: " + selectedApplication.getApplicant().getNric());
        System.out.println("Age: " + selectedApplication.getApplicant().getAge());
        System.out.println("Marital Status: " + (selectedApplication.getApplicant().isMarried() ? "Married" : "Single"));
        System.out.println("Project Name: " + selectedApplication.getProject().getProjectName());
        System.out.println("Booked Room: " + selectedApplication.getBookedUnit());
        System.out.println("---------------------------");
    }
}
