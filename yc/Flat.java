public class Flat {
    private String type;
    private int numberOfUnits;
    private double sellingPrice;
    
    public Flat(String type, int numberOfUnits, double sellingPrice) {
        this.type = type;
        this.numberOfUnits = numberOfUnits;
        this.sellingPrice = sellingPrice;
    }
    
    // Getters and toString()
    public String getType() { return type; }
    public int getNumberOfUnits() { return numberOfUnits; }
    public double getSellingPrice() { return sellingPrice; }
    
    @Override
    public String toString() {
        return "Flat{" +
                "type='" + type + '\'' +
                ", numberOfUnits=" + numberOfUnits +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}