package SC2002_Assignment;

import java.util.List;

/**
 * Interface representing the ability to view BTO projects.
 * Any class implementing this interface should provide the functionality to view a list of available BTO projects.
 */
public interface ViewProjects {

    /**
     * Displays a list of BTO projects.
     * This method allows the user to view all the projects available in the given list.
     * The specific criteria for displaying projects (e.g., eligibility, visibility, etc.) may depend on the implementing class.
     *
     * @param allProjects The list of all available BTO projects.
     */
    public void viewProjects(List<BTOProject> allProjects);
}
