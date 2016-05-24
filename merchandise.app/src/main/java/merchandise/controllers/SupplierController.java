package merchandise.controllers;

import merchandise.entities.Supplier;
import merchandise.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/suppliers", method = RequestMethod.GET)
    public List<Supplier> getSupplierList() {
        return supplierService.getAllSupplierList();
    }

    @RequestMapping(value = "/suppliers", method = RequestMethod.POST)
    public boolean addSupplier(@RequestBody Supplier supplier) {
        if (supplierService.isExistingSupplier(supplier.getName().toUpperCase()))
            return false;
        else {
            supplierService.createAndSaveNewSupplier(supplier.getName().toUpperCase());
            return true;
        }
    }
}