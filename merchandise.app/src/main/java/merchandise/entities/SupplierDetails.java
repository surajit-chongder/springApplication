package merchandise.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier_details")
public class SupplierDetails {
    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int product_id;

    @Getter
    @Setter
    private int supplier_id;

    @Getter
    @Setter
    private int product_price;

}
