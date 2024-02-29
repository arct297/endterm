import java.time.LocalDate;
import java.util.Scanner;

public class AuthenticationService {
    public User loginUser(Scanner scanner, DatabaseManager dbManager) {
        System.out.println("[Authorizing]:");
        System.out.print("Enter login: ");
        String login = scanner.next();
        scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.next();

        int authResult = dbManager.authenticateUser(login, password);
        if (authResult != -1) {
            return dbManager.getUserData(authResult);
        } else {
            return null;
        }
    }

    public User registerUser(Scanner scanner, DatabaseManager dbManager) {
        // Registering new user account
        System.out.println("[Registration]:");
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        // Valid phone number getting block
        System.out.print("Enter phone number (format +70001234567): ");
        String phone = scanner.nextLine();
        while (!isValidPhoneNumber(phone)) {
            System.out.print("Invalid phone number format. Please enter a valid phone number: ");
            phone = scanner.nextLine();
        }

        System.out.print("Enter your address: ");
        String location = scanner.nextLine();

        // Default fields
        String registeringDate = LocalDate.now().toString();
        int purchasedAmount = 0;

        // Adding new user into database
        int userId = dbManager.addUser(login, password, username, phone, location, registeringDate, purchasedAmount);
        if (userId != -1) {
            // If successfully added -> return User exemplar
            return new User(userId, username, phone, location, registeringDate, purchasedAmount);
        } else {
            // If database error -> return null
            System.out.println("Error occurred while adding user to the database.");
            return null;
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        // Checking phone number format correctness. Format +77001234567
        return phone.matches("\\+?\\d{10,12}");
    }


}
