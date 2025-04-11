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
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Parse the received items data
            LinkedHashMap<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
            LinkedHashMap<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);

            // 2. Prepare SAP API request
            SapCamelotApiConnector apiConnector = new SapCamelotApiConnector();
            String endpoint = "/InventoryGenEntries"; // SAP endpoint for goods receipt
            HttpURLConnection connection = apiConnector.createConnection(endpoint, "POST");

            // 3. Build the JSON payload for SAP
            JSONObject payload = new JSONObject();
            payload.put("Comments", "Delivery checkup for PO: " + invoiceNumber);
            payload.put("DocDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            JSONArray documentLines = new JSONArray();
            for (Map.Entry<String, String> entry : deliveredItems.entrySet()) {
                JSONObject line = new JSONObject();
                line.put("ItemCode", entry.getKey());
                line.put("Quantity", Double.parseDouble(entry.getValue()));
                line.put("WarehouseCode", "01"); // Main warehouse
                documentLines.put(line);
            }
            payload.put("DocumentLines", documentLines);

            // 4. Execute SAP API call
            apiConnector.sendRequestBody(connection, payload.toString());

            // 5. Handle API response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) { // 201
                JSONObject response = apiConnector.getJsonResponse(connection);
                String sapDocNum = response.getString("DocNum");

                // Store success message
                redirectAttributes.addFlashAttribute("successMessage",
                        "Successfully created SAP Goods Receipt #" + sapDocNum);

                // Log the successful transaction
                Logger.getLogger(getClass().getName()).info(
                        "Created SAP delivery for PO " + invoiceNumber
                        + ", SAP Doc: " + sapDocNum);
            } else {
                String error = apiConnector.getErrorResponse(connection);
                redirectAttributes.addFlashAttribute("errorMessage",
                        "SAP Error: " + error);
            }

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "System Error: " + ex.getMessage());
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
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
