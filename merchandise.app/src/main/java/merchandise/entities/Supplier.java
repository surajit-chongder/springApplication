package merchandise.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier3")
@EqualsAndHashCode
public class Supplier {

    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

}
