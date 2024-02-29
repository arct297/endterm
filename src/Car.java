public class Car extends Transport {
    private int numOfSeats;

    public Car(int id, String make, String model, int year, double price, int sellerId, int numOfSeats) {
        super(id, make, model, year, price, sellerId);
        this.numOfSeats = numOfSeats;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    @Override
    public String displayInfo() {
        return "-------------------[CAR]------------------| ID: " + getId() + "\n" +
        "Brand: " + getMake() + "\n" +
        "Model: " + getModel() + "\n" +
        "Year: " + getYear() + "\n" +
        "Price: " + getPrice() + "\n" +
        "Number of Seats: " + getNumOfSeats();
    }
}
