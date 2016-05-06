package merchandise;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier2")
public class Supplier {

    @Id
    private int id;
    private String name;
    private int pro_id;

    public int getId() {
        return id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPro_id(int id) {
        this.pro_id = id;
    }

}
