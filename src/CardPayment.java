import java.util.Scanner;

public class CardPayment implements PaymentStrategy {
    private Scanner scanner = new Scanner(System.in);
    private String cardNumber;
    private String cvv;
    private String expiryDate;


    @Override
    public void makePayment() {
        // Implement logic to process card payment
        System.out.println("[...Card payment processed successfully...]");
    }

    @Override
    public void requestPaymentDetails() {
        System.out.print("Enter card number: ");
        cardNumber = scanner.nextLine();

        System.out.print("Enter CVV: ");
        cvv = scanner.nextLine();

        System.out.print("Enter expiry date (MM/YY): ");
        expiryDate = scanner.nextLine();
    }
}