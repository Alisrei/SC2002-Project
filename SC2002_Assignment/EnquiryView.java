package SC2002_Assignment;

/**
 * This interface defines the contract for viewing enquiries.
 * Classes that implement this interface must provide the functionality to view a list of enquiries.
 */
public interface EnquiryView {

    /**
     * Displays the list of enquiries for the user.
     * The method will print all the enquiries associated with the user.
     * If no enquiries are available, a message indicating the absence of enquiries will be displayed.
     */
    public void viewEnquiries();
}
