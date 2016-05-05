package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public List<String> getAllProduct() {
        List<Product> products = (List<Product>) productRepository.findAll();
        ArrayList<String> productsName = new ArrayList<>();
        for (Product product : products) {
            productsName.add(product.getName());
        }
        return productsName;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMessage() {
        return "Welcome to merchandise application";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String getSpecificId(@PathVariable Integer id) {
        return productRepository.findOne(id).getName();
    }

    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public String addProduct(@RequestBody String name) {
        List<String> products = getAllProduct();
        int totalSize = products.size();
        Product newProduct = new Product();
        newProduct.setId(++totalSize);
        newProduct.setName(name);
        productRepository.save(newProduct);
        return newProduct.getName();
    }
}