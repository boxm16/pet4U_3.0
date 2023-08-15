/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuppliersAndStockController {

    @RequestMapping(value = "suppliersAndStockDashboard")
    public String inventoryDashboard() {
       
        return "sas/sasDashboard";
    }
}
