package merchandise.controllers;

import merchandise.entities.Product;
import merchandise.entities.SupplierDetails;
import merchandise.entities.Supplier;
import merchandise.services.ProductService;
import merchandise.services.SupplierDetailsService;
import merchandise.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SupplierDetailsController {
    @Autowired
    SupplierDetailsService supplierDetailsService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/supplier_details", method = RequestMethod.GET)
    public List<SupplierDetails> getAllSupplierDetails() {
        return supplierDetailsService.getAllSupplierDetails();
    }

    @RequestMapping(value = "/products/{id}/suppliers", method = RequestMethod.GET)
    public List<Supplier> getSuppliersOfSpecificProduct(@PathVariable int id) {
        return supplierDetailsService.getSuppliersOfSpecificProduct(id);
    }

    @RequestMapping(value = "/suppliers/{id}/list", method = RequestMethod.GET)
    public List<SupplierDetails> getProductsOfSpecificSupplier(@PathVariable Integer id) {
        return supplierDetailsService.getProductsOfSpecificSupplier(id);
    }

    @RequestMapping(value = "/products/{id}/delete", method = RequestMethod.GET)
    public List<Product> deleteSpecificProduct(@PathVariable int id) {
        productService.deleteSpecificProduct(id);
        supplierDetailsService.deleteSpecificProductStock(id);
        return productService.getAllProductList();
    }
    @RequestMapping(value = "/suppliers/{id}/delete", method = RequestMethod.GET)
    public List<Supplier> deleteSpecificSupplier(@PathVariable int id) {
        supplierService.deleteSpecificSupplier(id);
        supplierDetailsService.deleteSpecificSupplierStock(id);
        return supplierService.getAllSupplierList();
    }

    @RequestMapping(value = "/suppliers/{supplier_id}/products", method = RequestMethod.POST)
    public String saveSupplierAndProduct(@PathVariable Integer supplier_id, @RequestBody Map<String, Object> data) {
        int productPrice = (int) data.get("product_price");
        int productId = (int) data.get("product_id");
        if (supplierDetailsService.hasSpecificProduct(supplier_id, productId)) {
            return "supplier already have the product";
        }
        if (!supplierService.isValidSupplier(supplier_id)) {
            return "supplier does not exists";
        }
        if (!productService.isValidProduct(productId)) {
            return "product is not available";
        }
        supplierDetailsService.createAndSaveNewStockDetails(productId, supplier_id,productPrice);
        return "product added to supplier";
    }
}
