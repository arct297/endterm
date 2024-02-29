import java.util.Scanner;
public class AddressDelivery implements DeliveryStrategy {
    private String address;
    private Scanner scanner = new Scanner(System.in);


    @Override
    public void deliver(String address) {

    }

    @Override
    public String getAdress() {
        return this.getAdress();
    }

    @Override
    public void deliver() {
        // Implement logic to deliver to address
        System.out.println("[...Delivering to address: " + address + "...]");
    }

    @Override
    public void requestDeliveryDetails() {
        System.out.print("Enter delivery address: ");
        address = scanner.nextLine();
    }
}