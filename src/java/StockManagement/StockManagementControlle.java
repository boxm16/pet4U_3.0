package StockManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StockManagementControlle {

    @RequestMapping(value = "suppliersAndStockManagementDashboard")
    public String suppliersAndStockManagementDashboard() {
        return "stockManagement/suppliersDashboard";
    }
}
