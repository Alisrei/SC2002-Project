package SC2002_Assignment;

import java.util.List;

public interface EnquiryCreateEditDelete {
    public void submitEnquiry(List<BTOProject> AP);
    public void editEnquiry(int index, String newText);
    public void deleteEnquiry(int index);
}
