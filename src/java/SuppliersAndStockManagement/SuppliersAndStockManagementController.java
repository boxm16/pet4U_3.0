package SuppliersAndStockManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuppliersAndStockManagementController {
  @RequestMapping(value = "iva")
    public String inventoryDashboard() {

        return "inventory/inventoryDashboard";
    }
}
