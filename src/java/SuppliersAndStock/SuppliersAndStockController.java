/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuppliersAndStockController {

    @RequestMapping(value = "suppliersAndStockDashboard")
    public String suppliersAndStockDashboard() {

        return "suppliersAndStock/suppliersAndStockDashboard";
    }

    @RequestMapping(value = "goForAddingSupplier")
    public String goForAddingSupplier() {

        return "suppliersAndStock/addSupplier";
    }

    //----------------
    @RequestMapping(value = "goForAddingItemToSupplier")
    public String goForEditingCamelotItemOfInterest(@RequestParam(name = "supplierId") String supplierId, ModelMap model) {

        return "/camelot/editItem";
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
