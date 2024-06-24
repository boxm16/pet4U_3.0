/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeliveryController_V_3_1 {

    @RequestMapping(value = "deliveryDashboard_X")
    public String deliveryDashboard(ModelMap modelMap) {
        LocalDate date = LocalDate.now();
        modelMap.addAttribute("date", date);

        DeliveryDao_V_3_1 deliveryDao = new DeliveryDao_V_3_1();
        ArrayList<DeliveryInvoice> allCheckedDeliveryInvoices = deliveryDao.getAllCheckedDeliveryInvoices();
        modelMap.addAttribute("allCheckedDeliveryInvoices", allCheckedDeliveryInvoices);
        return "delivery/deliveryDashboard";
    }

    @RequestMapping(value = "deliveryInvoicesForDate")
    public String deliveryInvoicesForDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        DeliveryDao_V_3_1 deliveryDao = new DeliveryDao_V_3_1();

        LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = deliveryDao.getDeliveryInvoices(date);
        modelMap.addAttribute("deliveryInvoices", deliveryInvoices);
        return "delivery/deliveryInvoices";
    }

    @RequestMapping(value = "openDeliveryInvoiceForChecking", method = RequestMethod.GET)
    public String openDeliveryInvoiceForChecking(@RequestParam(name = "id") String invoiceId, ModelMap modelMap) {

        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();
        boolean isChecked = dao.deliveryInvocieIsChecked(invoiceId);

        if (isChecked) {
            modelMap.addAttribute("checkedInvoiceId", invoiceId);
            return "delivery/deliveryPreloadChecking_V_3_1";
        }

        DeliveryInvoice deliveryInvoice = dao.getDeliveryInvoice(invoiceId);

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveCheckUp.htm')\"><H1>Save Delivery Checking</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceChecking";
    }

    @RequestMapping(value = "saveCheckUp", method = RequestMethod.POST)
    public String saveCheckUp(@RequestParam(name = "sentItems") String sentItemsData,
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

    @RequestMapping(value = "loadCheckedRoyalDataFromDatabaseByInvoiceId")
    public String loadCheckedRoyalDataFromDatabaseByInvoiceId(@RequestParam(name = "invoiceId") String invoiceId,
            ModelMap modelMap) {
        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();
        DeliveryInvoice deliveryInvoice = dao.getDeliveryInvoiceByInvoiceId(invoiceId);
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        LinkedHashMap<String, DeliveryItem> deliveryItems = deliveryInvoice.getItems();
        for (DeliveryItem deliveryItem : pet4UItemsRowByRow) {
            String code = deliveryItem.getCode();
            if (deliveryItems.containsKey(code)) {
                DeliveryItem di = deliveryItems.get(code);
                di.setDescription(deliveryItem.getDescription());
                deliveryItems.put(code, di);
            }
        }
        deliveryInvoice.setItems(deliveryItems);

        String saveButton = "<button class=\"btn-danger\" onclick=\"requestRouter('rewriteDeliveryChecking.htm')\">Rewrite Checked Invoice</button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceReChecking";
    }

    @RequestMapping(value = "rewriteDeliveryChecking", method = RequestMethod.POST)
    public String rewriteDeliveryChecking(@RequestParam(name = "sentItems") String sentItemsData,
            @RequestParam(name = "deliveredItems") String deliveredItemsData,
            @RequestParam(name = "invoiceNumber") String invoiceNumber,
            @RequestParam(name = "invoiceId") String invoiceId,
            @RequestParam(name = "supplier") String supplier) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();

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
        String deleteResult = dao.deleteDeliveryChecking(invoiceId);
        String result = dao.saveDeliveryChecking(invoiceId, supplier, invoiceNumber, deliveryItems);
        return "redirect:deliveryDashboard_X.htm";
    }

    @RequestMapping(value = "deliveryInvoiceJointLoad")
    public String deliveryInvoiceJointLoad(@RequestParam(name = "invoiceId") String invoiceId,
            ModelMap modelMap) {

        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        DeliveryDao_V_3_1 dao = new DeliveryDao_V_3_1();
        DeliveryInvoice deliveryInvoiceFromView = dao.getDeliveryInvoice(invoiceId);
        DeliveryInvoice checkedDeliveryInvoice = dao.getDeliveryInvoiceByInvoiceId(invoiceId);
        LinkedHashMap<String, DeliveryItem> deliveryItemsFromExcelFile = deliveryInvoiceFromView.getItems();
        LinkedHashMap<String, DeliveryItem> deliveryItemsFromDatabase = checkedDeliveryInvoice.getItems();

        for (DeliveryItem deliveryItem : pet4UItemsRowByRow) {
            String code = deliveryItem.getCode();
            if (deliveryItemsFromDatabase.containsKey(code)) {
                DeliveryItem di = deliveryItemsFromDatabase.get(code);
                di.setDescription(deliveryItem.getDescription());
                deliveryItemsFromDatabase.put(code, di);
            }
        }

        for (Map.Entry<String, DeliveryItem> deliveryItemsEntry : deliveryItemsFromDatabase.entrySet()) {
            //   System.out.println("DELIVERY ITEM CODE:"+deliveryItemsEntry.getValue().getCode()+ "   DELIVERED QUANTITY:"+deliveryItemsEntry.getValue().getDeliveredQuantity());
            DeliveryItem deliveryItemFromExcelFile = deliveryItemsFromExcelFile.remove(deliveryItemsEntry.getKey());
            if (deliveryItemFromExcelFile == null) {
                deliveryItemsEntry.getValue().setQuantity("0");
            } else {
                deliveryItemsEntry.getValue().setQuantity(deliveryItemFromExcelFile.getQuantity());
            }
        }
        if (deliveryItemsFromExcelFile.size() > 0) {
            for (Map.Entry<String, DeliveryItem> deliveryItemFromExcelFileEntry : deliveryItemsFromExcelFile.entrySet()) {
                checkedDeliveryInvoice.getItems().put(deliveryItemFromExcelFileEntry.getKey(), deliveryItemFromExcelFileEntry.getValue());
            }
        }

        modelMap.addAttribute("deliveryInvoice", checkedDeliveryInvoice);

        String saveButton = "<button class=\"btn-danger\" onclick=\"requestRouter('rewriteDeliveryChecking.htm')\">Rewrite Checked Invoice</button>";
        modelMap.addAttribute("saveButton", saveButton);

        return "delivery/deliveryInvoiceReChecking";
    }

}
