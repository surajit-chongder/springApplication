package merchandise.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier_details")
public class SupplierDetails {
    @Id
    private int id;
    private int product_id;
    private int supplier_id;
    private int product_price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }


    public int getProduct_price() {
        return product_price;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public int getProduct_id() {
        return product_id;
    }

}
