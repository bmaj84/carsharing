import java.util.List;

public class CompanyService implements CompanyDAO{

    private final JPACompanyRepository repository;

    public CompanyService(String dbName) {
        this.repository = JPACompanyRepository.getInstance(dbName);
    }

    @Override
    public List<Company> getAllCompany() {
        return repository.getAllCompanies();
    }

    @Override
    public void addCompany(String name) {
        repository.insertCompany(name);
    }


}