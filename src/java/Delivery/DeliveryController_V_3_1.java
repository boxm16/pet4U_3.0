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
    public String deliveryDashboardTesting(@RequestParam(name = "date") String date, ModelMap modelMap) {

        DeliveryDao_V_3_1 deliveryDao = new DeliveryDao_V_3_1();

        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = deliveryDao.getDeliveryInvoices(date);
        modelMap.addAttribute("deliveryInvoices", deliveryInvoices);
        return "delivery/deliveryInvoices";
    }

    @RequestMapping(value = "openDeliveryInvoiceForChecking", method = RequestMethod.GET)
    public String openDeliveryInvoiceForChecking(@RequestParam(name = "id") String invoiceId, ModelMap modelMap) {

        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();

        DeliveryInvoice deliveryInvoice = dao.getDeliveryInvoice(invoiceId);

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        ArrayList<DeliveryInvoice> allCheckedDeliveryInvoices = dao.getAllCheckedDeliveryInvoices();
        for (DeliveryInvoice checkedDeliveryInvoice : allCheckedDeliveryInvoices) {

            if (deliveryInvoice.getInvoiceId().equals(checkedDeliveryInvoice.getInvoiceId())) {
                modelMap.addAttribute("checkedInvoiceId", invoiceId);
                return "delivery/deliveryPreloadChecking_V_3_1";
            }
        }

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveCheckUp.htm')\">Save Delivery Checking</button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceChecking";
    }
}
