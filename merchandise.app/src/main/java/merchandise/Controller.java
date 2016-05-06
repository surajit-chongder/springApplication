package merchandise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<String> getProductsNameOfSpecificSupplier(@PathVariable String name) {
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
    @RequestMapping(value = "/supplier/save",method = RequestMethod.POST)
    public String saveSupplierAndProduct(@RequestBody Map<String,Object> data){
        String item = (String) data.get("item");
        String supplierName = (String) data.get("name");
        if(hasProduct(supplierName,item)){
            return "supplier already have the product";
        }
        if(!isExistingSupplier(supplierName)){
            Supplier newSupplier = new Supplier();
            newSupplier.setName(supplierName);
            newSupplier.setId(createNewSupplierId());

            supplierRepository.save(newSupplier);
        }
        if(!isProductExists(item)){
            Product newProduct = new Product();
            newProduct.setId(createNewProductId());
            newProduct.setName(item);
            productRepository.save(newProduct);
        }
        SupplyDetails newSupplyDetails = new SupplyDetails();
        newSupplyDetails.setPro_id(getSpecificProductId(item));
        newSupplyDetails.setSup_id(getSpecificSupplierId(supplierName));
        supplyDetailsRepository.save(newSupplyDetails);
        return "product added to supplier";
    }

    private int getSpecificSupplierId(String supplierName) {
        List<Supplier> supplierList = (List<Supplier>) supplierRepository.findAll();
        for (Supplier supplier : supplierList) {
            if (supplier.getName().equals(supplierName))
                return supplier.getId();
        }
        return 0;
    }

    private int getSpecificProductId(String item) {
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : products) {
            if (product.getName().equals(item))
                return product.getId();
        }
        return 0;
    }

    private boolean isExistingSupplier(String supplierName) {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(supplierName))
                return true;
        }
        return false;
    }

    private int createNewSupplierId() {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        int totalSupplier = suppliers.size();
        return ++totalSupplier;
    }
    private int createNewProductId() {
        List<Product> products = (List<Product>) productRepository.findAll();
        int totalProduct = products.size();
        return ++totalProduct;
    }

    private boolean hasProduct(String supplierName, String item) {
        List<Supplier> suppliers = (List<Supplier>) supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            if(supplier.getName().equals(supplierName)) {
                List<String> allProduct = getProductsNameOfSpecificSupplier(supplierName);
                for (String eachProduct : allProduct) {
                    if(eachProduct.equals(item))
                        return true;
                }
            }
        }
        return false;
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