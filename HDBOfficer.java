public class HDBOfficer {
    private String name;
    private String officerId; // Unique ID for identification

    public HDBOfficer(String name, String officerId) {
        this.name = name;
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public String getOfficerId() {
        return officerId;
    }

    @Override
    public String toString() {
        return name + " (ID: " + officerId + ")";
    }
}
