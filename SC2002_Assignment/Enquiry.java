package SC2002_Assignment;

/**
 * Represents an enquiry made by an applicant regarding a specific BTO (Build-To-Order) project.
 * Each enquiry can have a reply, and its status (whether replied or not) can be tracked.
 * Provides methods to edit, delete, reply, and view enquiry details.
 */
public class Enquiry {
    
    private String Enqid;
    private String mainEnq;
    private String reply;
    private Boolean replied;
    private BTOProject project;
    private Applicant applicant;

    /**
     * Constructor for creating a new enquiry.
     * 
     * @param m the main enquiry text.
     * @param p the BTO project related to the enquiry.
     * @param A the applicant who made the enquiry.
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

    // Getters

    /**
     * Gets the main enquiry text.
     *
     * @return the main enquiry text.
     */
    public String getMainEnq() {
        return mainEnq;
    }

    /**
     * Gets the reply to the enquiry.
     *
     * @return the reply to the enquiry.
     */
    public String getReply() {
        return reply;
    }

    /**
     * Gets the status of the enquiry (whether it has been replied to).
     *
     * @return true if the enquiry has been replied to, false otherwise.
     */
    public Boolean getReplied() {
        return replied;
    }

    /**
     * Gets the project associated with the enquiry.
     *
     * @return the BTO project related to the enquiry.
     */
    public BTOProject getProject() {
        return project;
    }

    /**
     * Gets the applicant who made the enquiry.
     *
     * @return the applicant who made the enquiry.
     */
    public Applicant getApplicant() {
        return applicant;
    }

    // Setters

    /**
     * Sets the applicant who made the enquiry.
     *
     * @param applicant the applicant to set.
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    /**
     * Sets the main enquiry text.
     *
     * @param mainEnq the new enquiry text.
     */
    public void setMainEnq(String mainEnq) {
        this.mainEnq = mainEnq;
    }

    /**
     * Sets the project related to the enquiry.
     *
     * @param project the new project associated with the enquiry.
     */
    public void setProject(BTOProject project) {
        this.project = project;
    }

    /**
     * Sets the replied status of the enquiry.
     *
     * @param replied the new replied status.
     */
    public void setReplied(Boolean replied) {
        this.replied = replied;
    }

    /**
     * Sets the reply to the enquiry.
     *
     * @param reply the new reply to the enquiry.
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    // Methods for viewing, editing, changing, and deleting enquiries

    /**
     * Displays the enquiry details including the main enquiry, reply, and project.
     */
    public void viewEnq() {
        System.out.println("Enquiry: " + this.getMainEnq());
        System.out.println("Reply: " + this.getReply());
        System.out.println("Project: " + this.getProject().getProjectName());
    }

    /**
     * Edits the main enquiry text. The enquiry cannot be edited if it has already been replied to.
     *
     * @param e the new enquiry text.
     */
    public void editEnq(String e) {
        if (this.replied == true) {
            System.out.println("Unable to edit, enquiry has been replied");
        } else {
            this.mainEnq = e;
            System.out.println("Enquiry edited.");
        }
    }

    /**
     * Changes the project associated with the enquiry. The enquiry cannot be changed if it has been replied to.
     *
     * @param N the new BTO project to associate with the enquiry.
     */
    public void changeProject(BTOProject N) {
        if (this.replied == true) {
            System.out.println("Unable to change, enquiry has been replied");
        } else {
            this.project = N;
            System.out.println("Project changed.");
        }
    }

    /**
     * Deletes the enquiry. The enquiry cannot be deleted if it has already been replied to.
     *
     * @return true if the enquiry was successfully deleted, false if it could not be deleted.
     */
    public boolean deleteEnq() {
        if (this.replied == true) {
            System.out.println("Unable to delete, enquiry has been replied");
            return false;
        } else {
            this.mainEnq = null;
            this.reply = null;
            this.replied = null;
            this.project = null;
            System.out.println("Enquiry deleted successfully");
            return true;
        }
    }

    /**
     * Sets a reply to the enquiry and marks it as replied.
     *
     * @param r the reply to the enquiry.
     */
    public void replyToEnq(String r) {
        this.reply = r;
        this.replied = true;
        System.out.println("Enquiry replied.");
    }
}
