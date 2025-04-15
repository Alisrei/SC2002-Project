package SC2002_Assignment;

public class Enquiry {
    private String mainEnq;
    private String reply;
    private Boolean replied;
    private BTOProject project;
    private Applicant applicant;

    //getters
    public String getMainEnq() {return mainEnq;}
    public String getReply() {return reply;}
    public Boolean getReplied() {return replied;}
    public BTOProject getProject(){return project;}
    public Applicant getApplicant(){return applicant;}

    public Enquiry(String m,BTOProject p,Applicant A){
        this.mainEnq = m;
        this.reply = "no reply yet";
        this.replied = false;
        this.project = p;
        this.applicant = A;
        p.addEnquiry(this);
    }
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
    public void replyToEnq(String r){
        this.reply = r;
        this.replied = true;
        System.out.println("Enquiry replied.");
    }
    public void viewEnq(){
        System.out.println("Enquiry:" + this.getMainEnq());
        System.out.println("Reply:" + this.getReply());
        System.out.println("Project" + this.getProject().getProjectName());
    }

    public void deleteEnq(){
        if(this.replied == true){
            System.out.println("Unable to delete, enquiry has been replied");
        }
        else{
            this.mainEnq = null;
            this.reply = null;
            this.replied = null;
            this.project = null;
            System.out.println("Enquiry deleted successfully");
        }
    }
}
