/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import Delivery.DeliveryDao;
import Delivery.DeliveryDao_V_3_1;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapCamelotDeliveryController {

    @RequestMapping(value = "camelotDeliveryDashboard")
    public String camelotItemsDashboard(ModelMap modelMap) {
        SapCamelotDeliveryDao sampSapCamelotDeliveryDao = new SapCamelotDeliveryDao();
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> duePurchaseOrders = sampSapCamelotDeliveryDao.getDuePurchaseOrders();

        modelMap.addAttribute("duePurchaseOrders", duePurchaseOrders);
        return "sap/camelot/delivery/sapCamelotDeliveryDashboard";
    }

    @RequestMapping(value = "/sapCamelotDeliveryInvoiceChecking.htm", method = RequestMethod.GET)
    public String sapCamelotDeliveryInvoiceChecking(@RequestParam("invoiceId") String purchaseOrderNumber, ModelMap modelMap) {
        SapCamelotDeliveryDao sampSapCamelotDeliveryDao = new SapCamelotDeliveryDao();
        DeliveryInvoice deliveryInvoice = sampSapCamelotDeliveryDao.getPurchaseOrderForDeliveryChecking(purchaseOrderNumber);
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveSapCamelotDeliveryCheckUp.htm')\"><H1>Save Delivery Checking</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "sap/camelot/delivery/sapCamelotDeliveryInvoiceChecking";

    }

    @RequestMapping(value = "saveSapCamelotDeliveryCheckUp", method = RequestMethod.POST)
    public String saveSapCamelotDeliveryCheckUp(@RequestParam(name = "sentItems") String sentItemsData,
            @RequestParam(name = "deliveredItems") String deliveredItemsData,
            @RequestParam(name = "invoiceNumber") String invoiceNumber,
            @RequestParam(name = "invoiceId") String invoiceId,
            @RequestParam(name = "supplier") String supplier) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        deliveryInvoice.setInvoiceId(invoiceId);
        deliveryInvoice.setSupplier(supplier);
        deliveryInvoice.setNumber(invoiceNumber);

        LinkedHashMap<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
        LinkedHashMap<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);

        ArrayList<DeliveryItem> deliveryItems = new ArrayList<>();
        for (Map.Entry<String, String> deliveredItemsEntry : deliveredItems.entrySet()) {
            DeliveryItem deliveryItem = new DeliveryItem();
            deliveryItem.setCode(deliveredItemsEntry.getKey());
            deliveryItem.setDeliveredQuantity(deliveredItemsEntry.getValue());
            deliveryItem.setSentQuantity(sentItems.get(deliveredItemsEntry.getKey()));
            deliveryItems.add(deliveryItem);
        }

        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();
        String result = dao.saveDeliveryChecking(invoiceId, supplier, invoiceNumber, deliveryItems);
        return "redirect:deliveryDashboard_X.htm";
    }

    private LinkedHashMap<String, String> decodeDeliveredItemsData(String data) {
        LinkedHashMap<String, String> decodedData = new LinkedHashMap<>();
        //trimming and cleaning input
        data = data.trim();
        if (data.length() == 0) {
            return decodedData;
        }
        if (data.substring(data.length() - 1, data.length()).equals(",")) {
            data = data.substring(0, data.length() - 1).trim();
        }
        String[] its = data.split(",");
        for (String it : its) {
            String[] item_code_and_quantity = it.split(":");
            String codePart = item_code_and_quantity[0];
            String quantity = item_code_and_quantity[1];
            String[] code_text = codePart.split("_");
            String code = code_text[0];
            decodedData.put(code, quantity);
        }

        return decodedData;
    }
}
