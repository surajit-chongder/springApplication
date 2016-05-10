package merchandise.services;

import merchandise.entities.Product;
import merchandise.entities.StockDetails;
import merchandise.entities.Supplier;
import merchandise.repositories.StockDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StockDetailsService {
    @Autowired
    StockDetailsRepository stockDetailsRepository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;

    public List<StockDetails> getAllStockDetails() {
        return (List<StockDetails>) stockDetailsRepository.findAll();
    }

    public List<Supplier> getSuppliersNameOfSpecificProduct(Integer id) {
        List<Integer> supplierIds = new ArrayList<>();
        List<Supplier> supplierList = new ArrayList<>();
        for (StockDetails eachStock : getAllStockDetails()) {
            if (eachStock.getPro_id() == id)
                supplierIds.add(eachStock.getSup_id());
        }
        List<Supplier> allSupplierList = supplierService.getAllSupplierList();
        for (Supplier eachSupplier : allSupplierList) {
            for (Integer supplierId : supplierIds) {
                if (eachSupplier.getId() == supplierId)
                    supplierList.add(eachSupplier);
            }
        }
        return supplierList;
    }

    public HashMap<String, Integer> getProductsOfSpecificSupplier(Integer id) {
        List<Integer> productIds = new ArrayList<>();
        HashMap<String, Integer> productAndPriceMap = new HashMap<>();

        for (StockDetails eachStock : getAllStockDetails()) {
            if (eachStock.getSup_id() == id) {
                productIds.add(eachStock.getPro_id());
            }
        }
        List<Product> allProductList = productService.getAllProductList();
        for (Product eachProduct : allProductList) {
            for (Integer productId : productIds) {
                if (eachProduct.getId() == productId) {
                    productAndPriceMap.put(eachProduct.getName(), getPriceOfProduct(eachProduct.getId(), id));
                }
            }
        }
        return productAndPriceMap;
    }

    private int getPriceOfProduct(int productId, int supplierId) {
        for (StockDetails stockDetails : getAllStockDetails()) {
            if (stockDetails.getPro_id() == productId && stockDetails.getSup_id() == supplierId)
                return stockDetails.getPro_price();
        }
        return 0;
    }

    public void createAndSaveNewStockDetails(int productId, int supplier_id, int product_price) {
        StockDetails newStockDetails = new StockDetails();
        newStockDetails.setPro_id(productId);
        newStockDetails.setSup_id(supplier_id);
        newStockDetails.setPro_price(product_price);
        stockDetailsRepository.save(newStockDetails);
    }

    public boolean hasSpecificProduct(int supplier_id, int productId) {
        for (StockDetails eachStock : getAllStockDetails()) {
            if (eachStock.getSup_id() == supplier_id && eachStock.getPro_id() == productId)
                return true;
        }
        return false;
    }
}
