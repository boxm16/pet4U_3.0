/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeliveryController_V_3_1 {

    @RequestMapping(value = "deliveryDashboardTesting")
    public String deliveryDashboardTesting(ModelMap modelMap) {
        DeliveryDao_V_3_1 deliveryDao = new DeliveryDao_V_3_1();
        String date = "2024-06-13 00:00:00 000";
        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = deliveryDao.getDeliveryInvoices(date);
        modelMap.addAttribute("deliveryInvoices", deliveryInvoices);
        return "delivery/deliveryInvoices";
    }

}
