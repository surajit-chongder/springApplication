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
    @Autowired
    SupplyDetailsRepository supplyDetailsRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMessage() {
        return "Welcome to merchandise app";
    }

    @RequestMapping(value = "/product/{name}", method = RequestMethod.GET)
    public List<String> getSuppliersName(@PathVariable String name) {
        String checkQuery = "select sup_id from supply_details where pro_id in(select id from product3 where name=?)";
        List<Integer> supplierIds = this.jdbcTemplate.query(checkQuery, new Object[]{name}, new supIdsMapper());
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        List<String> supplierList = new ArrayList<>();
        for (Integer supplierId : supplierIds) {
            for (Supplier eachSupplier : suppliers) {
                if (eachSupplier.getId() == supplierId)
                    supplierList.add(eachSupplier.getName());
            }
        }
        return supplierList;
    }

    @RequestMapping(value = "/supplier/{name}", method = RequestMethod.GET)
    public List<String> getProductsName(@PathVariable String name) {
        String checkQuery = "select pro_id from supply_details where sup_id in(select id from supplier3 where name=?)";
        List<Integer> productIds = this.jdbcTemplate.query(checkQuery, new Object[]{name}, new productIdsMapper());
        List<Product> products = (List<Product>) productRepository.findAll();
        List<String> productList = new ArrayList<>();
        for (Integer productId : productIds) {
            for (Product eachProduct : products) {
                if (eachProduct.getId() == productId)
                    productList.add(eachProduct.getName());
            }
        }
        return productList;
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product) {
        List<Product> products = (List<Product>) productRepository.findAll();
        int totalSize = products.size();
        if (isProductExists(product.getName()))
            return false;
        else {
            Product newProduct = new Product();
            newProduct.setId(++totalSize);
            newProduct.setName(product.getName());
            productRepository.save(newProduct);
            return true;
        }
    }
    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public List<String> getAllProductList(){
        List<Product> products = (List<Product>) productRepository.findAll();
        List<String> productList = new ArrayList<>();
        for (Product product : products) {
            productList.add(product.getName());
        }
        return productList;
    }
    @RequestMapping(value = "/supplier/all", method = RequestMethod.GET)
    public List<String> getAllSupplierList(){
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        List<String> supplierList = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierList.add(supplier.getName());
        }
        return supplierList;
    }

    private boolean isProductExists(String productName) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            if (product.isSame(productName))
                return true;
        }
        return false;
    }

    private static final class supIdsMapper implements RowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("sup_id");
        }
    }

    private static final class productIdsMapper implements RowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("pro_id");
        }
    }
}