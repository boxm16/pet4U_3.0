/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeliveryController_V_3_1 {

    @RequestMapping(value = "deliveryDashboardTesting")
    public String deliveryDashboardTesting(ModelMap modelMap) {
        DeliveryDao_V_3_1 deliveryDao = new DeliveryDao_V_3_1();
        String date = "2024-06-12";
        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = deliveryDao.getDeliveryInvoices(date);
        modelMap.addAttribute("deliveryInvoices", deliveryInvoices);
        return "delivery/deliveryInvoices";
    }

    @RequestMapping(value = "openDeliveryInvoiceForChecking", method = RequestMethod.GET)
    public String openDeliveryInvoiceForChecking(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();

        DeliveryInvoice deliveryInvoice = dao.getDeliveryInvoice(id);

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);
        return "delivery/deliveryInvoiceChecking";
    }
}
