package SC2002_Assignment;

/**
 * This interface defines the contract for replying to an enquiry.
 * Classes that implement this interface must provide the functionality to reply to a specific enquiry.
 */
public interface EnquiryReply {

    /**
     * Replies to an enquiry at the specified index with a response for the given BTO project.
     *
     * @param index The index of the enquiry to reply to.
     * @param P The BTO project related to the enquiry.
     */
    public void replyEnquiry(int index, BTOProject P);
}
