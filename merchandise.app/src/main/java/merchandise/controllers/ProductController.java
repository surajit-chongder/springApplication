package merchandise.controllers;

import merchandise.entities.Product;
import merchandise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> collectProductList() {
        return productService.getAllProductList();
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public boolean addProduct(@RequestBody Product product) {
        if (productService.isProductExists(product.getName().toUpperCase()))
            return false;
        else {
            productService.createAndSaveNewProduct(product.getName().toUpperCase());
            return true;
        }
    }
}
