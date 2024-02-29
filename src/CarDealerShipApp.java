import java.util.*;

public class CarDealerShipApp {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationService authService = new AuthenticationService();
    private static DatabaseManager dbManager = new DatabaseManager();

    public static void main(String[] args) {
        // Initializing local user entity
        User currentUser = null;

        // Creating tables if not exist
        dbManager.createTables();

        // Data initializing
        ArrayList<Transport> transportsList = initializeTransports();
        ArrayList<Seller> sellersList = initializeSellers();

        // Menu panels creating
        ArrayList<String> authMenu = new ArrayList<>();
        authMenu.add("Login");
        authMenu.add("Create new account");
        authMenu.add("Exit");

        ArrayList<String> mainMenu = new ArrayList<>();
        mainMenu.add("My account");
        mainMenu.add("Available transports");
        mainMenu.add("Buy transport");
        mainMenu.add("Log out");
        mainMenu.add("Exit");

        // Greeting message sending
        System.out.println("Good day! Welcome to");


        // Main algorithm
        boolean authorization = true;
        boolean running = false;

        while (true) {

            // Authorization block
            while (authorization) {
                int actionNumber = requestAction(scanner, authMenu, "Login menu");
                switch (actionNumber) {
                    case 1:
                        currentUser = authService.loginUser(scanner, dbManager);
                        if (currentUser != null) {
                            System.out.println("You have successfully authorized!\n\nWelcome, " + currentUser.getUsername() + "!");
                            authorization = false;
                            running = true;
                        } else {
                            System.out.println("Wrong password or login!");
                        }
                        break;
                    case 2:
                        currentUser = authService.registerUser(scanner, dbManager);
                        if (currentUser != null) {
                            System.out.println("New account was successfully registered. You have successfully authorized!");
                            authorization = false;
                            running = true;
                        } else {
                            System.out.println("Creating account error.");
                        }
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        System.exit(-1);
                        break;
                    default:
                        System.out.println("Please, choose action from given list!");
                }
            }

            // Main block.
            while (running){
                int actionNumber = requestAction(scanner, mainMenu, "Main menu");
                switch (actionNumber) {
                    case 1:
                        System.out.println("Profile information:");
                        System.out.println(currentUser.displayInfo());
                        break;
                    case 2:
                        displayAvailableTransports(scanner, transportsList, sellersList);
                        break;
                    case 3:
                        System.out.println("Do you need to show transports list?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Choose action: ");
                        int isShowTList = scanner.nextInt();
                        if (isShowTList == 1) {
                            displayAvailableTransports(scanner, transportsList, sellersList);
                        } else {
                            System.out.println("Transports list will not be shown.");
                        }

                        System.out.print("Choose ID of transport you want to buy: ");
                        int selectedTransportId = scanner.nextInt();
                        Transport selectedTransport = getTransportByIdFromList(selectedTransportId, transportsList);
                        if (selectedTransport == null){
                            System.out.println("Transport with this ID was not found");
                        } else {
                            Purchase purchase = new Purchase(selectedTransport);

                            // Requesting payment way
                            PaymentStrategy paymentWay = requestPaymentWay(scanner);
                            paymentWay.requestPaymentDetails();
                            // Requesting shipping strategy
                            DeliveryStrategy shippingWay = requestShippingWay(scanner);
                            shippingWay.requestDeliveryDetails();

                            // Setting purchasing features
                            purchase.setPaymentStrategy(paymentWay);
                            purchase.setDeliveryStrategy(shippingWay);

                            // Implementing purchase
                            purchase.processPurchase();
                        }
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        running = false;
                        authorization = true;
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        System.exit(-1);
                        break;
                    default:
                        System.out.println("Please, choose action from given list!");
                }
            }
        }
    }

    // Method requests action from given list from user
    private static int requestAction(Scanner scanner, ArrayList<String> actionsList, String header) {
        System.out.println("[" + header + "]:");
        for (int i = 0; i < actionsList.size(); i++) {
            System.out.println((i + 1) + ". " + actionsList.get(i));
        }
        System.out.print("Input action number: ");
        int actionNumber = scanner.nextInt();
        scanner.nextLine();
        if (actionNumber > 0 && actionNumber <= actionsList.size()) {
            return actionNumber;
        }
        return 0;
    }

    private static ArrayList<Transport> initializeTransports() {
        ArrayList<Transport> transportDataList = dbManager.getAllTransports();
        return new ArrayList<>(transportDataList);
    }

