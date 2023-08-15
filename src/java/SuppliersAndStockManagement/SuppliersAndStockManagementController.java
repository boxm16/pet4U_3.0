package SuppliersAndStockManagement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuppliersAndStockManagementController {

    @RequestMapping(value = "suppliersAndStockManagementDashboard")
    public String suppliersAndStockManagementDashboard() {
        return "suppliersAndStockManagement/suppliersAndStockManagementDashboard";
    }

    @RequestMapping(value = "royalStockManagement")
    public String royalStockManagement(ModelMap modelMap) {
        modelMap.addAttribute("supplier", "Royal");
        return "suppliersAndStockManagement/stockManagement";
    }
}
