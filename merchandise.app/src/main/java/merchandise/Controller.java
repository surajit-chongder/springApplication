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

    @RequestMapping(value = "/suppliers/{id}/products", method = RequestMethod.GET)
    public List<String> getProductsNameOfSpecificSupplier(@PathVariable Integer id) {
        String checkQuery = "select name from product3 where id in(select pro_id from supply_details where sup_id=?)";
        return this.jdbcTemplate.query(checkQuery, new Object[]{id}, new nameMapper());
    }

    @RequestMapping(value = "/products/{id}/suppliers", method = RequestMethod.GET)
    public List<String> getSuppliersName(@PathVariable Integer id) {
        String checkQuery = "select name from supplier3 where id in(select sup_id from supply_details where pro_id=?)";
        return this.jdbcTemplate.query(checkQuery, new Object[]{id}, new nameMapper());
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

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<String> getAllProductList() {
        String checkQuery = "select name from product3";
        return this.jdbcTemplate.query(checkQuery, new nameMapper());
    }

    @RequestMapping(value = "/suppliers", method = RequestMethod.GET)
    public List<String> getAllSupplierList() {
        String checkQuery = "select name from supplier3";
        return this.jdbcTemplate.query(checkQuery, new nameMapper());
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product) {
        if (isProductExists(product.getName().toUpperCase()))
            return false;
        else {
            createAndSaveNewProduct(product.getName().toUpperCase());
            return true;
        }
    }

    @RequestMapping(value = "/suppliers", method = RequestMethod.POST)
    public boolean addSupplier(@RequestBody Supplier supplier) {
        if (isExistingSupplier(supplier.getName().toUpperCase()))
            return false;
        else {
            createAndSaveNewSupplier(supplier.getName().toUpperCase());
            return true;
        }
    }

    @RequestMapping(value = "/suppliers/{supplier_id}/products", method = RequestMethod.POST)
    public String saveSupplierAndProduct(@PathVariable Integer supplier_id, @RequestBody Map<String, Object> data) {
        int productId = (Integer) data.get("product_id");
        if (hasSpecificProduct(supplier_id, productId)) {
            return "supplier already have the product";
        }
        if (!isValidSupplier(supplier_id)) {
            return "supplier does not exists";
        }
        if (!isValidProduct(productId)) {
            return "product is not available";
        }
        String updateQuery = "insert into supply_details values(?, ?)";
        this.jdbcTemplate.update(updateQuery, productId, supplier_id);
        return "product added to supplier";
    }

    private boolean isValidProduct(int productId) {
        String sqlQuery = "select exists(select 1 from product3 where id=?)";
        return jdbcTemplate.queryForObject(sqlQuery, Boolean.class, productId);
    }

    private boolean isValidSupplier(int supplierId) {
        String sqlQuery = "select exists(select 1 from supplier3 where id=?)";
        return jdbcTemplate.queryForObject(sqlQuery, Boolean.class, supplierId);
    }

    private boolean hasSpecificProduct(int supplierId, int productId) {
        String sqlQuery = "select exists(select 1 from supply_details where pro_id=? and sup_id=?)";
        return jdbcTemplate.queryForObject(sqlQuery, Boolean.class, productId, supplierId);
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