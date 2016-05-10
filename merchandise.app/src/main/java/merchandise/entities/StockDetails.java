package merchandise.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stock_details")
public class StockDetails {
    @Id
    private int id;
    private int pro_id;
    private int sup_id;
    private int pro_price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public void setSup_id(int sup_id) {
        this.sup_id = sup_id;
    }

    public void setPro_price(int pro_price) {
        this.pro_price = pro_price;
    }


    public int getPro_price() {
        return pro_price;
    }

    public int getSup_id() {
        return sup_id;
    }

    public int getPro_id() {
        return pro_id;
    }

}
