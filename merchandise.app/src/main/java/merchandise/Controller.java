package merchandise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    SupplyDetailsRepository supplyDetailsRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMessage() {
        return "Welcome to merchandise app";
    }

    @RequestMapping(value = "/product/{name}", method = RequestMethod.GET)
    public List<String> getSuppliersName(@PathVariable String name) {
        String checkQuery = "select name from supplier3 where id in(select sup_id from supply_details where pro_id in(select id from product3 where name=?))";
        return this.jdbcTemplate.query(checkQuery, new Object[]{name}, new nameMapper());
    }

    @RequestMapping(value = "/supplier/{name}", method = RequestMethod.GET)
    public List<String> getProductsNameOfSpecificSupplier(@PathVariable String name) {
        String checkQuery = "select name from product3 where id in(select pro_id from supply_details where sup_id in(select id from supplier3 where name=?))";
        return this.jdbcTemplate.query(checkQuery, new Object[]{name}, new nameMapper());
    }

    private int getSpecificSupplierId(String supplierName) {
        String checkQuery = "SELECT id from supplier3 where name=?";
        return this.jdbcTemplate.queryForObject(checkQuery, new Object[]{supplierName}, Integer.class);
    }

    private int getSpecificProductId(String item) {
        String checkQuery = "select id from product3 where name=?";
        return this.jdbcTemplate.queryForObject(checkQuery, new Object[]{item}, Integer.class);
    }

    private int createNewSupplierId() {
        String checkQuery = "SELECT max(id) from supplier3";
        Integer lastSupplierId = this.jdbcTemplate.queryForObject(checkQuery, Integer.class);
        return ++lastSupplierId;
    }

    private int createNewProductId() {
        String checkQuery = "select max(id) from product3";
        Integer lastProductId = this.jdbcTemplate.queryForObject(checkQuery, Integer.class);
        return ++lastProductId;
    }

    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public List<String> getAllProductList() {
        String checkQuery = "select name from product3";
        return this.jdbcTemplate.query(checkQuery, new nameMapper());
    }

    @RequestMapping(value = "/supplier/all", method = RequestMethod.GET)
    public List<String> getAllSupplierList() {
        String checkQuery = "select name from supplier3";
        return this.jdbcTemplate.query(checkQuery, new nameMapper());
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product) {
        if (isProductExists(product.getName()))
            return false;
        else {
            createAndSaveNewProduct(product.getName());
            return true;
        }
    }

    @RequestMapping(value = "/supplier/save", method = RequestMethod.POST)
    public boolean addSupplier(@RequestBody Supplier supplier) {
        if (isExistingSupplier(supplier.getName()))
            return false;
        else {
            createAndSaveNewSupplier(supplier.getName());
            return true;
        }
    }

    @RequestMapping(value = "/cart/save", method = RequestMethod.POST)
    public String saveSupplierAndProduct(@RequestBody Map<String, Object> data) {
        String item = (String) data.get("item");
        String supplierName = (String) data.get("name");
        if (hasProduct(supplierName, item)) {
            return "supplier already have the product";
        }
        if (!isExistingSupplier(supplierName)) {
            createAndSaveNewSupplier(supplierName);
        }
        if (!isProductExists(item)) {
            createAndSaveNewProduct(item);
        }
        String updateQuery = "insert into supply_details values(?, ?)";
        this.jdbcTemplate.update(updateQuery,getSpecificProductId(item), getSpecificSupplierId(supplierName));
        return "product added to supplier";
    }

    private void createAndSaveNewProduct(String productName) {
        Product newProduct = new Product();
        newProduct.setId(createNewProductId());
        newProduct.setName(productName);
        productRepository.save(newProduct);
    }

    private void createAndSaveNewSupplier(String supplierName) {
        Supplier newSupplier = new Supplier();
        newSupplier.setName(supplierName);
        newSupplier.setId(createNewSupplierId());
        supplierRepository.save(newSupplier);
    }

    private boolean isExistingSupplier(String supplierName) {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(supplierName))
                return true;
        }
        return false;
    }

    private boolean hasProduct(String supplierName, String item) {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(supplierName)) {
                List<String> allProduct = getProductsNameOfSpecificSupplier(supplierName);
                for (String eachProduct : allProduct) {
                    if (eachProduct.equals(item))
                        return true;
                }
            }
        }
        return false;
    }

    private boolean isProductExists(String productName) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            if (product.isSame(productName))
                return true;
        }
        return false;
    }

    private static final class nameMapper implements RowMapper<String> {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("name");
        }
    }
}