/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuppliersAndStockController {
    
    @Autowired
    private SupplierDao supplierDao;
    
    @RequestMapping(value = "suppliersAndStockDashboard")
    public String suppliersAndStockDashboard(ModelMap modelMap) {
        ArrayList<Supplier> suppliers = supplierDao.getAllSuppliers();
        modelMap.addAttribute("suppliers", suppliers);
        return "suppliersAndStock/suppliersAndStockDashboard";
    }
    
    @RequestMapping(value = "goForAddingSupplier")
    public String goForAddingSupplier() {
        
        return "suppliersAndStock/addSupplier";
    }
    
    @RequestMapping(value = "addSupplier")
    public String addSupplier(@RequestParam(name = "name") String name,
            @RequestParam(name = "afm") String afm,
            ModelMap modelMap) {
        
        Supplier supplier = new Supplier();
        supplier.setId(0);
        supplier.setName(name);
        supplier.setAfm(afm);
        if (name.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");
            modelMap.addAttribute("supplier", supplier);
            return "suppliersAndStock/addSupplier";
            
        }
        
        String result = supplierDao.addSupplier(supplier);
        
        modelMap.addAttribute("resultColor", "green");
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("supplier", supplier);
        
        return "suppliersAndStock/addSupplier";
    }

    //----------------
    @RequestMapping(value = "stockManagement")
    public String stockManagement(@RequestParam(name = "supplierId") String supplierId, ModelMap modelMap) {
        
        Supplier supplier = new Supplier();
        supplier.setId(Integer.parseInt(supplierId));
        supplier.setName("VAKAKARA");
        modelMap.addAttribute("supplier", supplier);
        return "suppliersAndStock/stockManagement";
    }
    
    @RequestMapping(value = "royalStockManagement")
    public String royalStockManagement(ModelMap modelMap) {
        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("Royal");
        
        modelMap.addAttribute("supplier", supplier);
        return "suppliersAndStock/stockManagement";
    }
}
