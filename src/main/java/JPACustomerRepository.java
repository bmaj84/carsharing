import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JPACustomerRepository {

    private final String databaseFilename;
    private static JPACustomerRepository INSTANCE = null;

    public JPACustomerRepository(String databaseFilename) {
        this.databaseFilename = databaseFilename;
        createDatabase();
    }

    private void createDatabase() {
        String query1 = "CREATE TABLE IF NOT EXISTS customer ( ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(20) NOT NULL UNIQUE, RENTED_CAR_ID INT DEFAULT NULL, FOREIGN KEY (rented_car_id) REFERENCES car (id))";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() {
        final String DB_URL = "jdbc:h2:./src/carsharing/db/";
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_URL + databaseFilename);
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static JPACustomerRepository getInstance(String databaseFilename) {
        if (INSTANCE == null) {
            INSTANCE = new JPACustomerRepository(databaseFilename);
            return INSTANCE;
        }
        return INSTANCE;
    }

    public List<Customer> getAllCustomers() {
        String query = " SELECT * FROM CUSTOMER;";
        List<Customer> customers = new ArrayList<>();
        Connection conn = connect();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("ID"));
                customer.setName(resultSet.getString("NAME"));
                customer.setRented_car_id(resultSet.getInt("RENTED_CAR_ID"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public void insertCustomer(String name) {
        String query = "INSERT INTO CUSTOMER (name) VALUES (?);";
        Connection conn = connect();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,name);
            statement.executeUpdate();
            System.out.println("The customer was added!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCarId(int carId, int customerId) {
        String query = "UPDATE CUSTOMER SET RENTED_CAR_ID =? WHERE id = ?";
        Connection conn = connect();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,carId);
            statement.setInt(2,customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getRentedCar(int customerId) {
        String query = "SELECT CAR.id AS id, CAR.name AS name, CAR.company_id AS company_id FROM CAR LEFT JOIN CUSTOMER ON CAR.id = CUSTOMER.rented_car_id WHERE CUSTOMER.id = ? ;";
        Connection conn = connect();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setName(resultSet.getString("NAME"));
                car.setCompany_id(resultSet.getInt("COMPANY_ID"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void returnCar(int customerId) {
        Customer customer = new Customer();
        String query = "SELECT * FROM CUSTOMER WHERE ID = ? AND RENTED_CAR_ID IS NOT NULL";
        Connection conn = connect();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String query2 = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE id = ?";
                PreparedStatement statement2 = conn.prepareStatement(query2);
                statement2.setInt(1,customerId);
                statement2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> checkCustomerCar(int customerId) {
        String query = "SELECT * FROM CUSTOMER WHERE ID = ? AND RENTED_CAR_ID IS NOT NULL";
        Connection conn = connect();
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer.setRented_car_id(resultSet.getInt("RENTED_CAR_ID"));
                customer.setName(resultSet.getString("NAME"));
                customer.setId(resultSet.getInt("ID"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
