public class Purchase {
    private Transport transport;
    private DeliveryStrategy deliveryStrategy;
    private PaymentStrategy paymentStrategy;

    public Purchase(Transport transport) {
        this.transport = transport;
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPurchase() {
        deliveryStrategy.deliver(deliveryStrategy.getAdress());
        paymentStrategy.makePayment();
        System.out.println("Purchase complete!");
    }
}