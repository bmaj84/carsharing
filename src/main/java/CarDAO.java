import java.util.List;

public interface CarDAO {

    List<Car> getAllCars(int id);
    void addCar(String name, int id);
    List<Car> getAllFreeCars(int companyId);
}
