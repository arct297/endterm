public class CashPayment implements PaymentStrategy {

    @Override
    public void makePayment() {
        System.out.println("[...Waiting cash payment...]");
    }

    @Override
    public void requestPaymentDetails() {

    }
}