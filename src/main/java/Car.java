import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "CAR")
public class Car implements Serializable {

    @Id
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name ="company_id")
    private int company_id;

    public Car(int id, String name, int company_id) {
        this.id = id;
        this.name = name;
        this.company_id = company_id;
    }

    public Car() {
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}