import java.util.List;

import java.util.List;

public class CarService implements CarDAO{

    JPACarRepository repository;

    public CarService(String dbName) {
        this.repository = JPACarRepository.getInstance(dbName);
    }

    @Override
    public List<Car> getAllCars(int id) {
        return repository.getAllCars(id);
    }

    @Override
    public void addCar(String name, int id) {
        repository.insertCar(name,id);
    }

    @Override
    public List<Car> getAllFreeCars(int companyId) {
        return repository.getAllFreeCar(companyId);
    }
}