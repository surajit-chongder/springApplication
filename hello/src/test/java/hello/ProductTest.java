package hello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
    Product product = new Product();

    @Before
    public void setUp() throws Exception {
        product.setId(1);
        product.setName("Coffee");
    }

    @Test
    public void getIdGivesTheIdOfAProduct() throws Exception {
        Assert.assertEquals(1, product.getId());
    }

    @Test
    public void getNameGivesTheNameOfAProduct() throws Exception {
        Assert.assertEquals("Coffee", product.getName());
    }
}