package SC2002_Assignment;

public class Registration {
    private String regId;
    private HDBOfficer officer;
    private BTOProject project;
    private Boolean accepted;

    public Registration(String RID, HDBOfficer O, BTOProject P){
        this.regId = RID;
        this.officer = O;
        this.project = P;
        this.accepted = false;
    }
    //getters
    public String getRegId(){return this.regId;}
    public HDBOfficer getOfficer(){return this.officer;}
    public BTOProject getProject(){return this.project;}
    public Boolean getAccepted(){return this.accepted;}
    //setters
    public void setAccepted(Boolean T){this.accepted = T;}

    public void displayRegistration(){
        System.out.println("Registration ID:" + this.getRegId() + "\n" +
                "Project of Registration:" + this.getProject() + "\n" +
                "Registration status:" + this.getAccepted());
    }
}
