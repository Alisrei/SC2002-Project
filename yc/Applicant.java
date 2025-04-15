public class Applicant {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;
    
    // Constructor
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public String getNric() { return nric; }
    public int getAge() { return age; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getPassword() { return password; }
    
    @Override
    public String toString() {
        return "Applicant{" +
                "name='" + name + '\'' +
                ", nric='" + nric + '\'' +
                ", age=" + age +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}