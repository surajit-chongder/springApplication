package merchandise.services;

import merchandise.entities.Supplier;
import merchandise.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAllSupplierList() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    public boolean isExistingSupplier(String supplierName) {
        for (Supplier supplier : getAllSupplierList()) {
            if (Objects.equals(supplier.getName(), supplierName))
                return true;
        }
        return false;
    }

    public void createAndSaveNewSupplier(String supplierName) {
        Supplier newSupplier = new Supplier();
        newSupplier.setId(createNewProductId());
        newSupplier.setName(supplierName);
        supplierRepository.save(newSupplier);
    }

    private int createNewProductId() {
        Supplier lastIncludedSupplier = getAllSupplierList().get(getAllSupplierList().size() - 1);
        int lastIncludedSupplierId = lastIncludedSupplier.getId();
        return ++lastIncludedSupplierId;
    }

    public boolean isValidSupplier(int supplier_id) {
        for (Supplier supplier : getAllSupplierList()) {
            if (supplier.getId() == supplier_id)
                return true;
        }
        return false;
    }
}
