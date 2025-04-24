package SC2002_Assignment;

import java.util.List;

/**
 * Interface for managing the lifecycle of an enquiry, including creating, editing, and deleting enquiries.
 * Any class implementing this interface should provide the functionality to submit a new enquiry, 
 * edit an existing enquiry, and delete an enquiry.
 */
public interface EnquiryCreateEditDelete {

    /**
     * Submits a new enquiry for a list of Build-to-Order (BTO) projects.
     * This method allows an applicant to create a new enquiry and associate it with a project.
     *
     * @param AP A list of available BTO projects that the enquiry can be submitted to.
     */
    public void submitEnquiry(List<BTOProject> AP);

    /**
     * Edits an existing enquiry by updating its content at a specified index.
     * This method allows the modification of an enquiry's text, such as its message.
     *
     * @param index The index of the enquiry to edit in a list of enquiries.
     * @param newText The new text to replace the original enquiry content.
     */
    public void editEnquiry(int index, String newText);

    /**
     * Deletes an existing enquiry at the specified index.
     * This method removes the enquiry from the list of enquiries.
     *
     * @param index The index of the enquiry to delete from a list of enquiries.
     */
    public void deleteEnquiry(int index);
}
