import java.util.Scanner;

public class PayPalPayment implements PaymentStrategy {
    private Scanner scanner = new Scanner(System.in);
    private String email;
    private String password;


    @Override
    public void makePayment() {
        // Implement logic to process PayPal payment
        System.out.println("[...PayPal payment processed successfully...]");
    }


    @Override
    public void requestPaymentDetails() {
        System.out.print("Enter PayPal email: ");
        email = scanner.nextLine();

        System.out.print("Enter PayPal password: ");
        password = scanner.nextLine();
    }
}