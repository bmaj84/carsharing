import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JPACarRepository {

    private final String databaseFilename;
    private static JPACarRepository INSTANCE = null;

    public JPACarRepository(String databaseFilename) {
        this.databaseFilename = databaseFilename;
        createDatabase();
    }

    public static JPACarRepository getInstance(String databaseFilename) {
        if (INSTANCE == null) {
            INSTANCE = new JPACarRepository(databaseFilename);
            return INSTANCE;
        }
        return INSTANCE;
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

    private void createDatabase() {
        String query2 = "CREATE TABLE IF NOT EXISTS car (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(60) NOT NULL UNIQUE, company_id INT NOT NULL, FOREIGN KEY (company_id) REFERENCES company(id));";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllCars(int id) {

        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM CAR WHERE company_id = ?";
        Connection conn = connect();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("ID"));
                car.setName(resultSet.getString("NAME"));
                car.setCompany_id(resultSet.getInt("COMPANY_ID"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }


    public void insertCar(String name, int id) {
        String query = "INSERT INTO CAR (name, company_id) VALUES (?,?)";
        Connection conn = connect();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1,name);
            statement.setInt(2,id);
            statement.executeUpdate();
            System.out.println("The car was added!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllFreeCar(int companyId) {
        String query = "SELECT * FROM CAR LEFT JOIN CUSTOMER ON CAR.id = CUSTOMER.rented_car_id WHERE company_id = ? AND CUSTOMER.rented_car_id is null;";
        Connection conn = connect();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,companyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("ID"));
                car.setName(resultSet.getString("NAME"));
                car.setCompany_id(resultSet.getInt("RENTED_CAR_ID"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}