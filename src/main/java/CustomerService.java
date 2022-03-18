import java.util.List;

public class CustomerService implements CustomerDAO{

    private final JPACustomerRepository repository;

    public CustomerService(String dbName) {
        this.repository = JPACustomerRepository.getInstance(dbName);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return repository.getAllCustomers();
    }

    @Override
    public void addCustomer(String name) {
        repository.insertCustomer(name);
    }

    @Override
    public void updateCarId(int carId, int customerId) {
        repository.updateCarId(carId,customerId);
    }

    @Override
    public List<Car> getRentedCar(int customerId) {
        return repository.getRentedCar(customerId);
    }

    @Override
    public void returnCar(int customerId) {
        repository.returnCar(customerId);
    }

    @Override
    public List<Customer> checkCustomerCar(int customerId) {
        return repository.checkCustomerCar(customerId);
    }
}
