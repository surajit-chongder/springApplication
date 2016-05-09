package merchandise;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerTest {
    private Product product1 = new Product();
    private Product product2 = new Product();
    private Supplier supplier1 = new Supplier();
    private Supplier supplier2 = new Supplier();
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Before
    public void setUp() throws Exception {
        product1.setName("tea");
        product1.setId(1);
        product2.setName("coffee");
        product2.setId(2);
        supplier1.setId(1001);
        supplier2.setId(1002);
        supplier1.setName("sony");
        supplier2.setName("micromax");
    }

    @Test
    public void name() throws Exception {
        Controller controller = new Controller();
    }
}