    private static ArrayList<Seller> initializeSellers() {
        ArrayList<Seller> sellerDataList = dbManager.getAllSellers();
        return new ArrayList<>(sellerDataList);
    }
    
    private static void displayAvailableTransports(
            Scanner scanner,
            ArrayList<Transport> transportsList,
            ArrayList<Seller> sellersList
    ) {
        boolean isFilter = false;
        String transportFilterType = "any";
        int lowPrice = 0;
        int highPrice = 1000000000;

        System.out.println("Do you want to apply filters?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Choose point: ");
        int applyingFiltersChoice = scanner.nextInt();

        if (applyingFiltersChoice == 1){
            isFilter = true;
            System.out.println("Transport types:");
            System.out.println("1. Car");
            System.out.println("2. Bus");
            System.out.println("3. Motorcycle");
            System.out.println("4. Truck");
            System.out.println("5. All");
            System.out.print("Choose point: ");
            int transportTypeFilterChoise = scanner.nextInt();
            switch (transportTypeFilterChoise){
                case 1:
                    transportFilterType = "car";
                    break;
                case 2:
                    transportFilterType = "bus";
                    break;
                case 3:
                    transportFilterType = "motorcycle";
                    break;
                case 4:
                    transportFilterType = "truck";
                    break;
                default:
                    System.out.println("Setted transport type: ANY");
                    break;
            }

            System.out.print("Input lowest price: ");
            lowPrice = scanner.nextInt();
            System.out.print("\nInput highest price: ");
            highPrice = scanner.nextInt();
        } else {
            System.out.println("Filters will not be applied");
        }

        System.out.println("[Available Transports]:");
        List<Transport> filteredTransports = transportsList;
        if (isFilter){
            filteredTransports = filterTransports(transportsList, transportFilterType, lowPrice, highPrice);
        }

        for (Transport transport : filteredTransports) {
            System.out.println(transport.displayInfo());
            Seller seller = getSellerById(sellersList, transport.getSellerId());
            System.out.println("\n[Seller info]:");
            if (seller != null) {
                System.out.println("Name: " + seller.getName());
                System.out.println("Contact Phone: " + seller.getContactPhone());
                System.out.println("Address: " + seller.getAddress());
            } else {
                System.out.println("\nSeller information not available.");
            }
            System.out.println("-----------------------------------------------------\n");
        }

    }
    // Filter transports
    private static List<Transport> filterTransports(List<Transport> transportsList, String type, int minPrice, int maxPrice) {
        List<Transport> filteredTransports = new ArrayList<>();
        for (Transport transport : transportsList) {
            boolean condition1;
            if (Objects.equals(type, "any")){
                condition1 = true;
            } else {
                condition1 = transport.getClass().getSimpleName().equalsIgnoreCase(type);
            }
            if (condition1 && transport.getPrice() >= minPrice && transport.getPrice() <= maxPrice) {
                filteredTransports.add(transport);
            }
        }
        return filteredTransports;
    }

    // Method to get seller entity by its ID
    private static Seller getSellerById(ArrayList<Seller> sellersList, int sellerId) {
        for (Seller seller : sellersList) {
            if (seller.getId() == sellerId) {
                return seller;
            }
        }
        return null; // if this seller does not exist
    }

    public static Transport getTransportByIdFromList(int id, ArrayList<Transport> transportsList) {
        for (Transport transport : transportsList) {
            if (transport.getId() == id) {
                return transport;
            }
        }
        return null;
    }

    public static PaymentStrategy requestPaymentWay(Scanner scanner){
        System.out.println("Select payment method:");
        System.out.println("1. Card");
        System.out.println("2. PayPal");
        System.out.println("3. Cash");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 1 -> new CardPayment();
            case 2 -> new PayPalPayment();
            case 3 -> new CashPayment();
            default -> {
                System.out.println("Invalid choice. Defaulting to cash payment.");
                yield new CashPayment();
            }
        };
    }

    public static DeliveryStrategy requestShippingWay(Scanner scanner) {
        System.out.println("Select delivery method:");
        System.out.println("1. Pickup");
        System.out.println("2. Address delivery");

        int choice = scanner.nextInt();
        scanner.nextLine();

        return switch (choice) {
            case 1 -> new PickupDelivery();
            case 2 -> new AddressDelivery();
            default -> {
                System.out.println("Invalid choice. Defaulting to pickup delivery.");
                yield new PickupDelivery();
            }
        };
    }

}


