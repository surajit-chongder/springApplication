package merchandise;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supply_details")
public class SupplyDetails {
    @Id
    private int pro_id;
    private int sup_id;

    public SupplyDetails(int pro_id, int sup_id) {
        this.pro_id = pro_id;
        this.sup_id = sup_id;
    }
}
