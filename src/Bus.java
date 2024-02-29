public class Bus extends Transport {
    private int seatingCapacity;

    public Bus(int id, String make, String model, int year, double price, int sellerId, int seatingCapacity) {
        super(id, make, model, year, price, sellerId);
        this.seatingCapacity = seatingCapacity;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    @Override
    public String displayInfo() {
        return "-------------------[BUS]------------------| ID: " + getId() + "\n" +
                "Brand: " + getMake() + "\n" +
                "Model: " + getModel() + "\n" +
                "Year: " + getYear() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Seating Capacity: " + seatingCapacity;
    }
}