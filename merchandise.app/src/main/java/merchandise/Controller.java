package merchandise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierRepository supplierRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMessage() {
        return "Welcome to merchandise app";
    }

    @RequestMapping(value = "/product/{name}", method = RequestMethod.GET)
    public List<String> getSupplierName(@PathVariable String name) {
        String checkQuery = "select s.name from product2 p inner join supplier2 s on s.pro_id = p.id  where p.name = ?";
        return this.jdbcTemplate.query(checkQuery, new Object[]{name}, new NameMapper());
    }

    @RequestMapping(value = "/supplier/{name}", method = RequestMethod.GET)
    public List<String> findAllProductNameOfSpecificCompany(@PathVariable String name) {
        String checkQuery = "select p.name from product2 p inner join supplier2 s on s.pro_id = p.id  where s.name = ?";
        return this.jdbcTemplate.query(checkQuery, new Object[]{name}, new NameMapper());
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String addProduct(@RequestBody Product product) {
        List<Product> products = (List<Product>) productRepository.findAll();
        int totalSize = products.size();
        if (isProductExists(product.getName()))
            return "Product is already exists";
        else {
            Product newProduct = new Product();
            newProduct.setId(++totalSize);
            newProduct.setName(product.getName());
            productRepository.save(newProduct);
            return "product saved";
        }
    }

    private boolean isProductExists(String name) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            if (product.isSame(name))
                return true;
        }
        return false;
    }


    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public List<String> getAllProduct() {
        List<Product> products = (List<Product>) productRepository.findAll();
        ArrayList<String> productsName = new ArrayList<>();
        for (Product product : products) {
            productsName.add(product.getName());
        }
        return productsName;
    }

    @RequestMapping(value = "/supplier/all", method = RequestMethod.GET)
    public List<String> getAllSupplier() {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        ArrayList<String> suppliersName = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            suppliersName.add(supplier.getName());
        }
        return suppliersName;
    }

    private static final class NameMapper implements RowMapper<String> {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("name");
        }
    }
}
