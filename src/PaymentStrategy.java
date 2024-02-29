public interface PaymentStrategy {
    void makePayment();

    void requestPaymentDetails();
}