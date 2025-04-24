package SC2002_Assignment;

/**
 * Represents an enquiry made by an applicant for a Build-to-Order (BTO) project.
 * This class stores the details of the enquiry such as the enquiry text, reply, 
 * whether the enquiry has been replied to, the associated project, and the applicant who made the enquiry.
 * It also provides methods to view, edit, reply to, delete, or change the project associated with the enquiry.
 */
public class Enquiry {
    private String Enqid;
    private String mainEnq;
    private String reply;
    private Boolean replied;
    private BTOProject project;
    private Applicant applicant;

    /**
     * Constructs a new enquiry with the specified enquiry text, BTO project, and applicant.
     * Initializes the enquiry as not replied with a default reply message of "no reply yet".
     * 
     * @param m The main enquiry text.
     * @param p The BTO project associated with the enquiry.
     * @param A The applicant who made the enquiry.
     */
    public Enquiry(String m, BTOProject p, Applicant A) {
        this.mainEnq = m;
        this.reply = "no reply yet";
        this.replied = false;
        this.project = p;
        this.applicant = A;
        this.Enqid = A.getName();
        p.addEnquiry(this);
    }

    // Getters and Setters

    /**
     * Gets the main enquiry text.
     * 
     * @return The main enquiry.
     */
    public String getMainEnq() {
        return mainEnq;
    }

    /**
     * Gets the reply to the enquiry.
     * 
     * @return The reply to the enquiry.
     */
    public String getReply() {
        return reply;
    }

    /**
     * Checks if the enquiry has been replied to.
     * 
     * @return True if the enquiry has been replied to, otherwise false.
     */
    public Boolean getReplied() {
        return replied;
    }

    /**
     * Gets the BTO project associated with the enquiry.
     * 
     * @return The associated BTO project.
     */
    public BTOProject getProject() {
        return project;
    }

    /**
     * Gets the applicant who made the enquiry.
     * 
     * @return The applicant.
     */
    public Applicant getApplicant() {
        return applicant;
    }

    // Setters

    /**
     * Sets the applicant for the enquiry.
     * 
     * @param applicant The applicant who made the enquiry.
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * Sets the main enquiry text.
     * 
     * @param mainEnq The main enquiry text.
     */
    public void setMainEnq(String mainEnq) {
        this.mainEnq = mainEnq;
    }

    /**
     * Sets the BTO project associated with the enquiry.
     * 
     * @param project The BTO project.
     */
    public void setProject(BTOProject project) {
        this.project = project;
    }

    /**
     * Sets the reply status for the enquiry (whether it has been replied to).
     * 
     * @param replied True if the enquiry has been replied to, otherwise false.
     */
    public void setReplied(Boolean replied) {
        this.replied = replied;
    }

    /**
     * Sets the reply to the enquiry.
     * 
     * @param reply The reply to the enquiry.
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    // Methods for viewing and editing the enquiry

    /**
     * Displays the details of the enquiry, including the main enquiry text, the reply, and the associated project.
     */
    public void viewEnq() {
        System.out.println("Enquiry: " + this.getMainEnq());
        System.out.println("Reply: " + this.getReply());
        System.out.println("Project: " + this.getProject().getProjectName());
    }

    /**
     * Edits the main enquiry text. If the enquiry has already been replied to, the enquiry cannot be edited.
     * 
     * @param e The new enquiry text.
     */
    public void editEnq(String e) {
        if (this.replied) {
            System.out.println("Unable to edit, enquiry has been replied.");
        } else {
            this.mainEnq = e;
            System.out.println("Enquiry edited.");
        }
    }

    /**
     * Changes the BTO project associated with the enquiry. If the enquiry has already been replied to, 
     * the project cannot be changed.
     * 
     * @param N The new BTO project to associate with the enquiry.
     */
    public void changeProject(BTOProject N) {
        if (this.replied) {
            System.out.println("Unable to change, enquiry has been replied.");
        } else {
            this.project = N;
            System.out.println("Project changed.");
        }
    }

    /**
     * Deletes the enquiry. If the enquiry has already been replied to, it cannot be deleted.
     * 
     * @return True if the enquiry is deleted successfully, otherwise false.
     */
    public boolean deleteEnq() {
        if (this.replied) {
            System.out.println("Unable to delete, enquiry has been replied.");
            return false;
        } else {
            this.mainEnq = null;
            this.reply = null;
            this.replied = null;
            this.project = null;
            System.out.println("Enquiry deleted successfully.");
            return true;
        }
    }

    /**
     * Replies to the enquiry. Once the enquiry is replied to, the `replied` flag is set to true.
     * 
     * @param r The reply to the enquiry.
     */
    public void replyToEnq(String r) {
        this.reply = r;
        this.replied = true;
        System.out.println("Enquiry replied.");
    }
}
