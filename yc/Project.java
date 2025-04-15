// Project.java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectName;
    private String neighborhood;
    private List<Flat> flats;
    private LocalDate applicationOpeningDate;
    private LocalDate applicationClosingDate;
    private String manager;
    private List<String> officers;
    
    public Project(String projectName, String neighborhood, 
                  List<Flat> housingUnits, 
                  String applicationOpeningDate, String applicationClosingDate,
                  String manager, List<String> officers) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.flats = housingUnits;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        this.applicationOpeningDate = LocalDate.parse(applicationOpeningDate, formatter);
        this.applicationClosingDate = LocalDate.parse(applicationClosingDate, formatter);
        
        this.manager = manager;
        this.officers = officers;
    }
    
    // Getters
    public String getProjectName() { return projectName; }
    public String getNeighborhood() { return neighborhood; }
    public List<Flat> getHousingUnits() { return flats; }
    public LocalDate getApplicationOpeningDate() { return applicationOpeningDate; }
    public LocalDate getApplicationClosingDate() { return applicationClosingDate; }
    public String getManager() { return manager; }
    public List<String> getOfficers() { return officers; }
    
    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", housingUnits=" + flats +
                ", applicationOpeningDate=" + applicationOpeningDate +
                ", applicationClosingDate=" + applicationClosingDate +
                ", manager='" + manager + '\'' +
                ", officers=" + officers +
                '}';
    }
}