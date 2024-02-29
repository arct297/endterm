public class Motorcycle extends Transport {
    private String motorcycleType;

    public Motorcycle(int id, String make, String model, int year, double price, int sellerId, String motorcycleType) {
        super(id, make, model, year, price, sellerId);
        this.motorcycleType = motorcycleType;
    }

    public String getMotorcycleType() {
        return motorcycleType;
    }

    @Override
    public String displayInfo() {
        return "-------------------[MOTORCYCLE]------------------| ID: " + getId() + "\n" +
                "Brand: " + getMake() + "\n" +
                "Model: " + getModel() + "\n" +
                "Year: " + getYear() + "\n" +
                "Price: " + getPrice() + "\n" +
                " Motorcycle type: " + motorcycleType;
    }
}