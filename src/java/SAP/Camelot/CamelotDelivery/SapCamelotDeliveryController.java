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
import java.util.Arrays;
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
        LinkedHashMap<String, ArrayList<DeliveryInvoice>> todaysGoodsReceipts = sampSapCamelotDeliveryDao.getGoodsReceipts();

        modelMap.addAttribute("duePurchaseOrders", duePurchaseOrders);
        modelMap.addAttribute("todaysGoodsReceipts", todaysGoodsReceipts);
        return "sap/camelot/delivery/sapCamelotDeliveryDashboard";
    }

    @RequestMapping(value = "/sapCamelotDeliveryInvoiceChecking.htm", method = RequestMethod.GET)
    public String sapCamelotDeliveryInvoiceChecking(@RequestParam("invoiceId") String invoiceId, ModelMap modelMap) {
        SapCamelotDeliveryDao sampSapCamelotDeliveryDao = new SapCamelotDeliveryDao();
        DeliveryInvoice deliveryInvoice = sampSapCamelotDeliveryDao.getPurchaseOrderForDeliveryChecking(invoiceId);
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
            @RequestParam(name = "baseLines") String baseLinesData, // Added BaseLines parameter
            @RequestParam(name = "invoiceNumber") String invoiceNumber,
            @RequestParam(name = "invoiceId") String invoiceId,
            @RequestParam(name = "supplier") String supplierCode,
            RedirectAttributes redirectAttributes) {

        System.out.println("🔥 saveSapGoodsReceipt: Method started");
        System.out.println("📦 Inputs - Invoice: " + invoiceNumber + ", Supplier: " + supplierCode);

        try {
            // 1. Parse and validate input data
            System.out.println("🔍 Parsing delivered items...");
            Map<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
            Map<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);
            Map<String, String> baseLines = decodeDeliveredItemsData(baseLinesData); // Parse BaseLines

            if (deliveredItems.isEmpty()) {
                System.out.println("❌ Error: No delivered items found!");
                redirectAttributes.addFlashAttribute("message", "Error: No items marked as delivered");
                return "redirect:camelotDeliveryDashboard.htm";
            }
            System.out.println("✅ Delivered Items: " + deliveredItems);

            // 2. Prepare SAP B1 API connection
            System.out.println("🔌 Connecting to SAP B1 API...");
            SapCamelotApiConnector apiConnector = new SapCamelotApiConnector();
            String endpoint = "/PurchaseDeliveryNotes";
            HttpURLConnection conn = apiConnector.createConnection(endpoint, "POST");

            // 3. Build payload with all required fields
            System.out.println("📝 Building payload...");
            JSONObject payload = new JSONObject();
            payload.put("CardCode", supplierCode);
            payload.put("DocDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            payload.put("TaxDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            payload.put("DocDueDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            payload.put("Comments", "Goods Receipt for PO: " + invoiceNumber);
            payload.put("DocObjectCode", "oPurchaseDeliveryNotes"); // Required field

            // Add currency information to prevent exchange rate errors
            payload.put("DocCurrency", "EUR"); // Set your local currency
            payload.put("DocRate", 1.0); // Default rate for local currency

            JSONArray documentLines = new JSONArray();
            for (Map.Entry<String, String> entry : deliveredItems.entrySet()) {
                String itemCode = entry.getKey();
                JSONObject line = new JSONObject();
                line.put("ItemCode", itemCode);
                line.put("Quantity", Double.parseDouble(entry.getValue()));
                line.put("WarehouseCode", "AX-BAR");

                // Critical PO linking information
                line.put("BaseEntry", invoiceId); // PO DocEntry
                line.put("BaseType", "22"); // 22 = Purchase Order
                line.put("BaseLine", baseLines.get(itemCode)); // PO Line Number

                // Additional recommended fields
                //  line.put("AccountCode", "_SYS00000000001"); // Default inventory account
                // line.put("CostingCode", "PROJ001"); // If using projects
                documentLines.put(line);
            }
            payload.put("DocumentLines", documentLines);

            System.out.println("📦 Payload: " + payload.toString(2)); // Pretty-print JSON

            // 4. Send request with enhanced error handling
            System.out.println("🚀 Sending request to SAP B1...");
            apiConnector.sendRequestBody(conn, payload.toString());

            // 5. Handle response with detailed logging
            int responseCode = conn.getResponseCode();
            if (responseCode == 201) {
                JSONObject response = apiConnector.getJsonResponse(conn);
                String docNum = response.getString("DocNum");
                System.out.println("🎉 Success! GRPO created: " + docNum);
                redirectAttributes.addFlashAttribute("message",
                        "Goods Receipt posted successfully! SAP GRPO #: " + docNum);

                // Log full response for debugging
                System.out.println("📄 Full SAP Response: " + response.toString());
            } else {
                String error = apiConnector.getErrorResponse(conn);
                System.out.println("❌ SAP Error (HTTP " + responseCode + "): " + error);

                // Additional debug info
                System.out.println("🔧 Request Details:");
                System.out.println("URL: " + conn.getURL());
                System.out.println("Headers: " + conn.getRequestProperties());

                redirectAttributes.addFlashAttribute("message",
                        "SAP Error: " + error);
                return "redirect:camelotDeliveryDashboard.htm";
            }

        } catch (JSONException e) {
            System.out.println("❌ JSON Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "Invalid JSON payload: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("❌ IO Error: " + e.getMessage());
            System.out.println("🛠️ StackTrace: " + Arrays.toString(e.getStackTrace()));
            redirectAttributes.addFlashAttribute("message",
                    "SAP API connection failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected Error: " + e.getMessage());
            System.out.println("🛠️ StackTrace: " + Arrays.toString(e.getStackTrace()));
            redirectAttributes.addFlashAttribute("message",
                    "Unexpected error: " + e.getMessage());
        }

        System.out.println("🏁 Method completed.");
        return "redirect:camelotDeliveryDashboard.htm";
    }

// Helper method to parse key-value pairs from comma-separated strings
    private Map<String, String> decodeDeliveredItemsData(String data) {
        Map<String, String> result = new LinkedHashMap<>();
        if (data == null || data.isEmpty()) {
            return result;
        }

        String[] pairs = data.split(",");
        for (String pair : pairs) {
            if (!pair.isEmpty()) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    result.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return result;
    }
}
