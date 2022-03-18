import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JPACompanyRepository {

    private final String databaseFilename;
    private static JPACompanyRepository INSTANCE = null;

    public JPACompanyRepository(String databaseFilename) {
        this.databaseFilename = databaseFilename;
        createDatabase();
    }

    private void createDatabase() {


        String query1 = "CREATE TABLE IF NOT EXISTS company ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL UNIQUE);";
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

    public static JPACompanyRepository getInstance(String databaseFilename) {
        if (INSTANCE == null) {
            INSTANCE = new JPACompanyRepository(databaseFilename);
            return INSTANCE;
        }
        return INSTANCE;
    }

    public void insertCompany(String name) {
        String query = "INSERT INTO COMPANY (NAME) VALUES (?)";
        Connection con = connect();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,name);
            statement.executeUpdate();
            System.out.println("The company was created!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        String query = "SELECT * FROM COMPANY;";
        Connection conn = connect();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

}
