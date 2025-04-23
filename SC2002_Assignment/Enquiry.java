package SC2002_Assignment;

public class Enquiry {
    private String mainEnq;
    private String reply;
    private Boolean replied;
    private BTOProject project;
    private Applicant applicant;

    //constructor
    public Enquiry(String m,BTOProject p,Applicant A){
        this.mainEnq = m;
        this.reply = "no reply yet";
        this.replied = false;
        this.project = p;
        this.applicant = A;
        p.addEnquiry(this);
    }

    //getters
    public String getMainEnq() {return mainEnq;}
    public String getReply() {return reply;}
    public Boolean getReplied() {return replied;}
    public BTOProject getProject(){return project;}
    public Applicant getApplicant(){return applicant;}

    //setters
    public void setApplicant(Applicant applicant) {this.applicant = applicant;}
    public void setMainEnq(String mainEnq) {this.mainEnq = mainEnq;}
    public void setProject(BTOProject project) {this.project = project;}
    public void setReplied(Boolean replied) {this.replied = replied;}
    public void setReply(String reply) {this.reply = reply;}

    //view enq
    public void viewEnq(){
        System.out.println("Enquiry:" + this.getMainEnq());
        System.out.println("Reply:" + this.getReply());
        System.out.println("Project:" + this.getProject().getProjectName());
    }

    //change enq
    public void editEnq(String e){
        if(this.replied == true){
            System.out.println("Unable to edit, enquiry has been replied");
        }
        else{
            this.mainEnq = e;
            System.out.println("Enquiry edited.");
        }
    }
    public void changeProject(BTOProject N){
        if(this.replied == true){
            System.out.println("Unable to change, enquiry has been replied");
        }
        else{
            this.project = N;
            System.out.println("Project changed.");
        }
    }
    public boolean deleteEnq(){
        if(this.replied == true){
            System.out.println("Unable to delete, enquiry has been replied");
            return false;
        }
        else{
            this.mainEnq = null;
            this.reply = null;
            this.replied = null;
            this.project = null;
            System.out.println("Enquiry deleted successfully");
            return true;
        }
    }
    public void replyToEnq(String r){
        this.reply = r;
        this.replied = true;
        System.out.println("Enquiry replied.");
    }




}
