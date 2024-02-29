public abstract class Transport {
    private int id;
    private String make;
    private String model;
    private int year;
    private double price;
    private int sellerId;

    public Transport(int id, String make, String model, int year, double price, int sellerId) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public abstract String displayInfo();
}