package merchandise;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
    private Product product;
    @Before
    public void setUp() throws Exception {
        product = new Product();
        product.setId(1);
        product.setName("coffee");
    }
    @Test
    public void isSameGivesTrueWhenTheGivenNameIsSame() throws Exception {
        Assert.assertEquals(true,product.isSame("coffee"));
    }
    @Test
    public void isSameGivesFalseWhenTheGivenNameIsSame() throws Exception {
        Assert.assertEquals(false,product.isSame("tea"));
    }
}
