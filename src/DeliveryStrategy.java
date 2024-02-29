public interface DeliveryStrategy {
    void deliver(String address);

    String getAdress();

    void deliver();

    void requestDeliveryDetails();
}