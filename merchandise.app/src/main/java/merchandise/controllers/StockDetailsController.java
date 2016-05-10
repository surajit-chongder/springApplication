package merchandise.controllers;

import merchandise.entities.StockDetails;
import merchandise.entities.Supplier;
import merchandise.services.ProductService;
import merchandise.services.StockDetailsService;
import merchandise.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StockDetailsController {
    @Autowired
    StockDetailsService stockDetailsService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/stock_details", method = RequestMethod.GET)
    public List<StockDetails> collectSuppliers() {
        return stockDetailsService.getAllStockDetails();
    }

    @RequestMapping(value = "/products/{id}/suppliers", method = RequestMethod.GET)
    public List<Supplier> collectSuppliers(@PathVariable int id) {
        return stockDetailsService.getSuppliersNameOfSpecificProduct(id);
    }

    @RequestMapping(value = "/suppliers/{id}/products", method = RequestMethod.GET)
    public HashMap<String, Integer> collectProductsOfSpecificSupplier(@PathVariable Integer id) {
        return stockDetailsService.getProductsOfSpecificSupplier(id);
    }

    @RequestMapping(value = "/suppliers/{supplier_id}/products", method = RequestMethod.POST)
    public String saveSupplierAndProduct(@PathVariable Integer supplier_id, @RequestBody Map<String, Object> data) {
        int productPrice = (int) data.get("product_price");
        int productId = (int) data.get("product_id");
        if (stockDetailsService.hasSpecificProduct(supplier_id, productId)) {
            return "supplier already have the product";
        }
        if (!supplierService.isValidSupplier(supplier_id)) {
            return "supplier does not exists";
        }
        if (!productService.isValidProduct(productId)) {
            return "product is not available";
        }
        stockDetailsService.createAndSaveNewStockDetails(productId, supplier_id,productPrice);
        return "product added to supplier";
    }
}
