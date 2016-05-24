package merchandise.services;

import merchandise.entities.Supplier;
import merchandise.entities.SupplierDetails;
import merchandise.repositories.SupplierDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierDetailsService {
    @Autowired
    SupplierDetailsRepository supplierDetailsRepository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;

    public List<SupplierDetails> getAllSupplierDetails() {
        return (List<SupplierDetails>) supplierDetailsRepository.findAll();
    }

    public List<Supplier> getSuppliersOfSpecificProduct(Integer id) {
        List<Integer> supplierIds = getAllSupplierIdsOfSpecificProductId(id);
        List<Supplier> supplierList = new ArrayList<>();
        List<Supplier> allSupplierList = supplierService.getAllSupplierList();
        for (Supplier eachSupplier : allSupplierList) {
            supplierList.addAll(supplierIds
                    .stream()
                    .filter(supplierId -> eachSupplier.getId() == supplierId)
                    .map(supplierId -> eachSupplier)
                    .collect(Collectors.toList()));
        }
        return supplierList;
    }
    private List<Integer> getAllSupplierIdsOfSpecificProductId(int id){
        return getAllSupplierDetails()
                .stream()
                .filter(eachStockDetails -> eachStockDetails.getProduct_id() == id)
                .map(SupplierDetails::getSupplier_id)
                .collect(Collectors.toList());
    }

    public List<SupplierDetails> getProductsOfSpecificSupplier(Integer id) {
        return getAllSupplierDetails()
                .stream()
                .filter(eachStockDetails -> eachStockDetails.getSupplier_id() == id)
                .collect(Collectors.toList());
    }
    
    public void createAndSaveNewStockDetails(int productId, int supplier_id, int product_price) {
        SupplierDetails newSupplierDetails = new SupplierDetails();
        newSupplierDetails.setProduct_id(productId);
        newSupplierDetails.setSupplier_id(supplier_id);
        newSupplierDetails.setProduct_price(product_price);
        supplierDetailsRepository.save(newSupplierDetails);
    }

    public boolean hasSpecificProduct(int supplier_id, int productId) {
        for (SupplierDetails eachStock : getAllSupplierDetails()) {
            if (eachStock.getSupplier_id() == supplier_id && eachStock.getProduct_id() == productId)
                return true;
        }
        return false;
    }

    public void deleteSpecificProductStock(int id) {
        getAllSupplierDetails()
                .stream()
                .filter(supplierDetails -> supplierDetails.getProduct_id() == id)
                .forEach(supplierDetails -> supplierDetailsRepository.delete(supplierDetails.getId()));
    }

    public void deleteSpecificSupplierStock(int id) {
        getAllSupplierDetails()
                .stream()
                .filter(supplierDetails -> supplierDetails.getSupplier_id() == id)
                .forEach(supplierDetails -> supplierDetailsRepository.delete(supplierDetails.getId()));
    }
}
