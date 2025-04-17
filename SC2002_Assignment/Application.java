package SC2002_Assignment;

public class Application {
    private String applicationId;
    private Applicant applicant;
    private BTOProject project;
    private FlatType flatTypeBooking;
    private String bookedUnit;
    private ApplicationStatus status;

    // Constructor, getters, setters
    public Application(String applicationId, Applicant applicant, BTOProject project) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.project = project;
        this.flatTypeBooking = null;
        this.bookedUnit = null;
        this.status = ApplicationStatus.PENDING;
        project.addApplication(this);
    }

    //getters
    public String getApplicationId(){return this.applicationId;}
    public Applicant getApplicant(){return this.applicant;}
    public BTOProject getProject(){return this.project;}
    public FlatType getFlatTypeBooking(){return this.flatTypeBooking;}
    public String getBookedUnit() {return bookedUnit;}
    public ApplicationStatus getStatus(){return this.status;}
    //setters
    public void setApplicationId(String I){this.applicationId = I;}
    public void setApplicant(Applicant A){this.applicant = A;}
    public void setProject(BTOProject P){this.project = P;}
    public void setFlatTypeBooking(FlatType T){this.flatTypeBooking = T;}
    public void setBookedUnit(String BU) {this.bookedUnit = BU;}
    public void setStatus(ApplicationStatus S){this.status = S;}

    public void displayApplication(){
        System.out.println("ApplicantionID:" + this.getApplicationId() + "\n" +
                           "Project of application:" + this.getProject().getProjectName() + "\n" +
                           "Booking choice:" + this.getFlatTypeBooking() + "\n" +
                           "Application status:" + this.getStatus() + "\n" +
                           "Booked unit:" + this.getBookedUnit());
    }

    public void deleteApplication(){
        this.getProject().getFlats().addRoom(this.getBookedUnit());
        this.setApplicationId(null);
        this.setApplicant(null);
        this.setProject(null);
        this.setFlatTypeBooking(null);
        this.setBookedUnit(null);
        this.setStatus(null);
        //System.out.println("deleted successfully");
    }


}
