package SC2002_Assignment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BTOProject {
    private String projectName;
    private String neighborhood;
    private BTOFlats flats; // Stores types of flats and their count
    private List<FlatType> flatTypes; // List of flat types in this project
    private LocalDate applicationOpenDate;
    private LocalDate applicationCloseDate;
    private HDBManager managerInCharge;
    private List<Application> applications;
    private List<Registration> registrations;
    private List<Enquiry> enquiries;
    private List<HDBOfficer> assignedOfficers; // List of HDB Officers
    private static final int MAX_OFFICERS = 10;
    private Boolean visibility;


    //constructor
    public BTOProject(String projectName, String neighborhood, LocalDate applicationOpenDate,
                      LocalDate applicationCloseDate, HDBManager managerInCharge,
                      List<FlatType> flatTypes, int twoRoomFlats, int threeRoomFlats,int twoP, int threeP)
    {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.managerInCharge = managerInCharge;
        this.flatTypes = flatTypes;
        this.flats = new BTOFlats(twoRoomFlats, threeRoomFlats,twoP,threeP);
        this.assignedOfficers = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.registrations = new ArrayList<>();
        this.enquiries = new ArrayList<>();
        LocalDate today = LocalDate.now();
        if (today.isAfter(applicationOpenDate) && today.isBefore(applicationCloseDate)){
            this.visibility = true;
        }
        else {
            this.visibility = false;
        }
    }
    //getters
    public String getProjectName(){return projectName;}
    public String getNeighborhood(){return neighborhood;}
    public BTOFlats getFlats(){return flats;}
    public List<FlatType> getFlatTypes(){return flatTypes;}
    public LocalDate getApplicationOpenDate(){return applicationOpenDate;}
    public LocalDate getApplicationCloseDate(){return applicationCloseDate;}
    public HDBManager getManagerInCharge(){return managerInCharge;}
    public List<Application> getApplications(){return applications;}
    public List<Registration> getRegistrations(){return registrations;}
    public List<Enquiry> getEnquiries(){return enquiries;}
    public List<HDBOfficer> getAssignedOfficers(){return assignedOfficers;}
    public Boolean getVisibility(){return visibility;}

    //setters
    public void setProjectName(String Name){this.projectName = Name;}
    public void setNeighborhood(String Neighbourhood){this.neighborhood = Neighbourhood;}
    public void setFlats(BTOFlats Flats){this.flats = Flats;}
    public void setFlatTypes(List<FlatType> FlatType){this.flatTypes = FlatType;}
    public void setApplicationOpenDate(LocalDate Open){this.applicationOpenDate = Open;}
    public void setApplicationCloseDate(LocalDate Close){this.applicationCloseDate = Close;}
    public void setManagerInCharge(HDBManager M){this.managerInCharge = M;}
    public void setAssignedOfficers(List<HDBOfficer> o){this.assignedOfficers = o;}
    public void setVisibility(Boolean vis){this.visibility = vis;}
    public void addApplication(Application A){this.applications.add(A);}
    public void addRegistration(Registration R){this.registrations.add(R);}
    public void addEnquiry(Enquiry E){this.enquiries.add(E);}
    public boolean addOfficer(HDBOfficer officer) {
        if (assignedOfficers.size() < MAX_OFFICERS) {
            assignedOfficers.add(officer);
            return true;
        } else {
            return false;
        }
    }
    public boolean removeOfficer(HDBOfficer officer) {
        if (assignedOfficers.contains(officer)) {
            assignedOfficers.remove(officer);
            return true;
        } else {
            return false;
        }
    }


    //view proj
    public void displayProjectDetails() {
        System.out.println("Project Name: " + this.projectName);
        System.out.println("Neighborhood: " + this.neighborhood);
        System.out.println("Flat Types Available: " + this.flatTypes);
        System.out.println("Application Open Date: " + this.applicationOpenDate);
        System.out.println("Application Close Date: " + this.applicationCloseDate);
        System.out.println("HDB Manager in Charge: " + this.managerInCharge.getName());
        System.out.println("Number of applications:" + this.applications.size());
        System.out.print("Assigned Officers (" + this.assignedOfficers.size() + "/" + MAX_OFFICERS + "): "); //+ this.assignedOfficers);
        System.out.println(
                this.assignedOfficers.stream()
                        .map(HDBOfficer::getName)
                        .collect(Collectors.joining(", "))
        );


        // Display total flats and price based on available types
        if (this.flatTypes.contains(FlatType.TWOROOM)) {
            System.out.println("Total 2-Room Flats: " + flats.getTwoRoomFlats());
            System.out.println("Two room flat price:" + this.flats.getTwoRoomPrice());
        }
        if (this.flatTypes.contains(FlatType.THREEROOM)) {
            System.out.println("Total 3-Room Flats: " + flats.getThreeRoomFlats());
            System.out.println("Three room flat price:" + this.flats.getThreeRoomPrice());
        }

        flats.displayAvailableFlats();
    }

    // Checks if within application period
    public boolean isWithinApplicationPeriod(LocalDate date) {
        return (date.isEqual(applicationOpenDate) || date.isAfter(applicationOpenDate)) &&
                (date.isEqual(applicationCloseDate) || date.isBefore(applicationCloseDate));
    }

    //check if deletable
    public boolean deletable(){
        return this.applications.isEmpty() && this.getRegistrations().isEmpty() && this.enquiries.isEmpty() && this.assignedOfficers.isEmpty();
    }


    // Removes an officer by ID
    public boolean removeOfficer(String officerNric) {
        for (HDBOfficer officer : assignedOfficers) {
            if (officer.getNric().equals(officerNric)) {
                assignedOfficers.remove(officer);
                System.out.println("Officer " + officer.getName() + " removed.");
                return true;
            }
        }
        System.out.println("Officer Nric " + officerNric + " not found.");
        return false;
    }
}
