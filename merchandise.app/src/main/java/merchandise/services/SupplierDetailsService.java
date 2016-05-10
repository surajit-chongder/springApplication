package merchandise.services;

import merchandise.entities.Supplier;
import merchandise.entities.SupplierDetails;
import merchandise.repositories.SupplierDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierDetailsService {
    @Autowired
    SupplierDetailsRepository supplierDetailsRepository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;

    public List<SupplierDetails> getAllStockDetails() {
        return (List<SupplierDetails>) supplierDetailsRepository.findAll();
    }

    public List<Supplier> getSuppliersNameOfSpecificProduct(Integer id) {
        List<Integer> supplierIds = new ArrayList<>();
        List<Supplier> supplierList = new ArrayList<>();
        for (SupplierDetails eachStock : getAllStockDetails()) {
            if (eachStock.getProduct_id() == id)
                supplierIds.add(eachStock.getSupplier_id());
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

    public List<SupplierDetails> getProductsOfSpecificSupplier(Integer id) {
        List<SupplierDetails> stockDetailList = new ArrayList<>();
        for (SupplierDetails eachStock : getAllStockDetails()) {
            if (eachStock.getSupplier_id() == id) {
                stockDetailList.add(eachStock);
            }
        }
        return stockDetailList;
    }
    
    public void createAndSaveNewStockDetails(int productId, int supplier_id, int product_price) {
        SupplierDetails newSupplierDetails = new SupplierDetails();
        newSupplierDetails.setProduct_id(productId);
        newSupplierDetails.setSupplier_id(supplier_id);
        newSupplierDetails.setProduct_price(product_price);
        supplierDetailsRepository.save(newSupplierDetails);
    }

    public boolean hasSpecificProduct(int supplier_id, int productId) {
        for (SupplierDetails eachStock : getAllStockDetails()) {
            if (eachStock.getSupplier_id() == supplier_id && eachStock.getProduct_id() == productId)
                return true;
        }
        return false;
    }

    public void deleteSpecificProductStock(int id) {
        for (SupplierDetails supplierDetails : getAllStockDetails()) {
            if (supplierDetails.getProduct_id() == id){
                supplierDetailsRepository.delete(supplierDetails.getId());
            }
        }
    }

    public void deleteSpecificSupplierStock(int id) {
        for (SupplierDetails supplierDetails : getAllStockDetails()) {
            if (supplierDetails.getSupplier_id() == id){
                supplierDetailsRepository.delete(supplierDetails.getId());
            }
        }
    }
}
