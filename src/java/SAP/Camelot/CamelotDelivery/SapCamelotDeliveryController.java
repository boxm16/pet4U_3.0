/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import Delivery.DeliveryDao;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import SAP.SapCamelotApiConnector;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SapCamelotDeliveryController {

    @RequestMapping(value = "camelotDeliveryDashboard")
    public String camelotDeliveryDashboard(ModelMap modelMap) {
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

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveSapCamelotDeliveryCheckUp.htm')\"><H1>Save Delivery Checking IN SAP</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "sap/camelot/delivery/sapCamelotDeliveryInvoiceChecking";

    }

    @RequestMapping(value = "saveSapCamelotDeliveryCheckUp", method = RequestMethod.POST)
    public String saveSapCamelotDeliveryCheckUp(
            @RequestParam(name = "sentItems") String sentItemsData,
            @RequestParam(name = "deliveredItems") String deliveredItemsData,
            @RequestParam(name = "invoiceNumber") String invoiceNumber,
            @RequestParam(name = "invoiceId") String invoiceId,
            @RequestParam(name = "supplier") String supplier,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Parse and validate input data
            LinkedHashMap<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
            LinkedHashMap<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);

            if (deliveredItems.isEmpty()) {
                System.out.println("❌ Error: No delivered items received");
                redirectAttributes.addFlashAttribute("message", "Error: No items were marked as delivered");
                return "redirect:camelotDeliveryDashboard.htm";
            }

            // 2. Prepare SAP API connection
            SapCamelotApiConnector apiConnector = new SapCamelotApiConnector();
            String endpoint = "/InventoryGenEntries"; // Goods receipt endpoint
            HttpURLConnection conn = apiConnector.createConnection(endpoint, "POST");

            // 3. Build payload following same pattern as item creation
            JSONObject payload = new JSONObject();
            payload.put("Comments", "Delivery for PO: " + invoiceNumber);
            payload.put("DocDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            JSONArray documentLines = new JSONArray();
            for (Map.Entry<String, String> entry : deliveredItems.entrySet()) {
                JSONObject line = new JSONObject();
                line.put("ItemCode", entry.getKey());
                line.put("Quantity", Double.parseDouble(entry.getValue()));
                line.put("WarehouseCode", "01"); // Default warehouse
                documentLines.put(line);
            }
            payload.put("DocumentLines", documentLines);

            // 4. Send request and handle response (same pattern as item creation)
            apiConnector.sendRequestBody(conn, payload.toString());

            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject jsonResponse = apiConnector.getJsonResponse(conn);
                System.out.println("✅ Delivery Saved Successfully!");
                System.out.println("SAP Document: " + jsonResponse.toString());
                redirectAttributes.addFlashAttribute("message",
                        "Delivery saved successfully. SAP Doc: " + jsonResponse.optString("DocNum"));
            } else {
                String errorResponse = apiConnector.getErrorResponse(conn);
                System.out.println("❌ Error Saving Delivery: " + errorResponse);
                redirectAttributes.addFlashAttribute("message",
                        "Error saving delivery: " + errorResponse);
                return "redirect:camelotDeliveryDashboard.htm";
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Number Format Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "Invalid quantity format: " + e.getMessage());
            return "redirect:camelotDeliveryDashboard.htm";
        } catch (IOException e) {
            System.out.println("❌ API Communication Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "Failed to connect to SAP: " + e.getMessage());
            return "redirect:camelotDeliveryDashboard.htm";
        } catch (Exception e) {
            System.out.println("❌ Unexpected Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "Unexpected error: " + e.getMessage());
            return "redirect:camelotDeliveryDashboard.htm";
        }

        return "redirect:camelotDeliveryDashboard.htm";
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
