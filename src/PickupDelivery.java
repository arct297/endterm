public class PickupDelivery implements DeliveryStrategy {
    @Override
    public void deliver(String address) {
        System.out.println("Ready for pickup at the dealership");
    }

    @Override
    public String getAdress() {
        return null;
    }

    @Override
    public void deliver() {
        System.out.println("[...Ready for pickup at the dealership...]");
    }

    @Override
    public void requestDeliveryDetails() {

    }
}
