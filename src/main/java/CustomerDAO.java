import java.util.List;

public interface CustomerDAO {

    List<Customer> getAllCustomer();
    void addCustomer(String name);
    void updateCarId(int carId, int customerId);
    List<Car> getRentedCar(int customerId);
    void returnCar(int customerId);
    List<Customer> checkCustomerCar(int customerId);


}
