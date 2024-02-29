public class Truck extends Transport {
    private double loadCapacity;

    public Truck(int id, String make, String model, int year, double price, int sellerId, double loadCapacity) {
        super(id, make, model, year, price, sellerId);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public String displayInfo() {
        return "-------------------[TRUCK]------------------| ID: " + getId() + "\n" +
                "Brand: " + getMake() + "\n" +
                "Model: " + getModel() + "\n" +
                "Year: " + getYear() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Load Capacity: " + loadCapacity;
    }
}