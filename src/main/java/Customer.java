import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "CUSTOMER")
public class Customer implements Serializable {

    @Id
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name ="car_id")
    private int rented_car_id;

    public Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(int rented_car_id) {
        this.rented_car_id = rented_car_id;
    }
}