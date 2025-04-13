package SC2002_Assignment;

public class Application {
    private String applicationId;
    private Applicant applicant;
    private BTOProject project;
    private FlatType flatTypeBooking;
    private ApplicationStatus status;

    // Constructor, getters, setters
    public Application(String applicationId, Applicant applicant, BTOProject project) {
        this.applicationId = applicationId;
        this.applicant = applicant;
        this.project = project;
        this.flatTypeBooking = null;
        this.status = ApplicationStatus.PENDING;
        project.addApplication(this);
    }

    //getters
    public String getApplicationId(){return this.applicationId;}
    public Applicant getApplicant(){return this.applicant;}
    public BTOProject getProject(){return this.project;}
    public FlatType getFlatTypeBooking(){return this.flatTypeBooking;}
    public ApplicationStatus getStatus(){return this.status;}
    //setters
    public void setApplicationId(String I){this.applicationId = I;}
    public void setApplicant(Applicant A){this.applicant = A;}
    public void setProject(BTOProject P){this.project = P;}
    public void setFlatTypeBooking(FlatType T){this.flatTypeBooking = T;}
    public void setStatus(ApplicationStatus S){this.status = S;}

    public void displayApplication(){
        System.out.println("ApplicantionID:" + this.getApplicationId() + "\n" +
                           "Project of application:" + this.getProject() + "\n" +
                           "Booking choice:" + this.getFlatTypeBooking() + "\n" +
                           "Application status:" + this.getStatus());
    }

    public void deleteApplication(){
        this.setApplicationId(null);
        this.setApplicant(null);
        this.setProject(null);
        this.setFlatTypeBooking(null);
        this.setStatus(null);
        //System.out.println("deleted successfully");
    }


}
