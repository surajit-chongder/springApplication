package merchandise.services;

import merchandise.entities.Product;
import merchandise.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProductList() {
        return (List<Product>) productRepository.findAll();
    }

    public boolean isProductExists(String productName) {
        for (Product product : getAllProductList()) {
            if (Objects.equals(product.getName(), productName))
                return true;
        }
        return false;
    }

    public void createAndSaveNewProduct(String productName) {
        Product newProduct = new Product();
        newProduct.setId(createNewProductId());
        newProduct.setName(productName);
        productRepository.save(newProduct);
    }

    private int createNewProductId() {
        Product lastIncludedProduct = getAllProductList().get(getAllProductList().size() - 1);
        int lastIncludedProductId = lastIncludedProduct.getId();
        return ++lastIncludedProductId;
    }

    public boolean isValidProduct(int productId) {
        for (Product product : getAllProductList()) {
            if (product.getId() == productId)
                return true;
        }
        return false;
    }
}
