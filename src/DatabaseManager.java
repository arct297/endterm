import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        createUsersTable();
        createTransportsTable();
        createSellersTable();
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "login VARCHAR(20) UNIQUE NOT NULL," +
                "password VARCHAR(20) NOT NULL," +
                "username VARCHAR(20) NOT NULL," +
                "phone VARCHAR(20) NOT NULL," +
                "location VARCHAR(100) NOT NULL," +
                "registering_date VARCHAR(10) NOT NULL," +
                "purchased_amount INTEGER NOT NULL" +
                ")";
        executeUpdate(sql);
    }

    private void createTransportsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transports (" +
                "id SERIAL PRIMARY KEY," +
                "make VARCHAR(50) NOT NULL," +
                "model VARCHAR(50) NOT NULL," +
                "year INT NOT NULL," +
                "price DOUBLE PRECISION NOT NULL," +
                "type VARCHAR(50) NOT NULL," +
                "num_of_seats INT," + // unique for Car
                "seating_capacity INT," + // unique for Bus
                "fuel_type VARCHAR(20)," + // unique for Bus
                "motorcycle_type VARCHAR(20)," + // unique for Motorcycle
                "load_capacity DOUBLE PRECISION," + // unique for Truck
                "seller_id INT NOT NULL" +
                ")";
        executeUpdate(sql);
    }

    private void createSellersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sellers (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "contact_phone VARCHAR(20) NOT NULL," +
                "address VARCHAR(100) NOT NULL" +
                ")";
        executeUpdate(sql);
    }

    private void executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int authenticateUser(String login, String password) {
        try {
            String query = "SELECT id FROM users WHERE login=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1; // User was not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Query error
        }
    }

    public User getUserData(int user_id) {
        String query = "SELECT id, username, phone, location, registering_date, purchased_amount FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user_id); // Используем setInt для установки ID пользователя
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    if (id == user_id) { // Проверяем, совпадает ли фактический ID с запрошенным
                        String uname = resultSet.getString("username");
                        String phone = resultSet.getString("phone");
                        String location = resultSet.getString("location");
                        String registeringDate = resultSet.getString("registering_date");
                        int purchasedAmount = resultSet.getInt("purchased_amount");
                        return new User(id, uname, phone, location, registeringDate, purchasedAmount);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int addUser(String login, String password, String username, String phone, String location, String registeringDate, int purchasedAmount) {
        try {
            // Inserting new user data
            String insertQuery = "INSERT INTO users (login, password, username, phone, location, registering_date, purchased_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, location);
            preparedStatement.setString(6, registeringDate);
            preparedStatement.setInt(7, purchasedAmount);
            preparedStatement.executeUpdate();

            // Getting generated user id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                System.out.println("Failed to get user ID.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Error adding user to the database.");
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Transport> getAllTransports() {
        ArrayList<Transport> transportsList = new ArrayList<>();

        try {
            String query = "SELECT * FROM Transports";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transport transport = createTransportFromResultSet(resultSet);
                transportsList.add(transport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transportsList;
    }

    // Метод для получения всех продавцов из базы данных
    public ArrayList<Seller> getAllSellers() {
        ArrayList<Seller> sellersList = new ArrayList<>();

        try {
            String query = "SELECT * FROM Sellers";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Seller seller = createSellerFromResultSet(resultSet);
                sellersList.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellersList;
    }

    // Вспомогательный метод для создания объекта Transport на основе ResultSet
    private Transport createTransportFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        int year = resultSet.getInt("year");
        double price = resultSet.getDouble("price");
        String type = resultSet.getString("type");
        int sellerId = resultSet.getInt("seller_id");

        Transport transport;

        switch (type) {
            case "Car":
                int numOfSeats = resultSet.getInt("num_of_seats");
                transport = new Car(id, make, model, year, price, sellerId, numOfSeats);
                break;
            case "Bus":
                int seatingCapacity = resultSet.getInt("seating_capacity");
                transport = new Bus(id, make, model, year, price, sellerId, seatingCapacity);
                break;
            case "Motorcycle":
                String motorcycleType = resultSet.getString("motorcycle_type");
                transport = new Motorcycle(id, make, model, year, price, sellerId, motorcycleType);
                break;
            case "Truck":
                double loadCapacity = resultSet.getDouble("load_capacity");
                transport = new Truck(id, make, model, year, price, sellerId, loadCapacity);
                break;
            default:
                throw new IllegalArgumentException("Unknown transport type: " + type);
        }

        return transport;
    }



    // Вспомогательный метод для создания объекта Seller на основе ResultSet
    private Seller createSellerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String contactPhone = resultSet.getString("contact_phone");
        String address = resultSet.getString("address");

        return new Seller(id, name, contactPhone, address);
    }

}
