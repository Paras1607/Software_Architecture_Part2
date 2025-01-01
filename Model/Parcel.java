package Model;

public class Parcel {
    private String parcelID;
    private double length, width, height, daysInDepot;
    private double weight;
    private boolean isCollected;

    public Parcel(String parcelID, double length, double width, double height, int daysInDepot, double weight) {
        this.parcelID = parcelID;
        this.length = length;
        this.width = width;
        this.height = height;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.isCollected = false;
    }

    public String getParcelID() { return parcelID; }
    public double getDaysInDepot() { return daysInDepot; }
    public double getWeight() { return weight; }
    public double getLength() { return length; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getVolume() { return length * width * height; }
    public boolean isCollected() { return isCollected; }

    public void setCollected(boolean collected) {
        this.isCollected = collected;
    }
    public void collect() { this.isCollected = true; }

    @Override
    public String toString() {
        return "Parcel ID: " + parcelID +
                "\nDimensions: " + length + " x " + width + " x " + height +
                "\nWeight: " + weight + " kg" +
                "\nDays in Depot: " + daysInDepot;
    }
}
