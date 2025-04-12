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
import org.json.JSONException;
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

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveSapGoodsReceipt.htm')\"><H1>Save Delivery Checking IN SAP</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "sap/camelot/delivery/sapCamelotDeliveryInvoiceChecking";

    }

    @RequestMapping(value = "saveSapGoodsReceipt", method = RequestMethod.POST)
    public String saveSapGoodsReceipt(
            @RequestParam(name = "sentItems") String sentItemsData,
            @RequestParam(name = "deliveredItems") String deliveredItemsData,
            @RequestParam(name = "invoiceNumber") String invoiceNumber,
            @RequestParam(name = "invoiceId") String invoiceId,
            @RequestParam(name = "supplier") String supplierCode, // SAP Vendor ID (CardCode)
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Parse and validate input data
            Map<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
            Map<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);

            if (deliveredItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Error: No items marked as delivered");
                return "redirect:camelotDeliveryDashboard.htm";
            }

            // 2. Prepare SAP B1 API connection
            SapCamelotApiConnector apiConnector = new SapCamelotApiConnector(); // Your SAP B1 API helper
            String endpoint = "/PurchaseDeliveryNotes"; // SAP B1 GRPO endpoint
            HttpURLConnection conn = apiConnector.createConnection(endpoint, "POST");

            // 3. Build payload for SAP B1 Goods Receipt PO (GRPO)
            JSONObject payload = new JSONObject();
            payload.put("CardCode", supplierCode); // Mandatory: Vendor ID
            payload.put("DocDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Mandatory
            payload.put("TaxDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Mandatory
            payload.put("DocDueDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Mandatory
            payload.put("Comments", "Goods Receipt for PO: " + invoiceNumber);

            // 4. Add document lines (items received)
            JSONArray documentLines = new JSONArray();
            for (Map.Entry<String, String> entry : deliveredItems.entrySet()) {
                JSONObject line = new JSONObject();
                line.put("ItemCode", entry.getKey()); // Mandatory: SAP Item Code
                line.put("Quantity", Double.parseDouble(entry.getValue())); // Mandatory
                line.put("WarehouseCode", "01"); // Mandatory: Default warehouse
                line.put("BaseEntry", invoiceId); // Optional: Link to original PO (if known)
                line.put("BaseType", "22"); // 22 = Purchase Order (for PO linking)
                documentLines.put(line);
            }
            payload.put("DocumentLines", documentLines);

            // 5. Send request to SAP B1
            apiConnector.sendRequestBody(conn, payload.toString());

            // 6. Handle response
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) { // 201 = Created
                JSONObject response = apiConnector.getJsonResponse(conn);
                String docNum = response.getString("DocNum"); // SAP GRPO document number
                redirectAttributes.addFlashAttribute("message",
                        "Goods Receipt posted successfully! SAP GRPO #: " + docNum);
            } else {
                String error = apiConnector.getErrorResponse(conn);
                redirectAttributes.addFlashAttribute("message",
                        "SAP Error: " + error);
                return "redirect:camelotDeliveryDashboard.htm";
            }

        } catch (JSONException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Invalid JSON payload: " + e.getMessage());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    "SAP API connection failed: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Unexpected error: " + e.getMessage());
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
