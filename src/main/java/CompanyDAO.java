import java.util.List;

public interface CompanyDAO {

    List<Company> getAllCompany();
    void addCompany(String name);


}
